package org.krost.os.jvm.mvn.plugins.gitlog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.krost.os.jvm.mvn.plugins.gitlog.filters.CommitFilter;
import org.krost.os.jvm.mvn.plugins.gitlog.renderers.ChangeLogRenderer;

class Generator {

	private final List<ChangeLogRenderer> renderers;
	private RevWalk walk;
	private Map<String, List<RevTag>> commitIDToTagsMap;
	private final List<CommitFilter> commitFilters;
	private final Log log;

	public Generator(List<ChangeLogRenderer> renderers, List<CommitFilter> commitFilters, Log log) {
		this.renderers = renderers;
		this.commitFilters = (commitFilters == null) ? new ArrayList<>() : commitFilters;
		this.log = log;
	}

	public Repository openRepository() throws IOException, NoGitRepositoryException {
		return openRepository(null);
	}

	public Repository openRepository(File gitdir) throws IOException, NoGitRepositoryException {
		log.debug("About to open git repository.");
		Repository repository;
		try {
			if (gitdir == null) {
				repository = new RepositoryBuilder().findGitDir().build();
			} else {
				repository = new RepositoryBuilder().findGitDir(gitdir).build();
			}
		} catch (IllegalArgumentException iae) {
			throw new NoGitRepositoryException();
		}
		log.debug("Opened " + repository + ". About to load the commits.");
		walk = createWalk(repository);
		log.debug("Loaded commits. about to load the tags.");
		commitIDToTagsMap = createCommitIDToTagsMap(repository, walk);
		log.debug("Loaded tag map: " + commitIDToTagsMap);

		return repository;
	}

	public void generate(String reportTitle) throws IOException {
		generate(reportTitle, new Date(0l), "");
	}

	public void generate(String reportTitle, Date includeCommitsAfter, String includeCommitsAfterCommit)
			throws IOException {
		for (ChangeLogRenderer renderer : renderers) {
			renderer.renderHeader(reportTitle);
		}

		long dateInSecondsSinceEpoch = includeCommitsAfter.getTime() / 1000;
		for (RevCommit commit : walk) {
			int commitTimeInSecondsSinceEpoch = commit.getCommitTime();
			if (isSameCommit(commit.toString(), includeCommitsAfterCommit)) {
				break;
			}
			if (dateInSecondsSinceEpoch < commitTimeInSecondsSinceEpoch) {
				List<RevTag> revTags = commitIDToTagsMap.get(commit.name());
				for (ChangeLogRenderer renderer : renderers) {
					if (revTags != null) {
						for (RevTag revTag : revTags) {
							renderer.renderTag(revTag);
						}
					}
				}
				if (show(commit)) {
					for (ChangeLogRenderer renderer : renderers) {
						renderer.renderCommit(commit);
					}
				}
			}
		}
		walk.dispose();

		for (ChangeLogRenderer renderer : renderers) {
			renderer.renderFooter();
			renderer.close();
		}
	}

	private boolean isSameCommit(String commitName, String commitId) {
		if (commitName != null && commitId != null && !commitName.isEmpty() && !commitId.isEmpty()) {
			// Get commit id from commit name
			return commitName.split(" ")[1].contains(commitId);
		}
		return false;
	}

	private boolean show(RevCommit commit) {
		for (CommitFilter commitFilter : commitFilters) {
			if (!commitFilter.renderCommit(commit)) {
				log.debug("Commit filtered out by " + commitFilter.getClass().getSimpleName());
				return false;
			}
		}
		return true;
	}

	private static RevWalk createWalk(Repository repository) throws IOException {
		RevWalk walk = new RevWalk(repository);
		ObjectId head = repository.resolve("HEAD");
		if (head != null) {
			// if head is null, it means there are no commits in the repository. The walk will be empty.
			RevCommit mostRecentCommit = walk.parseCommit(head);
			walk.markStart(mostRecentCommit);
		}
		return walk;
	}

	private Map<String, List<RevTag>> createCommitIDToTagsMap(Repository repository, RevWalk revWalk)
			throws IOException {
		Map<String, Ref> allTags = repository.getTags();

		Map<String, List<RevTag>> revTags = new HashMap<>();

		for (Ref ref : allTags.values()) {
			try {
				RevTag revTag = revWalk.parseTag(ref.getObjectId());
				String commitID = revTag.getObject().getId().getName();
				if (!revTags.containsKey(commitID)) {
					revTags.put(commitID, new ArrayList<>());
				}
				revTags.get(commitID).add(revTag);
			} catch (IncorrectObjectTypeException e) {
				log.debug("Light-weight tags not supported. Skipping " + ref.getName());
			}
		}

		return revTags;
	}

}

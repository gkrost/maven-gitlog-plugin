package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

/**
 * A filter that excludes commits made by specific committers.
 * 
 * This filter checks the committer's name against a list of specified committers. If the committer's name matches any
 * in the list, the commit will not be rendered.
 */
public class CommiterFilter implements CommitFilter {

	private final List<String> commiters;

	public CommiterFilter(List<String> commiters) {
		this.commiters = commiters;
	}

	@Override
	public boolean renderCommit(RevCommit commit) {
		for (String commiter : commiters) {
			if (commiter.equalsIgnoreCase(commit.getCommitterIdent().getName())) {
				return false;
			}
		}
		return true;
	}
}
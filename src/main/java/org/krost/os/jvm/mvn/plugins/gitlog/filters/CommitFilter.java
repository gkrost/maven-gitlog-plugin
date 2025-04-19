package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import org.eclipse.jgit.revwalk.RevCommit;

public interface CommitFilter {

	/**
	 * Filter to exclude commits that are related to the JGitFlow plugin.
	 * 
	 * @param commit
	 * @return
	 */
	boolean renderCommit(RevCommit commit);

}

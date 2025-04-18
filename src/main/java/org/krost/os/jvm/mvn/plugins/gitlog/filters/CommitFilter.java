package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import org.eclipse.jgit.revwalk.RevCommit;

public interface CommitFilter {

	/**
	 * Determines whether the commit should be rendered or not.
	 *
	 * @param commit The commit to check.
	 * @return true if the commit should be rendered, false otherwise.
	 */
	boolean renderCommit(RevCommit commit);

}

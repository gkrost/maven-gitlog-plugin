package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Filters out commits with duplicate commit messages.
 * <p>
 * This filter checks if the commit message of the current commit is the same as the previous one. If they are the same,
 * the commit will not be rendered.
 */
public class DuplicateCommitMessageFilter implements CommitFilter {

	private RevCommit previous;

	@Override
	public boolean renderCommit(RevCommit commit) {
		boolean isDuplicate = previous != null
				&& messagesEquivalent(commit.getShortMessage(), previous.getShortMessage());
		previous = commit;
		return !isDuplicate;
	}

	private boolean messagesEquivalent(String message1, String message2) {
		message1 = "" + message1.trim().toLowerCase();
		message2 = "" + message2.trim().toLowerCase();
		return message1.equals(message2);
	}
}

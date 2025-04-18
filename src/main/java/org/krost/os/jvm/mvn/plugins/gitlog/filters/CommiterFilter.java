package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

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
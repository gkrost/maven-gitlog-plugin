package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.revwalk.RevCommit;

public class RegexpFilter implements CommitFilter {

	private Pattern pattern;

	public RegexpFilter(String excludeCommitsRegexp) {
		this.pattern = Pattern.compile(excludeCommitsRegexp);
	}

	@Override
	public boolean renderCommit(RevCommit commit) {
		Matcher matcher = pattern.matcher(commit.getShortMessage());
		return !matcher.matches();
	}
}

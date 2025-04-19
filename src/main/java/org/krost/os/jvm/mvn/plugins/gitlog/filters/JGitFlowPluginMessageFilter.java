package org.krost.os.jvm.mvn.plugins.gitlog.filters;

import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Filter to exclude commits that are related to the JGitFlow plugin.
 */
public class JGitFlowPluginMessageFilter implements CommitFilter {
	@Override
	public boolean renderCommit(RevCommit commit) {
		boolean isMavenRelease = commit.getShortMessage().startsWith("jgf:");
		return !isMavenRelease;
	}
}

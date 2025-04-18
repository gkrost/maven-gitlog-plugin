package org.krost.os.jvm.mvn.plugins.gitlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.krost.os.jvm.mvn.plugins.gitlog.filters.CommitFilter;
import org.krost.os.jvm.mvn.plugins.gitlog.filters.DuplicateCommitMessageFilter;
import org.krost.os.jvm.mvn.plugins.gitlog.filters.JGitFlowPluginMessageFilter;
import org.krost.os.jvm.mvn.plugins.gitlog.filters.MavenReleasePluginMessageFilter;

class Defaults {
	public static final List<CommitFilter> DEFAULT_COMMIT_FILTERS = Arrays.asList(new MavenReleasePluginMessageFilter(),
			// new MergeCommitFilter(),
			new JGitFlowPluginMessageFilter(), new DuplicateCommitMessageFilter());

	public static final List<CommitFilter> COMMIT_FILTERS;

	static {
		COMMIT_FILTERS = new ArrayList<>();
		COMMIT_FILTERS.addAll(DEFAULT_COMMIT_FILTERS);

		Iterator<CommitFilter> it = ServiceLoader.load(CommitFilter.class).iterator();
		while (it.hasNext()) {
			COMMIT_FILTERS.add(it.next());
		}
	}
}

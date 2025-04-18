package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

public interface MessageConverter {

	public String formatCommitMessage(String original);

}

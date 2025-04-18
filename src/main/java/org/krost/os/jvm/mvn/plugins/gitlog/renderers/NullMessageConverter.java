package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

public class NullMessageConverter implements MessageConverter {
	@Override
	public String formatCommitMessage(String original) {
		return original;
	}
}

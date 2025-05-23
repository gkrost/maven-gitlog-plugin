package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

import static org.krost.os.jvm.mvn.plugins.gitlog.renderers.Formatter.NEW_LINE;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.logging.Log;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;

public class PlainTextRenderer extends FileRenderer {

	private boolean previousWasTag = false;
	private final boolean fullGitMessage;

	public PlainTextRenderer(Log log, File targetFolder, String filename, boolean fullGitMessage) throws IOException {
		super(log, targetFolder, filename, false);
		this.fullGitMessage = fullGitMessage;
	}

	@Override
	public void renderHeader(String reportTitle) throws IOException {
		if (reportTitle != null && reportTitle.length() > 0) {
			writer.write(reportTitle);
			writer.write(NEW_LINE);
			writer.write(NEW_LINE);
		}
	}

	@Override
	public void renderTag(RevTag tag) throws IOException {
		if (!previousWasTag) {
			writer.write(NEW_LINE);
		}
		writer.write(tag.getTagName());
		writer.write(NEW_LINE);
		previousWasTag = true;
	}

	@Override
	public void renderCommit(RevCommit commit) throws IOException {
		String message = null;
		if (fullGitMessage) {
			message = commit.getFullMessage();
		} else {
			message = commit.getShortMessage();
		}
		writer.write(Formatter.formatDateTime(commit.getCommitTime()) + "    " + message);
		writer.write(" " + Formatter.formatCommiter(commit.getCommitterIdent()));
		writer.write(NEW_LINE);
		previousWasTag = false;
	}

	@Override
	public void renderFooter() throws IOException {
	}

}

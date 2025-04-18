package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

import java.io.IOException;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;

public class MavenLoggerRenderer implements ChangeLogRenderer {

	private final Log log;
	private boolean previousWasTag = false;

	public MavenLoggerRenderer(Log log) {
		if (log == null) {
			log = new SystemStreamLog();
		}
		this.log = log;
	}

	@Override
	public void renderHeader(String reportTitle) throws IOException {
		log.info("*********************************************");
		log.info(reportTitle);
		log.info("*********************************************");
	}

	@Override
	public void renderTag(RevTag tag) throws IOException {
		if (!previousWasTag) {
			log.info("");
		}
		log.info(tag.getTagName());
		previousWasTag = true;
	}

	@Override
	public void renderCommit(RevCommit commit) throws IOException {
		log.info(Formatter.formatDateTime(commit.getCommitTime()) + " " + commit.getShortMessage() + " "
				+ Formatter.formatCommiter(commit.getCommitterIdent()));
		previousWasTag = false;
	}

	@Override
	public void renderFooter() throws IOException {
		log.info("");
		log.info("*********************************************");
	}

	@Override
	public void close() {
	}
}

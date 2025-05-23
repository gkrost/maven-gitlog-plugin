package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

import static org.krost.os.jvm.mvn.plugins.gitlog.renderers.Formatter.NEW_LINE;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.logging.Log;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;

/**
 * AsciidocReleaseNotesRenderer is a .md renderer for generating release notes in Asciidoc format. 
 * [http://asciidoctor.org/docs/]
 */
public class AsciidocReleaseNotesRenderer extends FileRenderer {

	private boolean previousWasTag = false;
	private final boolean fullGitMessage;
	protected final MessageConverter messageConverter;
	private AsciidocLinkConverter asciidocLinkConverter;
	private String asciidocHeading; // `=`
	private boolean asciidocTableView;
	private String asciidocTableViewHeader1; // Date
	private String asciidocTableViewHeader2; // Commit

	public AsciidocReleaseNotesRenderer(Log log, File targetFolder, String filename, boolean fullGitMessage,
			MessageConverter messageConverter, String asciidocHeading, boolean asciidocTableView,
			String asciidocTableViewHeader1, String asciidocTableViewHeader2) throws IOException {
		super(log, targetFolder, filename, false);
		this.fullGitMessage = fullGitMessage;
		this.messageConverter = messageConverter;
		this.asciidocHeading = ((asciidocHeading == null) ? "=" : asciidocHeading);
		this.asciidocTableView = asciidocTableView;
		this.asciidocTableViewHeader1 = ((asciidocTableViewHeader1 == null) ? "Date" : asciidocTableViewHeader1);
		this.asciidocTableViewHeader2 = ((asciidocTableViewHeader2 == null) ? "Commit" : asciidocTableViewHeader2);
		asciidocLinkConverter = new AsciidocLinkConverter(log);
	}

	@Override
	public void renderHeader(String reportTitle) throws IOException {
		if (reportTitle != null && reportTitle.length() > 0) {
			writer.write(this.asciidocHeading + " "); // MD Heading 1
			writer.write(reportTitle);
			writer.write(NEW_LINE);
			if (asciidocTableView) {
				renderTableHeader();
			}
		}
	}

	@Override
	public void renderTag(RevTag tag) throws IOException {
		if (!previousWasTag) {
			writer.write(NEW_LINE);
		} else {
			if (asciidocTableView) {
				renderTableFooter();
				writer.write(asciidocHeading + "= ");
				writer.write(tag.getTagName());
				renderTableHeader();
			} else {
				writer.write("*"); // MD start bold
				writer.write(tag.getTagName());
				writer.write("*"); // MD end bold
				writer.write(" +"); // MD line warp
				writer.write(NEW_LINE);
			}
		}
		previousWasTag = true;
	}

	@Override
	public void renderCommit(RevCommit commit) throws IOException {
		String message = null;
		// use the message formatter to get a HTML hyperlink
		if (fullGitMessage) {
			message = messageConverter.formatCommitMessage(commit.getFullMessage());
		} else {
			message = messageConverter.formatCommitMessage(commit.getShortMessage());
		}
		// now convert the HTML hyperlink into an Asciidoc link
		message = asciidocLinkConverter.formatCommitMessage(message);

		if (asciidocTableView) {
			renderTableCommit(commit, message);
		} else {
			writer.write(Formatter.formatDateTime(commit.getCommitTime()) + "     " + message);
			writer.write(" " + Formatter.formatCommiter(commit.getCommitterIdent()));
			writer.write(" +"); // MD line warp
			writer.write(NEW_LINE);
		}
		previousWasTag = false;
	}

	private void renderTableCommit(RevCommit commit, String message) throws IOException {
		if (asciidocTableView) {
			writer.write(NEW_LINE);
			writer.write("|");
			writer.write(Formatter.formatDateTime(commit.getCommitTime()) + " | " + message);
			writer.write(" (" + commit.getCommitterIdent().getName() + ")");
			writer.write(NEW_LINE);
		}
	}

	public void renderTableHeader() throws IOException {
		if (asciidocTableView) {
			writer.write(NEW_LINE);
			writer.write("|===");
			writer.write(NEW_LINE);
			writer.write("|" + asciidocTableViewHeader1 + " | " + asciidocTableViewHeader2);
			writer.write(NEW_LINE);
		}
	}

	public void renderTableFooter() throws IOException {
		if (asciidocTableView) {
			writer.write(NEW_LINE);
			writer.write("|===");
			writer.write(NEW_LINE);
		}
	}

	@Override
	public void renderFooter() throws IOException {
		if (asciidocTableView) {
			renderTableFooter();
		}
	}
}

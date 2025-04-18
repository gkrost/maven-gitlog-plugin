package org.krost.os.jvm.mvn.plugins.gitlog.renderers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;
import org.krost.os.jvm.mvn.plugins.gitlog.renderers.Formatter;

public class FormatterTest {
	
	@Test
	public void formatterShouldUseNewFormat() {
		Formatter.setFormat("yyyy-MM-dd", new SystemStreamLog());
		Calendar calendar = Calendar.getInstance();
		calendar.set(1999, 10, 11);
		
		assertThat(Formatter.formatDateTime(calendar.getTime()), is("1999-11-11"));
	}
	
	@Test
	public void nullFormatShouldNotThrowException() {
		Formatter.setFormat(null, new SystemStreamLog());
	}
	
	@Test
	public void invalidFormatShouldNotThrowException() {
		Formatter.setFormat("xxx", new SystemStreamLog());
	}
}

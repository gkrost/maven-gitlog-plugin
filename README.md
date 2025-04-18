[![Build and test](https://github.com/gkrost/maven-gitlog-plugin/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/gkrost/maven-gitlog-plugin/actions/workflows/ci.yaml) [![Build and test](https://github.com/gkrost/maven-gitlog-plugin/actions/workflows/ci.yaml/badge.svg?branch=develop)](https://github.com/gkrost/maven-gitlog-plugin/actions/workflows/ci.yaml)

Overview
========

This plugin allows the creation of text and HTML changelogs based on the git log. During the Maven packaging
phase this plugin can generate plaintext and HTML reports showing all the commits (with tags) from the local git
repository.  These text files can then be sent to a web server or included during packaging.

Using as a reporting plugin (with maven 3.1+), the site generation will include the generated gitlog.
In this case, the outputDirectory parameter can not be set.

Note: when using together with the reporting plugin named *changelog*, it is advised to change
the *simpleHTMLChangeLogFilename* parameter to *gitlog.html*.

Usage instructions and Documentation
====================================

See the **[Maven Gitlog Plugin documentation](http://danielflower.github.io/maven-gitlog-plugin/)** for usage and more information.


Merge Log Only
===============

For release notes with only merged branches.

```$xslt
                 <configuration>
                 
                        <fullGitMessage>false</fullGitMessage>
                        <mergeCommitFilter>false</mergeCommitFilter>
                        <excludeCommitsPattern>^(?!Merge branch.*).*$</excludeCommitsPattern>
                        
                    </configuration>
```


AsciiDoc Table View and special title hierarchy 
===============================================

For release notes with only merged branches.

```$xslt
                 <configuration>
                 
                        <asciidocTableView>true</asciidocTableView>
                        <asciidocTableViewHeader1>Date</asciidocTableViewHeader1>
                        <asciidocTableViewHeader2>Merge</asciidocTableViewHeader2>
                        
                    </configuration>
```

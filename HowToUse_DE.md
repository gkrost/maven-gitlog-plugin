maven-gitlog-pluginEin Maven-Plugin zur Generierung von Changelogs basierend auf den Commits in einem Git-Repository. Das Plugin kann verschiedene Ausgabeformate (Plain Text, HTML, Markdown, Asciidoc, JSON) erzeugen und ist nützlich, um Release Notes oder Projektchroniken zu erstellen.Für wen ist dieses Plugin?Dieses Plugin ist für Java-Entwickler gedacht, die Maven verwenden und automatisch Changelogs als Teil ihres Build-Prozesses generieren möchten. Es ist nützlich für Projekte jeder Größe und Erfahrungsstufe, vom Junior-Entwickler, der schnell einen Überblick über Änderungen benötigt, bis zum Senior-Entwickler, der komplexe Build-Pipelines konfiguriert.Schnellstart: Plugin einbindenUm das gitlog-maven-plugin in Ihrem Maven-Projekt zu verwenden, fügen Sie den folgenden <plugin>-Abschnitt zum <build>-Abschnitt Ihrer pom.xml hinzu:<build>
    <plugins>
        <plugin>
            <groupId>com.github.danielflower.mavenplugins</groupId>
            <artifactId>gitlog-maven-plugin</artifactId>
            <version>AKTUELLE_VERSION</version> <executions>
                <execution>
                    <id>generate-gitlog</id>
                    <phase>generate-sources</phase> <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
Hinweis: Ersetzen Sie AKTUELLE_VERSION durch die neueste verfügbare Version des Plugins. Sie finden die neueste Version auf Maven Central.Standardmäßig generiert das Plugin einen einfachen Text-Changelog im target-Verzeichnis Ihres Projekts.Konfiguration des PluginsDas Plugin bietet verschiedene Konfigurationsmöglichkeiten, um die Ausgabe anzupassen. Hier sind einige gängige Optionen innerhalb des <configuration>-Abschnitts im <execution>-Block:<configuration>
    <repositoryDirectory>${project.basedir}</repositoryDirectory>

    <outputFile>${project.build.directory}/changelog.txt</outputFile>

    <outputType>plaintext</outputType>

    </configuration>
Eine vollständige Liste der Konfigurationsoptionen finden Sie in der Plugin-Dokumentation (sobald verfügbar) oder durch Überprüfung des Quellcodes.Beispiele für verschiedene AusgabeformateDas Plugin unterstützt verschiedene Ausgabeformate, die über den <outputType>-Parameter gesteuert werden. Hier sind Beispiele für einige der verfügbaren Renderer:Plain Text Ausgabe (outputType>plaintext)Dies ist das Standardformat. Es erzeugt eine einfache Textdatei mit den Commit-Nachrichten.<configuration>
    <outputFile>${project.build.directory}/changelog.txt</outputFile>
    <outputType>plaintext</outputType>
</configuration>
Simple HTML Ausgabe (outputType>simplehtml)Generiert einen einfachen HTML-Changelog.<configuration>
    <outputFile>${project.build.directory}/changelog.html</outputFile>
    <outputType>simplehtml</outputType>
    <issueManagement>
        <system>GitHub</system>
        <url>https://github.com/IhrBenutzername/IhrRepository/issues</url>
    </issueManagement>
</configuration>
Markdown Ausgabe (outputType>markdown)Erzeugt einen Changelog im Markdown-Format, der sich gut für die Verwendung in README.md-Dateien oder auf Plattformen wie GitHub eignet.<configuration>
    <outputFile>${project.build.directory}/CHANGELOG.md</outputFile>
    <outputType>markdown</outputType>
    <issueManagement>
        <system>Jira</system>
        <url>https://ihre-jira-instanz.com/browse</url>
    </issueManagement>
</configuration>
Asciidoc Ausgabe (outputType>asciidoc)Generiert einen Changelog im Asciidoc-Format.<configuration>
    <outputFile>${project.build.directory}/CHANGELOG.adoc</outputFile>
    <outputType>asciidoc</outputType>
     <issueManagement>
        <system>Bugzilla</system>
        <url>https://ihre-bugzilla-instanz.com/show_bug.cgi?id=</url>
    </issueManagement>
</configuration>
JSON Ausgabe (outputType>json)Erzeugt einen Changelog im JSON-Format, nützlich für die maschinelle Weiterverarbeitung.<configuration>
    <outputFile>${project.build.directory}/changelog.json</outputFile>
    <outputType>json</outputType>
</configuration>
Weitere mögliche Renderer (basierend auf den Klassennamen, erfordern möglicherweise spezifische Konfiguration):asciidocReleaseNoteschangelog (könnte ein spezifisches Format sein)mavenlogger (gibt den Changelog in der Maven-Konsole aus)Für diese Formate müssen Sie möglicherweise die genauen outputType-Werte und erforderlichen Konfigurationen in der Plugin-Dokumentation oder im Quellcode überprüfen.Plugin ausführenNachdem Sie das Plugin in Ihre pom.xml eingebunden haben, wird es automatisch während der konfigurierten Maven-Phase ausgeführt (im obigen Beispiel generate-sources).Sie können das Plugin auch direkt über die Kommandozeile aufrufen:mvn com.github.danielflower.mavenplugins:gitlog-maven-plugin:generate
Dies führt das generate-Goal des Plugins mit der Standardkonfiguration aus.MitwirkenWenn Sie zur Entwicklung dieses Plugins beitragen möchten, sind Pull Requests auf GitHub willkommen. Bitte beachten Sie die CONTRIBUTING.md (falls vorhanden) für Details.LizenzDieses Plugin steht unter der MIT License.Dies ist eine automatisch generierte README.md. Bitte überprüfen und passen Sie die Inhalte, insbesondere die Versionsnummer und spezifische Konfigurationsdetails, an Ihr Projekt an.


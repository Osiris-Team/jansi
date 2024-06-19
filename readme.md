# ![](http://fusesource.github.io/jansi/images/project-logo.png)

Fork of Jansi which allows Ansi to Html conversion. Quick example:
```java
String ansiAsHtml = new UtilsAnsiHtml().convertAnsiToHtml(ansi);
```
Also contains additional fixes for:
 - JVM crashes due to systemInstall() when running javaw (no terminal), [read more here](https://github.com/fusesource/jansi/issues/216).

## Installation
- Java 8 or higher required.
- [Click here for maven/gradle/sbt/leinigen instructions.](https://jitpack.io/#Osiris-Team/jansi)
- Make sure to watch this repository to get notified of future updates.

## Contribute & Build
- All contributions are welcome, but create an issue first before making major changes.
- Execute `mvn clean install -DskipTests -Dgpg.skip` in the project root to create a runnable jar which is located in `/target`.
Note that on most IDEs you simply need to create a maven execution profile and insert only the arguments above (stuff without `mvn`).
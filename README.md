# codenarc-maven-plugin

The main objective is to run codenarc easily from maven

## Instructions
1. Clone this repository
2. Run ``mvn clean install``
3. Paste the following configuration in your ``<plugins>`` section.

```xml
<plugin>
    <groupId>com.github</groupId>
    <artifactId>codenarc-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>check-code</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <parameters>
            <parameter>
                <basedir>${project.basedir}/src/main/</basedir>
                <includes>**/*.groovy</includes>
                <rules>
                    <rule>
                        <name>basic</name>
                    </rule>
                    <rule>
                        <name>naming</name>
                    </rule>
                    <rule>
                        <name>design</name>
                    </rule>
                    <rule>
                        <name>unused</name>
                    </rule>
                </rules>
                <maxPriority1>0</maxPriority1>
                <maxPriority2>100</maxPriority2>
                <maxPriority3>800</maxPriority3>
                <report>${project.build.directory}/codenarc/report.html</report>
            </parameter>
        </parameters>
    </configuration>
</plugin>
```
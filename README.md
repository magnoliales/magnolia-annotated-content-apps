magnolia-annotated-app-builder
==============================

Magnolia annotated app builder


For the compilation add the following repositories to your a profile in `settings.xml`
```xml
<settings>
    ...
    <profiles>
        <profile>
            <id>magnolia</id>
            <repositories>
                <repository>
                    <id>magnolia.public.releases</id>
                    <url>https://nexus.magnolia-cms.com/content/repositories/magnolia.public.releases</url>
                </repository>
                <repository>
                    <id>thirdparty</id>
                    <url>https://nexus.magnolia-cms.com/content/repositories/thirdparty</url>
                </repository>
                <repository>
                    <id>thirdparty.customized</id>
                    <url>https://nexus.magnolia-cms.com/content/repositories/thirdparty.customized</url>
                </repository>
                <repository>
                    <id>vaadin-addons</id>
                    <url>http://maven.vaadin.com/vaadin-addons</url>
                </repository>
                <repository>
                    <id>maven.central</id>
                    <url>http://repo1.maven.org/maven2</url>
                </repository>
            </repositories>
        </profile>
    </profiles>
    ...
</settings>
```


todo
====

- ~~Automatically add apps into the annotated-content-apps module and to the ui-admincentral app launcher~~ ( completed using AnnotatedContentAppInstallTask )
- ~~Automatically scan packages for annotated content apps to remove the requirement of separate AppDescriptor classes~~ ( decided against auto scanning, and using install tasks instead )
- Simplify AppDescriptor and UI.App annotations ( * in progress * )
- Integrate apps with a ContentConnector to pull remote content, eg Twitter
# Log Client - Java
This is used to instrument Java code with log messages for debugging purposes. See [logserver](https://github.com/drognisep/logserver) for more details.

## Maven Build Installation
For a Maven build, run the Gradle wrapper in this project appropriate to your environment with the `install` task to put the jar in your `~/.m2/repository` folder.
```bash
./gradlew install
```

Then you can reference the installed jar directly in your pom.
```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>local</groupId>
        <artifactId>logclient</artifactId>
        <version>1.0</version>
    </dependency>
    <!-- ... -->
</dependencies>
```

## Gradle Build Installation
For Gradle, the same install step can be done.
```bash
./gradlew install
```

Then ensure that `mavenLocal()` is included in your repositories and add the log client to your dependencies.
```groovy
/* ... */

repositories {
    /* ... */
    // Add this LAST so the rest of your dependencies will be resolved as you expect.
    mavenLocal()
}

/* ... */

dependencies {
    /* ... */
    implementation 'local:logclient:1.0'
    /* ... */
}

/* ... */
```

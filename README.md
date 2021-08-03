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

## LogClient Initialization
A `LogClient` is initialized with a host, port number, and serviceName.
* **host**: This identifies the host name on which the logserver is listening.
* **port**: The default port is 6839, but this can be changed.
* **serviceName**: This is a user provided unique identifier for the logging source, and is the key for LogClient instance caching. Uniqueness isn't enforced, but is certainly recommended.

## Methods of Operation
There are two different ways that the client can be used, depending on how much you want to disturb the code.

### Static logging
The easiest way to use this is to use the static `LogClient` methods. However, it's best if the client instance is initialized early in the application, as late initialization can lead to significant overhead.

By default, any time a `LogClient` instance is created, it is added to the cache so follow on static calls can work efficiently.
```java
public class Driver {
    public static void main(String[] args) {
        // This will initialize a LogClient instance and add it to the cache.
        LogClient client = new LogClient("some service name");

        /* ... some time later ... */

        // This will use the same instance from earlier.
        LogClient.logInfo("some service name", "An informative message about what's going on.");
    }
}
```

### Create an Instance
Of course, `LogClient` is just a Java class, so it can be instantiated and used directly.
```java
public class SomeJavaClass {
    private LogClient client = new LogClient("SomeJavaClass");
    
    /* ... */
    
    public void doSomeOperation() {
        this.client.info("In doSomeOperation");
        /* ... */
        try {
            /* ... */
        } catch(Exception ex) {
            this.client.error("Uh-oh! An exception happened! " + ex.getMessage());
        }
    }
}
```

package local.logclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

public class LogClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 6839;
    private final Sender sender;
    private final String serviceName;

    public LogClient(String serviceName, String remoteAddress, int port) {
        this.serviceName = serviceName;
        this.sender = new Sender(remoteAddress, port);
        if(!logCache.containsKey(serviceName)) {
            logCache.put(serviceName, this);
        }
    }

    public LogClient(String serviceName, String remoteAddress) {
        this(serviceName, remoteAddress, DEFAULT_PORT);
    }

    public LogClient(String serviceName) {
        this(serviceName, DEFAULT_HOST, DEFAULT_PORT);
    }

    public void info(String message) {
        LogEntry entry = new LogEntry(serviceName, "INFO", message);
        System.out.println(entry);
        send(entry);
    }

    public void error(String message) {
        LogEntry entry = new LogEntry(serviceName, "ERROR", message);
        System.err.println(entry);
        send(entry);
    }

    private void send(LogEntry entry) {
        try {
            this.sender.send(entry.toJson());
        } catch(IOException iox) {
            System.err.printf("Failed to write remotely: %s%n", iox.getMessage());
            iox.printStackTrace(System.err);
        }
    }

    private static final Map<String, LogClient> logCache = new ConcurrentHashMap<>();

    public static void logInfo(String serviceName, String message) {
        LogClient client = logCache.get(serviceName);
        if(client == null) {
            client = new LogClient(serviceName);
            logCache.put(serviceName, client);
        }
        client.info(message);
    }

    public static void logError(String serviceName, String message) {
        LogClient client = logCache.get(serviceName);
        if(client == null) {
            client = new LogClient(serviceName);
            logCache.put(serviceName, client);
        }
        client.error(message);
    }
}

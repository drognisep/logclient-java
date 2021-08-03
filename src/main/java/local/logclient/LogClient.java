package local.logclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public void error(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println(t.getMessage());
        t.printStackTrace(pw);
        error(sw.toString());
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
        getClient(serviceName).info(message);
    }

    public static void logError(String serviceName, String message) {
        getClient(serviceName).error(message);
    }

    public static void logError(String serviceName, Throwable exception) {
        getClient(serviceName).error(exception);
    }

    private static LogClient getClient(String serviceName) {
        LogClient client = logCache.get(serviceName);
        if (client == null) {
            synchronized (logCache) {
                client = logCache.get(serviceName);
                if(client == null) {
                    client = new LogClient(serviceName);
                    logCache.put(serviceName, client);
                }
            }
        }
        return client;
    }
}

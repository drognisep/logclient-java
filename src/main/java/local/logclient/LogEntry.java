package local.logclient;

import static java.lang.String.format;

public class LogEntry {
    private String serviceName;
    private String level;
    private String message;

    public LogEntry() {}

    public LogEntry(String serviceName, String level, String message) {
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message.trim().replace("\"", "\\\"").replace("\r\n", "\n").replace("\n", "\\n");
        this.message = message;
    }

    public String toJson() {
        return format("{\"serviceName\":\"%s\",\"level\":\"%s\",\"message\":\"%s\"}", serviceName, level, message);
    }

    public String toString() {
        return format("[%s] %s: %s", serviceName, level, message);
    }
}

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
        this.message = message;
    }

    public String toJson() {
        String msg = message.trim().replace("\"", "\\\"").replace("\r\n", "\n").replace("\r", "\n").replace("\n", "\\n").replace("\t", "\\t");
        return format("{\"serviceName\":\"%s\",\"level\":\"%s\",\"message\":\"%s\"}", serviceName, level, msg);
    }

    public String toString() {
        return format("[%s] %s: %s", serviceName, level, message);
    }
}

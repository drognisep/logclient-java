package local.logclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    private final String serverAddress;
    private final int port;
    private final ExecutorService executor;
    private Socket socket;
    private PrintWriter out;

    public Sender(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.executor = Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("RemoteLoggerThread");
            t.setUncaughtExceptionHandler((t1, e) -> {
                System.out.printf("Thread %s threw exception: %s%n", t1.getName(), e.getMessage());
                e.printStackTrace(System.out);
            });

            return t;
        });
    }

    public void connect() throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.out.print("{\"major\":1}\n");
        this.out.flush();
    }

    public void send(String message) throws IOException {
        if(this.socket == null) {
            this.connect();
        }
        this.executor.execute(() -> {
            out.print(message.trim() + "\n");
            out.flush();
        });
    }
}

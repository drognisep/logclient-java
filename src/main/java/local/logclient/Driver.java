package local.logclient;

public class Driver {
    public static void main(String[] args) {
        LogClient log = new LogClient("Java Test");
        log.info("Hello log from Java!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}

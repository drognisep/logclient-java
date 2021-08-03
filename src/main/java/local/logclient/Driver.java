package local.logclient;

public class Driver {
    public static void main(String[] args) {
        LogClient log = new LogClient("Java Test");
        log.info("Hello log from Java!");
        try {
            throw new Exception("Testing exception handling");
        } catch(Exception ex) {
            log.error(ex);
        }
        log.info("And I said,\n\t\"That's neat!\"");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}

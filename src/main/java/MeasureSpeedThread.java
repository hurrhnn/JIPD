import oshi.hardware.NetworkIF;

public class MeasureSpeedThread extends Thread {

    private final NetworkIF networkIF;

    public MeasureSpeedThread(NetworkIF networkIF) {
        this.networkIF = networkIF;
    }

    public void run() {
        try {
            while (true) {
                long d1 = networkIF.getBytesSent();
                long t1 = networkIF.getTimeStamp();
                Thread.sleep(1000);
                networkIF.updateAttributes();
                long d2 = networkIF.getBytesSent();
                long t2 = networkIF.getTimeStamp();

                AfterAttackThread.sendingSpeed = ((double)((d2 - d1) / (t2 - t1)) / 100);
            }
        } catch (Exception ignored) { }
    }
}

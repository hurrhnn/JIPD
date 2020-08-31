import oshi.hardware.NetworkIF;

public class AfterAttackThread extends Thread {

    final private int connCount;
    final private int sendThread;
    final private NetworkIF networkIF;

    public static double sendingSpeed = 0;

    public AfterAttackThread(int connCount, int sendThread, NetworkIF networkIF) {
        this.networkIF = networkIF;
        this.connCount = connCount;
        this.sendThread = sendThread;
    }

    public void run() {
        try {
            Thread interruptThread = new InterruptThread();
            interruptThread.start();

            Thread measureSpeedThread = new MeasureSpeedThread(networkIF);
            measureSpeedThread.start();

            StringBuilder space = new StringBuilder();
            for(int i = 0; i < 100; i++) space.append(" ");

            while (true)
            {
                System.out.print(space.toString() + "\r");
                System.out.print("Connection: " + OnReady.i + "/" + connCount + ", SenderThread: " + OnReady.j + "/" + sendThread + ", " + (sendingSpeed == 0 ? "Measuring..." : String.format("%.1f", sendingSpeed) +"Mbps") + "\r");
                Thread.sleep(150);
            }
        }catch (Exception ignored) { }
    }
}

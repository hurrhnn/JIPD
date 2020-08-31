import java.util.Scanner;

public class InterruptThread extends Thread {
    public void run() {
        try {
            while (true) if(new Scanner(System.in).nextLine().equalsIgnoreCase("STOP")) System.exit(0);
        }catch (Exception ignored) {}
    }
}

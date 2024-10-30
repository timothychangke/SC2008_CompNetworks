import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Rfc865UdpServer {

    static int QOTD_PORT = 17;
    static String QUOTE = "Common sense is the collection of prejudices acquired by age eighteen - Albert Einstein.";

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(QOTD_PORT);
            System.out.println("UDP Server listening on port " + QOTD_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            while (true) {
                byte[] buf = new byte[512];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                System.out.println("Waiting for request...");

                socket.receive(request);
                String requestContent = new String(buf);
                System.out.println("Received request: " + requestContent);

                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                System.out.println("From client: " + clientAddress.getCanonicalHostName());


                String replyContent = QUOTE;
                byte[] replyBuf = replyContent.getBytes("UTF-8");
                System.out.println("Reply content: " + replyContent);

                DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length, clientAddress, clientPort);
                System.out.println("Sending reply...");
                socket.send(reply);
                System.out.println("Reply sent");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
// imports required for UDP/IP
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class EchoClientUDP {
    public static void main(String args[]) {
        // define a Datagram (UDP style) socket
        DatagramSocket aSocket = null;
        try {
            // show the announcement
            System.out.println("The client is running");

            // build an InetAddress object from a DNS name
            InetAddress aHost = InetAddress.getByName("localhost");

            // client sends a message to port 6789
            int serverPort = 6789;

            // define the user's input
            String nextLine;

            // initialize the socket
            aSocket = new DatagramSocket();

            // read user's input
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));

            // loop to receive the input from client
            while ((nextLine = typed.readLine()) != null) {
                // build packet contents by input string
                byte[] m = nextLine.getBytes();

                // build the packet holding the destination address, port and byte array to contact the server
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);

                // send the Datagram on the socket
                aSocket.send(request);

                // prepare room for the reply
                byte[] buffer = new byte[1000];

                // build a datagram for the reply
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                // block and wait
                aSocket.receive(reply);

                // get bytes arrays from the reply with correct length
                byte[] reply_bytes = Arrays.copyOf(reply.getData(), reply.getLength());

                // modify the request data with the correct number of bytes
                reply.setData(reply_bytes);

                // show the result to the client
                System.out.println("Reply: " + new String(reply.getData()));

                //  after hearing the "halt!" message from the server, the client makes an announcement and then exits
                if (nextLine.equals("halt!")) {
                    System.out.println("Client side quitting");
                    break;
                }
            }
            // handle socket exceptions
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            // handle general I/O exceptions
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            // always close the socket
            if (aSocket != null) aSocket.close();
        }
    }
}
// imports required for UDP/IP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class EchoServerUDP {
    public static void main(String args[]) {
        // define a Datagram (UDP style) socket
        DatagramSocket aSocket = null;
        try {
            // show the announcement
            System.out.println("The server is running");

            // initialize the socket using 6789 port number
            aSocket = new DatagramSocket(6789);

            // loop forever
            while (true) {

                // prepare room for the request
                byte[] buffer = new byte[1000];

                // initialize the request
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);

                // receive request data from the socket
                aSocket.receive(request);

                // get bytes arrays from the request with correct length
                byte[] request_bytes = Arrays.copyOf(request.getData(), request.getLength());

                // modify the request data with the correct number of bytes
                request.setData(request_bytes);

                // build the reply to client
                DatagramPacket reply = new DatagramPacket(request.getData(),
                        request.getLength(), request.getAddress(), request.getPort());

                // change request to string format
                String requestString = new String(request.getData());

                // show the request string
                System.out.println("Echoing: " + requestString);

                // send reply data to socket
                aSocket.send(reply);

                //  after hearing the "halt!" message from the client, the server makes an announcement and then exits
                if (new String(request.getData()).equals("halt!")) {
                    System.out.println("Server side quitting");
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

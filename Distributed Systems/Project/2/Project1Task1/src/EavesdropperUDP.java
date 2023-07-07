// imports required for UDP/IP

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class EavesdropperUDP {
    public static void main(String args[]) {
        // define a Datagram (UDP style) socket to communicate with client
        DatagramSocket aSocket = null;

        // define another Datagram socket to communicate with server
        DatagramSocket malSocket = null;
        try {
            // malicious player listens on 6798
            aSocket = new DatagramSocket(6798);

            // initialize another socket to connect with server
            malSocket = new DatagramSocket();

            // build an InetAddress object from a DNS name
            InetAddress aHost = InetAddress.getByName("localhost");

            // loop forever
            while (true) {

                // prepare room for the request
                byte[] requestBuffer = new byte[1024];

                // initialize the request from the client
                DatagramPacket request = new DatagramPacket(requestBuffer, requestBuffer.length);

                // receive request data from the client
                aSocket.receive(request);

                // get bytes arrays from the request with correct length
                byte[] request_bytes = Arrays.copyOf(request.getData(), request.getLength());

                // modify the request data with the correct number of bytes
                request.setData(request_bytes);

                // display the client's messages through eavesdropping
                System.out.println("Eavesdrop from client: " + new String(request.getData()));

                // malicious player contact the server at 6789
                DatagramPacket malRequest = new DatagramPacket(request.getData(), request.getData().length, aHost, 6789);

                // malicious player forwards the message to Echo serve
                malSocket.send(malRequest);

                // prepare room for the reply from server
                byte[] replyBuffer = new byte[1000];

                // build a datagram for the reply
                DatagramPacket malReply = new DatagramPacket(replyBuffer, replyBuffer.length);

                // block and wait
                malSocket.receive(malReply);

                // get bytes arrays from the reply with correct length
                byte[] malReply_bytes = Arrays.copyOf(malReply.getData(), malReply.getLength());

                // modify the malReply data with the correct number of bytes
                malReply.setData(malReply_bytes);

                // display the server's messages through eavesdropping
                System.out.println("Eavesdrop from server: " + new String(malReply.getData()));

                // build the reply to client
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());

                // send server's reply data to the client
                aSocket.send(reply);
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

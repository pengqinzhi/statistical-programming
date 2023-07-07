// imports required for UDP/IP

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class AddingClientUDP {
    // create global variables for socket, server port, and destination address
    static DatagramSocket aSocket = null;
    static int serverPort = 0;
    static InetAddress aHost = null;

    public static void main(String args[]) {
        // define a Datagram (UDP style) socket

        try {
            // show the announcement
            System.out.println("The client is running.");

            aHost = InetAddress.getByName("localhost");

            // define the user's input
            String nextLine;

            // read user's input
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));

            // ask user to input a server port and initialize the server port
            System.out.println("Please enter server port:");
            serverPort = Integer.parseInt(typed.readLine());

            //  prompt the user enter numbers
            System.out.println("Please enter numbers:");

            // loop to receive the input from client
            while ((nextLine = typed.readLine()) != null) {
                //  the client makes an announcement and then exits when user enter halt!
                if (nextLine.equals("halt!")) {
                    System.out.println("Client side quitting");
                    break;
                }

                // convert string to int
                int i = Integer.parseInt(nextLine);

                // call the add function related to client server communications
                int sum = add(i);

                // show the result to the client
                System.out.printf("The server returned %d.\n", sum);
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

    /**
     * client communicates with the server
     *
     * @param i from the user's input
     * @return sum result from the server
     */
    public static int add(int i) {
        // define and initialize the sum result
        int sum = 0;
        try {
            // initialize the socket
            aSocket = new DatagramSocket();

            // build a ByteBuffer with capacity 4 to store the int and then return a byte array
            byte[] m = ByteBuffer.allocate(4).putInt(i).array();

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

            // wraps the reply byte array into a buffer and then return the int value
            sum = ByteBuffer.wrap(reply_bytes).getInt();

            // handle socket exceptions
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            // handle general I/O exceptions
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }

        return sum;
    }

}
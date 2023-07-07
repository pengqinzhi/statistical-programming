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

public class RemoteVariableClientUDP {
    // create global variables for socket, server port, and destination address
    static DatagramSocket aSocket = null;
    static int serverPort = 0;
    static InetAddress aHost = null;

    public static void main(String args[]) {
        try {
            // show the announcement
            System.out.println("The client is running.");

            aHost = InetAddress.getByName("localhost");


            // read user's input
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));

            // ask user to input a server port and initialize the server port
            System.out.println("Please enter server port:");
            serverPort = Integer.parseInt(typed.readLine());

            // loop to receive the input from client
            while (typed.readLine() != null) {
                String operation = null;
                String value = null;
                String ID;

                // show the choice to the client
                System.out.println("1. Add a value to your sum.");
                System.out.println("2. Subtract a value from your sum.");
                System.out.println("3. Get your sum.");
                System.out.println("4. Exit client");

                // initialize the operation
                switch (typed.readLine()) {
                    case "1" -> operation = "add";
                    case "2" -> operation = "subtract";
                    case "3" -> operation = "get";
                    case "4" -> operation = "exit";
                }

                // if input is 1 or 2, then set the value
                assert operation != null;
                if (operation.equals("add")) {
                    System.out.println("Enter value to add:");
                    value = typed.readLine();
                } else if (operation.equals("subtract")) {
                    System.out.println("Enter value to subtract:");
                    value = typed.readLine();
                    // if input is 4, then exit the client
                } else if (operation.equals("exit")) {
                    System.out.println("Client side quitting. The remote variable server is still running.");
                    break;
                }

                // get the client ID
                System.out.println("Enter your ID:");
                ID = typed.readLine();

                String combined;
                // combine the  ID, operation if operation is "get";
                // otherwise, combine the  ID, operation, and value
                if (operation.equals("get")) {
                    combined = ID + "," + operation;
                } else {
                    combined = ID + "," + operation + "," + value;
                }

                // call the operate function related to client server communications
                int result = operate(combined);

                // show the result to the client
                System.out.printf("The result is %d.\n", result);
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
     * @param combined information including ID, operation, and value(if the operation is other than get)
     * @return result from the server
     */
    public static int operate(String combined) {
        // define and initialize the result
        int result = 0;
        try {
            // initialize the socket
            aSocket = new DatagramSocket();

            // build a ByteBuffer with capacity 4 to store the int and then return a byte array
            byte[] m = combined.getBytes();

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
            result = ByteBuffer.wrap(reply_bytes).getInt();

            // handle socket exceptions
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            // handle general I/O exceptions
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }

        return result;
    }

}
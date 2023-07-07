// imports required for UDP/IP

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class RemoteVariableServerUDP {
    // create a global variable for socket
    static DatagramSocket aSocket = null;
    // create a result array with 1000 size
    static int[] result = new int[1000];

    public static void main(String args[]) {
        try {
            // show the announcement
            System.out.println("Server started");

            // prompt the user enter a port number for the server to listen to
            System.out.println("Please enter server port:");
            int serverPort = new Scanner(System.in).nextInt();

            // initialize the socket using port number by user's input
            aSocket = new DatagramSocket(serverPort);

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

                // get combined string from the request
                String combined = new String(request.getData());

                // split the combined information into a string array
                String[] combined_array = combined.split(",");
                int ID = Integer.parseInt(combined_array[0]);
                String operation = combined_array[1];
                System.out.println("ID: " + ID);

                //if operation is add or subtract, then do the operation;
                //if operation is get, then do nothing
                if (operation.equals("add")) {
                    int value = Integer.parseInt(combined_array[2]);
                    // performs the add operation to the result associated with the ID
                    result[ID] = add(ID, value);

                    // show the add process to the server
                    System.out.println("Adding: " + value + " to " + result[ID]);
                } else if (operation.equals("subtract")) {
                    int value = Integer.parseInt(combined_array[2]);
                    // performs the subtract operation to the result associated with the ID
                    result[ID] = subtract(ID, value);

                    // show the subtract process to the server
                    System.out.println("Subtracting: " + value + " to " + result[ID]);
                } else {
                    System.out.println("Getting: " + result[ID]);
                }

                // build a ByteBuffer with capacity 4 to store the result and then return a byte array
                byte[] sum_bytes = ByteBuffer.allocate(4).putInt(result[ID]).array();

                // build the reply to client
                DatagramPacket reply = new DatagramPacket(sum_bytes, sum_bytes.length, request.getAddress(), request.getPort());

                // show the result to the server
                System.out.printf("Returning result of %d to client\n", result[ID]);

                // send reply data to socket
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

    /**
     * performs the add or subtract operation.
     *
     * @param ID    for choosing the result array's index
     * @param value for add
     * @return result after operating
     */
    public static int add(int ID, int value) {
        result[ID] += value;
        return result[ID];
    }

    /**
     * performs the add or subtract operation.
     *
     * @param ID    for choosing the result array's index
     * @param value for subtract
     * @return result after operating
     */
    public static int subtract(int ID, int value) {
        result[ID] -= value;
        return result[ID];
    }

}

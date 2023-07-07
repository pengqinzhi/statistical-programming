// imports required for UDP/IP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class AddingServerUDP {
    // create a global variable for socket and sum
    static DatagramSocket aSocket = null;
    static int sum = 0;

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

                // get add int from the request
                int add_num = ByteBuffer.wrap(request_bytes).getInt();

                // show the add process to the server
                System.out.println("Adding: " + add_num + " to " + sum);

                // performs the add operation
                sum = add(add_num);

                // build a ByteBuffer with capacity 4 to store the sum and then return a byte array
                byte[] sum_bytes = ByteBuffer.allocate(4).putInt(sum).array();

                // build the reply to client
                DatagramPacket reply = new DatagramPacket(sum_bytes, sum_bytes.length, request.getAddress(), request.getPort());

                // show the result to the server
                System.out.printf("Returning sum of %d to client\n", sum);

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
     * performs the add operation.
     *
     * @param i from the request
     * @return sum result
     */
    public static int add(int i) {
        sum += i;
        return sum;
    }
}

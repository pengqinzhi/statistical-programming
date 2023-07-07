import java.net.*;
import java.io.*;
import java.util.Scanner;

public class RemoteVariableServerTCP {
    // create a result array with 1000 size
    static int[] result = new int[1000];

    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            // show the announcement
            System.out.println("Server started");

            // prompt the user enter a port number for the server to listen to
            System.out.println("Please enter server port:");
            int serverPort = new Scanner(System.in).nextInt();

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.

            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());

            // Set up "out" to write to the client socket
            PrintWriter out;
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            // loop forever to accept the request and return the result to the client
            while (true) {
                if (in.hasNextLine()) {
                    // read the data from the client
                    String data = in.nextLine();

                    // split the combined information into a string array
                    String[] combined_array = data.split(",");
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

                    // show the result to the server
                    System.out.printf("Returning result of %d to client\n", result[ID]);

                    // send message to the client
                    out.println(result[ID]);
                    out.flush();
                } else {
                    // continue to accept requests after previous connection is closed
                    clientSocket = listenSocket.accept();
                    in = new Scanner(clientSocket.getInputStream());
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                }
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
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
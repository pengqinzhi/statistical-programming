import java.net.*;
import java.io.*;

public class RemoteVariableClientTCP {
    // create global variables for read from and write to the socket
    static Socket clientSocket;
    static BufferedReader in = null;
    static PrintWriter out = null;

    public static void main(String args[]) {
        try {
            // show the announcement
            System.out.println("The client is running.");

            InetAddress aHost = InetAddress.getByName("localhost");

            // read user's input
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));

            // ask user to input a server port and initialize the server port
            System.out.println("Please enter server port:");
            int serverPort = Integer.parseInt(typed.readLine());

            // initialize the socket
            clientSocket = new Socket(aHost, serverPort);

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
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
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
     * client communicates with the server
     *
     * @param data information including ID, operation, and value(if the operation is other than get)
     * @return result from the server
     */
    public static int operate(String data) {
        // define and initialize the result
        int result = 0;
        try {
            // initialize the read and write for socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            // send message to the server
            out.println(data);
            out.flush();

            // read a line of data from the stream
            result = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
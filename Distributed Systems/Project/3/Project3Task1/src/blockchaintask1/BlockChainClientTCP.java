package blockchaintask1;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Scanner;

public class BlockChainClientTCP {
    // create global variables for read from and write to the socket
    static Socket clientSocket;
    static BufferedReader in = null;
    static PrintWriter out = null;

    public static void main(String args[]) {
        try {
            InetAddress aHost = InetAddress.getByName("localhost");

            // read client's input
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));

            // set a server port
            int serverPort = 7777;

            // initialize the socket
            clientSocket = new Socket(aHost, serverPort);

            // Set the menu, and it will continuously provide the client with seven options.
            // loop to receive the input from client
            while (true) {
                System.out.println("0. View basic blockchain status.");
                System.out.println("1. Add a transaction to the blockchain.");
                System.out.println("2. Verify the blockchain.");
                System.out.println("3. View the blockchain.");
                System.out.println("4. corrupt the chain.");
                System.out.println("5. Hide the corruption by repairing the chain.");
                System.out.println("6. Exit");

                // Initialize the selection, difficulty, and data for the RequestMessage object
                int selection = Integer.parseInt(typed.readLine());
                int difficulty = 0;
                int blockID = 0;
                String data = "";

                // If the client selects option 0, the program will display:
                // The number of blocks on the chain
                // Difficulty of most recent block.
                // The total difficulty for all blocks Approximate hashes per second on this machine.
                // Expected total hashes required for the whole chain.
                // The computed nonce for most recent block.
                // The chain hash (hash of the most recent block).
                if (selection == 0) {
                    // Use RequestMessage to encapsulate message, and then communicate with the server
                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    // Use responseMessage and gson to parse the jsonString from the server
                    Gson g = new Gson();
                    ResponseMessage responseMessage = g.fromJson(jsonString, ResponseMessage.class);

                    // Displays the result
                    System.out.println("Current size of chain: " + responseMessage.size);
                    System.out.println("Difficulty of most recent block: " + responseMessage.diff);
                    System.out.println("Total difficulty for all blocks: " + responseMessage.totalDiff);
                    System.out.println("Approximate hashes per second on this machine: " + responseMessage.hps);
                    System.out.println("Expected total hashes required for the whole chain: " + responseMessage.totalHashes);
                    System.out.println("Nonce for most recent block: " + responseMessage.recentNonce);
                    System.out.println("Chain hash: " + responseMessage.chainHash);
                }

                // If the client selects option 1, the client will add a transaction to the blockchain.
                if (selection == 1) {
                    // the program will prompt for and then read the difficulty level for this bloc
                    System.out.println("Enter difficulty (> 0):");
                    difficulty = Integer.parseInt(typed.readLine());

                    // It will then prompt for and then read a line of data from the client (representing a transaction).
                    System.out.println("Enter transaction:");
                    data = typed.readLine();

                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    Gson g = new Gson();
                    ResponseMessage responseMessage = g.fromJson(jsonString, ResponseMessage.class);

                    // Display the time it took to add this block.
                    System.out.printf("Total execution time to add this block was %d milliseconds\n", responseMessage.executionTime);
                }

                // If the client selects option 2, then call the isChainValid method and display the results.
                if (selection == 2) {
                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    Gson g = new Gson();
                    ResponseMessage responseMessage = g.fromJson(jsonString, ResponseMessage.class);

                    if (!responseMessage.isChainValid.equals("TRUE")) {
                        System.out.println("Chain verification: FALSE");
                        System.out.println(responseMessage.isChainValid);
                    } else {
                        System.out.println("Chain verification: TRUE");
                    }

                    // Display the number of milliseconds it took for validation.
                    System.out.printf("Total execution time to verify the chain was %d milliseconds\n", responseMessage.executionTime);
                }

                // If the client selects option 3, then display the entire Blockchain contents as a correctly formed JSON document.
                if (selection == 3) {
                    System.out.println("View the Blockchain");

                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    System.out.print(jsonString);
                }

                // If the client selects option 4, then corrupt the chain.
                if (selection == 4) {
                    System.out.println("Corrupt the Blockchain");

                    // Ask for the block index
                    System.out.println("Enter block ID of block to corrupt:");
                    blockID = Integer.parseInt(typed.readLine());

                    // Ask for the new data that will be placed in the block.
                    System.out.println("Enter new data for block " + blockID);
                    data = typed.readLine();

                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    System.out.printf("Block %d now holds %s\n", blockID, data);
                }

                // If the client selects 5, then repair the chain.
                if (selection == 5) {
                    RequestMessage requestMessage = new RequestMessage(selection, difficulty, blockID, data);
                    String jsonString = operate(requestMessage.toString());

                    Gson g = new Gson();
                    ResponseMessage responseMessage = g.fromJson(jsonString, ResponseMessage.class);

                    // Display the number of milliseconds it took for repairing the chain.
                    System.out.printf("Total execution time required to repair the chain was %d milliseconds\n", responseMessage.executionTime);
                }

                // If the client selects 6, then exit the program.
                if (selection == 6) {
                    System.exit(0);
                }
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
     * @param jsonString information including selection, difficulty
     * @return the response from the server using JSON messages type
     */
    public static String operate(String jsonString) {
        // define and initialize the response
        StringBuilder response = new StringBuilder();
        try {
            // initialize the read and write for socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            // send message to the server
            out.println(jsonString);
            out.flush();

            // read each line of data from the stream
            String line = in.readLine();
            while (! line.equals("end")) {
                response.append(line).append("\n");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
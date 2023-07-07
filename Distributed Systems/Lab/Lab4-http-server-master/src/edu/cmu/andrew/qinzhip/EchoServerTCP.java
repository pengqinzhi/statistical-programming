package edu.cmu.andrew.qinzhip;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class EchoServerTCP {

    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            int serverPort = 7777; // the server port we are using

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

            // Set up "inFromSocket" to read from the client socket
            Scanner inFromSocket;
            inFromSocket = new Scanner(clientSocket.getInputStream());

            // Set up "outToSocket" to write to the client socket
            PrintWriter outToSocket;
            outToSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            /*
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */

            boolean flag = true;
            String fileString = "";
            while (true) {
                if (inFromSocket.hasNextLine()) {
                    String data = inFromSocket.nextLine();
                    System.out.println("Echoing: " + data);
                    if (data.contains("GET")) {
                        fileString = data.substring(data.indexOf(' ') + 2, data.lastIndexOf(' '));
                        System.out.println(fileString);
                    }
                }

                if (flag) {
                    File file = new File(fileString);
                    if (file.exists()) {
                        String currentLine;
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        outToSocket.println("HTTP/1.1 200 OK\n\n");

                        while ((currentLine = reader.readLine()) != null) {
                            outToSocket.println(currentLine);
                        }
                    } else {
                        outToSocket.println("HTTP/1.1 404 NOT FOUND\n\nFile Not Found");
                    }
                    outToSocket.flush();
                    flag = false;
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
}
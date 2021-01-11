package nl.barkat.server;

import java.net.*;
import java.io.*;

public class KnockKnockServer {

	public static void main(String[] args) throws IOException {

		// If no port number given
		if (args.length != 1) {
			System.err.println("Usage: Java KnockKnockSever <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			
			PrintWriter output = new PrintWriter(
					clientSocket.getOutputStream(), true);
			
			BufferedReader input = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			
            String inputLine, outputLine;
            
			// Initiate conversation with client
			KnockKnockProtocol kkp = new KnockKnockProtocol();
			inputLine = kkp.processInput(null);
			output.println(inputLine);

			// Communicate with client by reading and writing to socket
			 while ((inputLine = input.readLine()) != null) {
	                outputLine = kkp.processInput(inputLine);
	                output.println(outputLine);
	                if (outputLine.equals("Bye."))
	                    break;
	            }
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	    }
	}
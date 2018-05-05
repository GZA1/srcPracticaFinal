/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prácticafinalmtpa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple socket server
 * 
 */
public class ServidorMultihilo {

	private ServerSocket serverSocket;
	private int port;

	public ServidorMultihilo(int port) {
		this.port = port;
	}

	public void start() throws IOException, InterruptedException {

		serverSocket = new ServerSocket(port);
		System.out.println("Starting the socket server at port:" + port);

		Socket client = null;

		while (true) {
			System.out.println("Waiting for clients...");
			client = serverSocket.accept();
			System.out.println("The following client has connected:"
					+ client.getInetAddress().getCanonicalHostName());
			// A client has connected to this server. Send welcome message
			Thread thread = new Thread(new SocketClientHandler(client));
			thread.start();
		}
	}

}
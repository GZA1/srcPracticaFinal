/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prácticafinalmtpa;

import java.io.IOException;

class MyServer {

	/**
	 * Creates a SocketServer object and starts the server.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	
	public static void main(String[] args) throws InterruptedException {
		//int portNumber = Integer.parseInt(args[0]);

		int portNumber = 80;
		try {
			// initializing the Socket Server
			ServidorMultihilo socketServer = new ServidorMultihilo(
					portNumber);
			socketServer.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

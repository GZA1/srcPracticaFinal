/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prácticafinalmtpa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.

public class SocketClientHandler implements Runnable {

	private Socket client;
        
	public SocketClientHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:"+Thread.currentThread().getName());
			trabaja();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
        
        private void trabaja()throws IOException, InterruptedException{
            
            BufferedReader peticion = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter respuesta = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            String temp = ".";
            String cabeceraPeticion = "";
            while(!temp.equals("")){
                temp = peticion.readLine();
                System.out.println(temp);
                cabeceraPeticion += temp + "\n";
                
            }
            
            StringBuilder sb = new StringBuilder();
            //String file;
            String file = cabeceraPeticion.split("\n")[0].split(" ")[1];
            if(!file.equals("/")){
                file = file.split("/")[1];
            }else{
                file="index.html";
            }
            System.out.println(file);
			/*String file = cabeceraPeticion.split("\n")[0].split(" ")[1]
					.split("/")[1];*/
            if(cabeceraPeticion.split("\n")[0].contains("GET")
					/*&& checkURL(file)*/){
               constructResponseHeader(200, sb);
				respuesta.write(sb.toString());
				respuesta.write(getData(file));
				sb.setLength(0);
				respuesta.flush(); 
            }else{
                constructResponseHeader(404, sb);
				respuesta.write(sb.toString());
				sb.setLength(0);
				respuesta.flush();
                
            }
            peticion.close();
            client.close();
        }
        private static void constructResponseHeader(int responseCode,
			StringBuilder sb) {

		if (responseCode == 200) {

			sb.append("HTTP/1.1 200 OK\r\n");
			sb.append("Date:" + getTimeStamp() + "\r\n");
			sb.append("Server:localhost\r\n");
			sb.append("Content-Type: text/html\r\n");
			sb.append("Connection: Closed\r\n");
                        sb.append("\r\n");
                } else if (responseCode == 404) {

			sb.append("HTTP/1.1 404 Not Found\r\n");
			sb.append("Date:" + getTimeStamp() + "\r\n");
			sb.append("Server:localhost\r\n");
			sb.append("\r\n");
		}
        }
        private static String getTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
        
        private static String getData(String file) {
//                if(file.equals("/")){
//                    file = "index.html";
//                }
		File myFile = new File(file);
		String responseToClient = "";
		BufferedReader reader;

		// System.out.println(myFile.getAbsolutePath());

		try {
			reader = new BufferedReader(new FileReader(myFile));
			String line = null;
			while (!(line = reader.readLine()).contains("</html>")) {
				responseToClient += line;
			}
			responseToClient += line;
			// System.out.println(responseToClient);
			reader.close();

		} catch (Exception e) {

		}
		return responseToClient;
	}
        
        static boolean checkURL(String file) {
		File myFile = new File(file);
		return myFile.exists() && !myFile.isDirectory();

	}
}
package com.example.toeclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	private Socket m_socket;
	private ObjectInputStream m_iStream;
	private ObjectOutputStream m_oStream;
	private int m_port;
	private String m_hostname;
	private GameActivity game;
	
	public Client(GameActivity activity, String hostname, int port) throws UnknownHostException, IOException{
		game = activity;
		m_port = port;
		m_hostname = hostname;
	}
	
	@Override
	public void run() {
		try {
			m_socket = new Socket(m_hostname, m_port);
			m_oStream = new ObjectOutputStream(m_socket.getOutputStream());
			m_iStream = new ObjectInputStream(m_socket.getInputStream());
			String message = (String)m_iStream.readObject();
			parseInitialMessage(message);
			while(true){			
				message = (String)m_iStream.readObject();
				//game.setMessage(message);			
			}		
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end of run

	private void parseInitialMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(message);
		String[] splitMsg = message.split("\\r?\\n");
		int gameState = Integer.parseInt(splitMsg[0].split(":")[1]);
		System.out.println(gameState);
		String piece = splitMsg[1].split(":")[1];
		System.out.println(piece);
		if(gameState == -1 && piece == "t"){
			game.enableButtons();
			game.setMessage("SET THE BOARD SIZE");
		}
		
	}

	public void sendMessage(String message) throws IOException {
		m_oStream.reset();
		m_oStream.writeObject(message);
	}

}
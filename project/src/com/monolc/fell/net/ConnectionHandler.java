package com.monolc.fell.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
	Thread t;
	ArrayList<Client> clients;
	ServerSocket socket;
	public ConnectionHandler(ServerSocket s) {
		t = null;
		clients = new ArrayList<Client>();
		socket = s;
	}
	public void start() {
		t = new Thread(this, "Server Connection Handler");
		t.start();
	}
	public void run() {
		boolean cont = true;
		while (cont) {
			try {
				clients.add(new Client(socket.accept()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

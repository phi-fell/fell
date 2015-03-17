package com.monolc.fell.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.monolc.fell.version.VersionData;
import com.monolc.fell.world.Floor;

public class ServerIO implements Runnable {
	Thread t;
	ServerSocket socket;
	ConnectionHandler connector;
	ArrayList<Client> clients;
	VersionData version;
	Floor floor;
	public ServerIO(VersionData v) {
		clients = new ArrayList<Client>();
		version = v;
		t = null;
	}
	public void start() {
		if (t == null) {
			t = new Thread(this, "Server I/O");
			t.start();
		}
	}
	public void run() {
		try {
			socket = new ServerSocket(53476);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connector = new ConnectionHandler(socket);
		connector.start();
		boolean cont = true;
		while (cont) {
			while (connector.clientsAvailable()) {
				synchronized (clients) {
					clients.add(connector.getClient());
					System.out.println("New client #" + (clients.size() - 1) + " : " + clients.get(clients.size() - 1).toString());
					clients.get(clients.size() - 1).getStatus().informOfVersion(version);
				}
			}
			synchronized (clients) {
				for (int i = 0; i < clients.size(); i++) {
					if (!clients.get(i).getStatus().hasRecievedInitialData() && clients.get(i).messageAvailable()) {
						clients.get(i).getStatus().setInitialState(clients.get(i).recieve());
					}
					if (clients.get(i).getStatus().hasRecievedInitialData() && !clients.get(i).getStatus().isValid()) {
						System.out.println("Client #" + i + " : " + clients.get(i).toString() + " was disconnected. Reason given: " + clients.get(i).getStatus().getValidityMessage());
						clients.get(i).delete();
						clients.remove(i);
						i--;
					} else if (!clients.get(i).connected()) {
						System.out.println("Client #" + i + " : " + clients.get(i).toString() + " disconnected: " + clients.get(i).getDisconnectMessage());
						clients.get(i).delete();
						clients.remove(i);
						i--;
					} else if (!clients.get(i).getStatus().hasMap()) {
						clients.get(i).send(floor.toString());
						clients.get(i).getStatus().setHasMap(true);
					}
				}
			}
		}
	}
	public void sendAll(String s) {
		synchronized (clients) {
			for (Client c : clients) {
				c.send(s);
			}
		}
	}
	public void setFloor(Floor f) {
		synchronized (this) {
			floor = f;
		}
	}
}

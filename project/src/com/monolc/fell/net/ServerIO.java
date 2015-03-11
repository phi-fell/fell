package com.monolc.fell.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerIO implements Runnable {
	Thread t;
	ServerSocket socket;
	ConnectionHandler connector;
	public ServerIO() {
		t = null;
	}
	public void start() {
		if (t == null) {
			t = new Thread(this, "Server I/O");
			t.start();
		}
	}
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			socket = new ServerSocket(53476);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connector = new ConnectionHandler(socket);
		String msg = null;
		while (msg == null) {
			try {
				msg = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		out.println("Client said \"" + msg + "\"");
	}
}

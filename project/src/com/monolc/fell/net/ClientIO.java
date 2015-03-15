package com.monolc.fell.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.monolc.fell.version.VersionData;

public class ClientIO {
	public ClientIO(VersionData v) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 53476);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(v.getVersion());
		try {
			System.out.println(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

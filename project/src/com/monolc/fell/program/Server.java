package com.monolc.fell.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.monolc.fell.net.ServerIO;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.VersionData;
import com.monolc.fell.world.Floor;

public class Server {
	VersionData version;
	public Server(VersionData v) {
		version = v;
	}
	public void run() {
		System.out.println("Initializing server...");
		ResourceHandler resources = new ResourceHandler();
		ServerIO sio = new ServerIO(version);
		sio.start();
		Floor currentFloor = new Floor(resources, 121, 101);
		sio.setFloor(currentFloor);
		// console IO
		String helpText = resources.load("res/serverCommands.txt");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server console active.  Type \"help\" or \"?\" for a list of commands.");
		while (true) {
			String line = null;
			try {
				line = console.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line.equalsIgnoreCase("help") || line.equalsIgnoreCase("?")) {
				System.out.println(helpText);
				if (!line.equals("help") && !line.equals("?")) {
					System.out.println("WARNING: All other commands are case-sensitive.");
				}
			} else if (line.equals("altf4")) {
				System.exit(1);
				return;
			} else if (line.equals("exit")) {
				sio.exitGracefully();
				System.out.println("The server has been stopped.");
				return;
			} else {
				System.out.println("Unrecognized input!");
			}
			System.out.println();
		}
		// end
	}
}

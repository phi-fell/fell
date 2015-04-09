package com.monolc.fell;

import com.monolc.fell.console.Console;
import com.monolc.fell.program.ClientProgram;
import com.monolc.fell.program.ConcurrentProgram;
import com.monolc.fell.program.ServerProgram;
import com.monolc.fell.version.VersionData;

public class Program {
	public static void main(String args[]) {
		System.out.println("Main method reached.");
		VersionData v = new VersionData();
		System.out.println("Fell " + v);
		ConcurrentProgram p = null;
		if (args.length < 1) {
			System.out.println("NOT ENOUGH ARGUMENTS! Needs argument \"client\" or \"server\"");
			System.exit(0);
		} else if (args[0].equals("client")) {
			if (args.length < 3) {
				System.out.println("NOT ENOUGH ARGUMENTS! Client requires an ip and port to connect to (in that order)");
				System.exit(0);
			}
			String ip = args[1];
			int port = Integer.parseInt(args[2]);
			if (args.length < 4 || !args[3].equals("nogui")) {
				System.setOut((new Console("Fell Client")).getAsPrintStream());
			}
			p = new ClientProgram(ip, port);
		} else if (args[0].equals("server")) {
			if (args.length < 2) {
				System.out.println("NOT ENOUGH ARGUMENTS! Server requires a port to host on");
				System.exit(0);
			}
			int port = Integer.parseInt(args[1]);
			if (args.length < 3 || !args[2].equals("nogui")) {
				System.setOut((new Console("Fell Server")).getAsPrintStream());
			}
			p = new ServerProgram(port);
		}
		p.start();
	}
}

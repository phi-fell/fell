package com.monolc.fell;

import com.monolc.fell.program.Client;
import com.monolc.fell.program.Server;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.VersionData;

public class Program {
	public static void main(String args[]) {
		System.out.println("Running...");
		VersionData v = new VersionData();
		System.out.println("Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild());
		if (args.length < 1) {
			System.out.println("NOT ENOUGH ARGUMENTS!");
		} else if (args[0].equals("client")) {
			Client c = new Client(v);
			c.run();
		} else if (args[0].equals("server")) {
			Server s = new Server(v);
			s.run();
		}
	}
}

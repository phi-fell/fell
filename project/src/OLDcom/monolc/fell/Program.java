package OLDcom.monolc.fell;

import OLDcom.monolc.fell.program.Client;
import OLDcom.monolc.fell.program.Server;
import OLDcom.monolc.fell.resources.ResourceHandler;
import OLDcom.monolc.fell.version.VersionData;

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

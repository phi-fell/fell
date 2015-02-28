package com.monolc.fell.version;

import java.util.Scanner;

public class VersionData {
	String version;
	String build;

	public VersionData() {
		Scanner versionScanner = new Scanner(VersionData.class.getResourceAsStream("version"));
		version = versionScanner.nextLine();
		versionScanner.close();
		Scanner buildScanner = new Scanner(VersionData.class.getResourceAsStream("buildnumber"));
		buildScanner.nextLine();
		buildScanner.nextLine();
		build = buildScanner.nextLine().replace("build.number=", "");
		buildScanner.close();
	}

	public String getVersion() {
		return version;
	}

	public String getBuild() {
		return build;
	}
}

package com.monolc.fell.version;

import java.util.Scanner;

public class VersionData {
	int maj, min, rev;
	String stage;
	int build;
	public VersionData() {
		try {
			Scanner versionScanner = new Scanner(VersionData.class.getResourceAsStream("version"));
			String version = versionScanner.nextLine();
			maj = Integer.parseInt(version.substring(0, version.indexOf('.')));
			version = version.substring(version.indexOf('.') + 1);
			min = Integer.parseInt(version.substring(0, version.indexOf('.')));
			version = version.substring(version.indexOf('.') + 1);
			rev = Integer.parseInt(version);
			stage = versionScanner.nextLine();
			versionScanner.close();
			Scanner buildScanner = new Scanner(VersionData.class.getResourceAsStream("buildnumber"));
			buildScanner.nextLine();
			buildScanner.nextLine();
			build = Integer.parseInt(buildScanner.nextLine().replace("build.number=", ""));
			buildScanner.close();
		} catch (Exception e) {
			System.out.println("Invalid Version File!");
			e.printStackTrace();
		}
	}
	public String toString() {
		return stage + " V" + maj + '.' + min + '.' + rev + " b" + build;
	}
	public int major() {
		return maj;
	}
	public int minor() {
		return min;
	}
	public int revision() {
		return rev;
	}
	public String getStage() {
		return stage;
	}
	public int getBuild() {
		return build;
	}
}

package com.monolc.fell.resources;

public class FMLFile {
	public FMLFile(String file) {
		file.replace("\n", " ");
		while(file.contains("  ")){
			file.replace("  ", " ");
		}
	}
}
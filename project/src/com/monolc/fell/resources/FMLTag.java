package com.monolc.fell.resources;

import java.util.ArrayList;

public class FMLTag {
	ArrayList<FMLTag> tags;
	String id;
	String value;
	public FMLTag(String data) {
		tags = new ArrayList<FMLTag>();
		addTags(getLines(formatTags(data)));
	}
	public FMLTag(String name, String val) {
		tags = new ArrayList<FMLTag>();
		id = name;
		value = val;
	}
	public FMLTag getTag(String name) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).id.equals(name)) {
				return tags.get(i);
			}
		}
		return null;
	}
	public String getValue() {
		return value;
	}
	public int getValueAsInt() {
		return Integer.parseInt(value);
	}
	public long getValueAsLong() {
		return Long.parseLong(value);
	}
	public float getValueAsFloat() {
		return Float.parseFloat(value);
	}
	public double getValueAsDouble() {
		return Double.parseDouble(value);
	}
	public String toString() {
		return toString(0);
	}
	public String toString(int indent) {
		String ret = "";
		for (int i = 0; i < indent; i++) {
			ret += "\t";
		}
		ret += id + ": " + value + "\n";
		for (int i = 0; i < tags.size(); i++) {
			ret += tags.get(i).toString(indent + 1);
		}
		return ret;
	}
	private void addTags(ArrayList<String> data) {
		while (data.size() > 0) {
			addTag(data);
		}
	}
	private void addTag(ArrayList<String> data) {
		int mindent = getIndent(data.get(0)); // minimum indent
		FMLTag current = getTagFromLine(data.remove(0));
		if (current == null) {
			System.out.println("Invalid FMLTag");
			return;
		}
		ArrayList<String> pass = new ArrayList<String>();
		while (data.size() > 0 && mindent < getIndent(data.get(0))) {
			pass.add(data.remove(0));
		}
		current.addTags(pass);
		tags.add(current);
	}
	private FMLTag getTagFromLine(String line) {
		if (line == null || !line.contains(":")) {
			return null;
		}
		String n = removeOuterWhitespace(line.substring(0, line.indexOf(":")));
		String v = removeOuterWhitespace(line.substring(line.indexOf(":") + 1));
		return new FMLTag(n, v);
	}
	private ArrayList<String> getLines(String data) {
		ArrayList<String> ret = new ArrayList<String>();
		while (data.contains("\n")) {
			ret.add(data.substring(0, data.indexOf("\n")));
			data = data.substring(data.indexOf("\n") + 1);
		}
		int invalid = 0;
		for (int i = 0; i < ret.size(); i++) {
			if (removeOuterWhitespace(ret.get(i)).length() == 0) {
				ret.remove(i);
				i--;
			} else if (!ret.get(i).contains(":")) {
				ret.remove(i);
				i--;
				invalid++;
			}
		}
		if (invalid > 0) {
			System.out.println("Removed " + invalid + " invalid Tag(s)");
		}
		return ret;
	}
	private int getIndent(String line) {
		int indent = 0;
		while (line.startsWith(" ")) {
			indent++;
			line = line.substring(1);
		}
		return indent;
	}
	private String formatTags(String data) {
		data = data.replace("\t", " ");
		return data + "\n";
	}
	private String removeOuterWhitespace(String s) {
		while (s.startsWith(" ")) {
			s = s.substring(1);
		}
		while (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
}

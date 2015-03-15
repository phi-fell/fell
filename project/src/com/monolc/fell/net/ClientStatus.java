package com.monolc.fell.net;

import com.monolc.fell.version.VersionData;

public class ClientStatus {
	boolean recievedHeader;
	boolean needsMap;
	String version;
	VersionData serverVersion;
	public ClientStatus() {
		serverVersion = null;
		recievedHeader = false;
		needsMap = true;
		version = "";
	}
	public void setInitialState(String header) {
		version = header;
	}
	public boolean hasRecievedInitialData() {
		return recievedHeader;
	}
	public boolean isValid() {
		return getValidityMessage() == null;
	}
	public String getValidityMessage() {
		if (!version.equals(serverVersion.getVersion())) {
			return "INCORRECT VERSION: \"" + version + "\" != \"" + serverVersion.getVersion() + "\"";
		}
		return null;
	}
	public void informOfVersion(VersionData v) {
		serverVersion = v;
	}
}

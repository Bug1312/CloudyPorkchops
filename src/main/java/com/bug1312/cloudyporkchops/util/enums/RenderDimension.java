package com.bug1312.cloudyporkchops.util.enums;

import javax.annotation.Nullable;

public enum RenderDimension {
	TWO(),
	THREE("3d"); 
	
	private String string;
	
	RenderDimension() {}
	
	RenderDimension(@Nullable String string) {
		this.string = string;
	}
	
	public String toString() {
		if(string != null) return "_" + string;
		else return "";
	}
}

package com.bug1312.javajson;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

public class ModelData {

	public String parent;
	public String texture;
	public String lightmap;
	public String alphamap;
	public boolean gen_alpha = false;
	public List<ModelRenderer> groups = new ArrayList<ModelRenderer>();
	public int texture_width;
	public int texture_height;
	public float scale = 1;
	public String font_data;
	
	static public ResourceLocation getTexture(String tex) {
		if (tex == null) return null;
		
		int colonPos = tex.indexOf(':') + 1;
		String modId = tex.substring(0, colonPos);
		String path = tex.substring(colonPos);
		return new ResourceLocation(modId + "textures/" + path + ".png");
	}
	
	public ResourceLocation getParent() {
		if(parent != null && parent.length() > 0) return new ResourceLocation(parent + ".json");
		else return null;
	}
	
	public static class FontData{
		private float x, y, z, scale;
		private String string;
		private float rot_x;
		private float rot_y;
		private float rot_z;
		
		public float getRotationX() {
			return rot_x;
		}
		public float getRotationY() {
			return rot_y;
		}
		public float getRotationZ() {
			return rot_z;
		}
		
		public float getScale() {
			return scale;
		}
		
		public String getString() {
			return string;
		}
		
		public double getX() {
			return x;
		}
		public double getY() {
			return y;
		}
		public double getZ() {
			return z;
		}
	}
	
}

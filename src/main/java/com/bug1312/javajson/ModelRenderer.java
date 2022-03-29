package com.bug1312.javajson;

import java.util.ArrayList;
import java.util.List;

public class ModelRenderer {

	public String group_name;
	public List<Float> rotation = new ArrayList<Float>();
//	public float rotateAngleY;
//	public float rotateAngleZ;
	private List<ModelRenderer> children = new ArrayList<ModelRenderer>();
	
	public List<Cube> cubes = new ArrayList<Cube>();
	
	private float[] pivot;
	
	
	public ModelRenderer(ModelData model) {
		// TODO Auto-generated constructor stub
	}
	
	public void setRotationPoint(float x, float y, float z) {
		this.pivot = new float[] {x, y, z};
	}
	
	public void addCube(int u, int v, float x, float y, float z, float w, float h, float d, float inflate, boolean mirror) {
		cubes.add(new Cube(u, v, x, y, z, w, h, d, inflate, mirror));
	}
	
	public void addCube(int u, int v, float x, float y, float z, float w, float h, float d, float inflate) {
		cubes.add(new Cube(u, v, x, y, z, w, h, d, inflate, false));
	}
	
	public void addCube(int u, int v, float x, float y, float z, float w, float h, float d) {
		cubes.add(new Cube(u, v, x, y, z, w, h, d, 0, false));
	}
	
	public void addChild(ModelRenderer e) {
		children.add(e);
	}
	
	public float[] getPivot() {
		return pivot;
	}
	
	public List<ModelRenderer> getChildren() {
		return children;
	}
	
	public List<Cube> getCubes() {
		return cubes;
	}
	
	public float getxRot() {
		return (float) Math.toRadians(rotation.get(0));
	}
	
	public float getyRot() {
		return (float) Math.toRadians(rotation.get(1));
	}
	
	public float getzRot() {
		return (float) Math.toRadians(rotation.get(2));
	}

}

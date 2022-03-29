package com.bug1312.javajson;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.vector.Vector3f;

public class ModelRendererWrapper extends net.minecraft.client.renderer.model.ModelRenderer{

	private Vector3f initialRotation;

	public ModelRendererWrapper(Model model) {
		super(model);
	}

	public ModelRendererWrapper(Model model, int texOffX, int texOffY) {
		super(model, texOffX, texOffY);
	}

	public ModelRendererWrapper(int textureWidthIn, int textureHeightIn, int textureOffsetXIn, int textureOffsetYIn) {
		super(textureWidthIn, textureHeightIn, textureOffsetXIn, textureOffsetYIn);
	}

	@Override
	public void setPos(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
		super.setPos(rotationPointXIn, rotationPointYIn, rotationPointZIn);
		this.initialRotation = new Vector3f(rotationPointXIn, rotationPointYIn, rotationPointZIn);
	}

	public Vector3f getInitialRotation() {
		return initialRotation;
	}
}

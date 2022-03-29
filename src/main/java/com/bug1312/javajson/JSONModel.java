package com.bug1312.javajson;

import java.io.IOException;
import java.io.InputStream;

import com.bug1312.javajson.ModelData.FontData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

public class JSONModel {

	private ResourceLocation model_path;
	private ModelInformation model;
	public boolean render_lightmap = true;

	public JSONModel(ResourceLocation path) {
		model_path = path;
	}

	public ModelInformation getModelData() {
		return model;
	}

	public void load() {
		model = ModelLoader.loadModelInfo(model_path);
		
		if (model != null && model.getModel() != null) model.getModel().model = this;
		else model = new ModelInformation(new ModelWrapper(0, 0), MissingTextureSprite.getLocation(), MissingTextureSprite.getLocation(), null);
	}

	public static class ModelInformation {
		private ModelWrapper model;
		private ResourceLocation texture;
		private ResourceLocation light_map;
		private ResourceLocation alpha_map;
		public boolean genAlphaMap = false;
		private FontData[] fontData;

		public ModelInformation(ModelWrapper m, ResourceLocation tex, ResourceLocation lightMap, String alpha_map) {
			this.model = m;
			this.texture = tex;
			this.light_map = lightMap;
			if (alpha_map != null && alpha_map.equals("generated")) {
				this.alpha_map = generateAlphaMap(tex);
				this.genAlphaMap = true;
			} else {
				this.alpha_map = ModelData.getTexture(alpha_map);
			}
		}
		
		public void setFontData(FontData[] fontData) {
			this.fontData = fontData;
		}
		
		public FontData[] getFontData() {
			return fontData;
		}

		public ModelWrapper getModel() {
			return model;
		}

		public ResourceLocation getTexture() {
			return texture != null ? texture : MissingTextureSprite.getLocation();
		}

		public ResourceLocation getLightMap() {
			return light_map;
		}

		public ResourceLocation getAlphaMap() {
			return alpha_map;
		}

		public static ResourceLocation generateAlphaMap(ResourceLocation tex) {
			try {
				InputStream a = Minecraft.getInstance().getResourceManager().getResource(tex).getInputStream();

				NativeImage image_a = NativeImage.read(a);
				NativeImage image = new NativeImage(image_a.getHeight(), image_a.getWidth(), false);
				image.copyFrom(image_a);
				for (int x = 0; x < image.getHeight(); x++) {
					for (int y = 0; y < image.getWidth(); y++) {
						int color = image_a.getPixelRGBA(x, y);
						if (NativeImage.getA(color) > 0) {
							image.setPixelRGBA(x, y, 0x02FFFFFF);
						}
					}
				}

				DynamicTexture texture = new DynamicTexture(image);

				ResourceLocation res = Minecraft.getInstance().getTextureManager().register("dalekmod/textures/" + tex.getPath().replaceAll(".png", "") + "_alphamap.png", texture);

				return res;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return tex;

		}
	}
}

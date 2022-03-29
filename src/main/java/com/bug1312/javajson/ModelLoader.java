package com.bug1312.javajson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.bug1312.javajson.JSONModel.ModelInformation;
import com.bug1312.javajson.ModelData.FontData;
import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ModelLoader {

	private static Gson GSON = new Gson();
	public static ModelRendererWrapper NULL_PART = new ModelRendererWrapper(new BlankModel());
	private static Map<ResourceLocation, JSONModel> cache = new HashMap<ResourceLocation, JSONModel>();

	public static JSONModel loadModel(ResourceLocation rl) {
		if (cache.containsKey(rl)) {
			return cache.get(rl);
		}

		JSONModel m = new JSONModel(rl);
		m.load();
		cache.put(rl, m);

		return m;
	}

	public static ModelInformation loadModelInfo(ResourceLocation rl) {
		try {
			InputStream stream = Minecraft.getInstance().getResourceManager().getResource(rl).getInputStream();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				StringBuilder b = new StringBuilder();
				String s = null;

				while ((s = reader.readLine()) != null) {
					b.append(s);
				}

				ModelData model = GSON.fromJson(b.toString(), ModelData.class);
				FontData[] data = null;

				if (model.getParent() != null) {
					ModelInformation m = new JSONModel.ModelInformation(loadModelInfo(model.getParent()).getModel(), ModelData.getTexture(model.texture), ModelData.getTexture(model.lightmap), model.alphamap);
					m.setFontData(data);
					return m;
				}

				ModelWrapper mod = new ModelWrapper(model.texture_width, model.texture_height);

				mod.modelScale = model.scale;

				for (ModelRenderer renderer : model.groups) {
					ModelRendererWrapper group = new ModelRendererWrapper(mod);
					group.setPos(renderer.getPivot()[0], renderer.getPivot()[1], renderer.getPivot()[2]);
					group.xRot = renderer.getxRot();
					group.yRot = -renderer.getyRot();
					group.zRot = renderer.getzRot();
					if (renderer.cubes != null)
						for (Cube cube : renderer.cubes) {
							group.texOffs(cube.getUv()[0], cube.getUv()[1]).addBox(cube.getOrigin()[0], cube.getOrigin()[1], cube.getOrigin()[2], cube.getSize()[0], cube.getSize()[1], cube.getSize()[2], cube.getInflate(), cube.isMirrored());
						}

					addChildren(mod, renderer, group);

					mod.renderList.add(group);
					mod.partsList.put(renderer.group_name, group);
				}
				ModelInformation m = new JSONModel.ModelInformation(mod, ModelData.getTexture(model.texture), ModelData.getTexture(model.lightmap), model.alphamap);
				m.setFontData(data);
				return m;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void addChildren(ModelWrapper wrapper, ModelRenderer mv2, ModelRendererWrapper model) {
		if (mv2.getChildren() != null && mv2.getChildren().size() > 0) {
			for (ModelRenderer m : mv2.getChildren()) {
				ModelRendererWrapper renderer = new ModelRendererWrapper(wrapper);
				renderer.setPos(m.getPivot()[0], m.getPivot()[1], m.getPivot()[2]);
				renderer.xRot = m.getxRot();
				renderer.yRot = m.getyRot();
				renderer.zRot = m.getzRot();

				if (m.cubes != null)
					for (Cube cube : m.cubes) {
						renderer.texOffs(cube.getUv()[0], cube.getUv()[1]).addBox(cube.getOrigin()[0], cube.getOrigin()[1], cube.getOrigin()[2], cube.getSize()[0], cube.getSize()[1], cube.getSize()[2], cube.getInflate(), cube.isMirrored());
					}

				addChildren(wrapper, m, renderer);

				model.addChild(renderer);
				wrapper.partsList.put(m.group_name, renderer);
			}
		}
	}

	public static Map<ResourceLocation, JSONModel> getCache() {
		return cache;
	}

}

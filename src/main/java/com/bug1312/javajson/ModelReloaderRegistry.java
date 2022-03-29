package com.bug1312.javajson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class ModelReloaderRegistry {

	private static List<IModelPartReloader> models = new ArrayList<>();

	public static void register(IModelPartReloader part) {
		models.add(part);
		part.reload();
	}
		
	public static class reloadJavaJSONModels {
		public static void PRE () { ModelLoader.getCache().clear(); };
		public static void POST() { 
			for (Map.Entry<ResourceLocation, JSONModel> entry : ModelLoader.getCache().entrySet()) entry.getValue().load();
			for (IModelPartReloader model : models) model.reload();
		}
	}

}

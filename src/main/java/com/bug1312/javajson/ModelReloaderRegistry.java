package com.bug1312.javajson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class ModelReloaderRegistry {

	private static List<IModelPartReloader> models = new ArrayList<>();
	private static List<ResourceLocation> unbakedCache = new ArrayList<>();

	public static void register(IModelPartReloader part) {
		models.add(part);
		part.reload();
	}
		
	public static class reloadJavaJSONModels {
		public static void PRE () { 
			System.out.println("Hey thingy " + ModelLoader.getCache());
//			for (Map.Entry<ResourceLocation, JSONModel> entry : ModelLoader.getCache().entrySet()) {
//				System.out.println("woah jauver-"+entry.getKey());
//				unbakedCache.add(entry.getKey());
//			}
//			
//			for (ResourceLocation location : unbakedCache) ModelLoader.getCache().remove(location);
			
//			ModelLoader.getCache().clear();
		};
		public static void POST() { 
			for (Map.Entry<ResourceLocation, JSONModel> entry : ModelLoader.getCache().entrySet()) {
				System.out.println("Reloading JavaJSON - " + entry.getKey());
				entry.getValue().load();
			}
			for (IModelPartReloader model : models) model.reload();
		}
	}

}

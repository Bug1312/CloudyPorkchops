package com.bug1312.javajson;

import java.util.function.Predicate;

import com.bug1312.cloudyporkchops.client.init.CloudyEntityRenders;
import com.bug1312.cloudyporkchops.client.init.CloudyTileRenders;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

public class ModelReloadListener implements ISelectiveResourceReloadListener {

	public static void init() {
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if (manager instanceof IReloadableResourceManager)
			((IReloadableResourceManager) manager).registerReloadListener(new ModelReloadListener());
	}
	
	// set this to an interface for pre-post stuff
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		ModelReloaderRegistry.reloadJavaJSONModels.PRE();
		CloudyTileRenders.init();
		CloudyEntityRenders.init();
		ModelReloaderRegistry.reloadJavaJSONModels.POST();
	}

}

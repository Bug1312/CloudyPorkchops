package com.bug1312.javajson;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.bug1312.cloudyporkchops.client.init.RenderTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

public class ModelReloadListener implements ISelectiveResourceReloadListener {

	protected static Map<TileEntityType<?>, ResourceLocation> tileEntitiesForReload = new HashMap<>();
	
	public static void init() {
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if (manager instanceof IReloadableResourceManager)
			((IReloadableResourceManager) manager).registerReloadListener(new ModelReloadListener());
	}
	
	protected static <T extends TileEntity> void bindTileEntityRenderer(TileEntityType<T> tileEntityType, ResourceLocation location) {
		JSONModel model = ModelLoader.loadModel(location);
		
		if(model != null) {
			Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory = (s -> new RenderTileEntityBase(s, model.getModelData().getModel(), model.getModelData().getTexture()));
			ClientRegistry.bindTileEntityRenderer(tileEntityType, rendererFactory);
		}
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		ModelReloaderRegistry.reloadJavaJSONModels.PRE();
//		CloudyTileRenders.init();
		CloudyEntityRenders.init();
		for(Map.Entry<TileEntityType<?>, ResourceLocation>  entry: tileEntitiesForReload.entrySet()) 
			bindTileEntityRenderer(entry.getKey(), entry.getValue());
		
		RenderingRegistry.loadEntityRenderers(Minecraft.getInstance().getEntityRenderDispatcher());
		ModelReloaderRegistry.reloadJavaJSONModels.POST();
	}
	
	
	
}

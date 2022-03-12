package com.bug1312.cloudyporkchops.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.client.render.Item3DRendering;
import com.bug1312.cloudyporkchops.client.render.Item3DRendering.ItemRenderInfo;
import com.bug1312.cloudyporkchops.util.enums.RenderDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.data.ModelTextures;
import net.minecraft.item.Item;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.RegistryObject;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

	@Shadow private Map<ResourceLocation, IUnbakedModel> unbakedCache;
	@Shadow private Map<ResourceLocation, IUnbakedModel> topLevelModels;
	@Shadow public abstract IUnbakedModel getModel(ResourceLocation rl);

	@Inject(at = @At("HEAD"), remap = false, require = 1, allow = 1, method = "Lnet/minecraft/client/renderer/model/ModelBakery;processLoading(Lnet/minecraft/profiler/IProfiler;I)V")
	private void initer(IProfiler p_i226056_3_, int p_i226056_4_, CallbackInfo info) {
		for (RegistryObject<Item> key : Item3DRendering.ITEMS_3D)
			Item3DRendering.addItemRender(key.get());

		for (ItemRenderInfo renderInfo : Item3DRendering.getRenders()) {
			ModelResourceLocation model = ModelLoader.getInventoryVariant(ModelTextures.getItemTexture(renderInfo.getItem(), RenderDimension.THREE.toString()).toString().replaceFirst("item/", ""));
			ResourceLocation fullPath = new ResourceLocation(model.getNamespace(), "models/item/" + model.getPath() + ".json");
			if (Minecraft.getInstance().getResourceManager().hasResource(fullPath)) {
				IUnbakedModel iunbakedmodel = this.getModel(model);
				this.unbakedCache.put(model, iunbakedmodel);
				this.topLevelModels.put(model, iunbakedmodel);
			}
		}
	}
}

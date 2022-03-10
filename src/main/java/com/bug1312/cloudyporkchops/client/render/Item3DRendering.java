package com.bug1312.cloudyporkchops.client.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bug1312.cloudyporkchops.common.items.Item3D;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class Item3DRendering {
	private static List<ItemRenderInfo> renders = new ArrayList<>();

	public static void addItemRender(Item item) {
		if(item instanceof Item3D) {
			ItemRenderInfo renderInfo = new ItemRenderInfo((Item3D) item);
			renders.add(renderInfo);
		} else {
			new StackOverflowError(item.getRegistryName() + " is not a child of Item3D");
		}
	}

	public static List<ItemRenderInfo> getRenders() {
		return renders;
	}

	public static class ItemRenderInfo {

		private Map<TransformType, OtherModel> perspectives = new HashMap<>();

		private ResourceLocation baseLocation;
		private Item item;

		public ItemRenderInfo(Item3D item3d) {
			this.item = (Item) item3d;
			this.baseLocation = new ModelResourceLocation(item3d.getRegistryName(), "inventory");
			// Hands
			addTransformModel(item3d.handRendering().toString(), TransformType.FIRST_PERSON_LEFT_HAND);
			addTransformModel(item3d.handRendering().toString(), TransformType.FIRST_PERSON_RIGHT_HAND);
			addTransformModel(item3d.handRendering().toString(), TransformType.THIRD_PERSON_LEFT_HAND);
			addTransformModel(item3d.handRendering().toString(), TransformType.THIRD_PERSON_RIGHT_HAND);
			// Inventory
			addTransformModel(item3d.inventoryRendering().toString(), TransformType.GUI);
			// Item frame
			addTransformModel(item3d.itemFrameRendering().toString(), TransformType.FIXED);
			// Ground Model
			addTransformModel(item3d.itemEntityRendering().toString(), TransformType.GROUND);
			// Hat model
			addTransformModel(item3d.hatRendering().toString(), TransformType.HEAD);
		}

		private void addTransformModel(String extention, TransformType type) {
			String location = item.getRegistryName() + extention;
			ModelLoader.addSpecialModel(new ModelResourceLocation(location, "inventory"));
			perspectives.put(type, new OtherModel(this, location, type));
		}

		public Map<TransformType, OtherModel> getTransforms() {
			return perspectives;
		}

		public Item getItem() {
			return item;
		}

		public ResourceLocation getBaseLocation() {
			return baseLocation;
		}

		public static class OtherModel {
			private ResourceLocation location;
			private IBakedModel model;

			public OtherModel(ItemRenderInfo renderInfo, String location, TransformType type) {
				this.location = ModelLoader.getInventoryVariant(location);
			}

			public ResourceLocation getLocation() {
				return location;
			}

			public void setModel(IBakedModel model) {
				this.model = model;
			}

			public IBakedModel getModel() {
				return model;
			}
		}
	}
}

package com.bug1312.client.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bug1312.common.items.Item3D;

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

		private Map<TransformType, ItemModelMatch> transforms = new HashMap<>();

		private ResourceLocation defaultModelLocation;
		private Item item;

		public ItemRenderInfo(Item3D item3d) {
			this.item = (Item) item3d;
			ResourceLocation rlPath = item3d.getRegistryName();
			this.defaultModelLocation = new ModelResourceLocation(rlPath, "inventory");
			
			addTransformType(item3d.handRendering().toString(), TransformType.FIRST_PERSON_LEFT_HAND);
			addTransformType(item3d.handRendering().toString(), TransformType.FIRST_PERSON_RIGHT_HAND);
			addTransformType(item3d.handRendering().toString(), TransformType.THIRD_PERSON_LEFT_HAND);
			addTransformType(item3d.handRendering().toString(), TransformType.THIRD_PERSON_RIGHT_HAND);

			addTransformType(item3d.inventoryRendering().toString(), TransformType.GUI);
			
			addTransformType(item3d.itemFrameRendering().toString(), TransformType.FIXED);
			
			addTransformType(item3d.itemEntityRendering().toString(), TransformType.GROUND);
			
			addTransformType(item3d.hatRendering().toString(), TransformType.HEAD);
		}

		private void addTransformType(String s, TransformType type) {
			String location = item.getRegistryName() + s;
			ModelLoader.addSpecialModel(new ModelResourceLocation(location, "inventory"));
			transforms.put(type, new ItemModelMatch(this, location, type));
		}

		public Map<TransformType, ItemModelMatch> getTransforms() {
			return transforms;
		}

		public Item getItem() {
			return item;
		}

		public ResourceLocation getDefaultModelLocation() {
			return defaultModelLocation;
		}

		public static class ItemModelMatch {
			private TransformType type;
			private ResourceLocation location;
			private IBakedModel model;

			public ItemModelMatch(ItemRenderInfo renderInfo, String location, TransformType type) {
				this.location = new ModelResourceLocation(location, "inventory");
				this.type = type;
			}

			public ResourceLocation getModelLocation() {
				return location;
			}

			public TransformType getType() {
				return type;
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

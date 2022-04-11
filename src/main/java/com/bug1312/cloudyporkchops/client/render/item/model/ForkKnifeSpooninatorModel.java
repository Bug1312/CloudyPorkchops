package com.bug1312.cloudyporkchops.client.render.item.model;

import com.bug1312.cloudyporkchops.common.init.CloudyItems;
import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.javajson.IUseJavaJSON;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

@SuppressWarnings("rawtypes")
public class ForkKnifeSpooninatorModel extends BipedModel implements IUseJavaJSON {

	ItemStack currentArmorItem = new ItemStack(CloudyItems.FORK_KNIFE_SPOON_INATOR.get());

	public ForkKnifeSpooninatorModel() {
		super(1);
		registerJavaJSON(new ResourceLocation(CloudyPorkchops.MODID, "/models/test.json"));
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		
		//obviously can't do this, just trying to get it to render properly first
		LivingEntity player = Minecraft.getInstance().player;
		matrixStackIn.popPose();
		matrixStackIn.translate(-0.5, 24 / 16F, 0.5);
		matrixStackIn.mulPose(new Quaternion(player.xRot, -player.yRot, 0, true));
		getModel().renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedLightIn, red, green, blue, alpha);
		matrixStackIn.pushPose();

	}

}

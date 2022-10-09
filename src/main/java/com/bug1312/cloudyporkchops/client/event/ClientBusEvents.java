package com.bug1312.cloudyporkchops.client.event;

import com.bug1312.cloudyporkchops.client.overlay.Overlay;
import com.bug1312.cloudyporkchops.common.init.CloudyBlocks;
import com.bug1312.cloudyporkchops.util.helpers.SprayEntityHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientBusEvents {

	@SubscribeEvent
	public static void sprayedEntityParticles(LivingEvent event) {
		if (event != null && event.getEntity() != null) {
			Entity entity = event.getEntity();
			if (entity.level.isClientSide && SprayEntityHelper.isEntitySprayedOn(entity) && entity.level.getGameTime() % 2 == 0) {
				Vec3 pos = entity.position();
				BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, CloudyBlocks.SPRAY_ON_SIDE.get().defaultBlockState()).setPos(new BlockPos(pos));

				entity.level.addParticle(particle, pos.x, pos.y, pos.z, 0.0D, 0.1D, 0.0D);
			}
		}
	}

	@SubscribeEvent
	public static void renderOverlays(RenderGuiEvent.Pre event) {
		for (Overlay overlay : Overlay.overlays) {
			overlay.tick();
			if (overlay.conditional()) overlay.render(event.getPoseStack(), event.getPartialTick());
		}
	}

}

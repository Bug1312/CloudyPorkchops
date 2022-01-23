package com.bug1312.cloudyporkchops.network;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class NetworkHandler {

	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(CloudyPorkchops.MODID, "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();


	public static int id = 0;

	public static void register() {
	}

	public static void sendToAllClients(Object packet, World world) {
		world.getServer().getPlayerList().getPlayers().forEach(p -> sendTo(p, packet));
	}

	public static void sendToAllClients(Object packet) {
		ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(p -> sendTo(p, packet));
	}

	public static void sendTo(ServerPlayerEntity playerEntity, Object packet) {
		INSTANCE.sendTo(packet, playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendServerPacket(Object packet) {
		INSTANCE.sendToServer(packet);
	}
}

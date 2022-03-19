package com.bug1312.cloudyporkchops.main;

import org.apache.commons.lang3.tuple.Pair;

import com.bug1312.cloudyporkchops.util.statics.CloudyConfigKeys;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {
	public static class Client {
		
		public final ConfigValue<Boolean> deliverator_flashing;
		
		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Cloudy Porkchops's Client configuration settings").push("client");
			
			builder.comment("Will the Grocery Deliverator's shock overlay flash? | default: true");
			deliverator_flashing = builder.define(CloudyConfigKeys.DELIVERATOR_FLASHING, true);
		}
	}
	
	public static class Common {
		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Cloudy Porkchops's Common Configuration Setting").push("common");
		}
	}
	
	public static class Server {
		Server(ForgeConfigSpec.Builder builder) {
			builder.comment("Cloudy Porkchops's Server Configuration Settings").push("server");
		}
	}
	
	static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;
	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	static final ForgeConfigSpec serverSpec;
	public static final Server SERVER;
	static {
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		serverSpec = specPair.getRight();
		SERVER = specPair.getLeft();
	}
}

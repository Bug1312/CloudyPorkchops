package com.bug1312.cloudyporkchops.util.consts;

import net.minecraft.network.chat.contents.TranslatableContents;

public class CloudyTranslations {

	public static TranslatableContents GROCERY_DELIVERATOR_LOCATION = register("block.cloudyporkchops.grocery_deliverator.linked");

	private static TranslatableContents register(String translation) {
		return new TranslatableContents(translation);
	}
}

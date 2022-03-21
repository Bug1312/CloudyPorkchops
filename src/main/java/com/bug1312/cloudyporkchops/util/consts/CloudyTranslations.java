package com.bug1312.cloudyporkchops.util.consts;

import net.minecraft.util.text.TranslationTextComponent;

public class CloudyTranslations {
		
	public static TranslationTextComponent GROCERY_DELIVERATOR_LOCATION = register("block.cloudyporkchops.grocery_deliverator.linked");
	
	private static TranslationTextComponent register(String translation) {
		return new TranslationTextComponent(translation);
	}
}

package com.bug1312.common.init;

import net.minecraft.util.text.TranslationTextComponent;

public class Translations {
		
	@SuppressWarnings("unused")
	private static TranslationTextComponent register(String translation) {
		return new TranslationTextComponent(translation);
	}
}

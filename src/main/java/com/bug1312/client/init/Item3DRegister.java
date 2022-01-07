package com.bug1312.client.init;

import com.bug1312.client.render.Item3DRendering;
import com.bug1312.common.init.Items;

public class Item3DRegister {
	
	public static void registerItems() {
		Item3DRendering.addItemRender(Items.SPRAY_ON_SHOES_CAN.get());
	}

}

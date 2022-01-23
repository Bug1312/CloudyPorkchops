package com.bug1312.cloudyporkchops.client.init;

import com.bug1312.cloudyporkchops.client.render.Item3DRendering;
import com.bug1312.cloudyporkchops.common.init.CloudyItems;

public class Item3DRegister {
	
	public static void registerItems() {
		Item3DRendering.addItemRender(CloudyItems.SPRAY_ON_SHOES_CAN.get());
	}

}

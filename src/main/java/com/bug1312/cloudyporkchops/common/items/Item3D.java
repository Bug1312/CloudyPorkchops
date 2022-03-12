package com.bug1312.cloudyporkchops.common.items;

import com.bug1312.cloudyporkchops.util.enums.RenderDimension;

import net.minecraft.item.Item;

public class Item3D extends Item {
			
	public Item3D(Properties properties) {
		super(properties);
	}

	public RenderDimension handRendering() { return RenderDimension.THREE; }
	public RenderDimension inventoryRendering() { return RenderDimension.TWO; }
	public RenderDimension hatRendering() { return RenderDimension.THREE; }
	public RenderDimension itemEntityRendering() { return RenderDimension.TWO; }
	public RenderDimension itemFrameRendering() { return RenderDimension.TWO; }
	
}

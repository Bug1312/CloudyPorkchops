package com.bug1312.cloudyporkchops.common.item;

import com.bug1312.cloudyporkchops.util.enums.RenderDimension;

public interface IItem3D {
	public default RenderDimension handRendering() { return RenderDimension.THREE; }
	public default RenderDimension inventoryRendering() { return RenderDimension.TWO; }
	public default RenderDimension hatRendering() { return RenderDimension.THREE; }
	public default RenderDimension itemEntityRendering() { return RenderDimension.TWO; }
	public default RenderDimension itemFrameRendering() { return RenderDimension.TWO; }
}

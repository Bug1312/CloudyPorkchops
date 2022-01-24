package com.bug1312.cloudyporkchops.common.items;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

public class Item3D extends Item {
	
	// Don't forget to add to Item3DRegister
		
	public Item3D(Properties properties) {
		super(properties);
	}

	public RenderDimension handRendering() {
		return RenderDimension.THREE;
	}
	
	public RenderDimension inventoryRendering() {
		return RenderDimension.TWO;
	}
	
	public RenderDimension hatRendering() {
		return RenderDimension.THREE;
	}
	
	public RenderDimension itemEntityRendering() {
		return RenderDimension.TWO;
	}
	
	public RenderDimension itemFrameRendering() {
		return RenderDimension.TWO;
	}
	
	public enum RenderDimension { 
		TWO(null),
		THREE("3d"); 
		
		private String string;
		
		RenderDimension(@Nullable String string) {
			this.string = string;
		}
		
		public String toString() {
			if(string != null)
				return "_" + string;
			else return "";
		}
	};
	
}
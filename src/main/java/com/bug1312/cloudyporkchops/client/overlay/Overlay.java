package com.bug1312.cloudyporkchops.client.overlay;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Overlay {

	public static ArrayList<Overlay> overlays = new ArrayList<>();
	
	public Minecraft mc;
	public int width;
	public int height;

	public Overlay() { overlays.add(this); }
	
	public abstract void render(MatrixStack stack, float partialTicks);

	public void tick() {
		Minecraft minecraft = Minecraft.getInstance();
		MainWindow window = minecraft.getWindow();
		
		mc = minecraft;
		width = window.getGuiScaledWidth();
		height = window.getGuiScaledHeight();
	}
}

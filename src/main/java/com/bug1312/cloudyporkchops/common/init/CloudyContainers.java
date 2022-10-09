package com.bug1312.cloudyporkchops.common.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class CloudyContainers {

	/* Register Methods */
	private static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String id, IContainerFactory<C> container) {
		RegistryObject<MenuType<C>> registryItem = RegistryHandler.CONTAINER_TYPE.register(id, () -> IForgeMenuType.create(container));
		return registryItem;
	}

	public static void init() {};

}


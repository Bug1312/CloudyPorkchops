package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;

@SuppressWarnings("unused")
public class CloudyContainers {
	
	/* Register Methods */
	private static <C extends Container> RegistryObject<ContainerType<C>> register(String id, IContainerFactory<C> container) {
		RegistryObject<ContainerType<C>> registryItem = RegistryHandler.CONTAINER_TYPE.register(id, () -> IForgeContainerType.create(container));
		return registryItem;
	}
	
	public static void init() {};	
	
}


package com.bug1312.common.init;

import java.util.Optional;

import net.minecraft.entity.EntityType;

public class Entities {

    public static void init() {
    }
    
    public static EntityType<?> getEntityTypeFromString(String s){
    	Optional<EntityType<?>> ty = EntityType.byString("dalekmod:" + s);
    	return ty.get();
    }
}

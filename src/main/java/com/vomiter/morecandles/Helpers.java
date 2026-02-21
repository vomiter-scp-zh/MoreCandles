package com.vomiter.morecandles;

import net.minecraft.resources.ResourceLocation;

public class Helpers {
    public static ResourceLocation id(String namespace, String path){
        return new ResourceLocation(namespace, path);
    }
    public static ResourceLocation undeadVariantsId(String path){
        return id("undeadvariants", path);
    }
    public static ResourceLocation minecraftId(String path){
        return id("minecraft", path);
    }
}

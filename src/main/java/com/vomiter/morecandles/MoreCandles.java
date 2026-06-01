package com.vomiter.morecandles;

import com.mojang.logging.LogUtils;
import com.vomiter.morecandles.client.ClientModEvents;
import com.vomiter.morecandles.common.ModEvents;
import com.vomiter.morecandles.registry.ModRegistries;
import com.vomiter.morecandles.data.ModDataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(MoreCandles.MOD_ID)
public class MoreCandles
{
    // Define mod mobCivicsId in a common place for everything to reference
    public static final String MOD_ID = "morecandles";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public MoreCandles(ModContainer mod, IEventBus modBus) {
        modBus.addListener(this::commonSetup);
        modBus.addListener(ModDataGenerator::generateData);
        ModRegistries.register(modBus);
        mod.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModEvents.init(modBus);

        if(FMLLoader.getDist().isClient()){
            ClientModEvents.init(modBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

}

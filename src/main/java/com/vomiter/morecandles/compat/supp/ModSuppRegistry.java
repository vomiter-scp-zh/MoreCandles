package com.vomiter.morecandles.compat.supp;

import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class ModSuppRegistry {
    static Supplier<BlockBehaviour.Properties> HOLDER_PROP = () -> BlockBehaviour.Properties.of()
            .noCollission()
            .pushReaction(PushReaction.DESTROY)
            .noOcclusion()
            .instabreak()
            .sound(SoundType.LANTERN);

    public static final RegistryObject<Block> SOUL_CANDLE_HOLDER = ModBlocks.BLOCKS.register("soul_candle_holder", () -> new SoulCandleHolderBlock(DyeColor.WHITE, HOLDER_PROP.get()));
    public static final RegistryObject<Block> END_CANDLE_HOLDER = ModBlocks.BLOCKS.register("end_candle_holder", () -> new EndCandleHolderBlock(DyeColor.WHITE, HOLDER_PROP.get()));
    public static final RegistryObject<Block> COPPER_CANDLE_HOLDER = ModBlocks.BLOCKS.register("copper_candle_holder", () -> new CopperCandleHolderBlock(DyeColor.WHITE, HOLDER_PROP.get()));

    public static final Map<ModBlocks.Scented, RegistryObject<Block>> SCENTED_CANDLE_HOLDERS = new EnumMap<>(ModBlocks.Scented.class);
    static {
        for (ModBlocks.Scented scented : ModBlocks.Scented.values()) {
            SCENTED_CANDLE_HOLDERS.put(
                    scented,
                    ModBlocks.BLOCKS.register(
                            "scented_candle_holder/"
                                    + scented.toString().toLowerCase(Locale.ROOT),
                            () -> new ScentedCandleHolderBlock(DyeColor.WHITE, HOLDER_PROP.get(), scented.effect.get())));
        }
    }



    static {
        ModBlocks.registerItem(SOUL_CANDLE_HOLDER);
        ModBlocks.registerItem(END_CANDLE_HOLDER);
        ModBlocks.registerItem(COPPER_CANDLE_HOLDER);
        SCENTED_CANDLE_HOLDERS.values().forEach(ModBlocks::registerItem);
    }
}

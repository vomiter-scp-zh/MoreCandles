package com.vomiter.morecandles.common.compat.supp;

import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModSuppRegistry {
    static Supplier<BlockBehaviour.Properties> HOLDER_PROP = () -> BlockBehaviour.Properties.of()
            .noCollission()
            .pushReaction(PushReaction.DESTROY)
            .noOcclusion()
            .instabreak()
            .sound(SoundType.LANTERN);

    public static final RegistryObject<Block> SOUL_CANDLE_HOLDER = ModBlocks.BLOCKS.register("soul_candle_holder", () -> new SoulCandleHolderBlock(DyeColor.WHITE, HOLDER_PROP.get()));
}

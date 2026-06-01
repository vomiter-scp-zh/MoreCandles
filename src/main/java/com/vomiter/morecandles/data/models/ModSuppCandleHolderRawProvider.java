package com.vomiter.morecandles.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vomiter.morecandles.MoreCandles;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Raw JSON generator for Supplementaries-style candle holders.
 * Output examples:
 * - assets/morecandles/blockstates/soul_candle_holder.json
 * - assets/morecandles/models/block/compat/candle_holders/soul_candle_holder/floor_1.json
 * - assets/morecandles/models/item/soul_candle_holder.json
 *
 * For scented candles with slash paths:
 * - assets/morecandles/blockstates/scented_candle_holder/poppy.json
 * - assets/morecandles/models/block/compat/candle_holders/scented_candle_holder/poppy/floor_1.json
 */
public class ModSuppCandleHolderRawProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final PackOutput.PathProvider blockStates;
    private final PackOutput.PathProvider models;

    public ModSuppCandleHolderRawProvider(PackOutput output) {
        this.blockStates = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.models = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (CandleHolderDef def : currentDefs()) {
            // wrapper block models
            for (String parentModelName : allReferencedSuppModelNames()) {
                JsonObject modelJson = makeWrapperBlockModelJson(def, parentModelName);
                ResourceLocation modelId = MoreCandles.modLoc("block/" + wrapperModelPath(def.holderPath(), parentModelName));
                futures.add(save(cachedOutput, modelJson, models.json(modelId)));
            }

            // blockstate
            JsonObject blockStateJson = makeBlockStateJson(def);
            futures.add(save(cachedOutput, blockStateJson, blockStates.json(MoreCandles.modLoc(def.holderPath()))));

            // item model
            JsonObject itemModelJson = makeItemModelJson(def);
            futures.add(save(cachedOutput, itemModelJson, models.json(MoreCandles.modLoc("item/" + def.holderPath()))));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "MoreCandles - Supplementaries candle holder raw JSONs";
    }

    private static List<CandleHolderDef> currentDefs() {
        List<CandleHolderDef> defs = new ArrayList<>();

        defs.add(fromCandle("redstone_candle"));
        defs.add(fromCandle("soul_candle"));
        defs.add(fromCandle("end_candle"));
        defs.add(fromCandle("copper_candle"));

        defs.add(fromCandle("scented_candle/poppy"));
        defs.add(fromCandle("scented_candle/allium"));
        defs.add(fromCandle("scented_candle/dandelion"));
        defs.add(fromCandle("scented_candle/oxeye"));
        defs.add(fromCandle("scented_candle/cornflower"));

        return defs;
    }

    private static CandleHolderDef fromCandle(String candlePath) {
        return new CandleHolderDef(
                candlePath,
                candlePath.replace("_candle", "_candle_holder"),
                MoreCandles.modLoc("block/" + candlePath),
                MoreCandles.modLoc("block/" + candlePath + "_lit")
        );
    }

    // -------------------------------------------------------------------------
    // JSON builders
    // -------------------------------------------------------------------------

    private JsonObject makeWrapperBlockModelJson(CandleHolderDef def, String suppParentName) {
        JsonObject json = new JsonObject();

        json.addProperty("parent", suppModelLoc(suppParentName).toString());

        JsonObject textures = new JsonObject();
        // Supplementaries candle-holder models use #all for the candle body.
        if (suppParentName.endsWith("_lit")) {
            textures.addProperty("all", def.candleTextureLit().toString());
        } else {
            textures.addProperty("all", def.candleTexture().toString());
        }
        json.add("textures", textures);

        return json;
    }

    private JsonObject makeBlockStateJson(CandleHolderDef def) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        for (int candles = 1; candles <= 4; candles++) {
            for (boolean lit : new boolean[]{false, true}) {
                for (Face face : Face.values()) {
                    for (Horizontal facing : Horizontal.values()) {
                        String key = variantKey(lit, facing, face, candles);
                        String suppModelName = resolveSuppModelName(face, facing, candles, lit);

                        JsonObject variant = new JsonObject();
                        variant.addProperty("model", MoreCandles.modLoc("block/" + wrapperModelPath(def.holderPath(), suppModelName)).toString());

                        int y = resolveYRot(face, facing);
                        if (y != 0) {
                            variant.addProperty("y", y);
                        }

                        variants.add(key, variant);
                    }
                }
            }
        }

        root.add("variants", variants);
        return root;
    }

    private JsonObject makeItemModelJson(CandleHolderDef def) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/candle");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MoreCandles.modLoc("item/" + def.candlePath()).toString() + "_holder");
        json.add("textures", textures);

        return json;
    }

    // -------------------------------------------------------------------------
    // Naming / mapping
    // -------------------------------------------------------------------------

    private static String wrapperModelPath(String holderPath, String suppParentName) {
        return "compat/candle_holders/" + holderPath + "/" + suppParentName;
    }

    private static ResourceLocation suppModelLoc(String modelName) {
        return ResourceLocation.fromNamespaceAndPath("supplementaries", "block/candle_holders/" + modelName);
    }

    private static String variantKey(boolean lit, Horizontal facing, Face face, int candles) {
        // property order is not semantically important, but keep it stable
        return "lit=" + lit +
                ",facing=" + facing.serialized +
                ",face=" + face.serialized +
                ",candles=" + candles;
    }

    private static String resolveSuppModelName(Face face, Horizontal facing, int candles, boolean lit) {
        String suffix = lit ? "_lit" : "";

        return switch (face) {
            case FLOOR -> "floor_" + candles + suffix;
            case WALL -> "wall_" + candles + suffix;
            case CEILING -> {
                boolean flipped = (facing == Horizontal.EAST || facing == Horizontal.WEST);
                yield "ceiling_" + candles + (flipped ? "f" : "") + suffix;
            }
        };
    }

    private static int resolveYRot(Face face, Horizontal facing) {
        return switch (facing) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }

    private static List<String> allReferencedSuppModelNames() {
        List<String> names = new ArrayList<>();
        for (int candles = 1; candles <= 4; candles++) {
            names.add("floor_" + candles);
            names.add("floor_" + candles + "_lit");

            names.add("wall_" + candles);
            names.add("wall_" + candles + "_lit");

            names.add("ceiling_" + candles);
            names.add("ceiling_" + candles + "_lit");

            names.add("ceiling_" + candles + "f");
            names.add("ceiling_" + candles + "f_lit");
        }
        return names;
    }

    // -------------------------------------------------------------------------
    // Saving
    // -------------------------------------------------------------------------

    private static CompletableFuture<?> save(CachedOutput cachedOutput, JsonObject json, Path path) {
        return DataProvider.saveStable(cachedOutput, GSON.toJsonTree(json), path);
    }

    // -------------------------------------------------------------------------
    // Small enums / record
    // -------------------------------------------------------------------------

    private enum Face {
        FLOOR("floor"),
        WALL("wall"),
        CEILING("ceiling");

        private final String serialized;

        Face(String serialized) {
            this.serialized = serialized;
        }
    }

    private enum Horizontal {
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west");

        private final String serialized;

        Horizontal(String serialized) {
            this.serialized = serialized;
        }
    }

    private record CandleHolderDef(
            String candlePath,
            String holderPath,
            ResourceLocation candleTexture,
            ResourceLocation candleTextureLit
    ) {}
}
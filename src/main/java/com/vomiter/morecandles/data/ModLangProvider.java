package com.vomiter.morecandles.data;

import com.vomiter.morecandles.MoreCandles;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {
    String locale;
    static String[] locales = {
            "en_us",
            "zh_tw"
    };

    public ModLangProvider(PackOutput output, String locale) {
        super(output, MoreCandles.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.SOUL_CANDLE.get(), tr("Soul Candle", "靈魂蠟燭"));
        add(ModBlocks.REDSTONE_CANDLE.get(), tr("Redstone Candle", "紅石蠟燭"));
        add(ModBlocks.END_CANDLE.get(), tr("End Candle", "終界蠟燭"));
        add(ModBlocks.SCENTED_CANDLES.get(ModBlocks.Scented.POPPY).get(), tr("Poppy Scented Candle", "罌粟香氛蠟燭"));
        add(ModBlocks.SCENTED_CANDLES.get(ModBlocks.Scented.ALLIUM).get(), tr("Allium Scented Candle", "紫紅球花香氛蠟燭"));
        add(ModBlocks.SCENTED_CANDLES.get(ModBlocks.Scented.DANDELION).get(), tr("Dandelion Scented Candle", "蒲公英香氛蠟燭"));
        add(ModBlocks.SCENTED_CANDLES.get(ModBlocks.Scented.OXEYE).get(), tr("Oxeye Scented Candle", "雛菊香氛蠟燭"));
        add(ModBlocks.SCENTED_CANDLES.get(ModBlocks.Scented.CORNFLOWER).get(), tr("Cornflower Scented Candle", "矢車菊香氛蠟燭"));
    }

    /**
     * 讓同一份 provider 同時支援 en_us / zh_tw（用 locale 判斷）
     */
    private String tr(String en, String zhTw) {
        return switch (getName()) {
            default -> (locale.equals("zh_tw") ? zhTw : en);
        };
    }
}

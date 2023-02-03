package rlmixins.mixin.dsurround.orelib;

import org.orecruncher.lib.Translations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.handlers.DSurroundConfigHandler;
import rlmixins.wrapper.ITranslationsMixin;

import java.util.Map;

@Mixin(Translations.class)
public abstract class TranslationsMixin implements ITranslationsMixin {

    @Shadow(remap = false) private Map<String, String> lookup;

    /**
     * Add load method to load from config instead of hardcoded lang file
     */
    @Override
    public void loadFromConfig(String lang) {
        Map<String, String> temp = DSurroundConfigHandler.getDSurroundChatMap(lang);
        if(temp!=null) this.lookup = temp;
    }
}
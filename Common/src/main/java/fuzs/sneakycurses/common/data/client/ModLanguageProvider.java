package fuzs.sneakycurses.common.data.client;

import fuzs.puzzleslib.common.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.sneakycurses.common.handler.CurseRevealHandler;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(CurseRevealHandler.KEY_ITEM_CURSES_REVEALED, "Curses revealed for %s...");
    }
}

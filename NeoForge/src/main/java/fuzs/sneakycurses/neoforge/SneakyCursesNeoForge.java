package fuzs.sneakycurses.neoforge;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.sneakycurses.common.SneakyCurses;
import fuzs.sneakycurses.common.data.tags.ModEnchantmentTagsProvider;
import fuzs.sneakycurses.common.data.tags.ModItemTagsProvider;
import net.neoforged.fml.common.Mod;

@Mod(SneakyCurses.MOD_ID)
public class SneakyCursesNeoForge {

    public SneakyCursesNeoForge() {
        ModConstructor.construct(SneakyCurses.MOD_ID, SneakyCurses::new);
        DataProviderHelper.registerDataProviders(SneakyCurses.MOD_ID,
                ModItemTagsProvider::new,
                ModEnchantmentTagsProvider::new);
    }
}

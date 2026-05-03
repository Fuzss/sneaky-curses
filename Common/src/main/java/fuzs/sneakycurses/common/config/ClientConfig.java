package fuzs.sneakycurses.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import net.minecraft.world.item.DyeColor;

public class ClientConfig implements ConfigCore {
    @Config(description = "The glint color for items enchanted with curses.")
    public DyeColor cursedGlintColor = DyeColor.RED;
}

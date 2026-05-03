package fuzs.sneakycurses.mixin.client;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStackRenderState.FoilType.class)
enum FoilTypeMixin {
    SNEAKYCURSES_STANDARD_CURSE,
    SNEAKYCURSES_SPECIAL_CURSE;
}

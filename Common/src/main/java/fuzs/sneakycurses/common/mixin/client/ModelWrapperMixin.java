package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({CuboidItemModelWrapper.class, SpecialModelWrapper.class})
abstract class ModelWrapperMixin {

    @ModifyVariable(method = "update", at = @At("STORE"))
    public ItemStackRenderState.FoilType update(ItemStackRenderState.FoilType foilType, @Local(argsOnly = true) ItemStack item) {
        if (CustomItemFeatureRenderer.isItemStackCursed(item)) {
            return foilType == ItemStackRenderState.FoilType.SPECIAL ?
                    CustomItemFeatureRenderer.SPECIAL_CURSE_FOIL_TYPE :
                    CustomItemFeatureRenderer.STANDARD_CURSE_FOIL_TYPE;
        } else {
            return foilType;
        }
    }
}

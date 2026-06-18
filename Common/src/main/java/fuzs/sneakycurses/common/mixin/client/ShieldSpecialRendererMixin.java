package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import fuzs.sneakycurses.common.client.renderer.rendertype.ModRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.core.component.DataComponentMap;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ShieldSpecialRenderer.class)
abstract class ShieldSpecialRendererMixin {

    @ModifyArg(method = "submit(Lnet/minecraft/core/component/DataComponentMap;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    public RenderType submit(RenderType renderType, @Local(argsOnly = true) @Nullable DataComponentMap components) {
        if (components != null && CustomItemFeatureRenderer.isItemStackCursed(components)) {
            return ModRenderTypes.GLINT_RENDER_TYPES.getOrDefault(renderType, renderType);
        } else {
            return renderType;
        }
    }
}

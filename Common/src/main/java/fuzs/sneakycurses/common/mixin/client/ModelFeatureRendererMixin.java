package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import fuzs.sneakycurses.common.client.renderer.rendertype.ModRenderTypes;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelFeatureRenderer.class)
abstract class ModelFeatureRendererMixin {

    @ModifyExpressionValue(method = "prepareModel",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer;getVertexBuilder(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private <S> VertexConsumer prepareModel(VertexConsumer buffer, ModelFeatureRenderer.Submit<S> submit) {
        return ModRenderTypes.GLINT_RENDER_TYPES.containsValue(submit.renderType()) ?
                CustomItemFeatureRenderer.applyFoilBufferColor(buffer) : buffer;
    }
}

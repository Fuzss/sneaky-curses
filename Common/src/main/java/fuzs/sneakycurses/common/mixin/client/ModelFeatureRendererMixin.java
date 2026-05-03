package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import fuzs.sneakycurses.common.client.renderer.rendertype.ModRenderTypes;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ModelFeatureRenderer.class)
abstract class ModelFeatureRendererMixin {

    @ModifyVariable(method = "renderModel", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private VertexConsumer renderModel(VertexConsumer buffer, @Local(argsOnly = true) RenderType renderType) {
        return ModRenderTypes.GLINT_RENDER_TYPES.containsValue(renderType) ?
                CustomItemFeatureRenderer.applyFoilBufferColor(buffer) : buffer;
    }
}

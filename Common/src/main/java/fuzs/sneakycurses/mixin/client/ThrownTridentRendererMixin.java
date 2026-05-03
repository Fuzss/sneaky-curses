package fuzs.sneakycurses.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.puzzleslib.common.api.client.renderer.v1.RenderStateExtraData;
import fuzs.sneakycurses.client.handler.TridentGlintHandler;
import fuzs.sneakycurses.client.renderer.rendertype.ModRenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(ThrownTridentRenderer.class)
abstract class ThrownTridentRendererMixin extends EntityRenderer<ThrownTrident, ThrownTridentRenderState> {

    protected ThrownTridentRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @ModifyArg(method = "submit(Lnet/minecraft/client/renderer/entity/state/ThrownTridentRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    public RenderType submit(RenderType renderType, @Local(argsOnly = true) ThrownTridentRenderState state) {
        if (RenderStateExtraData.getOrDefault(state, TridentGlintHandler.IS_TRIDENT_CURSED_KEY, Optional.empty())
                .orElse(false)) {
            return ModRenderTypes.GLINT_RENDER_TYPES.getOrDefault(renderType, renderType);
        } else {
            return renderType;
        }
    }
}

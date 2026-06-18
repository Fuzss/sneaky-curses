package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemFeatureRenderer.class)
abstract class ItemFeatureRendererMixin extends RenderTypeFeatureRenderer<ItemFeatureRenderer.Submit> {

    @ModifyVariable(method = "prepareFoilSubmit", at = @At("STORE"))
    private PoseStack.Pose prepareFoilSubmit(PoseStack.Pose foilDecalPose, ItemFeatureRenderer.Submit submit) {
        // Extend this case to also cover our SPECIAL_CURSE foil type.
        ItemStackRenderState.FoilType foilType = submit.foilType();
        if (foilType == CustomItemFeatureRenderer.SPECIAL_CURSE_FOIL_TYPE) {
            PoseStack.Pose pose = submit.pose();
            return computeFoilDecalPose(submit.displayContext(), pose);
        } else {
            return foilDecalPose;
        }
    }

    @Shadow
    private static PoseStack.Pose computeFoilDecalPose(ItemDisplayContext type, PoseStack.Pose pose) {
        throw new UnsupportedOperationException();
    }

    @ModifyExpressionValue(method = "prepareFoilSubmit",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer prepareFoilSubmit(VertexConsumer foilBuffer, ItemFeatureRenderer.Submit submit, @Local PoseStack.Pose foilDecalPose, @Local BakedQuad quad) {
        if (CustomItemFeatureRenderer.isCurseFoilType(submit.foilType())) {
            return CustomItemFeatureRenderer.getFoilBuffer(this::getVertexBuilder,
                    quad.materialInfo().itemRenderType(),
                    foilDecalPose);
        } else {
            return foilBuffer;
        }
    }
}

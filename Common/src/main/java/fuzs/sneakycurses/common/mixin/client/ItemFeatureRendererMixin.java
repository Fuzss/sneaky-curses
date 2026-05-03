package fuzs.sneakycurses.common.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.client.renderer.feature.CustomItemFeatureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemFeatureRenderer.class)
abstract class ItemFeatureRendererMixin {

    @ModifyVariable(method = "renderItem", at = @At("STORE"), ordinal = 1)
    private PoseStack.Pose renderItem(PoseStack.Pose foilDecalPose, MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, SubmitNodeStorage.ItemSubmit submit) {
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

    @WrapOperation(method = "renderItem",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/rendertype/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private static VertexConsumer renderItem(MultiBufferSource bufferSource, RenderType renderType, PoseStack.Pose foilDecalPose, Operation<VertexConsumer> operation, @Local(
            argsOnly = true) SubmitNodeStorage.ItemSubmit submit) {
        if (CustomItemFeatureRenderer.isCurseFoilType(submit.foilType())) {
            return CustomItemFeatureRenderer.getFoilBuffer(bufferSource, renderType, foilDecalPose);
        } else {
            return operation.call(bufferSource, renderType, foilDecalPose);
        }
    }
}

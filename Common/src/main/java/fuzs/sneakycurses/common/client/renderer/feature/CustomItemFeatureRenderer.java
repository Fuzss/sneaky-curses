package fuzs.sneakycurses.common.client.renderer.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.SneakyCurses;
import fuzs.sneakycurses.common.client.renderer.rendertype.ModRenderTypes;
import fuzs.sneakycurses.common.config.ClientConfig;
import fuzs.sneakycurses.common.config.ServerConfig;
import fuzs.sneakycurses.common.handler.CurseRevealHandler;
import fuzs.sneakycurses.common.services.ClientAbstractions;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.function.Function;

public class CustomItemFeatureRenderer {
    private static final Identifier STANDARD_CURSE_LOCATION = SneakyCurses.id("standard_curse");
    private static final Identifier SPECIAL_CURSE_LOCATION = SneakyCurses.id("special_curse");
    public static final ItemStackRenderState.FoilType STANDARD_CURSE_FOIL_TYPE = getEnumConstant(STANDARD_CURSE_LOCATION,
            ItemStackRenderState.FoilType::valueOf);
    public static final ItemStackRenderState.FoilType SPECIAL_CURSE_FOIL_TYPE = getEnumConstant(SPECIAL_CURSE_LOCATION,
            ItemStackRenderState.FoilType::valueOf);

    private static <E extends Enum<E>> E getEnumConstant(Identifier identifier, Function<String, E> valueOfInvoker) {
        return valueOfInvoker.apply(identifier.toDebugFileName().toUpperCase(Locale.ROOT));
    }

    public static boolean isCurseFoilType(ItemStackRenderState.FoilType foilType) {
        return foilType == STANDARD_CURSE_FOIL_TYPE || foilType == SPECIAL_CURSE_FOIL_TYPE;
    }

    public static boolean isItemStackCursed(DataComponentMap components) {
        if (!hasItemStackFoil(components)) {
            return false;
        } else if (!SneakyCurses.CONFIG.getHolder(ServerConfig.class).isAvailable() || !SneakyCurses.CONFIG.get(
                ServerConfig.class).cursedItemGlint) {
            return false;
        } else {
            return CurseRevealHandler.isItemStackCursed(components);
        }
    }

    /**
     * @see ItemStack#hasFoil()
     */
    public static boolean hasItemStackFoil(DataComponentMap components) {
        return components.getOrDefault(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, Boolean.FALSE)
                || !components.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).isEmpty();
    }

    public static boolean isItemStackCursed(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.hasFoil()) {
            return false;
        } else if (!SneakyCurses.CONFIG.getHolder(ServerConfig.class).isAvailable() || !SneakyCurses.CONFIG.get(
                ServerConfig.class).cursedItemGlint) {
            return false;
        } else {
            return CurseRevealHandler.isItemStackCursed(itemStack);
        }
    }

    /**
     * @see ItemFeatureRenderer#getFoilBuffer(RenderType, PoseStack.Pose)
     */
    public static VertexConsumer getFoilBuffer(Function<RenderType, VertexConsumer> vertexBuilderGetter, RenderType renderType, PoseStack.@Nullable Pose foilDecalPose) {
        RenderType foilRenderType = getFoilRenderType(renderType);
        RenderType tintedFoilRenderType = ModRenderTypes.GLINT_RENDER_TYPES.getOrDefault(foilRenderType,
                foilRenderType);
        VertexConsumer foilBuffer = applyFoilBufferColor(vertexBuilderGetter.apply(tintedFoilRenderType));
        if (foilDecalPose != null) {
            return new SheetedDecalTextureGenerator(foilBuffer, foilDecalPose, 0.0078125F);
        } else {
            return foilBuffer;
        }
    }

    /**
     * @see ItemFeatureRenderer#getFoilBuffer(RenderType, PoseStack.Pose)
     */
    public static RenderType getFoilRenderType(RenderType baseRenderType) {
        return ItemFeatureRenderer.useTransparentGlint(baseRenderType) ? RenderTypes.glintTranslucent() :
                RenderTypes.glint();
    }

    /**
     * Copied from {@code OutlineBufferSource.EntityOutlineGenerator} on Minecraft 26.1.
     */
    public static VertexConsumer applyFoilBufferColor(VertexConsumer vertexConsumer) {
        return ClientAbstractions.INSTANCE.getEntityOutlineGenerator(vertexConsumer,
                ARGB.opaque(SneakyCurses.CONFIG.get(ClientConfig.class).cursedGlintColor.getTextureDiffuseColor()));
    }
}

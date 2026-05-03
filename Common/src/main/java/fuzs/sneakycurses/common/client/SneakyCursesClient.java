package fuzs.sneakycurses.common.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.RenderBuffersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.RenderPipelinesContext;
import fuzs.puzzleslib.common.api.client.event.v1.entity.ClientEntityLevelEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ItemTooltipCallback;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenEvents;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import fuzs.puzzleslib.common.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.common.api.resources.v1.PackResourcesHelper;
import fuzs.sneakycurses.common.SneakyCurses;
import fuzs.sneakycurses.common.client.handler.ItemTooltipHandler;
import fuzs.sneakycurses.common.client.handler.TridentGlintHandler;
import fuzs.sneakycurses.common.client.packs.TransformingPackResources;
import fuzs.sneakycurses.common.client.renderer.rendertype.ModRenderTypes;
import net.minecraft.client.gui.screens.Screen;

public class SneakyCursesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ItemTooltipCallback.EVENT.register(ItemTooltipHandler::onItemTooltip);
        ScreenEvents.afterInit(Screen.class).register(ItemTooltipHandler::onAfterInit);
        ClientEntityLevelEvents.LOAD.register(TridentGlintHandler::onEntityLoad);
        ExtractEntityRenderStateCallback.EVENT.register(TridentGlintHandler::onExtractEntityRenderState);
    }

    @Override
    public void onRegisterRenderBuffers(RenderBuffersContext context) {
        ModRenderTypes.GLINT_RENDER_TYPES.values().forEach(context::registerRenderBuffer);
    }

    @Override
    public void onAddResourcePackFinders(PackRepositorySourcesContext context) {
        context.registerRepositorySource(PackResourcesHelper.buildClientPack(SneakyCurses.id("grayscale_glint"),
                TransformingPackResources::new,
                true));
    }

    @Override
    public void onRegisterRenderPipelines(RenderPipelinesContext context) {
        context.registerRenderPipeline(ModRenderTypes.GLINT_RENDER_PIPELINE);
    }
}

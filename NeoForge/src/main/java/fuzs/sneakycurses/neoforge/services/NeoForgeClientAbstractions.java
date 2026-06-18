package fuzs.sneakycurses.neoforge.services;

import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.services.ClientAbstractions;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;

public final class NeoForgeClientAbstractions implements ClientAbstractions {
    /**
     * Copied from {@code OutlineBufferSource.EntityOutlineGenerator} on Minecraft 26.1.
     */
    @Override
    public VertexConsumer getEntityOutlineGenerator(VertexConsumer vertexConsumer, int color) {
        return new VertexConsumerWrapper(vertexConsumer) {
            @Override
            public VertexConsumer addVertex(float x, float y, float z) {
                super.addVertex(x, y, z);
                return super.setColor(color);
            }

            @Override
            public VertexConsumer setColor(int color) {
                return this;
            }

            @Override
            public VertexConsumer setColor(int r, int g, int b, int a) {
                return this;
            }
        };
    }
}

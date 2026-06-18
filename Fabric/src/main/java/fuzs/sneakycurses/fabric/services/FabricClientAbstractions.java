package fuzs.sneakycurses.fabric.services;

import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.sneakycurses.common.services.ClientAbstractions;

public final class FabricClientAbstractions implements ClientAbstractions {
    /**
     * Copied from {@code OutlineBufferSource.EntityOutlineGenerator} on Minecraft 26.1.
     */
    @Override
    public VertexConsumer getEntityOutlineGenerator(VertexConsumer vertexConsumer, int color) {
        return new VertexConsumer() {
            @Override
            public VertexConsumer addVertex(float x, float y, float z) {
                vertexConsumer.addVertex(x, y, z).setColor(color);
                return this;
            }

            @Override
            public VertexConsumer setColor(int r, int g, int b, int a) {
                return this;
            }

            @Override
            public VertexConsumer setColor(int color) {
                return this;
            }

            @Override
            public VertexConsumer setUv(float u, float v) {
                vertexConsumer.setUv(u, v);
                return this;
            }

            @Override
            public VertexConsumer setUv1(int u, int v) {
                return this;
            }

            @Override
            public VertexConsumer setUv2(int u, int v) {
                return this;
            }

            @Override
            public VertexConsumer setNormal(float x, float y, float z) {
                return this;
            }

            @Override
            public VertexConsumer setLineWidth(float width) {
                return this;
            }
        };
    }
}

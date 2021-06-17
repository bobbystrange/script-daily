package org.dreamcat.daily.script.rendering;

import java.util.EnumMap;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
public final class RenderingManager {

    private RenderingManager() {
    }

    public static Rendering getRendering(Rendering.Type type) {
        return renderings.get(type);
    }

    public static void registerRendering(Rendering.Type type, Rendering rendering) {
        renderings.put(type, rendering);
    }

    private static final EnumMap<Rendering.Type, Rendering> renderings = new EnumMap<>(Rendering.Type.class);
}

package org.dreamcat.daily.script.rendering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dreamcat.daily.script.ApiDoc;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkdownRendering implements Rendering {

    static {
        RenderingManager.registerRendering(Type.MARKDOWN, new MarkdownRendering());
    }

    @Override
    public void rendering(ApiDoc apiDoc) {

    }

}

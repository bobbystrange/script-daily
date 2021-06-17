package org.dreamcat.daily.script.rendering;

import org.dreamcat.daily.script.ApiDoc;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
public interface Rendering {

    void rendering(ApiDoc apiDoc);

    enum Type {
        MARKDOWN,
        ;
    }
}

package org.dreamcat.daily.script;

import lombok.Data;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@Data
public class ApiDoc {

    String url;
    String[] methods; // http methods
    ParamSchema requestSchema;
    ParamSchema responseSchema;
}

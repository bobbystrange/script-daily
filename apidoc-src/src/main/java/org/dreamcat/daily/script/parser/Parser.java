package org.dreamcat.daily.script.parser;

import org.dreamcat.daily.script.ApiDoc;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
public interface Parser {

    ApiDoc parse(String... args);

    enum Type {
        SOURCE_JAR,
        ;
    }


}

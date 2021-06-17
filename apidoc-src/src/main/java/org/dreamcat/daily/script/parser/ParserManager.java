package org.dreamcat.daily.script.parser;

import java.util.EnumMap;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
public final class ParserManager {

    private ParserManager() {
    }

    public static Parser getParser(Parser.Type type) {
        return parsers.get(type);
    }

    public static void registerParser(Parser.Type type, Parser parser) {
        parsers.put(type, parser);
    }

    private static final EnumMap<Parser.Type, Parser> parsers = new EnumMap<>(Parser.Type.class);

}

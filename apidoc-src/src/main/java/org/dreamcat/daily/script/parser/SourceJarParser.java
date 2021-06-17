package org.dreamcat.daily.script.parser;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.core.FieldColumn;
import org.dreamcat.common.text.argparse.ArgParser;
import org.dreamcat.common.util.ReflectUtil;
import org.dreamcat.daily.script.ApiDoc;
import org.dreamcat.daily.script.ParamSchema;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceJarParser extends CliParser {

    protected String sourceDir;

    @Override
    protected ApiDoc doParse() {
        ApiDoc apiDoc = new ApiDoc();

        ParamSchema requestSchema = new ParamSchema();
        ParamSchema responseSchema = new ParamSchema();
        apiDoc.setRequestSchema(requestSchema);
        apiDoc.setResponseSchema(responseSchema);

        responseSchema.setType(returnType);

        if (ReflectUtil.isFlat(returnType)) {
            return apiDoc;
        } else if (ReflectUtil.isCollectionOrArray(returnType)) {

            ReflectUtil.getTypeArgument(returnType.getType)
        } else {

        }

        if (!ReflectUtil.isFlat(returnType)) {
            List<FieldColumn> respCol = FieldColumn.parse(returnType);

        }

        return apiDoc;
    }

    @Override
    protected ArgParser defineArgs() {
        ArgParser argParser = super.defineArgs();
        argParser.add("sourceDir", "s", "sd", "source-dir");
        return argParser;
    }

    @Override
    protected ArgParser parseArgs(String... args) {
        ArgParser argParser = super.parseArgs(args);
        this.sourceDir = argParser.get("sourceDir");
        return argParser;
    }

    static {
        ParserManager.registerParser(Parser.Type.SOURCE_JAR, new SourceJarParser());
    }

}

package org.dreamcat.daily.script;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.daily.script.parser.Parser;
import org.dreamcat.daily.script.parser.ParserManager;
import org.dreamcat.daily.script.rendering.Rendering;
import org.dreamcat.daily.script.rendering.RenderingManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@Slf4j
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args).close();
    }

    @Value("${apidoc.parser:source_jar}")
    private String parserTypeName;
    @Value("${apidoc.rendering:markdown}")
    private String renderingTypeName;

    @Override
    public void run(String... args) throws Exception {
        Parser.Type parserType;
        Rendering.Type renderingType;

        try {
            parserType = Parser.Type.valueOf(parserTypeName);
            renderingType = Rendering.Type.valueOf(renderingTypeName);
        } catch (Exception e) {
            log.error("fail to parse ${apidoc.parser} or ${apidoc.rendering}: {}", e.toString());
            System.exit(1);
            return;
        }

        Parser parser = ParserManager.getParser(parserType);
        Rendering rendering = RenderingManager.getRendering(renderingType);

        ApiDoc apiDoc = parser.parse(args);
        rendering.rendering(apiDoc);
    }
}

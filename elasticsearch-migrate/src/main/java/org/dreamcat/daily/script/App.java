package org.dreamcat.daily.script;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.daily.script.common.SchemaHandler;
import org.dreamcat.jwrap.elasticsearch.EsDocumentComponent;
import org.dreamcat.jwrap.elasticsearch.EsIndexComponent;
import org.dreamcat.jwrap.elasticsearch.EsSearchComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create by tuke on 2021/3/22
 */
@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Value("${elastic.index-formatter:}")
    private String indexFormatter;

    @Resource(name = "sourceEsIndexComponent")
    private EsIndexComponent sourceEsIndexComponent;
    @Resource(name = "sourceEsSearchComponent")
    private EsSearchComponent sourceEsSearchComponent;
    @Resource(name = "targetEsIndexComponent")
    private EsIndexComponent targetEsIndexComponent;
    @Resource(name = "targetEsDocumentComponent")
    private EsDocumentComponent targetEsDocumentComponent;

    @Override
    public void run(String... args) {
        SchemaHandler schemaHandler = ElasticsearchSchemaHandler.builder()
                .indexFormatter(indexFormatter)
                .sourceEsIndexComponent(sourceEsIndexComponent)
                .sourceEsSearchComponent(sourceEsSearchComponent)
                .targetEsIndexComponent(targetEsIndexComponent)
                .targetEsDocumentComponent(targetEsDocumentComponent)
                .build();
        schemaHandler.run(args);
    }
}

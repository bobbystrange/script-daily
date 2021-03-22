package org.dreamcat.daily.script;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.ScriptException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.core.Pair;
import org.dreamcat.common.script.DelegateScriptEngine;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.x.jackson.JacksonUtil;
import org.dreamcat.daily.script.common.SchemaMigrateHandler;
import org.dreamcat.jwrap.elasticsearch.EsDocumentComponent;
import org.dreamcat.jwrap.elasticsearch.EsIndexComponent;
import org.dreamcat.jwrap.elasticsearch.EsSearchComponent;
import org.dreamcat.jwrap.elasticsearch.EsSearchComponent.ScrollMapIter;
import org.elasticsearch.common.unit.TimeValue;

/**
 * Create by tuke on 2021/3/22
 */
@Slf4j
public class ElasticsearchSchemaHandler extends SchemaMigrateHandler {

    private final String indexFormatter;
    private final EsIndexComponent sourceEsIndexComponent;
    private final EsSearchComponent sourceEsSearchComponent;
    private final EsIndexComponent targetEsIndexComponent;
    private final EsDocumentComponent targetEsDocumentComponent;
    private final DelegateScriptEngine scriptEngine;

    @Builder
    public ElasticsearchSchemaHandler(
            String indexFormatter,
            EsIndexComponent sourceEsIndexComponent,
            EsSearchComponent sourceEsSearchComponent,
            EsIndexComponent targetEsIndexComponent,
            EsDocumentComponent targetEsDocumentComponent) {
        this.indexFormatter = indexFormatter;
        this.sourceEsIndexComponent = sourceEsIndexComponent;
        this.sourceEsSearchComponent = sourceEsSearchComponent;
        this.targetEsIndexComponent = targetEsIndexComponent;
        this.targetEsDocumentComponent = targetEsDocumentComponent;
        this.scriptEngine = new DelegateScriptEngine();
    }

    @Override
    public String getSchemaName() {
        return "index";
    }

    @Override
    protected Iterable<String> getSchemas() {
        return sourceEsIndexComponent.getAllIndex();
    }

    @Override
    protected String formatSchema(String sourceSchema) {
        if (ObjectUtil.isNotBlank(indexFormatter)) {
            Map<List<String>, Object> context = Collections.singletonMap(
                    DEFAULT_PARAMS, sourceSchema);
            try {
                return scriptEngine.evalMultiKey(indexFormatter, context);
            } catch (ScriptException e) {
                log.error("failed to format schema {}, error: {}", sourceSchema, e.getMessage());
            }
        }
        return super.formatSchema(sourceSchema);
    }

    @Override
    protected void handle(String sourceIndex, String targetIndex) {
        if (targetEsIndexComponent.existsIndex(targetIndex)) {
            if (force) {
                log.warn("drop target index {}", targetIndex);
                targetEsIndexComponent.deleteIndex(targetIndex);
            } else {
                log.warn("index {} already exists in target source", targetIndex);
                return;
            }
        }

        Pair<String, String> pair = sourceEsIndexComponent.getIndex(sourceIndex);
        String mappings = pair.first();
        String settings = pair.second();
        targetEsIndexComponent.createIndex(targetIndex, mappings, settings);

        // huge index, then
        try (ScrollMapIter scrollIter = sourceEsSearchComponent.scrollMapIter(
                sourceIndex, DEFAULT_SIZE, TimeValue.timeValueMillis(DEFAULT_KEEP_ALIVE))) {
            while (scrollIter.hasNext()) {
                List<Map<String, Object>> list = scrollIter.next();
                if (list.isEmpty()) continue;

                Map<String, String> idJsonMap = list.stream().collect(Collectors.toMap(
                        it -> it.get("id").toString(), JacksonUtil::toJson));
                targetEsDocumentComponent.bulkSave(targetIndex, idJsonMap);
            }
        }
    }

    private static final int DEFAULT_SIZE = 1024;
    private static final long DEFAULT_KEEP_ALIVE = 60L * 1000;
    private static final List<String> DEFAULT_PARAMS = Arrays.asList(
            "$1", "_1", "it", "a1", "p1", "arg1", "param1", "parameter1");
}

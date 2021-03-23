package org.dreamcat.daily.script.common;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.text.argparse.ArgParseException;
import org.dreamcat.common.text.argparse.ArgParser;
import org.dreamcat.common.util.ObjectUtil;

/**
 * Create by tuke on 2021/3/22
 */
@Slf4j
public abstract class SchemaHandler {

    protected boolean verbose;
    protected boolean force;

    private boolean exclude;
    private List<Pattern> patterns;
    // abort by exception
    private boolean abort;

    protected String getSchemaName() {
        return DEFAULT_SCHEMA_NAME;
    }

    protected abstract Iterable<String> getSchemas();

    protected abstract void handle(String schema);

    public void run(String... args) {
        this.parseArgs(args);
        String schemaName = this.getSchemaName();
        for (String schema : this.getSchemas()) {
            if (!matchSchema(schema)) {
                if (verbose) log.info("skip unmatched {} {}", schemaName, schema);
                continue;
            }
            try {
                handle(schema);
            } catch (Exception e) {
                log.error(String.format("handle %s %s, error occurred: %s",
                        schemaName, schema, e.getMessage()), e);
                if (abort) {
                    log.info("operation is aborted by exception");
                    break;
                }
            }
        }
    }

    private void parseArgs(String... args) {
        ArgParser argParser = ArgParser.newInstance();
        argParser.addList("include", "i", "include");
        argParser.addList("exclude", "e", "exclude");
        argParser.addBool("verbose", "v", "verbose");
        argParser.addBool("force", "f", "force");
        argParser.addBool("abort", "a", "abort");

        try {
            argParser.parse(args);
        } catch (ArgParseException e) {
            log.error("fail to parse args: {}", e.toString());
            System.exit(1);
        }

        List<String> includeList = argParser.getList("include");
        List<String> excludeList = argParser.getList("exclude");
        this.verbose = argParser.getBool("verbose");
        this.force = argParser.getBool("force");
        this.abort = argParser.getBool("abort");

        List<String> list = Collections.emptyList();
        if (ObjectUtil.isNotEmpty(includeList)) {
            this.exclude = false;
            list = includeList;
        } else if (ObjectUtil.isNotEmpty(excludeList)) {
            this.exclude = true;
            list = excludeList;
        }
        this.patterns = list.stream().map(Pattern::compile).collect(Collectors.toList());
    }

    private boolean matchSchema(String collectionName) {
        if (ObjectUtil.isEmpty(patterns)) return true;
        for (Pattern pattern : patterns) {
            if (pattern.matcher(collectionName).find()) {
                // in matched case
                // if exclude, then return false to skip it
                return !exclude;
            }
        }
        // in unmatched case
        // if include, then return false to skip it
        return exclude;
    }

    private static final String DEFAULT_SCHEMA_NAME = "schema";
}

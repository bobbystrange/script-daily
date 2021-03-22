package org.dreamcat.daily.script.common;

/**
 * Create by tuke on 2021/3/22
 */
public abstract class SchemaMigrateHandler extends SchemaHandler {

    protected abstract void handle(String sourceSchema, String targetSchema);

    protected String formatSchema(String sourceSchema) {
        return sourceSchema;
    }

    @Override
    protected final void handle(String schema) {
        handle(schema, formatSchema(schema));
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.proyecto404.backoffice.infrastructure.jooq.generated.tables;


import com.proyecto404.backoffice.infrastructure.jooq.generated.Keys;
import com.proyecto404.backoffice.infrastructure.jooq.generated.Public;
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.records.ChatbotConfigurationsRecord;

import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatbotConfigurations extends TableImpl<ChatbotConfigurationsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.chatbot_configurations</code>
     */
    public static final ChatbotConfigurations CHATBOT_CONFIGURATIONS = new ChatbotConfigurations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ChatbotConfigurationsRecord> getRecordType() {
        return ChatbotConfigurationsRecord.class;
    }

    /**
     * The column <code>public.chatbot_configurations.id</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.chatbot_configurations.api_key</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, String> API_KEY = createField(DSL.name("api_key"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.chatbot_configurations.context</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, String> CONTEXT = createField(DSL.name("context"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.chatbot_configurations.max_tokens</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, Integer> MAX_TOKENS = createField(DSL.name("max_tokens"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.chatbot_configurations.temperature</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, BigDecimal> TEMPERATURE = createField(DSL.name("temperature"), SQLDataType.NUMERIC(2, 1).nullable(false), this, "");

    /**
     * The column <code>public.chatbot_configurations.top_p</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, BigDecimal> TOP_P = createField(DSL.name("top_p"), SQLDataType.NUMERIC(2, 1).nullable(false), this, "");

    /**
     * The column <code>public.chatbot_configurations.frequency_penalty</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, Integer> FREQUENCY_PENALTY = createField(DSL.name("frequency_penalty"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column
     * <code>public.chatbot_configurations.parameterpresence_penalty</code>.
     */
    public final TableField<ChatbotConfigurationsRecord, Integer> PARAMETERPRESENCE_PENALTY = createField(DSL.name("parameterpresence_penalty"), SQLDataType.INTEGER.nullable(false), this, "");

    private ChatbotConfigurations(Name alias, Table<ChatbotConfigurationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChatbotConfigurations(Name alias, Table<ChatbotConfigurationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.chatbot_configurations</code> table
     * reference
     */
    public ChatbotConfigurations(String alias) {
        this(DSL.name(alias), CHATBOT_CONFIGURATIONS);
    }

    /**
     * Create an aliased <code>public.chatbot_configurations</code> table
     * reference
     */
    public ChatbotConfigurations(Name alias) {
        this(alias, CHATBOT_CONFIGURATIONS);
    }

    /**
     * Create a <code>public.chatbot_configurations</code> table reference
     */
    public ChatbotConfigurations() {
        this(DSL.name("chatbot_configurations"), null);
    }

    public <O extends Record> ChatbotConfigurations(Table<O> child, ForeignKey<O, ChatbotConfigurationsRecord> key) {
        super(child, key, CHATBOT_CONFIGURATIONS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ChatbotConfigurationsRecord, Integer> getIdentity() {
        return (Identity<ChatbotConfigurationsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ChatbotConfigurationsRecord> getPrimaryKey() {
        return Keys.CHATBOT_CONFIGURATIONS_PKEY;
    }

    @Override
    public ChatbotConfigurations as(String alias) {
        return new ChatbotConfigurations(DSL.name(alias), this);
    }

    @Override
    public ChatbotConfigurations as(Name alias) {
        return new ChatbotConfigurations(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ChatbotConfigurations rename(String name) {
        return new ChatbotConfigurations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ChatbotConfigurations rename(Name name) {
        return new ChatbotConfigurations(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, String, Integer, BigDecimal, BigDecimal, Integer, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}

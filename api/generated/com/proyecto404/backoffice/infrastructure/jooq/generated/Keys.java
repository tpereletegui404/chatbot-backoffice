/*
 * This file is generated by jOOQ.
 */
package com.proyecto404.backoffice.infrastructure.jooq.generated;


import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.ChatbotConfigurations;
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.FlywaySchemaHistory;
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.records.ChatbotConfigurationsRecord;
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.records.FlywaySchemaHistoryRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatbotConfigurationsRecord> CHATBOT_CONFIGURATIONS_PKEY = Internal.createUniqueKey(ChatbotConfigurations.CHATBOT_CONFIGURATIONS, DSL.name("chatbot_configurations_pkey"), new TableField[] { ChatbotConfigurations.CHATBOT_CONFIGURATIONS.ID }, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
}

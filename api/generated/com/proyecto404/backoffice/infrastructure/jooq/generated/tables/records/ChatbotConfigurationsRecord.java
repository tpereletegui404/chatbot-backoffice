/*
 * This file is generated by jOOQ.
 */
package com.proyecto404.backoffice.infrastructure.jooq.generated.tables.records;


import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.ChatbotConfigurations;
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.pojos.ChatbotConfigurationsDto;

import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatbotConfigurationsRecord extends UpdatableRecordImpl<ChatbotConfigurationsRecord> implements Record8<Integer, String, String, Integer, BigDecimal, BigDecimal, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.chatbot_configurations.id</code>.
     */
    public ChatbotConfigurationsRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.chatbot_configurations.api_key</code>.
     */
    public ChatbotConfigurationsRecord setApiKey(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.api_key</code>.
     */
    public String getApiKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.chatbot_configurations.context</code>.
     */
    public ChatbotConfigurationsRecord setContext(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.context</code>.
     */
    public String getContext() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.chatbot_configurations.max_tokens</code>.
     */
    public ChatbotConfigurationsRecord setMaxTokens(Integer value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.max_tokens</code>.
     */
    public Integer getMaxTokens() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.chatbot_configurations.temperature</code>.
     */
    public ChatbotConfigurationsRecord setTemperature(BigDecimal value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.temperature</code>.
     */
    public BigDecimal getTemperature() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.chatbot_configurations.top_p</code>.
     */
    public ChatbotConfigurationsRecord setTopP(BigDecimal value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.top_p</code>.
     */
    public BigDecimal getTopP() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>public.chatbot_configurations.frequency_penalty</code>.
     */
    public ChatbotConfigurationsRecord setFrequencyPenalty(Integer value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>public.chatbot_configurations.frequency_penalty</code>.
     */
    public Integer getFrequencyPenalty() {
        return (Integer) get(6);
    }

    /**
     * Setter for
     * <code>public.chatbot_configurations.parameterpresence_penalty</code>.
     */
    public ChatbotConfigurationsRecord setParameterpresencePenalty(Integer value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for
     * <code>public.chatbot_configurations.parameterpresence_penalty</code>.
     */
    public Integer getParameterpresencePenalty() {
        return (Integer) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, String, Integer, BigDecimal, BigDecimal, Integer, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, String, String, Integer, BigDecimal, BigDecimal, Integer, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.ID;
    }

    @Override
    public Field<String> field2() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.API_KEY;
    }

    @Override
    public Field<String> field3() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.CONTEXT;
    }

    @Override
    public Field<Integer> field4() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.MAX_TOKENS;
    }

    @Override
    public Field<BigDecimal> field5() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.TEMPERATURE;
    }

    @Override
    public Field<BigDecimal> field6() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.TOP_P;
    }

    @Override
    public Field<Integer> field7() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.FREQUENCY_PENALTY;
    }

    @Override
    public Field<Integer> field8() {
        return ChatbotConfigurations.CHATBOT_CONFIGURATIONS.PARAMETERPRESENCE_PENALTY;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getApiKey();
    }

    @Override
    public String component3() {
        return getContext();
    }

    @Override
    public Integer component4() {
        return getMaxTokens();
    }

    @Override
    public BigDecimal component5() {
        return getTemperature();
    }

    @Override
    public BigDecimal component6() {
        return getTopP();
    }

    @Override
    public Integer component7() {
        return getFrequencyPenalty();
    }

    @Override
    public Integer component8() {
        return getParameterpresencePenalty();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getApiKey();
    }

    @Override
    public String value3() {
        return getContext();
    }

    @Override
    public Integer value4() {
        return getMaxTokens();
    }

    @Override
    public BigDecimal value5() {
        return getTemperature();
    }

    @Override
    public BigDecimal value6() {
        return getTopP();
    }

    @Override
    public Integer value7() {
        return getFrequencyPenalty();
    }

    @Override
    public Integer value8() {
        return getParameterpresencePenalty();
    }

    @Override
    public ChatbotConfigurationsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value2(String value) {
        setApiKey(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value3(String value) {
        setContext(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value4(Integer value) {
        setMaxTokens(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value5(BigDecimal value) {
        setTemperature(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value6(BigDecimal value) {
        setTopP(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value7(Integer value) {
        setFrequencyPenalty(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord value8(Integer value) {
        setParameterpresencePenalty(value);
        return this;
    }

    @Override
    public ChatbotConfigurationsRecord values(Integer value1, String value2, String value3, Integer value4, BigDecimal value5, BigDecimal value6, Integer value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatbotConfigurationsRecord
     */
    public ChatbotConfigurationsRecord() {
        super(ChatbotConfigurations.CHATBOT_CONFIGURATIONS);
    }

    /**
     * Create a detached, initialised ChatbotConfigurationsRecord
     */
    public ChatbotConfigurationsRecord(Integer id, String apiKey, String context, Integer maxTokens, BigDecimal temperature, BigDecimal topP, Integer frequencyPenalty, Integer parameterpresencePenalty) {
        super(ChatbotConfigurations.CHATBOT_CONFIGURATIONS);

        setId(id);
        setApiKey(apiKey);
        setContext(context);
        setMaxTokens(maxTokens);
        setTemperature(temperature);
        setTopP(topP);
        setFrequencyPenalty(frequencyPenalty);
        setParameterpresencePenalty(parameterpresencePenalty);
    }

    /**
     * Create a detached, initialised ChatbotConfigurationsRecord
     */
    public ChatbotConfigurationsRecord(ChatbotConfigurationsDto value) {
        super(ChatbotConfigurations.CHATBOT_CONFIGURATIONS);

        if (value != null) {
            setId(value.getId());
            setApiKey(value.getApiKey());
            setContext(value.getContext());
            setMaxTokens(value.getMaxTokens());
            setTemperature(value.getTemperature());
            setTopP(value.getTopP());
            setFrequencyPenalty(value.getFrequencyPenalty());
            setParameterpresencePenalty(value.getParameterpresencePenalty());
        }
    }
}

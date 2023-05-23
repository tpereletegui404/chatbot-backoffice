CREATE TABLE chatbot_configurations
(
    id                        SERIAL PRIMARY KEY,
    api_key                   varchar(100) NULL,
    context                   TEXT NULL,
    max_tokens                INTEGER       NOT NULL,
    temperature               DECIMAL(2, 1) NOT NULL,
    top_p                     DECIMAL(2, 1) NOT NULL,
    frequency_penalty         INTEGER       NOT NULL,
    parameterpresence_penalty INTEGER       NOT NULL
);
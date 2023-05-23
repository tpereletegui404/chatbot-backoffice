package com.proyecto404.backoffice.base.data.jooq

import org.jooq.DSLContext
import org.jooq.ExecuteContext
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultExecuteListener
import org.slf4j.LoggerFactory

class SQLLogger: DefaultExecuteListener() {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)

    override fun executeStart(ctx: ExecuteContext) {
        val dsl: DSLContext = DSL.using(
            ctx.dialect(),
            Settings().withRenderFormatted(false)
        )

        if (ctx.query() != null) {
            logger.info(dsl.renderInlined(ctx.query()))
        } else if (ctx.routine() != null) {
            logger.info(dsl.renderInlined(ctx.routine()))
        } else if (ctx.batchQueries().isNotEmpty()) {
            ctx.batchSQL().forEach { logger.info(it) }
        }
    }
}

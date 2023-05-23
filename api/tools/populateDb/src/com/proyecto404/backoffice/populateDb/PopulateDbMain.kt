package com.proyecto404.backoffice.populateDb

import com.nbottarini.asimov.environment.Env
import com.proyecto404.backoffice.populateDb.PopulateDb
import com.xenomachina.argparser.ArgParser

fun main(rawArgs: Array<String>) {
    Env.addSearchPath("./api")
    val args = ArgParser(rawArgs).parseInto(::Args)
    PopulateDb().run(args)
}

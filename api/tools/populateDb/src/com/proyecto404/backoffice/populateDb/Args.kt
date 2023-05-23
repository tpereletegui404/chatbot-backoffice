package com.proyecto404.backoffice.populateDb

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class Args(parser: ArgParser) {
    val scenario by parser.storing("-s", "--scenario", help = "Scenario to execute").default("default")
}

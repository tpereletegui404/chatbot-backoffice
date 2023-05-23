package com.proyecto404.backoffice.modules.common.base.data.jdbc

import java.sql.Savepoint

class SavePoints {
    var active = mutableListOf<Savepoint>()
    var released = mutableListOf<Savepoint>()
    var rollbacked = mutableListOf<Savepoint>()
}

package com.proyecto404.backoffice.base.lang

import java.time.LocalDate
import java.time.YearMonth

fun LocalDate.monthOfYear() = YearMonth.of(year, month)
/**
 * Copyright 2015, deepsense.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.deepsense.deeplang.doperations.inout

import io.deepsense.deeplang.params.{Params, StringParam}

trait JdbcParameters {
  this: Params =>

  val jdbcUrl = StringParam(
    name = "url",
    description = "JDBC connection URL.")
  setDefault(jdbcUrl,
    "jdbc:mysql://HOST:PORT/DATABASE?user=DB_USER&password=DB_PASSWORD")

  def getJdbcUrl: String = $(jdbcUrl)
  def setJdbcUrl(value: String): this.type = set(jdbcUrl, value)

  val jdbcDriverClassName = StringParam(
    name = "driver",
    description = "JDBC driver ClassName.")
  setDefault(jdbcDriverClassName, "com.mysql.jdbc.Driver")

  def getJdbcDriverClassName: String = $(jdbcDriverClassName)
  def setJdbcDriverClassName(value: String): this.type = set(jdbcDriverClassName, value)

  val jdbcTableName = StringParam(
    name = "table",
    description = "Table name.")

  def getJdbcTableName: String = $(jdbcTableName)
  def setJdbcTableName(value: String): this.type = set(jdbcTableName, value)
}

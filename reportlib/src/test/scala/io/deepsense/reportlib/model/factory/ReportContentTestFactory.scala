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

package io.deepsense.reportlib.model.factory

import io.deepsense.reportlib.model.{ReportType, ReportContent}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructField, StructType}

trait ReportContentTestFactory {

  import ReportContentTestFactory._

  def testReport: ReportContent = ReportContent(
    reportName,
    reportType,
    Seq(TableTestFactory.testEmptyTable),
    Map(ReportContentTestFactory.categoricalDistName ->
      DistributionTestFactory.testCategoricalDistribution(
        ReportContentTestFactory.categoricalDistName),
      ReportContentTestFactory.continuousDistName ->
      DistributionTestFactory.testContinuousDistribution(
        ReportContentTestFactory.continuousDistName)
    )
  )

}

object ReportContentTestFactory extends ReportContentTestFactory {
  val continuousDistName = "continuousDistributionName"
  val categoricalDistName = "categoricalDistributionName"
  val reportName = "TestReportContentName"
  val reportType = ReportType.Empty

  val someReport: ReportContent = ReportContent("empty", ReportType.Empty)
}

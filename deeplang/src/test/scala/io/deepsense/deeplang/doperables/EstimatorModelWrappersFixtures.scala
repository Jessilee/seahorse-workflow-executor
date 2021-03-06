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

package io.deepsense.deeplang.doperables

import scala.language.reflectiveCalls

import org.apache.spark.ml
import org.apache.spark.ml.param.{BooleanParam, DoubleParam, ParamMap}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame => SparkDataFrame}
import org.scalatest.mock.MockitoSugar

import io.deepsense.deeplang.{DeeplangTestSupport, ExecutionContext}
import io.deepsense.deeplang.doperables.report.Report
import io.deepsense.deeplang.doperables.serialization.SerializableSparkModel
import io.deepsense.deeplang.params.wrappers.spark.DoubleParamWrapper
import io.deepsense.deeplang.params.{Param, Params}

object EstimatorModelWrappersFixtures extends MockitoSugar with DeeplangTestSupport {

  trait HasNumericParam extends Params {
    val numericParamWrapper = new DoubleParamWrapper[
        ml.param.Params { val numericParam: ml.param.DoubleParam }](
      "name",
      "description",
      _.numericParam)
    setDefault(numericParamWrapper, 1.0)
  }

  class ExampleSparkEstimatorWrapper
    extends SparkEstimatorWrapper
      [ExampleSparkModel, ExampleSparkEstimator, ExampleSparkModelWrapper]
    with HasNumericParam {

    def setNumericParamWrapper(value: Double): this.type = set(numericParamWrapper, value)

    override def report: Report = ???
    override val params: Array[Param[_]] = declareParams(numericParamWrapper)
  }

  class ExampleSparkEstimator extends ml.Estimator[ExampleSparkModel] {

    def this(id: String) = this()

    override val uid: String = "estimatorId"

    val numericParam = new DoubleParam(uid, "numeric", "description")

    def setNumericParam(value: Double): this.type = set(numericParam, value)

    override def fit(dataset: SparkDataFrame): ExampleSparkModel = {
      require($(numericParam) == paramValueToSet)
      fitModel
    }

    val transformSchemaShouldThrowParam = new BooleanParam(uid, "throwing", "description")
    setDefault(transformSchemaShouldThrowParam -> false)

    def setTransformSchemaShouldThrow(b: Boolean): this.type =
      set(transformSchemaShouldThrowParam, b)

    override def transformSchema(schema: StructType): StructType = {
      if ($(transformSchemaShouldThrowParam)) {
        throw exceptionThrownByTransformSchema
      }
      require($(numericParam) == paramValueToSet)
      transformedSchema
    }

    override def copy(extra: ParamMap): ml.Estimator[ExampleSparkModel] = {
      defaultCopy(extra)
    }
  }

  class ExampleSparkModel extends ml.Model[ExampleSparkModel] {

    override val uid: String = "modelId"

    val numericParam = new DoubleParam(uid, "name", "description")

    def setNumericParam(value: Double): this.type = set(numericParam, value)

    override def copy(extra: ParamMap): ExampleSparkModel =
      extra.toSeq.foldLeft(new ExampleSparkModel())((model, paramPair) => model.set(paramPair))

    override def transform(dataset: SparkDataFrame): SparkDataFrame = {
      require($(numericParam) == paramValueToSet)
      fitDataFrame
    }

    override def transformSchema(schema: StructType): StructType = ???
  }

  class ExampleSparkModelWrapper
    extends SparkModelWrapper[ExampleSparkModel, ExampleSparkEstimator]
    with HasNumericParam {

    def setNumericParamWrapper(value: Double): this.type = set(numericParamWrapper, value)

    override def report: Report = ???
    override val params: Array[Param[_]] = declareParams(numericParamWrapper)

    override protected def loadModel(
      ctx: ExecutionContext,
      path: String): SerializableSparkModel[ExampleSparkModel] = ???
  }

  val fitModel = new ExampleSparkModel()
  val fitDataFrame = createSparkDataFrame()
  val transformedSchema = createSchema()
  val paramValueToSet = 12.0

  val exceptionThrownByTransformSchema = new Exception("mock exception")
}

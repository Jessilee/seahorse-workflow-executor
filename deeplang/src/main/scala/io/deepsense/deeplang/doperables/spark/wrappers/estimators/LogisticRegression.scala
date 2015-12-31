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

package io.deepsense.deeplang.doperables.spark.wrappers.estimators

import org.apache.spark.ml.classification.{LogisticRegression => SparkLogisticRegression, LogisticRegressionModel => SparkLogisticRegressionModel}

import io.deepsense.deeplang.ExecutionContext
import io.deepsense.deeplang.doperables.spark.wrappers.models.LogisticRegressionModel
import io.deepsense.deeplang.doperables.spark.wrappers.params.common._
import io.deepsense.deeplang.doperables.{Report, SparkEstimatorWrapper}
import io.deepsense.deeplang.params.Param

class LogisticRegression
  extends SparkEstimatorWrapper[
    SparkLogisticRegressionModel,
    SparkLogisticRegression,
    LogisticRegressionModel]
  with ProbabilisticClassifierParams
  with HasLabelColumnParam
  with HasThreshold
  with HasRegularizationParam
  with HasElasticNetParam
  with HasMaxIterationsParam
  with HasTolerance
  with HasFitIntercept
  with HasStandardization {

  setDefault(maxIterations, 100.0)

  override def report(executionContext: ExecutionContext): Report = Report()

  override val params: Array[Param[_]] = declareParams(
    elasticNetParam,
    fitIntercept,
    maxIterations,
    regularizationParam,
    tolerance,
    standardization,
    featuresColumn,
    labelColumn,
    probabilityColumn,
    rawPredictionColumn,
    predictionColumn,
    threshold)
}

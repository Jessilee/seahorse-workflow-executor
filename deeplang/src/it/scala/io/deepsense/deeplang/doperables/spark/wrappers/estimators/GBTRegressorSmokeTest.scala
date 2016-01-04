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

import org.apache.spark.ml.regression.{GBTRegressor => SparkGBTRegressor}

import io.deepsense.deeplang.params.ParamPair
import io.deepsense.deeplang.params.selections.NameSingleColumnSelection

class GBTRegressorSmokeTest extends AbstractEstimatorModelWrapperSmokeTest[SparkGBTRegressor] {

  override def className: String = "GBTRegressor"

  override val estimatorWrapper = new GBTRegressor()

  private val labelColumnName = "myRating"

  import estimatorWrapper._

  override val estimatorParams: Seq[ParamPair[_]] = Seq(
    featuresColumn -> NameSingleColumnSelection("myFeatures"),
    impurity -> GBTRegressor.Variance(),
    labelColumn -> NameSingleColumnSelection(labelColumnName),
    lossType -> GBTRegressor.Squared(),
    maxBins -> 2.0,
    maxDepth -> 6.0,
    maxIterations -> 10.0,
    minInfoGain -> 0.0,
    minInstancesPerNode -> 1,
    predictionColumn -> "prediction",
    seed -> 100.0,
    stepSize -> 0.11,
    subsamplingRate -> 0.999
  )
}

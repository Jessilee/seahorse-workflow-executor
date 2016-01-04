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

package io.deepsense.deeplang.doperations.spark.wrappers.estimators

import io.deepsense.deeplang.DOperation.Id
import io.deepsense.deeplang.doperables.spark.wrappers.estimators.GBTRegressor
import io.deepsense.deeplang.doperations.EstimatorAsFactory

class CreateGBTRegressor extends EstimatorAsFactory[GBTRegressor] {

  override val id: Id = "e18c13f8-2108-46f0-979f-bba5a11ea312"
  override val name: String = "GBT Regressor"
  override val description: String =
    "Gradient-Boosted Trees (GBTs) learning algorithm for regression." +
      " It supports both continuous and categorical features."
}

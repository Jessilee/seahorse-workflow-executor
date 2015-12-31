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
import io.deepsense.deeplang.doperables.spark.wrappers.estimators.CountVectorizer
import io.deepsense.deeplang.doperations.EstimatorAsFactory

class CreateCountVectorizer extends EstimatorAsFactory[CountVectorizer] {

  override val id: Id = "e640d7df-d464-4ac0-99c4-235c29a0aa31"
  override val name: String = "Count Vectorizer"
  override val description: String =
    "Extracts a vocabulary from document collections and generates a CountVectorizerModel"
}

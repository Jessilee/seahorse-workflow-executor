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

package io.deepsense.workflowexecutor.session.storage

import scala.collection.concurrent.TrieMap

import io.deepsense.commons.models.Id
import io.deepsense.deeplang.DataFrameStorage
import io.deepsense.deeplang.DataFrameStorage.{DataFrameId, DataFrameName}
import io.deepsense.deeplang.doperables.dataframe.DataFrame

class DataFrameStorageImpl extends DataFrameStorage {

  private val storage: TrieMap[DataFrameId, DataFrame] = TrieMap.empty

  override def get(
      workflowId: Id,
      dataFrameName: DataFrameName): Option[DataFrame] =
    storage.get((workflowId, dataFrameName))

  override def put(
      workflowId: Id,
      dataFrameName: DataFrameName,
      dataFrame: DataFrame): Unit =
    storage.put((workflowId, dataFrameName), dataFrame)

  override def listDataFrameNames(
      workflowId: Id): Iterable[DataFrameName] =
    storage.keys
      .collect { case (w, d) if w == workflowId => d }
 }
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

name := "seahorse"

lazy val commons                = project
lazy val deeplang               = project dependsOn (
  commons,
  commons % "test->test",
  graph,
  graph % "test->test",
  reportlib,
  reportlib % "test->test")
lazy val docgen                 = project dependsOn (
  deeplang)
lazy val graph                  = project dependsOn (
  commons,
  commons % "test->test")
lazy val workflowjson           = project dependsOn (commons, deeplang, graph, models)
lazy val models                 = project dependsOn (commons, deeplang, graph)
lazy val reportlib              = project dependsOn commons
lazy val workflowexecutor       = project dependsOn (
  commons % "test->test",
  deeplang,
  deeplang % "test->test",
  deeplang % "test->it",
  models,
  workflowjson,
  workflowjson % "test -> test")

// Sequentially perform integration tests
addCommandAlias("ds-it",
    ";commons/it:test " +
    ";deeplang/it:test " +
    ";graph/it:test " +
    ";workflowjson/it:test " +
    ";models/it:test " +
    ";reportlib/it:test " +
    ";workflowexecutor/it:test")

addCommandAlias("sPublish", "aetherDeploy")
addCommandAlias("sPublishLocal", "aetherInstall")
addCommandAlias("generateExamples", "deeplang/it:testOnly io.deepsense.deeplang.doperations.examples.*")

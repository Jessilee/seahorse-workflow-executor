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

package io.deepsense.deeplang.doperations

import spray.json.{JsNumber, JsObject}

import io.deepsense.deeplang._
import io.deepsense.deeplang.doperables.MetricValue
import io.deepsense.deeplang.doperables.dataframe.DataFrame
import io.deepsense.deeplang.doperations.MockDOperablesFactory._
import io.deepsense.deeplang.doperations.exceptions.TooManyPossibleTypesException
import io.deepsense.deeplang.exceptions.DeepLangMultiException
import io.deepsense.deeplang.inference.{InferContext, InferenceWarnings}
import io.deepsense.deeplang.params.ParamsMatchers._

class EvaluateSpec extends UnitSpec with DeeplangTestSupport {

  "Evaluate" should {

    "evaluate input Evaluator on input DataFrame with proper parameters set" in {
      val evaluator = new MockEvaluator

      def testEvaluate(op: Evaluate, expected: MetricValue): Unit = {
        val Vector(outputDataFrame) = op.execute(mock[ExecutionContext])(
          Vector(mock[DataFrame], evaluator))
        outputDataFrame shouldBe expected
      }

      val op1 = Evaluate()
      testEvaluate(op1, metricValue1)

      val paramsForEvaluator = JsObject(evaluator.paramA.name -> JsNumber(2))
      val op2 = Evaluate().setEvaluatorParams(paramsForEvaluator)
      testEvaluate(op2, metricValue2)
    }

    "not modify params in input Evaluator instance upon execution" in {
      val evaluator = new MockEvaluator
      val originalEvaluator = evaluator.replicate()

      val paramsForEvaluator = JsObject(evaluator.paramA.name -> JsNumber(2))
      val op = Evaluate().setEvaluatorParams(paramsForEvaluator)
      op.execute(mock[ExecutionContext])(Vector(mock[DataFrame], evaluator))

      evaluator should have (theSameParamsAs (originalEvaluator))
    }

    "infer knowledge from input Evaluator on input DataFrame with proper parameters set" in {
      val evaluator = new MockEvaluator

      def testInference(op: Evaluate, expectedKnowledge: DKnowledge[MetricValue]): Unit = {
        val inputDF = DataFrame.forInference(createSchema())
        val (knowledge, warnings) = op.inferKnowledge(mock[InferContext])(
          Vector(DKnowledge(inputDF), DKnowledge(evaluator)))
        // Currently, InferenceWarnings are always empty.
        warnings shouldBe InferenceWarnings.empty
        val Vector(dataFrameKnowledge) = knowledge
        dataFrameKnowledge shouldBe expectedKnowledge
      }

      val op1 = Evaluate()
      testInference(op1, metricValueKnowledge1)

      val paramsForEvaluator = JsObject(evaluator.paramA.name -> JsNumber(2))
      val op2 = Evaluate().setEvaluatorParams(paramsForEvaluator)
      testInference(op2, metricValueKnowledge2)
    }

    "not modify params in input Evaluator instance upon inference" in {
      val evaluator = new MockEvaluator
      val originalEvaluator = evaluator.replicate()

      val paramsForEvaluator = JsObject(evaluator.paramA.name -> JsNumber(2))
      val op = Evaluate().setEvaluatorParams(paramsForEvaluator)
      val inputDF = DataFrame.forInference(createSchema())
      op.inferKnowledge(mock[InferContext])(Vector(DKnowledge(inputDF), DKnowledge(evaluator)))

      evaluator should have (theSameParamsAs (originalEvaluator))
    }

    "throw Exception" when {
      "there is more than one Evaluator in input Knowledge" in {
        val inputDF = DataFrame.forInference(createSchema())
        val evaluators = Set[DOperable](new MockEvaluator, new MockEvaluator)

        val op = Evaluate()
        a [TooManyPossibleTypesException] shouldBe thrownBy {
          op.inferKnowledge(mock[InferContext])(
            Vector(DKnowledge(inputDF), DKnowledge(evaluators)))
        }
      }
      "values of dynamic parameters are invalid" in {
        val evaluator = new MockEvaluator

        val inputDF = DataFrame.forInference(createSchema())

        val paramsForEvaluator = JsObject(evaluator.paramA.name -> JsNumber(-2))
        val evaluatorWithParams = Evaluate().setEvaluatorParams(paramsForEvaluator)

        a [DeepLangMultiException] shouldBe thrownBy {
          evaluatorWithParams.inferKnowledge(mock[InferContext])(
            Vector(DKnowledge(inputDF), DKnowledge(evaluator)))
        }
      }
    }
  }
}

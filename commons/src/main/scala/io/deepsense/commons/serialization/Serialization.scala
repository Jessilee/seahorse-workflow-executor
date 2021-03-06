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

package io.deepsense.commons.serialization

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

trait Serialization {

  def deserialize[T](bytes: Array[Byte]): T = {
    val bufferIn = new ByteArrayInputStream(bytes)
    val streamIn = new ObjectInputStream(bufferIn)
    try {
      streamIn.readObject().asInstanceOf[T]
    } finally {
      streamIn.close()
    }
  }

  def serialize[T](objectToSerialize: T): Array[Byte] = {
    val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(byteArrayOutputStream)
    try {
      oos.writeObject(objectToSerialize)
      oos.flush()
      byteArrayOutputStream.toByteArray
    } finally {
      oos.close()
    }
  }

  def serializeDeserialize[T](obj: T): T = deserialize[T](serialize[T](obj))
}

object Serialization extends Serialization

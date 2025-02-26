/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.status.protobuf.sql

import org.apache.spark.sql.streaming.ui.StreamingQueryProgressWrapper
import org.apache.spark.status.protobuf.{ProtobufSerDe, StoreTypes}

class StreamingQueryProgressWrapperSerializer extends ProtobufSerDe {

  override val supportClass: Class[_] =
    classOf[StreamingQueryProgressWrapper]

  override def serialize(input: Any): Array[Byte] = {
    val data = input.asInstanceOf[StreamingQueryProgressWrapper]
    val builder = StoreTypes.StreamingQueryProgressWrapper.newBuilder()
    builder.setProgress(StreamingQueryProgressSerializer.serialize(data.progress))
    builder.build().toByteArray
  }

  override def deserialize(bytes: Array[Byte]): StreamingQueryProgressWrapper = {
    val processWrapper = StoreTypes.StreamingQueryProgressWrapper.parseFrom(bytes)
    new StreamingQueryProgressWrapper(
      StreamingQueryProgressSerializer.deserialize(processWrapper.getProgress))
  }
}

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

import org.apache.spark.sql.streaming.SinkProgress
import org.apache.spark.status.protobuf.StoreTypes

private[protobuf] object SinkProgressSerializer {

  def serialize(sink: SinkProgress): StoreTypes.SinkProgress = {
    val builder = StoreTypes.SinkProgress.newBuilder()
    builder.setDescription(sink.description)
    builder.setNumOutputRows(sink.numOutputRows)
    sink.metrics.forEach {
      case (k, v) => builder.putMetrics(k, v)
    }
    builder.build()
  }

  def deserialize(sink: StoreTypes.SinkProgress): SinkProgress = {
    new SinkProgress(
      description = sink.getDescription,
      numOutputRows = sink.getNumOutputRows,
      metrics = sink.getMetricsMap
    )
  }
}

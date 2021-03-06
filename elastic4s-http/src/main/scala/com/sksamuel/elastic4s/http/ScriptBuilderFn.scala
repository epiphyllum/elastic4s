package com.sksamuel.elastic4s.http

import com.sksamuel.elastic4s.json.{XContentBuilder, XContentFactory}
import com.sksamuel.elastic4s.script.{ScriptDefinition, ScriptType}

object ScriptBuilderFn {
  def apply(script: ScriptDefinition): XContentBuilder = {

    val builder = XContentFactory.jsonBuilder()

    script.lang.foreach(builder.field("lang", _))

    script.scriptType match {
      case ScriptType.File => builder.field("file", script.script)
      case ScriptType.Inline => builder.field("inline", script.script)
    }

    if (script.params.nonEmpty) {
      builder.startObject("params")
      script.params.foreach { case (key, value) =>
        builder.field(key, value.toString)
      }
      builder.endObject()
    }

    if (script.options.nonEmpty) {
      builder.startObject("options")
      script.params.foreach { case (key, value) =>
        builder.field(key, value.toString)
      }
      builder.endObject()
    }

    builder.endObject()
  }
}

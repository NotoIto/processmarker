package jp.notoito.processmarker.ui

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Main")
object Main {
  val mainView = ScalaComponent
    .builder[String]("MainView")
    .render_P(name => <.div("Hello", name))
    .build

  @JSExport
  def main(): Unit = {
    mainView("world")
  }

  @JSExport
  def text(): String = { "hello" }

}

import sbt._
object Dependencies {
  private lazy val scalaTestVersion = "3.2.3"
  private lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )

  private lazy val circeVersion = "0.12.3"
  private lazy val circeDependencies = Seq(
    "io.circe" %% "circe-core"  % circeVersion,
    "io.circe" %% "circe-generic"  % circeVersion,
    "io.circe" %% "circe-parser"  % circeVersion
  )

  lazy val scalaJsDomVersion = "1.1.0"
  lazy val scalaJsReactVersion = "1.7.7"

  lazy val reactJsVersion = "16.5.2"

  lazy val domainDependencies = circeDependencies ++ testDependencies
  lazy val appDependencies = testDependencies
}

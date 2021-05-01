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

  lazy val reactJsVersion = "17.0.1"
  lazy val reactJsNpmDependencies = Seq(
    "react" -> reactJsVersion,
    "react-dom" -> reactJsVersion
  )
  lazy val slinkyVersion = "0.6.6"

  lazy val domainDependencies = circeDependencies ++ testDependencies
  lazy val appDependencies = testDependencies
}

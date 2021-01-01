import org.scalajs.jsdependencies.sbtplugin.JSDependenciesPlugin.autoImport.packageJSDependencies
import sbt.Keys.skip
import sbt._
object Dependencies {
  private lazy val scalaTestVersion = "3.2.3"
  private lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )

  private lazy val silencerVersion = "1.6.0"
  private lazy val silencerDependencies: Seq[ModuleID] = Seq(
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
  )

  private lazy val circeVersion = "0.12.3"
  private lazy val circeDependencies = Seq(
    "io.circe" %% "circe-core"  % circeVersion,
    "io.circe" %% "circe-generic"  % circeVersion,
    "io.circe" %% "circe-parser"  % circeVersion
  )

  lazy val domainDependencies = circeDependencies ++ testDependencies
  lazy val appDependencies = testDependencies
}

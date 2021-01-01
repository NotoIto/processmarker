name := "processmarker"

version := "0.1"

import Dependencies._

lazy val commonSettings = Seq(
  organization := "jp.notoito",
  idePackagePrefix := Some("jp.notoito.processmarker"),
  scalaVersion := "2.13.4",
  scalacOptions := Seq(
    "Xfatal-warnings",
    "-deprecation",
    "-feature",
    "-Wunused:locals",
    "-Wunused:imports",
    "-Wunused:patvars",
    "-Wunused:privates",
    "-target:jvm-1.8"
  ),
  scalafmtOnCompile in ThisBuild := true,
  test in assembly := {}
)

val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case _ => MergeStrategy.first
  },
  assemblyJarName in assembly := s"${name.value}.jar",
  publishArtifact in(Compile, packageBin) := false,
  publishArtifact in(Compile, packageSrc) := false,
  publishArtifact in(Compile, packageDoc) := false
)

lazy val root = (project in file("."))
  .aggregate(
    domain,
    app,
    ui,
    infrastructure
  )
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false
  )

lazy val domain = (project in file("modules/domain"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies
  )

lazy val app = (project in file("modules/app"))
  .dependsOn(domain, infrastructure)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
  )

lazy val scalaJsDomVersion = "1.1.0"
lazy val scalaJsReactVersion = "1.7.7"

lazy val reactJsVersion = "16.5.2"
lazy val reactJsDependencies = Seq("org.webjars" % "react" % reactJsVersion / "react-with-addons.js" commonJSName "React")

lazy val ui = (project in file("modules/ui"))
  .enablePlugins(ScalaJSPlugin, JSDependenciesPlugin)
  .dependsOn(domain)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion,
      "com.github.japgolly.scalajs-react" %%% "core" % scalaJsReactVersion,
      "com.github.japgolly.scalajs-react" %%% "extra" % scalaJsReactVersion
    ),
    jsDependencies ++= reactJsDependencies
  )

lazy val infrastructure = (project in file("modules/infrastructure"))
  .dependsOn(domain)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
    parallelExecution in Test := false
  )

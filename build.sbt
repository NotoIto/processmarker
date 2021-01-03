name := "processmarker"

version := "0.1"

import Dependencies._
import sbtcrossproject.CrossType

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

lazy val assemblySettings = Seq(
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
    domain.jvm,
    app,
    ui,
    infrastructure.jvm
  )
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false
  )

lazy val domain = (crossProject(JSPlatform, JVMPlatform) crossType CrossType.Pure in file("modules/domain"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies
  )

lazy val app = (project in file("modules/app"))
  .dependsOn(domain.jvm, infrastructure.jvm)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
  )

lazy val reactJsDependencies = Seq("org.webjars" % "react" % reactJsVersion / "react-with-addons.js" commonJSName "React")
lazy val scalaJsDependenciesSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion,
    "com.github.japgolly.scalajs-react" %%% "core" % scalaJsReactVersion,
    "com.github.japgolly.scalajs-react" %%% "extra" % scalaJsReactVersion
  )
)
lazy val ui = (project in file("modules/ui"))
  .enablePlugins(ScalaJSPlugin, JSDependenciesPlugin)
  .dependsOn(domain.js)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(scalaJsDependenciesSettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
    scalaJSUseMainModuleInitializer := true,
    jsDependencies ++= reactJsDependencies
  )

lazy val infrastructure = (crossProject(JSPlatform, JVMPlatform) crossType CrossType.Full in file("modules/infrastructure"))
  .dependsOn(domain)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
    parallelExecution in Test := false
  )

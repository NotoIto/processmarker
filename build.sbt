name := "processmarker"

version := "0.1"

import Dependencies._
import sbtcrossproject.CrossType

lazy val commonSettings = Seq(
  organization := "jp.notoito",
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
    appDaemon,
    infrastructure.jvm
  )
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false
  )
  .settings(
    commands += Command.command("compileUiFast") { state =>
      "uiJS / fastOptJS" ::
      "uiJS / copyReactSrc" ::
      state
    },
    commands += Command.command("compileUiFull") { state =>
      "uiJS / fullOptJS" ::
      "uiJS / copyReactSrc" ::
      state
    }
  )

lazy val domain = (crossProject(JSPlatform, JVMPlatform) crossType CrossType.Full in file("modules/domain"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .jvmSettings(commonSettings: _*)
  .jsSettings(commonSettings: _*)
  .jvmSettings(
    libraryDependencies ++= domainDependencies
  )

lazy val appDaemon = (project in file("modules/app/daemon"))
  .dependsOn(domain.jvm, infrastructure.jvm)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= domainDependencies,
  )
lazy val appConfigurator = (crossProject(JSPlatform) crossType CrossType.Pure in file("modules/app/configurator"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(domain, infrastructure)
  .settings(commonSettings: _*)
  .jsSettings(
    npmDependencies in Compile ++= reactJsNpmDependencies,
  )

lazy val slinkyJsDependenciesSettings = Seq(
  libraryDependencies ++= Seq(
    "me.shadaj" %%% "slinky-core" % slinkyVersion,
    "me.shadaj" %%% "slinky-web" % slinkyVersion,
    "me.shadaj" %%% "slinky-native" % slinkyVersion,
    "me.shadaj" %%% "slinky-hot" % slinkyVersion,
    "me.shadaj" %%% "slinky-scalajsreact-interop" % slinkyVersion
  )
)
lazy val copyReactSrc = taskKey[Unit]("Copy react source file.")
lazy val ui = (crossProject(JSPlatform) crossType CrossType.Pure in file("modules/ui"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(domain, appConfigurator)
  .settings(commonSettings: _*)
  .jsSettings(slinkyJsDependenciesSettings: _*)
  .jsSettings(
    npmDependencies in Compile ++= reactJsNpmDependencies,
    scalacOptions ++= Seq(
      "-Ymacro-annotations"
    )
  )
  .settings(
    crossTarget in (Compile, fullOptJS) := target.value / "html",
    crossTarget in (Compile, fastOptJS) := target.value / "html",
    target in (Compile, fullOptJS) := target.value / "html",
    target in (Compile, fastOptJS) := target.value / "html",
    copyReactSrc := {
        val from =  baseDirectory.value / ".." / "src" / "react"
        val to = target.value / "html"
        to.mkdirs()
        IO.copyDirectory(from, to)
        println(s"Copied from $from to $to")
      }
  )

lazy val infrastructure = (crossProject(JSPlatform, JVMPlatform) crossType CrossType.Full in file("modules/infrastructure"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(domain)
  .jvmSettings(commonSettings: _*)
  .jsSettings(commonSettings: _*)
  .jvmSettings(assemblySettings: _*)
  .jvmSettings(
    libraryDependencies ++= domainDependencies,
    parallelExecution in Test := false
  )

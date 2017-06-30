organization := "com.iservport"

name := "iservport-vusage-trans"

scalaVersion := "2.11.8"

sbtVersion := "0.13.15"

version := "0.1.DEV"

lazy val root = (project in file("."))
  .enablePlugins(JavaServerAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    mainClass in Compile := Some("com.iservport.vusage.Application"),
    packageName in Docker := "iservport/iservport-vusage",
    dockerBaseImage := "anapsix/alpine-java:latest",
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(8080),
    dockerExposedVolumes := Seq("/opt/data"),
    dockerRepository := Some("docker.io")
  )

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"            % "2.1.0",
  "org.apache.spark"  %% "spark-sql"             % "2.1.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "2.0.0",
  "com.typesafe"       % "config"                % "1.3.1",
  "org.projectlombok"  % "lombok"                % "1.16.8"

)
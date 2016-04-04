

name := "dcos-intro"

organization := "ftrossbach"

version := "1.0"

scalaVersion := "2.11.7"

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}

imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"${organization.value}/${name.value}:latest")
)

buildOptions in docker := BuildOptions(cache = false)


libraryDependencies ++= Seq(

  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.2",
  "com.softwaremill.reactivekafka" %% "reactive-kafka-core" % "0.10.0"

)
name := """ShiftPlanning"""

version := "1.0"

scalaVersion := "2.11.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= List(
  // Scala Libraries
  // ---------------
  "org.virtuslab" %% "unicorn-play" % "0.6.2",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
  //
  // Java Libraries
  // --------------
  "joda-time" % "joda-time" % "2.4",
  "org.joda" % "joda-convert" % "1.6",
  "org.hsqldb" % "hsqldb" % "2.3.2",
  "org.webjars" % "bootstrap" % "3.3.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play" % "2.3.6",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0"
)
name := """ShiftPlanning"""

version := "1.0"

scalaVersion := "2.11.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL+"/.m2/repository"

libraryDependencies ++= List(
  // Scala Libraries
  // ---------------
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
  //
  // Java Libraries
  // --------------
  "com.google.inject" % "guice" % "3.0",
  "javax.inject" % "javax.inject" % "1",
  "joda-time" % "joda-time" % "2.4",
  "org.joda" % "joda-convert" % "1.6",
  //
  // AngularJS Libraries
  // --------------
  "org.webjars" % "bootstrap" % "3.3.2",
  "org.webjars" % "angularjs" % "1.3.13",
  "org.webjars" % "angular-ui-bootstrap" % "0.12.1",
  "org.webjars" % "angular-translate" % "2.6.0",
  "org.webjars" % "checklist-model" % "0.2.4"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= List(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "com.typesafe.play" %% "play" % "2.3.7"
)
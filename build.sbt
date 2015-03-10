
libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.0.1" % "test")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")
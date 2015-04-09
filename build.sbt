import play.Project._

name := "PersonCrud"

version := "1.0"

libraryDependencies ++= Seq(
  javaJdbc, 
  javaJpa, 
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
  )

playJavaSettings

Keys.fork in (Test) := false

javaOptions in Test += "-Dtest.timeout=1000000"
import com.typesafe.sbt.packager.docker._

name := "akka-bootstrap"

version := "1.0"

scalaVersion := "2.12.5"

enablePlugins(JavaServerAppPackaging)


libraryDependencies += "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % "0.10.0"
libraryDependencies += "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % "0.10.0"

dockerEntrypoint ++= Seq(
  """-Dakka.remote.netty.tcp.hostname="$(eval "echo $AKKA_REMOTING_BIND_HOST")"""",
  """-Dakka.management.http.hostname="$(eval "echo $AKKA_REMOTING_BIND_HOST")""""
)

dockerCommands :=
  dockerCommands.value.flatMap {
    case ExecCmd("ENTRYPOINT", args @ _*) => Seq(Cmd("ENTRYPOINT", args.mkString(" ")))
    case v => Seq(v)
  }

dockerRepository := Some("aacevedoosorio")
dockerCommands += Cmd("USER", "root")

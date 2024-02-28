package observer

import observer.device.{BrightnessSensor, Switch}
import observer.log.TerminalLogger
import observer.user.Account

@main def ObserverMain(): Unit =
  val user = Account("foo", "foobar", "foo@bar.local")
  val sensor = BrightnessSensor("kitchenBrightness")
  val switch = {
    Switch("kitchenLightSwitch")
  }
  val obs = TerminalLogger()
  user.attach(obs)
  sensor.attach(obs)
  switch.attach(obs)

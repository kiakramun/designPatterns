package observer.device

import observer.log.SensorChange.NoChange
import observer.log.{Entity, EntityChange, Loggable, SensorChange}

import scala.util.Random

case class BrightnessSensor(name: String) extends Sensor with Loggable[Entity, EntityChange]:
  private var measure: Double = 0.0d

  override def currentMeasure: Double = measure

  override def remeasure(): Unit = measure = Random.between(0.0d, 1.0d+Double.MinPositiveValue)
  private val tmp: Double = measure
  var delta: SensorChange = SensorChange.NoChange
  if (measure < tmp) {
    delta = SensorChange.Increase(tmp - measure)
  } else {
    delta = SensorChange.Decrease(measure - tmp)
  }
  measure = tmp
  notifyObserver(this, delta)

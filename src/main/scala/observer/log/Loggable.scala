package observer.log

import scala.collection.mutable.Set as mSet

trait Loggable[Subject, Delta]:
  val observers: mSet[LoggingObserver[Subject, Delta]] = mSet.empty

  def attach(observer: LoggingObserver[Subject, Delta]): Unit = observers += observer
  def detach(observer: LoggingObserver[Subject, Delta]): Unit = observers -= observer

  def notifyObserver(subject: Subject, delta: Delta): Unit =
    val timestamp = System.currentTimeMillis()
    observers.foreach(_.update(subject, delta, timestamp))
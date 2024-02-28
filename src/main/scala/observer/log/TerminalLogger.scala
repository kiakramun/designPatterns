package observer.log

class TerminalLogger extends LoggingObserver[Entity, EntityChange] {
  override def update(subject: Entity, delta: EntityChange, timestamp: Long): Unit =
    println(s"$timestamp;${subject.identifier};${delta.pretty}")
}

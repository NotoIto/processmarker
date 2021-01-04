package jp.notoito.processmarker.domain.process

case class ProcessName(value: String) extends AnyVal
case class ProcessID(value: Long)
case class Process(
    processName: ProcessName,
    processID: ProcessID
)

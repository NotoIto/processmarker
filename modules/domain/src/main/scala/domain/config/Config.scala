package jp.notoito.processmarker.domain.config

import jp.notoito.processmarker.domain.cpuaffinity.CPUAffinity

case class CPUAffinityByProcess(
    process: Process,
    cpuAffinity: CPUAffinity
)

case class PollingInterval(value: Int) extends AnyVal
case class SchemaVersion(value: Int)   extends AnyVal

case class Config(
    schemaVersion: SchemaVersion,
    cpuAffinityByProcess: Seq[CPUAffinityByProcess],
    pollingInterval: PollingInterval
)

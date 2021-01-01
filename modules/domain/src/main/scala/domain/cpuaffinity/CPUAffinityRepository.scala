package jp.notoito.processmarker.domain.cpuaffinity

import jp.notoito.processmarker.domain.DomainDefinedError

trait CPUAffinityRepository {
  def update(cpuAffinity: CPUAffinity)(process: Process): Either[DomainDefinedError, CPUAffinity]
}

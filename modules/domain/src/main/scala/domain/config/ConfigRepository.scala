package jp.notoito.processmarker.domain.config

import jp.notoito.processmarker.domain.DomainDefinedError

trait ConfigRepository {
  def read: Either[DomainDefinedError, Config]
  def update(config: Config): Either[DomainDefinedError, Config]
}

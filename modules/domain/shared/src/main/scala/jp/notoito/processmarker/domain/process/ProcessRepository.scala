package jp.notoito.processmarker.domain.process

import jp.notoito.processmarker.domain.DomainDefinedError

trait ProcessRepository {
  def findBy(executeFileName: String): Either[DomainDefinedError, Process]
  def findAll: Either[DomainDefinedError, Seq[Process]]
}

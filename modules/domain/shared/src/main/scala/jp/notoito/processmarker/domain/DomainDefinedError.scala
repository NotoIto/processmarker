package jp.notoito.processmarker.domain

import scala.util.Try

sealed class DomainDefinedError(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
case class SystemError(item: String, cause: Throwable = null)                    extends DomainDefinedError(s"SystemError$item", cause)

object Try2EitherSystemErrorImplicit {
  class Try2EitherSystemErrorImplicit[T](t: Try[T]) {
    def toEitherSystemError(item: String): Either[SystemError, T] = t.fold(e => Left(SystemError(item, e)), Right(_))
  }
  implicit def try2EitherSystemErrorImplicit[T](t: Try[T]): Try2EitherSystemErrorImplicit[T] = new Try2EitherSystemErrorImplicit(t)
}

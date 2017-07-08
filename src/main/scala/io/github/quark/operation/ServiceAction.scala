package io.github.quark.operation

import shapeless._
import shapeless.ops.hlist.IsHCons

sealed trait ServiceAction[L <: HList] {
  def operation[T <: OperationAction](
      implicit operationSelector: OperationSelector[L, T]): T
}

object ServiceAction {

  final case class Service[L <: HList](path: String)(ops: L)(
      implicit lUBConstraint: LUBConstraint[L, OperationAction],
      isDistinctConstraint: IsDistinctConstraint[L],
      isHCons: IsHCons[L])
      extends ServiceAction[L] {
    def operation[T <: OperationAction](
        implicit operationSelector: OperationSelector[L, T]): T = {
      operationSelector(ops)
    }
  }

}

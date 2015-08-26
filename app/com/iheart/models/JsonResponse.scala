package com.iheart.models

import play.Logger


case class VclRequest(hostnames: Seq[Either[HostnameError,Hostname]],
                      orderedRules: Seq[Either[RuleError,Rule]],
                      globalRules: Seq[Either[RuleError,Rule]])


object VclRequest extends ModelValidations {

  def build(hostnames: Seq[Either[HostnameError,Hostname]],
            orderedRules: Seq[Either[RuleError,Rule]],
            globalRules: Seq[Either[RuleError,Rule]]): Either[RequestError,VclRequest] = {
    isValid(Seq(hasIndex(orderedRules))) match {
      case Left(x) => Logger.info("Request is invalid, returning " + x); Left(RequestError(x))
      case Right(y) => Logger.info("Request is valid, returning:" +y); Right(VclRequest(hostnames,orderedRules,globalRules))
    }
  }
}


trait BaseError {
  def errors: Seq[String]
}

case class RuleError(errors: Seq[String]) extends BaseError
case class HostnameError(errors: Seq[String]) extends BaseError
case class RequestError(errors: Seq[String]) extends BaseError
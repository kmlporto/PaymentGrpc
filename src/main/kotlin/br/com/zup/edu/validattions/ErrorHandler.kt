package br.com.zup.edu.validattions

import br.com.zup.edu.handler.ExceptionHandlerGrpcServerInterceptor
import io.micronaut.aop.Around
import io.micronaut.context.annotation.Type

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Type(ExceptionHandlerGrpcServerInterceptor::class)
@Around
annotation class ErrorHandler()

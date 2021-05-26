package br.com.zup.edu.handler

import br.com.zup.edu.handler.ExceptionHandler.StatusWithDetails
import com.google.protobuf.Any
import com.google.rpc.BadRequest
import com.google.rpc.Code
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ConstraintViolationExceptionHandler: ExceptionHandler<ConstraintViolationException> {
    override fun handle(e: ConstraintViolationException): StatusWithDetails {
        val details = BadRequest.newBuilder()
            .addAllFieldViolations(e.constraintViolations.map {
                BadRequest.FieldViolation.newBuilder()
                    .setField(it.propertyPath.last().name ?: "chave")
                    .setDescription(it.message)
                    .build()
            })
            .build()

        val statusProto = com.google.rpc.Status.newBuilder()
            .setCode(Code.INVALID_ARGUMENT_VALUE)
            .setMessage("Dados inválidos")
            .addDetails(Any.pack(details))
            .build()

        return StatusWithDetails(statusProto)
    }

    override fun supports(e: Exception): Boolean {
        return e is ConstraintViolationException
    }
}
package br.com.zup.edu.validattions

import br.com.zup.edu.cobranca.NovoPagamento
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import java.math.BigDecimal
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PagamentoValidoValidator::class])
annotation class PagamentoValido (
    val message: String = "pagamento inválido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)


@Singleton
class PagamentoValidoValidator: ConstraintValidator<PagamentoValido, NovoPagamento> {

    override fun isValid(
        clazz: NovoPagamento?,
        annotationMetadata: AnnotationValue<PagamentoValido>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(clazz == null)
            return false

        if(clazz.valor == BigDecimal.ZERO)
            return false

        return true
    }

}
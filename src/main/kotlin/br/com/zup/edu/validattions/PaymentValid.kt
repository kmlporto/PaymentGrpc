package br.com.zup.edu.validattions

import br.com.zup.edu.TipoCobranca
import br.com.zup.edu.cobranca.NovaCobranca
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
@Constraint(validatedBy = [PaymentValidValidator::class])
annotation class PaymentValid(
    val message: String = "cobrança inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

@Singleton
class PaymentValidValidator: ConstraintValidator<PaymentValid, NovaCobranca> {

    override fun isValid(
        value: NovaCobranca?,
        annotationMetadata: AnnotationValue<PaymentValid>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value == null)
            return false

        if(value.tipoCobranca!!.name == TipoCobranca.DINAMICA.name && value.valor!!.equals(BigDecimal.ZERO)){
            return false
        }
        return true
    }

}

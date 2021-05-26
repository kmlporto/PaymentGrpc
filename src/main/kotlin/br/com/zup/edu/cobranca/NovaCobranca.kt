package br.com.zup.edu.cobranca

import br.com.zup.edu.validattions.PaymentValid
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@PaymentValid
data class NovaCobranca (
    @field:NotEmpty
    val pix: String,

    @field:NotNull
    val tipoCobranca: TipoDeCobranca?,

    val valor: BigDecimal?,

    @field:Size(max = 100)
    val descricao: String
)
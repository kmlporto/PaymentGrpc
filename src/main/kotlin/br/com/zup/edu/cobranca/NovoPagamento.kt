package br.com.zup.edu.cobranca

import br.com.zup.edu.validattions.PagamentoValido
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
@PagamentoValido
data class NovoPagamento(
    @field:NotEmpty
    val idCobranca: String,
    @field:NotEmpty
    val chavePagador: String,
    @field:NotEmpty
    val chaveRecebedor: String,
    @field:NotNull
    val tipoDeCobranca: TipoDeCobranca,
    val valor: BigDecimal)
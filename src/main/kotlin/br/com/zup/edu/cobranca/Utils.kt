package br.com.zup.edu.cobranca

import br.com.zup.edu.PayGrpcCobrancaRequest
import br.com.zup.edu.PayGrpcPagamentoRequest
import com.google.protobuf.Timestamp
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun PayGrpcCobrancaRequest.toNovaQuebranca(): NovaCobranca{
    return NovaCobranca(
        pix = this.pix,
        tipoCobranca = TipoDeCobranca.valueOf(this.tipo!!.name),
        valor = if(this.valor.isEmpty()) null else BigDecimal(this.valor),
        descricao = this.descricao
    )
}

fun LocalDateTime.toTimesTemp(): Timestamp {
    val instant: Instant = this.toInstant(ZoneOffset.UTC)

    return Timestamp.newBuilder()
        .setSeconds(instant.epochSecond)
        .setNanos(instant.nano)
        .build()
}

fun PayGrpcPagamentoRequest.toNovoPagamento(): NovoPagamento{
    return NovoPagamento(
        idCobranca = this.idCobranca,
        chavePagador = this.chavePagador,
        chaveRecebedor = this.chaveRecebedor,
        tipoDeCobranca = TipoDeCobranca.valueOf(this.tipoCobranca.name),
        valor = this.valor.toBigDecimal()
    )
}
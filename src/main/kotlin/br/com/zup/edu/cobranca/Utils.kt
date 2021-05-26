package br.com.zup.edu.cobranca

import br.com.zup.edu.PayGrpcCobrancaRequest
import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun PayGrpcCobrancaRequest.toNovaQuebranca(): NovaCobranca{
    return NovaCobranca(
        pix = this.pix,
        tipoCobranca = TipoDeCobranca.valueOf(this.tipo!!.name),
        valor = this.valor.toBigDecimal(),
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
package br.com.zup.edu.externo

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import java.time.LocalDateTime

@Client("\${BCB.url}")
interface BCBClient {

    @Get(value = "/pix/keys/{key}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun consultaPix(@PathVariable key:String): HttpResponse<PixKeyDetailsResponse>
}

data class PixKeyDetailsResponse(
    val keyType: PixKeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
    val createdAt: LocalDateTime
)

enum class PixKeyType {
    CPF, PHONE, EMAIL, RANDOM;
}

enum class AccontType {
    CACC, SVGS;
}

data class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccontType
)

data class Owner(
    val type: OwnerType,
    val name: String,
    val taxIdNumber: String
) {

    enum class OwnerType {
        NATURAL_PERSON;
    }
}

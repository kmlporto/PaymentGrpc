package br.com.zup.edu.cobranca

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Cobranca(
    @field:NotBlank
    @Column(nullable = false)
    val chavePix: String,

    val valor: BigDecimal?,

    @field:NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val tipoCobranca: TipoDeCobranca,

    @ManyToOne
    val recebedor: Pessoa) {

    @Id
    @GeneratedValue
    var id: UUID? = null

    @field:Size(max = 100)
    var descricacao: String = ""

    @Column(nullable = false)
    val criadoEm = LocalDateTime.now()

    fun pertenceAo(chaveRecebedor: String): Boolean {
        return this.chavePix == chaveRecebedor
    }


}
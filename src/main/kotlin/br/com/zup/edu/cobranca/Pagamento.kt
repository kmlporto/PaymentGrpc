package br.com.zup.edu.cobranca

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Pagamento(
    @OneToOne
    val cobranca: Cobranca,

    @field:NotNull
    @Column(nullable = false)
    val valor: BigDecimal,

    @ManyToOne
    val pagador: Pessoa,
) {

    @Id
    @GeneratedValue
    var id: UUID? = null

    @Column(nullable = false)
    val criadoEm = LocalDateTime.now()
}
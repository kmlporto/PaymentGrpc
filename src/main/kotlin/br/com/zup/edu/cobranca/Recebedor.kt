package br.com.zup.edu.cobranca

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Recebedor(
    @field:NotBlank
    @Column(nullable = false)
    val nomeCompleto: String,

    @field:NotBlank
    @Column(nullable = false)
    val cpf: String,

    @field:NotBlank
    @Column(nullable = false)
    val instituicaoFinanceira: String
) {

    @Id
    @GeneratedValue
    var id: UUID? = null

    @OneToMany(mappedBy = "recebedor", cascade = [CascadeType.PERSIST])
    lateinit var cobrancas:List<Cobranca>

}
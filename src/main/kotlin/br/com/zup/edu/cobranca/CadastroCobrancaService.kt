package br.com.zup.edu.cobranca

import br.com.zup.edu.exceptions.NotFoundException
import br.com.zup.edu.externo.BCBClient
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
open class CadastroCobrancaService(
    @Inject private val repository: CobrancaRepository,
    @Inject private val recebedorResponsitorio: RecebedorResponsitorio,
    @Inject private val bcbClient: BCBClient,
) {

    @Transactional
    open fun cadastraCobranca(@Valid novaCobranca: NovaCobranca): Cobranca{
        val responseBCB = bcbClient.consultaPix(novaCobranca.pix)

        if(responseBCB.status != HttpStatus.OK || responseBCB.body() == null)
            throw NotFoundException("Chave não cadastrada no Banco Central")

        val recebedor = responseBCB.body().let {
            Recebedor(
                nomeCompleto = it.owner.name,
                cpf = formata(it.owner.taxIdNumber),
                instituicaoFinanceira = it.bankAccount.participant
            )
        }
        recebedorResponsitorio.save(recebedor)

        val cobranca = Cobranca(novaCobranca.pix, novaCobranca.valor, novaCobranca.tipoCobranca!!, recebedor)

        return repository.save(cobranca)
    }

    private fun formata(cpf: String): String {
        return "${cpf.subSequence(0,3)}.${cpf.subSequence(3,6)}.${cpf.subSequence(6,9)}-${cpf.subSequence(9,11)}"
    }
}
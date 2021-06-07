package br.com.zup.edu.cobranca

import br.com.zup.edu.exceptions.NotFoundException
import br.com.zup.edu.externo.BCBClient
import io.micronaut.http.HttpStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BCBClientService(
    @Inject private val bcbClient: BCBClient,
    @Inject private val pessoaRepository: PessoaRepository
) {

    fun recuperaPessoaNoBCB(pix: String): Pessoa{
        val responseBCB = bcbClient.consultaPix(pix)

        if(responseBCB.status != HttpStatus.OK || responseBCB.body() == null)
            throw NotFoundException("Chave não cadastrada no Banco Central")

        return responseBCB.body().let {
            pessoaRepository.findByCpf(formataCPF(it!!.owner.taxIdNumber)).orElseGet{
                pessoaRepository.save(Pessoa(
                    nomeCompleto = it.owner.name,
                    cpf = formataCPF(it.owner.taxIdNumber),
                    instituicaoFinanceira = it.bankAccount.participant
                ))
            }
        }
    }

    private fun formataCPF(cpf: String): String {
        return "${cpf.subSequence(0,3)}.${cpf.subSequence(3,6)}.${cpf.subSequence(6,9)}-${cpf.subSequence(9,11)}"
    }
}
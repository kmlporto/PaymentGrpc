package br.com.zup.edu.cobranca

import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
open class CadastroCobrancaService(
    @Inject private val repository: CobrancaRepository,
    @Inject private val bcbService: BCBClientService,
) {

    @Transactional
    open fun cadastraCobranca(@Valid novaCobranca: NovaCobranca): Cobranca{
        val recebedor = bcbService.recuperaPessoaNoBCB(novaCobranca.pix)

        val cobranca = Cobranca(novaCobranca.pix, novaCobranca.valor, novaCobranca.tipoCobranca!!, recebedor)

        return repository.save(cobranca)
    }

}
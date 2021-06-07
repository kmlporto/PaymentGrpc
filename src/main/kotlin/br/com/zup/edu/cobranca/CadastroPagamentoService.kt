package br.com.zup.edu.cobranca

import br.com.zup.edu.exceptions.NotFoundException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class CadastroPagamentoService(
    @Inject private val pagamentoRepository: PagamentoRepository,
    @Inject private val cobrancaRepository: CobrancaRepository,
    @Inject private val bcbService: BCBClientService,

) {

    fun realizaPagamento(@Valid novoPagamento: NovoPagamento): Pagamento{
        val cobranca = cobrancaRepository.findById(UUID.fromString(novoPagamento.idCobranca))
            .filter { it.pertenceAo(novoPagamento.chaveRecebedor)}
            .orElseThrow{ NotFoundException("Cobrança não existe") }

        if(cobranca.valor != novoPagamento.valor)
            false

        val pagador = bcbService.recuperaPessoaNoBCB(novoPagamento.chavePagador)

        val pagamento = Pagamento(cobranca, novoPagamento.valor, pagador)

        return pagamentoRepository.save(pagamento)
    }

}
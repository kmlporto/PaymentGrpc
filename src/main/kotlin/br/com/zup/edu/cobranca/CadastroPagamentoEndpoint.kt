package br.com.zup.edu.cobranca

import br.com.zup.edu.PayGrpcPagamentoRequest
import br.com.zup.edu.PayGrpcPagamentoResponse
import br.com.zup.edu.PaymentServiceGrpc
import br.com.zup.edu.PessoaResponse
import br.com.zup.edu.validattions.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class CadastroPagamentoEndpoint (@Inject private val service: CadastroPagamentoService): PaymentServiceGrpc.PaymentServiceImplBase(){

    override fun efetuaPagamento(request: PayGrpcPagamentoRequest, responseObserver: StreamObserver<PayGrpcPagamentoResponse>) {
        val novoPagamento = request.toNovoPagamento()

        val pagamentoRealizado = service.realizaPagamento(novoPagamento)

        responseObserver.onNext(getResponse(
            novoPagamento.chavePagador,
            novoPagamento.chaveRecebedor,
            pagamentoRealizado)
        )

        responseObserver.onCompleted()
    }

    fun getResponse(chavePagador:String, chaveRecebedor:String, pagamento: Pagamento):PayGrpcPagamentoResponse{
        return with(pagamento){
            PayGrpcPagamentoResponse
                .newBuilder()
                .setIdTransacao(id.toString())
                .setPagador(PessoaResponse
                    .newBuilder()
                    .setChavePix(chavePagador)
                    .setCpf(pagador.cpf)
                    .setNomeCompleto(pagador.nomeCompleto)
                    .setInstituicaoFinanceira(pagador.instituicaoFinanceira)
                    .build())
                .setRecebedor(PessoaResponse
                    .newBuilder()
                    .setChavePix(chaveRecebedor)
                    .setCpf(cobranca.recebedor.cpf)
                    .setNomeCompleto(cobranca.recebedor.nomeCompleto)
                    .setInstituicaoFinanceira(cobranca.recebedor.instituicaoFinanceira)
                    .build())
                .setPagoEm(criadoEm.toTimesTemp())
                .setValorPago(pagamento.valor.toString())
                .build()
        }
    }
}
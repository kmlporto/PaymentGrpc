package br.com.zup.edu.cobranca

import br.com.zup.edu.*
import br.com.zup.edu.Recebedor
import br.com.zup.edu.validattions.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class CadastroCobrancaEndpoint(
    @Inject private val service: CadastroCobrancaService
    ): PaymentServiceGrpc.PaymentServiceImplBase(){

    override fun geraCobranca(request: PayGrpcCobrancaRequest, responseObserver: StreamObserver<PayGrpcCobrancaResponse>) {
        val novaCobranca = request.toNovaQuebranca()

        val cobranca = service.cadastraCobranca(novaCobranca)

        responseObserver.onNext(PayGrpcCobrancaResponse
            .newBuilder()
            .setIdCobracanca(cobranca.id.toString())
            .setChaveRecebedor(cobranca.chavePix)
            .setTipo(TipoCobranca.valueOf(cobranca.tipoCobranca.name))
            .setRecebedor(Recebedor
                .newBuilder()
                .setNomeCompleto(cobranca.recebedor.nomeCompleto)
                .setInstituicaoFinanceira(cobranca.recebedor.instituicaoFinanceira)
                .setCpf(cobranca.recebedor.cpf)
                .build())
            .setCriadoEm(cobranca.criadoEm.toTimesTemp())
            .build())
        responseObserver.onCompleted()

    }
}
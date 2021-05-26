package br.com.zup.edu.cobranca

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface RecebedorResponsitorio: JpaRepository<Recebedor, UUID> {
}
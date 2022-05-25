package br.com.isale.monolith.shared.adapter.out.persistence

import br.com.isale.monolith.shared.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepositorySpringData : JpaRepository<Product, Long>
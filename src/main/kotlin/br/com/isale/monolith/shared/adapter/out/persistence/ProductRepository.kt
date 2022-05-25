package br.com.isale.monolith.shared.adapter.out.persistence

import br.com.isale.monolith.stock.newProduct.application.port.out.RegisterProductPort
import br.com.isale.monolith.shared.model.Product
import org.springframework.stereotype.Component

@Component
class ProductRepository(
    val productRepositorySpringData: ProductRepositorySpringData
) : RegisterProductPort {

    override fun execute(product: Product): Product = productRepositorySpringData.save(product)
}
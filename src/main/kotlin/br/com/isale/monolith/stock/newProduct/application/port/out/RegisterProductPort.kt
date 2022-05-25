package br.com.isale.monolith.stock.newProduct.application.port.out

import br.com.isale.monolith.shared.model.Product

interface RegisterProductPort {

    fun execute(product: Product): Product
}
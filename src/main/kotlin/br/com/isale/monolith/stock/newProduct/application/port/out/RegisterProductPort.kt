package br.com.isale.monolith.stock.newProduct.application.port.out

import br.com.isale.monolith.shared.model.Product

interface RegisterProductPort {

    fun register(product: Product): Product
}
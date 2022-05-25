package br.com.isale.monolith.stock.newProduct.application.port.`in`

import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import br.com.isale.monolith.stock.newProduct.model.NewProductResponse
import org.springframework.stereotype.Service
import javax.validation.Valid

interface NewProductUseCase {

    fun register(@Valid newProductRequest: NewProductRequest, cId: String): NewProductResponse
}

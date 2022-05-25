package br.com.isale.monolith.stock.newProduct.application.service

import br.com.isale.monolith.stock.newProduct.application.port.`in`.NewProductUseCase
import br.com.isale.monolith.stock.newProduct.application.port.out.RegisterProductPort
import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import br.com.isale.monolith.stock.newProduct.model.NewProductResponse
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Service
@Validated
class NewProductService(
    val registerProductPort: RegisterProductPort
) : NewProductUseCase {

    override fun register(@Valid newProductRequest: NewProductRequest, cId: String): NewProductResponse {
        val newProduct = registerProductPort.register(newProductRequest.toProduct())

        return NewProductResponse(
            newProduct.id ?: throw RuntimeException("Id must not be null"),
            newProduct.description.description,
            newProduct.price.price,
            newProduct.stock.stock
        )
    }
}
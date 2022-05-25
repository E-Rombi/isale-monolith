package br.com.isale.monolith.stock.newProduct.adapter.`in`.web

import br.com.isale.monolith.stock.newProduct.application.port.`in`.NewProductUseCase
import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import br.com.isale.monolith.stock.newProduct.model.NewProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class NewProductController(
    val newProductUseCase: NewProductUseCase
) {

    @PostMapping
    fun newProduct(
        @RequestBody request: NewProductRequest,
        @RequestHeader(required = false, value = "correlation-id") correlationId: String?,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<NewProductResponse> {
        val cId = correlationId ?: UUID.randomUUID().toString()

        val newProductResponse = newProductUseCase.register(request, cId)
        val uri = uriComponentsBuilder.path("/products/{productId}").buildAndExpand(newProductResponse.id).toUri()

        return ResponseEntity.created(uri).body(newProductResponse)
    }
}
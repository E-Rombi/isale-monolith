package br.com.isale.monolith.stock.newProduct.application.service

import br.com.isale.monolith.shared.application.port.out.FindCompanyByIdPort
import br.com.isale.monolith.shared.model.dto.CompanyResponse
import br.com.isale.monolith.shared.model.exception.BusinessException
import br.com.isale.monolith.stock.newProduct.application.port.`in`.NewProductUseCase
import br.com.isale.monolith.stock.newProduct.application.port.out.RegisterProductPort
import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import br.com.isale.monolith.stock.newProduct.model.NewProductResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Service
@Validated
class NewProductService(
    val registerProductPort: RegisterProductPort,
    val findCompanyByIdPort: FindCompanyByIdPort
) : NewProductUseCase {

    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun register(@Valid newProductRequest: NewProductRequest, cId: String): NewProductResponse {
        logger.info("action=startingRegistering, cid=$cId, request=${newProductRequest}")
        val newProduct = registerProductPort.execute(newProductRequest.toProduct(findCompanyByIdPort))

        return NewProductResponse(
            newProduct.id ?: throw RuntimeException("Id must not be null"),
            newProduct.description.description,
            newProduct.price.price,
            newProduct.stock.stock,
            CompanyResponse(
                newProduct.company.id ?: throw BusinessException("Invalid Company to response"),
                newProduct.company.name
            )
        ).apply { logger.info("action=responseBuilt, cid=$cId, productId=${newProduct.id}") }
    }
}
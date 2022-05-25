package br.com.isale.monolith.stock.newProduct.model

import br.com.isale.monolith.shared.model.Description
import br.com.isale.monolith.shared.model.Price
import br.com.isale.monolith.shared.model.Product
import br.com.isale.monolith.shared.model.Stock
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class NewProductRequest(
    @field:NotBlank @field:Size(min = 10, max = 200) val description: String,
    @field:NotNull @field:Positive val price: BigDecimal,
    @field:NotNull @field:PositiveOrZero val stock: BigDecimal
) {
    fun toProduct(): Product {
        return Product(
            Description(this.description),
            Price(this.price),
            Stock(this.stock)
        )
    }
}

data class NewProductResponse(
    val id: Long,
    val description: String,
    val price: BigDecimal,
    val stock: BigDecimal
)
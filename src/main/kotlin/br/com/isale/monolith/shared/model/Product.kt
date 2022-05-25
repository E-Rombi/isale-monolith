package br.com.isale.monolith.shared.model

import br.com.isale.monolith.shared.model.exception.BusinessException
import br.com.isale.monolith.shared.model.exception.Messages
import java.math.BigDecimal
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Product(
    @field:Embedded var description: Description,
    @field:Embedded var price: Price,
    @field:Embedded var stock: Stock
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

@Embeddable
class Price(
    val price: BigDecimal
) {
    init {
        if (price <= BigDecimal.ZERO)
            throw BusinessException(Messages.mustBeGreaterThan("price"))
    }
}

@Embeddable
class Stock(
    val stock: BigDecimal
) {
    init {
        if (stock < BigDecimal.ZERO)
            throw BusinessException(Messages.mustBeGreaterOrEquals("stock"))
    }
}

@Embeddable
class Description(
    val description: String
) {
    init {
        if (description.isBlank())
            throw BusinessException(Messages.mustNotBeBlank("description"))
        if (description.length < 10 || description.length > 200)
            throw BusinessException(Messages.mustBeBetween("description", 10, 200))
    }
}
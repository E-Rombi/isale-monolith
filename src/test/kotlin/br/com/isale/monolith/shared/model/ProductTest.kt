package br.com.isale.monolith.shared.model

import br.com.isale.monolith.shared.model.exception.BusinessException
import br.com.isale.monolith.shared.model.exception.Messages
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ProductTest {

    @Test
    fun `should throw BusinessException when description is blank`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description(""),
                Price(BigDecimal.TEN),
                Stock(BigDecimal.ZERO)
            )
        }
        assertEquals(Messages.mustNotBeBlank("description"), ex.message)
    }

    @Test
    fun `should throw BusinessException when description is less than 10`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description("123456789"),
                Price(BigDecimal.TEN),
                Stock(BigDecimal.ZERO)
            )
        }
        assertEquals(Messages.mustBeBetween("description", 10, 200), ex.message)
    }

    @Test
    fun `should throw BusinessException when description is greater than 200`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "a"),
                Price(BigDecimal.TEN),
                Stock(BigDecimal.ZERO)
            )
        }
        assertEquals(Messages.mustBeBetween("description", 10, 200), ex.message)
    }

    @Test
    fun `should throw BusinessException when price equals zero`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description("Sacola Plástica"),
                Price(BigDecimal.ZERO),
                Stock(BigDecimal.ZERO)
            )
        }
        assertEquals(Messages.mustBeGreaterThan("price"), ex.message)
    }

    @Test
    fun `should throw BusinessException when price is less than zero`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description("Sacola Plástica"),
                Price(BigDecimal.valueOf(-15)),
                Stock(BigDecimal.ZERO)
            )
        }
        assertEquals(Messages.mustBeGreaterThan("price"), ex.message)
    }

    @Test
    fun `should throw BusinessException when stock is less than zero`() {
        val ex = assertThrows(BusinessException::class.java) {
            Product(
                Description("Sacola Plástica"),
                Price(BigDecimal.TEN),
                Stock(BigDecimal.valueOf(-1))
            )
        }
        assertEquals(Messages.mustBeGreaterOrEquals("stock"), ex.message)
    }

    @Test
    fun `should create successfully Product`() {
        val product = Product(
            Description("Sacola Plástica"),
            Price(BigDecimal.TEN),
            Stock(BigDecimal.ZERO)
        )

        assertNotNull(product.toString())
    }

}
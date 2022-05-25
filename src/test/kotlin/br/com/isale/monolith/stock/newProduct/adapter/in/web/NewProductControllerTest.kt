package br.com.isale.monolith.stock.newProduct.adapter.`in`.web

import br.com.isale.monolith.shared.model.exception.Messages
import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotBlank

@AutoConfigureMockMvc
@SpringBootTest
internal class NewProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should register new product successfully`() {
        val requestBody = NewProductRequest("Sacola Plástica", BigDecimal.TEN, BigDecimal.ZERO)

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isCreated)
            .andExpect(
                jsonPath("$.description").value(requestBody.description)
            )
    }

    @Test
    fun `should return BAD_REQUEST when description length is less than 10`() {
        val requestBody = NewProductRequest("123456789", BigDecimal.TEN, BigDecimal.ZERO)

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isBadRequest)
            .andExpect(
                jsonPath("$[0].message").value("size must be between 10 and 200")
            )
    }

    @Test
    fun `should return BAD_REQUEST when description length is greater than 200`() {
        val requestBody = NewProductRequest(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "a", BigDecimal.TEN, BigDecimal.ZERO)

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isBadRequest)
            .andExpect(
                jsonPath("$[0].message").value("size must be between 10 and 200")
            )
    }

    @Test
    fun `should return BAD_REQUEST when price is equals 0`() {
        val requestBody = NewProductRequest(
            "Sacola Plástica", BigDecimal.ZERO, BigDecimal.ZERO)

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isBadRequest)
            .andExpect(
                jsonPath("$[0].message").value("must be greater than 0")
            )
    }

    @Test
    fun `should return BAD_REQUEST when stock is less than 0`() {
        val requestBody = NewProductRequest(
            "Sacola Plástica", BigDecimal.TEN, BigDecimal.valueOf(-15))

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isBadRequest)
            .andExpect(
                jsonPath("$[0].message").value("must be greater than or equal to 0")
            )
    }

}
package br.com.isale.monolith.stock.newProduct.adapter.`in`.web

import br.com.isale.monolith.shared.adapter.out.persistence.CompanyRepositorySpringData
import br.com.isale.monolith.shared.adapter.out.persistence.ProductRepositorySpringData
import br.com.isale.monolith.shared.model.*
import br.com.isale.monolith.shared.model.exception.Messages
import br.com.isale.monolith.shared.model.exception.ResourceNotFoundException
import br.com.isale.monolith.stock.newProduct.model.NewProductRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
internal class NewProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var companyRepositorySpringData: CompanyRepositorySpringData

    @MockBean
    private lateinit var productRepositorySpringData: ProductRepositorySpringData

    private val validCompany = Company("Company One").apply { id = 1 }

    @Test
    fun `should register new product successfully`() {
        val validProduct = Product(
            Description("Sacola Plástica"), Price(BigDecimal.TEN), Stock(BigDecimal.ZERO), validCompany
        ).apply { id = 1 }

        val requestBody = NewProductRequest("Sacola Plástica", BigDecimal.TEN, BigDecimal.ZERO, 1)

        doReturn(Optional.of(validCompany)) .`when`(companyRepositorySpringData).findById(anyLong())
        doReturn(validProduct).`when`(productRepositorySpringData).save(any(Product::class.java))

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

        verify(productRepositorySpringData, times(1)).save(any(Product::class.java))
    }

    @Test
    fun `should return BAD_REQUEST when description length is less than 10`() {
        val requestBody = NewProductRequest("123456789", BigDecimal.TEN, BigDecimal.ZERO, 1)

        `when`(companyRepositorySpringData.findById(anyLong()))
            .thenReturn(Optional.of(validCompany))

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
                    "a", BigDecimal.TEN, BigDecimal.ZERO, 1)

        `when`(companyRepositorySpringData.findById(anyLong()))
            .thenReturn(Optional.of(validCompany))

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
            "Sacola Plástica", BigDecimal.ZERO, BigDecimal.ZERO, 1)

        `when`(companyRepositorySpringData.findById(anyLong()))
            .thenReturn(Optional.of(validCompany))

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
            "Sacola Plástica", BigDecimal.TEN, BigDecimal.valueOf(-15), 1
        )

        `when`(companyRepositorySpringData.findById(anyLong()))
            .thenReturn(Optional.of(validCompany))

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

    @Test
    fun `should return NOT_FOUND when company does not exists`() {
        val requestBody = NewProductRequest(
            "Sacola Plástica", BigDecimal.TEN, BigDecimal.ZERO, 2
        )

        `when`(companyRepositorySpringData.findById(anyLong()))
            .thenThrow(ResourceNotFoundException(Messages.resourceNotFound("company")))

        mockMvc.perform(
            post("/products")
                .header("correlation-id", UUID.randomUUID().toString())
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestBody))
        )
            .andExpect(status().isNotFound)
    }

}
package br.com.isale.monolith.shared.model

import br.com.isale.monolith.shared.model.exception.BusinessException
import br.com.isale.monolith.shared.model.exception.Messages
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CompanyTest {

    @Test
    fun `should throw BusinessException when name is blank`() {
        val ex = assertThrows(BusinessException::class.java) {
            Company("")
        }

        assertEquals(Messages.mustNotBeBlank("name"), ex.message)
    }

    @Test
    fun `should throw BusinessException when name is less than 10`() {
        val ex = assertThrows(BusinessException::class.java) {
            Company("123456789")
        }

        assertEquals(Messages.mustBeBetween("name", 10, 200), ex.message)
    }

    @Test
    fun `should throw BusinessException when name is greater than 200`() {
        val ex = assertThrows(BusinessException::class.java) {
            Company("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "a")
        }

        assertEquals(Messages.mustBeBetween("name", 10, 200), ex.message)
    }

    @Test
    fun `should create Company successfully`() {
        val company = Company("Company One")

        assertNotNull(company.toString())
    }

}
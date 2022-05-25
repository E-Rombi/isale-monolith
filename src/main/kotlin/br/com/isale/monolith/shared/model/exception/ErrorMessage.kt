package br.com.isale.monolith.shared.model.exception

data class ErrorMessage(
    val message: String?
)

data class FieldErrorMessage(
    val field: String?,
    val message: String?
)


object Messages {
    fun resourceNotFound(resource: String): String = "$resource not found"
    fun mustNotBeBlank(field: String): String = "$field must not be blank"
    fun mustBeBetween(field: String, min: Int, max: Int): String = "$field must be between $min and $max"
    fun mustBeGreaterThan(field: String): String = "$field must be greater than 0"
    fun mustBeGreaterOrEquals(field: String): String = "$field must be greater or equals 0"
}
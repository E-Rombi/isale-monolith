package br.com.isale.monolith.shared.adapter.out.web

import br.com.isale.monolith.shared.model.exception.*
import org.hibernate.validator.internal.engine.path.PathImpl
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun handleConstraintViolation(e: ConstraintViolationException): ResponseEntity<List<FieldErrorMessage>> {
        val response = e.constraintViolations.map {
            FieldErrorMessage((it.propertyPath as PathImpl).leafNode.toString(), it.message)
        }.sortedBy { violation -> violation.field }

        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler
    fun handleResourceNotFound(e: ResourceNotFoundException): ResponseEntity<ErrorMessage> {
        logger.error("[ResourceNotFound] message=${e.message}", e)
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler
    fun handleBusiness(e: BusinessException): ResponseEntity<ErrorMessage> {
        logger.error("[ResourceNotFound] message=${e.message}", e)
        return ResponseEntity.unprocessableEntity().body(ErrorMessage(e.message))
    }
}
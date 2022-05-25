package br.com.isale.monolith.shared.model.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)

class BusinessException(message: String) : RuntimeException(message)
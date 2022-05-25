package br.com.isale.monolith.shared.model

import br.com.isale.monolith.shared.model.exception.BusinessException
import br.com.isale.monolith.shared.model.exception.Messages
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Company(
    var name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    init {
        if (this.name.isBlank())
            throw BusinessException(Messages.mustNotBeBlank("name"))
        if (this.name.length < 10 || this.name.length > 200)
            throw BusinessException(Messages.mustBeBetween("name", 10, 200))
    }
}

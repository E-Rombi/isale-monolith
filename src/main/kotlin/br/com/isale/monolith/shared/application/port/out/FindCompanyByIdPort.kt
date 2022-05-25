package br.com.isale.monolith.shared.application.port.out

import br.com.isale.monolith.shared.model.Company

interface FindCompanyByIdPort {

    fun execute(companyId: Long): Company
}
package br.com.isale.monolith.shared.adapter.out.persistence

import br.com.isale.monolith.shared.application.port.out.FindCompanyByIdPort
import br.com.isale.monolith.shared.model.Company
import br.com.isale.monolith.shared.model.exception.Messages
import br.com.isale.monolith.shared.model.exception.ResourceNotFoundException
import org.springframework.stereotype.Component

@Component
class CompanyRepository(
    val companyRepositorySpringData: CompanyRepositorySpringData
) : FindCompanyByIdPort  {
    override fun execute(companyId: Long): Company {
        return companyRepositorySpringData.findById(companyId)
            .orElseThrow { ResourceNotFoundException(Messages.resourceNotFound("company")) }
    }
}
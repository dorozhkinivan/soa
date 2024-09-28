package itmo.soa.flat_service.repository

import itmo.soa.flat_service.model.FlatEntity
import itmo.soa.flat_service.utils.ListRequestConfig
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

interface FlatCriteriaRepository {
    fun getList(
        listRequestConfig: ListRequestConfig,
        extraCondition: ((CriteriaBuilder, Root<FlatEntity>) -> Predicate)? = null
    ): List<FlatEntity>
}
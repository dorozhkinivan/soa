package itmo.soa.flat_service.repository

import itmo.soa.flat_service.model.FlatEntity
import itmo.soa.flat_service.utils.ListRequestConfig
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Component

@Component
class FlatCriteriaRepositoryImpl(
    private val entityManager : EntityManager
) : FlatCriteriaRepository {
    override fun getList(
        listRequestConfig: ListRequestConfig,
        extraCondition: ((CriteriaBuilder, Root<FlatEntity>) -> Predicate)?
    ): List<FlatEntity> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<FlatEntity> = cb.createQuery(FlatEntity::class.java)
        val root: Root<FlatEntity> = cq.from(FlatEntity::class.java)

        var predicate: Predicate = cb.conjunction()

        listRequestConfig.filterKeysToValues.forEach { (key, value) ->
            predicate = cb.and(predicate, cb.equal(root.get<Any>(key).`as`(String::class.java), value))
        }


        extraCondition?.let {
            predicate = cb.and(predicate, it(cb, root))
        }

        cq.where(predicate)

        var order = listRequestConfig.sortKeyToIsAscending.map { (key, isAscending) ->
            if (isAscending)
                cb.asc(root.get<Any>(key))
            else
                cb.desc(root.get<Any>(key))
        }
        val sortFields = listRequestConfig.sortKeyToIsAscending.map { it.first }.toSet()

        if ("id" !in sortFields){
            order = order + cb.asc(root.get<Long>("id"))
        }

        cq.orderBy(order)

        val typedQuery = entityManager.createQuery(cq)

        typedQuery.firstResult = (listRequestConfig.page - 1) * listRequestConfig.size
        typedQuery.maxResults = listRequestConfig.size

        return typedQuery.resultList
    }

}
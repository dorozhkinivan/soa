package itmo.soa.flat_service.service

import itmo.soa.flat_service.dto.Furnish
import itmo.soa.flat_service.exceptions.NotFoundException
import itmo.soa.flat_service.model.FlatEntity
import itmo.soa.flat_service.repository.FlatRepository
import itmo.soa.flat_service.repository.HouseRepository
import itmo.soa.flat_service.utils.ListRequestConfig
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
class FlatServiceImpl(
    private val flatRepository: FlatRepository,
    private val houseRepository: HouseRepository,
) : FlatService {
    override fun get(id: Long): FlatEntity = flatRepository.findById(id).getOrElse {
        throw NotFoundException()
    }

    override fun getWithMinCoordinates(): FlatEntity = flatRepository.getFlatWithMinCoordinates().getOrElse {
        throw NotFoundException()
    }

    override fun getNumberOfRoomsMoreThen(number: Int): Int = flatRepository.getNumberOfRoomsMoreThen(number)

    override fun getList(
        listRequestConfig : ListRequestConfig,
    ): List<FlatEntity> = flatRepository.getList(listRequestConfig)

    override fun getListWithFurnishLessThen(
        listRequestConfig : ListRequestConfig,
        maxFurnish: Furnish,
    ): List<FlatEntity> {
        val furnishes = maxFurnish.getValuesLessThenThis()
        val criteriaCondition = { cb: CriteriaBuilder, root: Root<FlatEntity> ->
            val path = root.get<Furnish>(FlatEntity::furnish.name)
            val inClause = cb.`in`(path)
            furnishes.forEach { inClause.value(it) }
            inClause
        }
        return flatRepository.getList(listRequestConfig, criteriaCondition)
    }

    override fun create(flatEntity: FlatEntity): FlatEntity {
        val house = flatEntity.house?.let {
            houseRepository.findAllByNameAndYearAndNumberOfFloorsAndNumberOfLifts(
                name = it.name,
                year = it.year,
                numberOfFloors = it.numberOfFloors,
                numberOfLifts = it.numberOfLifts
            ).firstOrNull() ?: houseRepository.save(it)
        }
        flatEntity.creationDate = LocalDateTime.now()
        flatEntity.house = house
        return flatRepository.save(flatEntity)
    }

    override fun update(id: Long, flatEntity: FlatEntity): FlatEntity{
        val existingFlat = get(id)
        flatEntity.id = id
        flatEntity.creationDate = existingFlat.creationDate
        return create(flatEntity)
    }

    override fun delete(id: Long) {
        get(id)
        flatRepository.deleteById(id)
    }

}
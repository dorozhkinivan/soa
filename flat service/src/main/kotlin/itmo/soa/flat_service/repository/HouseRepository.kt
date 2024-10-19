package itmo.soa.flat_service.repository

import itmo.soa.flat_service.model.HouseEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface HouseRepository : CrudRepository<HouseEntity, Long> {
    fun findAllByNameAndYearAndNumberOfFloorsAndNumberOfLifts(
        name: String?,
        year: Int?,
        numberOfFloors: Int,
        numberOfLifts: Int,
    ) : List<HouseEntity>
}
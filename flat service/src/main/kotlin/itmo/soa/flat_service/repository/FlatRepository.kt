package itmo.soa.flat_service.repository

import itmo.soa.flat_service.model.FlatEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FlatRepository : CrudRepository<FlatEntity, Long>, FlatCriteriaRepository
{
    fun save(entity: FlatEntity) : FlatEntity

    @Query("SELECT COUNT(f) FROM FlatEntity f WHERE f.numberOfRooms > :number")
    fun getNumberOfRoomsMoreThen(number: Int) : Int
    @Query("SELECT f FROM FlatEntity f ORDER BY (f.coordinateX + f.coordinateY) ASC LIMIT 1")
    fun getFlatWithMinCoordinates() : Optional<FlatEntity>

    override fun findById(id: Long): Optional<FlatEntity>
}
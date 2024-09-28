package itmo.soa.flat_service.repository

import itmo.soa.flat_service.model.HouseEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HouseRepository : CrudRepository<HouseEntity, Long>
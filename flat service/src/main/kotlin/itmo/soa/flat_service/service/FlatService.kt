package itmo.soa.flat_service.service

import itmo.soa.flat_service.dto.Furnish
import itmo.soa.flat_service.model.FlatEntity
import itmo.soa.flat_service.utils.ListRequestConfig
import org.springframework.stereotype.Service

@Service
interface FlatService {
    fun get(id: Long) : FlatEntity

    fun getWithMinCoordinates() : FlatEntity
    fun getNumberOfRoomsMoreThen(number: Int) : Int
    fun getList(
        listRequestConfig: ListRequestConfig
    ) : List<FlatEntity>

    fun getListWithFurnishLessThen(
        listRequestConfig : ListRequestConfig,
        maxFurnish: Furnish,
    ) : List<FlatEntity>

    fun create(
        flatEntity: FlatEntity,
    ) : FlatEntity

    fun update(
        id: Long,
        flatEntity: FlatEntity,
    ) : FlatEntity

    fun delete(
        id: Long
    )

}
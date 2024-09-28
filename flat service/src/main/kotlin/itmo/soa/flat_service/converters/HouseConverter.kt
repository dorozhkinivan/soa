package itmo.soa.flat_service.converters

import itmo.soa.flat_service.dto.HouseDto
import itmo.soa.flat_service.model.HouseEntity
import org.springframework.stereotype.Component

@Component
class HouseConverter : Converter<HouseEntity, HouseDto> {
    override fun toDto(entity: HouseEntity) = HouseDto(
        name = entity.name,
        year = entity.year,
        numberOfFloors = entity.numberOfFloors,
        numberOfLifts = entity.numberOfLifts,
    )

    override fun toEntity(dto: HouseDto) = HouseEntity(
        id = -1,
        name = dto.name,
        year = dto.year,
        numberOfLifts = dto.numberOfLifts,
        numberOfFloors = dto.numberOfFloors,
    )
}
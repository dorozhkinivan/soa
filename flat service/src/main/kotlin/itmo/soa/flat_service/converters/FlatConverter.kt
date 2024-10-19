package itmo.soa.flat_service.converters

import itmo.soa.flat_service.dto.Coordinate
import itmo.soa.flat_service.dto.FlatDto
import itmo.soa.flat_service.model.FlatEntity
import org.springframework.stereotype.Component

@Component
class FlatConverter(
    private val houseConverter: HouseConverter
) : Converter<FlatEntity, FlatDto> {
    override fun toDto(entity: FlatEntity) = FlatDto(
        id = entity.id,
        name = entity.name,
        coordinates = Coordinate(entity.coordinateX, entity.coordinateY),
        creationDate = entity.creationDate,
        area = entity.area,
        numberOfRooms = entity.numberOfRooms,
        furnish = entity.furnish,
        view = entity.view,
        transport = entity.transport,
        house = entity.house?.let(houseConverter::toDto)
    )

    override fun toEntity(dto: FlatDto) = FlatEntity(
        id = dto.id,
        name = dto.name!!,
        coordinateX = dto.coordinates!!.x,
        coordinateY = dto.coordinates.y,
        creationDate = dto.creationDate,
        area = dto.area!!,
        numberOfRooms = dto.numberOfRooms!!,
        furnish = dto.furnish!!,
        view = dto.view,
        transport = dto.transport!!,
        house = dto.house?.let(houseConverter::toEntity)
    )
}
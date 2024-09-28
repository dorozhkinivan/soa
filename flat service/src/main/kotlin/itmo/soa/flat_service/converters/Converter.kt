package itmo.soa.flat_service.converters

interface Converter<E, D> {
    fun toDto(entity: E) : D
    fun toEntity(dto: D) : E
}
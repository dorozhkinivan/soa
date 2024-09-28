package itmo.soa.flat_service.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "house")
data class HouseDto(
    val name: String?,
    val year: Int?,
    val numberOfFloors: Int,
    val numberOfLifts: Int,
)
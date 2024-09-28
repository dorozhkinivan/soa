package itmo.soa.agency_service.dtos

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "house")
data class HouseDto(
    val name: String?,
    val year: Int?,
    val numberOfFloors: Int,
    val numberOfLifts: Int,
)
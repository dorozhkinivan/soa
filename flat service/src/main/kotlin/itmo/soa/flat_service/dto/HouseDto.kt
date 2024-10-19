package itmo.soa.flat_service.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min


@JacksonXmlRootElement(localName = "house")
data class HouseDto(
    val name: String?,
    @field:Min(1)
    val year: Int?,
    @field:Min(1)
    @field:Max(39)
    val numberOfFloors: Int,
    @field:Min(1)
    val numberOfLifts: Int,
)
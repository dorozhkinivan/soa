package itmo.soa.agency_service.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import itmo.soa.agency_service.dtos.HouseDto
import java.time.LocalDateTime

@JacksonXmlRootElement(localName = "Flat")
data class FlatDto(
    val id: Long,
    val name: String,
    val coordinates: Coordinate,
    val creationDate: LocalDateTime,
    val area: Double,
    val numberOfRooms: Int,
    val furnish: Furnish,
    val view: View?,
    val transport: Transport,
    val house: HouseDto?
)


data class Coordinate(
    val x: Double,
    val y: Int,
)

enum class Transport {
    FEW,
    NONE,
    NORMAL,
}
enum class View {
    YARD,
    PARK,
    NORMAL,
    GOOD,
}
enum class Furnish() {
    NONE,
    BAD,
    LITTLE,
    FINE,
    DESIGNER;
}
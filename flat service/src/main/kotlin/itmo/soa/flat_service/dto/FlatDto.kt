package itmo.soa.flat_service.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
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
enum class Furnish(private val priority: Int) {
    NONE(0),
    BAD(1),
    LITTLE(2),
    FINE(3),
    DESIGNER(4);
    fun getValuesLessThenThis() : List<Furnish> = Furnish.values().filter { it.priority < this.priority }
}
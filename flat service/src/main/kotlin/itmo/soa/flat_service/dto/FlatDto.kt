package itmo.soa.flat_service.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.validation.Valid
import java.time.LocalDateTime
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@JacksonXmlRootElement(localName = "Flat")
data class FlatDto(
    @field:Min(1)
    val id: Long?,
    @field:NotBlank
    val name: String?,
    @field:NotNull
    val coordinates: Coordinate?,
    val creationDate: LocalDateTime?,
    @field:Max(889)
    @field:Min(0)
    val area: Double?,
    @field:Max(19)
    @field:Min(0)
    val numberOfRooms: Int?,
    @field:NotNull
    val furnish: Furnish?,
    val view: View?,
    @field:NotNull
    val transport: Transport?,
    val house: HouseDto?
)

data class Coordinate(
    @field:Min(-143)
    val x: Double,
    @field:Min(-903)
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
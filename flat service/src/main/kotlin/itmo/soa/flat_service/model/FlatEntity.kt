package itmo.soa.flat_service.model

import itmo.soa.flat_service.dto.Coordinate
import itmo.soa.flat_service.dto.Furnish
import itmo.soa.flat_service.dto.Transport
import itmo.soa.flat_service.dto.View
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "flat")
data class FlatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val name: String,
    val coordinateX: Double,
    val coordinateY: Int,
    val creationDate: LocalDateTime,
    val area: Double,
    val numberOfRooms: Int,
    @Enumerated(EnumType.STRING)
    val furnish: Furnish,
    @Enumerated(EnumType.STRING)
    val view: View?,
    @Enumerated(EnumType.STRING)
    val transport: Transport,
    @ManyToOne
    var house: HouseEntity?,
)
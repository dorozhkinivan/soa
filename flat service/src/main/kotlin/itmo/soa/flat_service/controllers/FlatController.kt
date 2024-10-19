package itmo.soa.flat_service.controllers

import itmo.soa.flat_service.converters.FlatConverter
import itmo.soa.flat_service.dto.Coordinate
import itmo.soa.flat_service.dto.FlatDto
import itmo.soa.flat_service.dto.FlatListDto
import itmo.soa.flat_service.dto.Furnish
import itmo.soa.flat_service.dto.NumberDto
import itmo.soa.flat_service.dto.Transport
import itmo.soa.flat_service.dto.View
import itmo.soa.flat_service.exceptions.ValidationException
import itmo.soa.flat_service.service.FlatService
import itmo.soa.flat_service.utils.getListRequestConfig
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/flats")
class FlatController(
    private val flatService: FlatService,
    private val flatConverter: FlatConverter,
) {
    @GetMapping
    fun getFlats(httpServletRequest: HttpServletRequest) : FlatListDto = FlatListDto(
        flats = flatService.getList(httpServletRequest.getListRequestConfig())
            .map(flatConverter::toDto)
    )

    @GetMapping( "/{id}")
    fun getFlat(@PathVariable("id") id: Long) : FlatDto = flatService.get(id).let(flatConverter::toDto)

    @GetMapping("/min-coordinates")
    fun getFlatWithMinCoordinate() : FlatDto = flatService.getWithMinCoordinates().let(flatConverter::toDto)

    @GetMapping("/furnish-less-then")
    fun getFurnishLessThen(
        @RequestParam("maxFurnish") maxFurnishString : String,
        httpServletRequest: HttpServletRequest
    ) : FlatListDto {
        val maxFurnish = kotlin.runCatching {
            Furnish.valueOf(maxFurnishString.uppercase())
        }.getOrElse {
            throw ValidationException("Value is unknown!", "maxFurnish")
        }
        return FlatListDto(flatService.getListWithFurnishLessThen(httpServletRequest.getListRequestConfig(), maxFurnish).map(flatConverter::toDto))
    }

    @GetMapping("/number-of-rooms")
    fun getNumberOfRoomsMoreThen(
      @RequestParam("minRooms")  minRooms : Int
    ) : NumberDto {
        if (minRooms < 0)
            throw ValidationException("Value must be positive or null!", "minRooms")
        return NumberDto(
            value = flatService.getNumberOfRoomsMoreThen(minRooms)
        )
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createFlat(@RequestBody @Valid flat: FlatDto) : FlatDto {
        val entity = flatService.create(flatConverter.toEntity(flat))
        return entity.let(flatConverter::toDto)
    }

    @PutMapping("/{id}")
    fun updateFlat(@PathVariable("id") id: Long, @RequestBody @Valid flat: FlatDto) : FlatDto{
        val entity = flatService.update(id, flat.let(flatConverter::toEntity))
        return entity.let(flatConverter::toDto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun deleteFlat(@PathVariable("id") id: Long){
        flatService.delete(id)
    }
}

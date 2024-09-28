package itmo.soa.flat_service.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "Flats")
data class FlatListDto(
    @JacksonXmlProperty(localName = "Flat")
    @JacksonXmlElementWrapper(useWrapping = false)
    val flats: List<FlatDto>
)
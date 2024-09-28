package itmo.soa.agency_service.dtos

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText


@JacksonXmlRootElement(localName = "number")
class NumberDto(
    @JacksonXmlText
    val value: Int
)
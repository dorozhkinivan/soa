package itmo.soa.agency_service.client

import com.fasterxml.jackson.jakarta.rs.xml.JacksonXMLProvider
import itmo.soa.agency_service.config.ObjectMapperContextResolver
import itmo.soa.agency_service.dtos.FlatDto
import itmo.soa.agency_service.dtos.FlatListDto
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.ClientBuilder


@Singleton
open class HttpClient @Inject constructor(
    private val xmlResolver: ObjectMapperContextResolver
) {
    // TODO move all envs to config @Singleton
    private val flatServiceUrl = System.getenv("FLAT_SERVICE_LOCAL_URL") ?: "http://localhost:8080/flat-management"
    private var client: Client? = null

    @PostConstruct
    open fun init() {
        val xmlMapper = xmlResolver.getContext(null)

        client = ClientBuilder.newBuilder()
            .register(JacksonXMLProvider(xmlMapper))
            .build()
    }

    open fun getFlats(page: Int, size: Int) : List<FlatDto> =
        client!!
            .target("$flatServiceUrl/flats?page=$page&size=$size")
            .request()
            .get()
            .let {
            when(it.status) {
                200 -> it.readEntity(FlatListDto::class.java).flats
                else -> throw RuntimeException(it.status.toString())
            }
    }

    open fun getFlatByParams(cheapest: Boolean, size: Int) : FlatDto? =
        client!!
            .target("$flatServiceUrl/flats?size=1&sort=${ if (cheapest) "" else "-" }area&filter=numberOfRooms,$size")
            .request()
            .get()
            .let {
            when(it.status) {
                200 -> it.readEntity(FlatListDto::class.java).flats.firstOrNull()
                else -> throw RuntimeException(it.status.toString())
            }
    }

    open fun getIp() : String = client!!.target("https://api.ipify.org").request().get().status.toString()

    @PreDestroy
    open fun cleanUp() {
        client?.close()
    }
}
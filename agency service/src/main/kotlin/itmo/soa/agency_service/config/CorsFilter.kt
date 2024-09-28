package itmo.soa.agency_service.config

import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider
import java.io.IOException


@Provider
open class CORSFilter : ContainerResponseFilter {
    @Throws(IOException::class)
    override fun filter(
        request: ContainerRequestContext,
        response: ContainerResponseContext,
    ) {
        response.headers.apply {
            add("Access-Control-Allow-Origin", "*")
            response.headers.add(
                "Access-Control-Allow-Headers",
                "CSRF-Token, X-Requested-By, Authorization, Content-Type"
            )
            response.headers.add("Access-Control-Allow-Credentials", "true")
            response.headers.add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD"
            )
        }
    }
}
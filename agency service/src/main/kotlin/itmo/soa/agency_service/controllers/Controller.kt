package itmo.soa.agency_service.controllers

import itmo.soa.agency_service.client.HttpClient
import itmo.soa.agency_service.dtos.NumberDto
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/")
open class Controller {
    @Inject
    private lateinit var client: HttpClient

    @GET
    @Path("/find-with-balcony/{cheapest}/{with-balcony}")
    @Produces(MediaType.APPLICATION_XML)
    open fun findWithBalcony(
        @PathParam("cheapest") cheapest: Boolean,
        @PathParam("with-balcony") withBalcony: Boolean,
    ): Response {
//        return Response.ok(FlatDto(
//            id = 1,
//            area = 0.0,
//            coordinates = Coordinate(1.0, 1),
//            creationDate = LocalDateTime.now(),
//            furnish = Furnish.FINE,
//            house = null,
//            name = "",
//            numberOfRooms = 1,
//            transport = Transport.NONE,
//            view = null
//        )).build()
        val flat = client.getFlatByParams(cheapest, if (withBalcony) 2 else 1)
        return flat?.let {
            Response.ok(it).build()
        } ?: Response.status(404).build()
    }

    @GET
    @Path("/get-total-cost")
    @Produces(MediaType.APPLICATION_XML)
    open fun getTotalCost(): Response {
        var proceedAll = false
        var totalNumberOfRooms = 0
        var requestIndex = 1
        val requestedSize = 100
        while (!proceedAll){
            val roomCountForFlats = client.getFlats(
                page = requestIndex,
                size = requestedSize
            ).map { it.numberOfRooms }
            if (roomCountForFlats.size < requestedSize)
                proceedAll = true
            requestIndex++
            totalNumberOfRooms += roomCountForFlats.sum()
        }
        return Response.ok(NumberDto(totalNumberOfRooms)).build()
    }
}
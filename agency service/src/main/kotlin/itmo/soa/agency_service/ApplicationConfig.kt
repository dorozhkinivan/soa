package itmo.soa.agency_service

import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/")
open class ApplicationConfig : Application()
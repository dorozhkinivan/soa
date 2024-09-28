package itmo.soa.flat_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlatServiceApplication

fun main(args: Array<String>) {
    runApplication<FlatServiceApplication>(*args)
}
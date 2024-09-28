package itmo.soa.flat_service.exceptions

class ValidationException(
    override val message: String,
    val fields: List<String>,
) : RuntimeException() {
    constructor(message: String, vararg fields: String) : this(message, fields.toList())
}
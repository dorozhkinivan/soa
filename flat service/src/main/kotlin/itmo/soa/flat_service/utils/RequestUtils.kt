package itmo.soa.flat_service.utils

import itmo.soa.flat_service.exceptions.ValidationException
import itmo.soa.flat_service.model.FlatEntity
import jakarta.servlet.http.HttpServletRequest

private val DEFAULT_PAGE = 1
private val MIN_PAGE = 1
private val DEFAULT_SIZE = System.getenv("PAGINATION_DEFAULT_SIZE")?.toIntOrNull() ?: 20
private val MAX_SIZE = System.getenv("PAGINATION_MAX_SIZE")?.toIntOrNull() ?: 100
private val MIN_SIZE = 1

/**
 * @throws ValidationException
 */
fun HttpServletRequest.getListRequestConfig() : ListRequestConfig {
    val invalidFields = mutableSetOf<String>()
    val page : Int? = this.getParameter("page")?.let {
        it.toIntOrNull().also { value ->
            if (value == null || value < MIN_PAGE) {
                invalidFields.add("page")
            }
        }
    }
    val size : Int? = this.getParameter("size")?.let {
        it.toIntOrNull().also { value ->
            if (value == null || value < MIN_SIZE || value > MAX_SIZE) {
                invalidFields.add("size")
            }
        }
    }
    val sort = (this.getParameterValues("sort") ?: emptyArray())
        .flatMap { it.split(",") }
        .map {
        if (it.startsWith("-"))
            it.substring(1) to false
        else
            it to true
    }

    for (key in sort.map { it.first }){
        if (key !in flatEntityValidFields){
            invalidFields.add("sort")
            break
        }
    }

    val filterQuery = this.getParameterValues("filter")?.flatMap { it.split(",") } ?: emptyList()
    val result = mutableMapOf<String, String>()

    var filterIsInvalid = false

    if (filterQuery.size % 2 == 0) {
        for (i in filterQuery.indices step 2) {
            result[filterQuery[i]] = filterQuery[i + 1]
        }
    } else {
        filterIsInvalid = true
    }
    result.forEach { (key, _) ->
        if (key !in flatEntityValidFields){
            filterIsInvalid = true
        }
    }

    if (filterIsInvalid){
        invalidFields.add("filter")
    }


    if (invalidFields.isNotEmpty()){
        throw ValidationException("Some params are invalid!", invalidFields.toList())
    }
    return ListRequestConfig(
        page = page ?: DEFAULT_PAGE,
        size = size ?: DEFAULT_SIZE,
        sortKeyToIsAscending = sort,
        filterKeysToValues = result,
    )
}

data class ListRequestConfig(
    val page: Int,
    val size: Int,
    val sortKeyToIsAscending: List<Pair<String, Boolean>>,
    val filterKeysToValues: Map<String, String>,
)

private val flatEntityValidFields = FlatEntity::class.java.declaredFields.map { it.name }.toSet()
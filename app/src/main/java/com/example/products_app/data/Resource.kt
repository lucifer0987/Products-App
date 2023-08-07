package com.example.products_app.data

sealed class Resource<T>(
    val data: T? = null,
    val error: Any? = null,
    val errors: ArrayList<String>? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data = data)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
    class DataError<T>(error: Any?) : Resource<T>(error = error)
    class MultipleDataError<T>(errors: ArrayList<String>) : Resource<T>(errors = errors)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$error]"
            is MultipleDataError -> "Error[exception=$errors]"
            is Loading<T> -> "Loading"
        }
    }
}

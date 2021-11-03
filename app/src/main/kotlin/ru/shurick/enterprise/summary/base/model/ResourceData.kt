package ru.shurick.enterprise.summary.base.model

import androidx.compose.runtime.Composable

sealed class ResourceData<out R> {

    data class Success<out T>(val data: T) : ResourceData<T>()
    data class Error(val exception: Throwable) : ResourceData<Nothing>()
    object Loading : ResourceData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val ResourceData<*>.isLoading: Boolean
    get() = this is ResourceData.Loading

val ResourceData<*>.isSucceeded
    get() = this is ResourceData.Success && data != null

@Composable
inline fun <reified R> ResourceData<R>.what(
    success: @Composable (data: ResourceData.Success<R>) -> Unit,
    failed: @Composable (ResourceData.Error) -> Unit,
    loading: @Composable () -> Unit
): Unit {
    return when (this) {
        is ResourceData.Success -> success(this)
        is ResourceData.Error -> failed(this)
        else -> loading()
    }
}

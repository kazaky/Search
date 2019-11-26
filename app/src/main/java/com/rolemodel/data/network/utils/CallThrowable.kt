package com.rolemodel.data.network.utils

import com.rolemodel.data.model.Status
import com.squareup.moshi.JsonDataException
import io.reactivex.functions.Consumer
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*

object CallThrowable {
    abstract class Errors : Consumer<Throwable> {

        override fun accept(throwable: Throwable) = when (throwable) {
            is SocketTimeoutException -> onShowStatus(Status(message = "Timeout"))
            is IOException -> onShowStatus(Status(message = "Connection problem"))
            is HttpException -> handleHttpException(throwable)
            is JsonDataException -> handleJsonDataError(throwable)
            else -> handleUnKnownError(throwable)
        }

        private fun handleHttpException(exception: HttpException) {
            with(exception) {
                when {
                    code() == 400 -> onFieldError(exception)
                    code() == 401 -> onUserUnAuthorized()
                    code() == 429 -> onRateLimitExceeded(exception)
                    code() == 500 -> onServerCrashed(exception)
                    code() == 503 -> onServerDown(exception)
                    else -> onUnKnownHttpError(exception)
                }
            }
        }

        private fun onFieldError(httpException: HttpException) {
            try {
                httpException.response().errorBody()?.let { safeErrorBody ->
                    val jsonObject = JSONObject(safeErrorBody.string())
                    val keys = jsonObject.keys()
                    while (keys.hasNext()) {
                        val field = (keys.next() as String).toLowerCase(Locale.US)
                        val message = jsonObject.getJSONArray(field).getString(0)
                        onShowStatus(Status(message, field))
                    }
                }
            } catch (e: IOException) {
                e.message?.let { Status(it) }?.let { onShowStatus(it) }
            } catch (e: JSONException) {
                e.message?.let { Status(it) }?.let { onShowStatus(it) }
            }
        }

        private fun onUserUnAuthorized() =
                onShowStatus(Status("Try again in seconds"))

        private fun onRateLimitExceeded(e: HttpException?) =
                onShowStatus(Status(extractLimitExceededMessage(e)))

        private fun extractLimitExceededMessage(httpException: HttpException?): String {
            val headers = httpException?.response()?.headers()
            val waitingPeriod = headers?.get("Retry-After")
            return "Try again after $waitingPeriod seconds"
        }

        private fun onServerCrashed(httpException: HttpException) {
            // Crashlytics.log(httpException.localizedMessage)

            if (httpException.message.isNullOrEmpty())
                onShowStatus(Status("An unexpected error occurred\nTry again later"))
            else onShowStatus(Status(httpException.message()))

        }

        private fun onServerDown(httpException: HttpException) {
            //  Crashlytics.log(httpException.localizedMessage)

            if (httpException.message.isNullOrEmpty())
                onShowStatus(Status("Server is currently under planned maintenance\nCome back soon"))
            else onShowStatus(Status(httpException.message()))
        }

        private fun onUnKnownHttpError(httpException: HttpException) {
            // Crashlytics.log(httpException.localizedMessage)

            if (httpException.message.isNullOrEmpty())
                onShowStatus(Status("UnKnown Http Error happened"))
            else onShowStatus(Status("UnKnown Http Error happened" + httpException.message()))
        }

        private fun handleJsonDataError(throwable: JsonDataException) {
            throwable.localizedMessage?.let { message ->
                onShowStatus(Status(message))
            }
        }

        private fun handleUnKnownError(throwable: Throwable) {
            //  Crashlytics.log(throwable.localizedMessage)
            onShowStatus(Status("UnKnown Error happened " + throwable.localizedMessage))
        }

        protected abstract fun onShowStatus(status: Status)
    }
}
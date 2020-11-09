package com.msewa.healthism.util

data class Resources<out T>(val code: Status, val data: T?, val message: String?, val messageId:Int?) {
    companion object {
        fun <T> success(data: T): Resources<T> {
            return Resources(Status.SUCCESS, data, null,null)
        }
        fun <T> loading(): Resources<T> {
            return Resources(Status.LOADING, null, null,null)
        }

        fun <T> errorMessage(message:String): Resources<T> {
            return Resources(Status.ERROR, null, message,null)
        }
        fun <T> errorId(messageId:Int): Resources<T> {
            return Resources(Status.ERROR, null, null,messageId)
        }

       fun <T> noInternet(): Resources<T> {
            return Resources(Status.NO_INTERNET, null,null,null )
        }
    }
}

package utility

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiCallThread {
    fun main(url: String): String? {
        // scope della coroutine
        val scope = CoroutineScope(Dispatchers.Default)

        // definisco i dati in input
        val urlInput = url

        // definisco la coroutine
        val apiCallCoroutine = scope.async {
            // compongo la richiesta API
            val client = OkHttpClient()
            val request = Request.Builder().url(urlInput).build()
            Log.e("AAAAAAA", "URL richiesta API: $urlInput")

            // eseguo la chiamata API
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return@async response.body!!.string()
                } else {
                    return@async null
                }
            }
        }

        // mi metto in attesa della fine della coroutine
        val apiCallResult = runBlocking {
            apiCallCoroutine.await()
        }

        // ritorno il risultato
        return apiCallResult
    }
}
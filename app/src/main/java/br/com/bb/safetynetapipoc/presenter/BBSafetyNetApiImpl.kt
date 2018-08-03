package br.com.bb.safetynetapipoc.presenter

import android.content.Context
import android.util.Base64
import android.util.Log
import br.com.bb.safetynetapipoc.MainActivity
import br.com.bb.safetynetapipoc.checkGooglePlayVersionImpl
import br.com.bb.safetynetapipoc.model.BBSafetyNetApiModel
import br.com.bb.safetynetapipoc.model.OnFinishGetSafetyNetAPI
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson


class BBSafetyNetApiImpl : BBSafetyNetAPI {
    override fun checkGooglePlayVersion(context: Context): Boolean {
        return checkGooglePlayVersionImpl(context)
    }

    override fun compatibilityCheckRequest(nonce: ByteArray, API_KEY: String, context: Context,
                                           onFinishGetSafetyNetAPI: OnFinishGetSafetyNetAPI) {
        Log.d("BBSafetyNetApiImpl", "Nonce_ByteArray: " + nonce + " API_KEY: " + API_KEY)
        context as MainActivity

        SafetyNet.getClient(context).attest(nonce, API_KEY)
            .addOnSuccessListener(context, OnSuccessListener<SafetyNetApi.AttestationResponse> { result ->
                val processedJws = String(extractJwsData(result.jwsResult)!!)
                val model = Gson().fromJson(processedJws, BBSafetyNetApiModel::class.java)
                onFinishGetSafetyNetAPI.onFinish(model)
                Log.d("BBSafetyNetApiImpl", processedJws)
            })
            .addOnFailureListener(context, OnFailureListener { e ->
                // An error occurred while communicating with the service.
                if (e is ApiException) {
                    Log.d("BBSafetyNetApiImpl", "APIError: " + e.message)
                    // An error with the Google Play services API contains some
                    // additional details.
                    val apiException = e
                    // You can retrieve the status code using the
                    // apiException.getStatusCode() method.
                } else {
                    // A different, unknown type of error occurred.
                    Log.d("BBSafetyNetApiImpl", "Error: " + e.message)
                }
            })

    }

    private fun extractJwsData(jws: String?): ByteArray? {
        // The format of a JWS is:
        // <Base64url encoded header>.<Base64url encoded JSON data>.<Base64url encoded signature>
        // Split the JWS into the 3 parts and return the JSON data part.
        val parts = jws?.split("[.]".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        if (parts?.size != 3) {
            System.err.println("Failure: Illegal JWS signature format. The JWS consists of "
                    + parts?.size + " parts instead of 3.")
            return null
        }
        return Base64.decode(parts[1], Base64.DEFAULT)
    }
}
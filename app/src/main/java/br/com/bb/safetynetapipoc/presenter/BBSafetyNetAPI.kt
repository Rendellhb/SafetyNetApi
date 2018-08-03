package br.com.bb.safetynetapipoc.presenter

import android.content.Context
import br.com.bb.safetynetapipoc.model.BBSafetyNetApiModel
import br.com.bb.safetynetapipoc.model.OnFinishGetSafetyNetAPI

interface BBSafetyNetAPI {
    fun checkGooglePlayVersion(context: Context): Boolean

    fun compatibilityCheckRequest(nonce: ByteArray, API_KEY: String, context: Context,
                                  onFinishGetSafetyNetAPI: OnFinishGetSafetyNetAPI)
}
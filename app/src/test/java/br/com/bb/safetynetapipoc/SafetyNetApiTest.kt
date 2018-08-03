package br.com.bb.safetynetapipoc

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import br.com.bb.safetynetapipoc.presenter.MainActivityPresenter
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SafetyNetApiTest {

    val context: Context = InstrumentationRegistry.getTargetContext()
    val nonce = String(getRequestNonce()!!)

    @Test
    fun runNonceGenerator() {
        System.out.println(nonce)
        assert(nonce.length >= 16)
    }

    @Test
    fun checkIfDeviceHasCompatibility() {
        val presenter = MainActivityPresenter()
        presenter.checkIntegrity(context)

        assert(presenter.nonceTV?.text == nonce)
    }

}
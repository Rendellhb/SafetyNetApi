package br.com.bb.safetynetapipoc

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.view.View
import br.com.bb.safetynetapipoc.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        findViewById<LinearLayoutCompat>(R.id.resultContainer).visibility = View.GONE

        presenter.nonceTV = findViewById(R.id.nonce)
        presenter.timestampMsTV = findViewById(R.id.timestampMs)
        presenter.apkPackageNameTV = findViewById(R.id.apkPackageName)
        presenter.apkCertificateDigestSha256TV = findViewById(R.id.apkCertificateDigestSha256)
        presenter.apkDigestSha256TV = findViewById(R.id.apkDigestSha256)
        presenter.basicIntegrityTV = findViewById(R.id.basicIntegrity)
        presenter.ctsProfileMatchTV = findViewById(R.id.ctsProfileMatch)
        presenter.resultContainerLL = findViewById(R.id.resultContainer)
    }

    fun onClickCheckIntegrityButton(view: View) {
        presenter.checkIntegrity(this as Context)
    }

}

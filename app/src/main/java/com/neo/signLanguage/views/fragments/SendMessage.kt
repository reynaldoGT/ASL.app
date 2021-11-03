package com.neo.signLanguage.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import com.neo.signLanguage.*


class SendMessage : Fragment() {

    private var interstitial: InterstitialAd? = null
    private var count = 0

    var imageview: ImageView? = null
    var currentLetter: TextView? = null
    var buttonSendMessage: Button? = null
    var buttonStopMessage: Button? = null
    var edSendMessage: TextInputLayout? = null
    var seeCurrentMessage: TextView? = null
    var parentLayout: View? = null
    var toolbar: Toolbar? = null

    var lettersArrays: ArrayList<Sing>? = null

    var bottomNavigationView: BottomNavigationView? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(false)
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Logger.addLogAdapter(AndroidLogAdapter())

        val view = inflater.inflate(R.layout.fragment_send_message, container, false)
        initAds()
        initListeners()

        val shared = Shared()
        lettersArrays = shared.getLetterArray()

        //toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        imageview = view.findViewById(R.id.ivSing)
        edSendMessage = view.findViewById(R.id.edSendMessage)
        seeCurrentMessage = view.findViewById(R.id.seeCurrentMessage)
        currentLetter = view.findViewById(R.id.currentLetter)
        parentLayout = view.findViewById(R.id.layoutSendMessage)
        /* Buttons */
        buttonSendMessage = view.findViewById(R.id.btnSendMessage)
        buttonStopMessage = view.findViewById(R.id.btnSendMessageCancel)

        bottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)

        edSendMessage?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            seeCurrentMessage?.visibility = View.VISIBLE
            seeCurrentMessage?.text = edSendMessage?.editText?.text.toString()

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                seeCurrentMessage?.text = edSendMessage?.editText?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = edSendMessage?.editText?.text.toString()

                sendMessage(createMessage)

                return@OnKeyListener true
            }
            false
        })


        imageview?.setOnClickListener {

            changeImage()
        }

        buttonSendMessage?.setOnClickListener {

            sendMessage(edSendMessage?.editText?.text.toString())
        }

        buttonStopMessage?.setOnClickListener {
            /*break;*/
        }

        setHasOptionsMenu(true);
        return view

    }


    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        val tipo =
            if (lettersArrays!![randomLetter].type == "letter") getString(R.string.letter) else getString(
                R.string.number
            )
        currentLetter?.text = tipo + " " + lettersArrays!![randomLetter].letter.toUpperCase(
            Locale.ROOT
        )
        imageview?.setImageDrawable(
            ContextCompat.getDrawable(
                activity!!.applicationContext, // Context
                lettersArrays!![randomLetter].image
            )
        )
    }

    private fun generateSingLanguageMessage(message: String) {

        seeCurrentMessage?.visibility = view!!.visibility
        seeCurrentMessage?.text = message

        buttonSendMessage?.isEnabled = false;
        edSendMessage?.isEnabled = false;

        /*hideKeyboard()*/

        val cleanString = message.trim().toLowerCase(Locale.ROOT)

        val arraySentenceSing = ArrayList<Sing>()
//        val stringArray = message.toCharArray()

        val stringArray = cleanString.replace(" ", "").toCharArray()

        for (i in stringArray) {

            for (letterPosition in lettersArrays!!) {
                if (letterPosition.letter == i.toString()) {
                    arraySentenceSing.add(letterPosition)
                }
            }

        }

        showMessageWithSing(arraySentenceSing)
    }

    private fun showMessageWithSing(sentenceInArrayImage: ArrayList<Sing>) {
        var job: Job? = null
        job = GlobalScope.launch(context = Dispatchers.Main) {
            for (index in sentenceInArrayImage) {
                buttonStopMessage?.isVisible = true
                buttonStopMessage?.setOnClickListener {
                    job?.cancel()
                    resetStatus()
                }
                bottomNavigationView?.isVisible = false;
                buttonSendMessage?.isVisible = false

                delay(MainActivity.pref.getDelay().toLong())
                val tipo =
                    if (index.type != "letter") getString(R.string.letter) else getString(R.string.number)
                currentLetter?.text = tipo + " " + index.letter.toUpperCase()
                imageview?.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!.applicationContext, // Context
                        index.image
                    )
                )
            }
            resetStatus()
        }
    }

    private fun sendMessage(message: String) {
        if (message.isNotEmpty()) {
            generateSingLanguageMessage(message)

        } else {

            Logger.d("Mensaje vacio")
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                R.string.empty_message,
                Snackbar.LENGTH_LONG,
            ).show();

        }
    }

    private fun resetStatus() {
        buttonStopMessage?.isVisible = false
        buttonSendMessage?.isEnabled = true
        edSendMessage?.isEnabled = true
        bottomNavigationView?.isVisible = true
        buttonSendMessage?.isVisible = true
        checkCounter()

    }


    private fun initListeners() {
        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }
        }
    }

    private fun initAds() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity!!.applicationContext, // Context,
            getString(R.string.propd_intersititial_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial = interstitialAd
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitial = null
                }
            })
    }

    private fun checkCounter() {
        count += 1
        if (count == 5) {
            showAds()
            count = 0
            initAds()
        }
    }

    private fun showAds() {
        Logger.d("Show add")

        interstitial?.show(
            activity!!
        )
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity!!.menuInflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.settings) {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
            true
        } else super.onOptionsItemSelected(item)
    }
*/override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
}

package com.neo.signLanguage.ui.view.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import com.google.common.collect.Lists
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.FragmentTranslateBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.binding
import com.neo.signLanguage.utils.ConfigTeclado
import com.neo.signLanguage.utils.SolicitarTarea
import java.util.*


const val USUARIO = 0
const val BOT = 1
const val ENTRADA_DE_VOZ = 2

class TranslateFragment : Fragment() {

    private var _binding: FragmentTranslateBinding? = null
    private var cliente: SessionsClient? = null
    private var sesion: SessionName? = null
    private val uuid: String = UUID.randomUUID().toString()
    private var asistente_voz: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* _binding?.scrollChat!!.post {
            _binding!!.scrollChat.fullScroll(ScrollView.FOCUS_DOWN)
        }*/


        /*_binding!!.cajaMensajes.setOnKeyListener { view, keyCode, event ->
            if (event.action === KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        enviarMensaje(_binding!!.enviar)
                    }
                    else -> {
                    }
                }
            }
            false
        }

        _binding!!.enviar.setOnClickListener(this::enviarMensaje)

        // Al botón del microfono también le pasamos un setOnClickListener, este envia mensajes de audio con el
        // método enviarMensajeMicrofono() el cual crearemos más adelante
        _binding!!.microfono.setOnClickListener(this::enviarMensajeMicrofono)

        // Llamamos al método iniciarAsistente() el cual crearemos más adelante
        iniciarAsistente()

        // Llamamos al método iniciarAsistenteVoz() el cual crearemos más adelante
        iniciarAsistenteVoz()*/
    }

    private fun enviarMensaje(view: View) {

        // Obtenemos el mensaje de la caja de texto y lo pasamos a String
        val mensaje = _binding!!.cajaMensajes.text.toString()

        // Si el usuario no ha escrito un mensaje en la caja de texto y presiona el botón enviar, le mostramos
        // un Toast con un mensaje 'Ingresa tu mensaje ...'
        if (mensaje.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.placeholder), Toast.LENGTH_LONG)
                .show()
        }

        // Si el usuario agrego un mensaje a la caja de texto, llamamos al método agregarTexto()
        else {
            agregarTexto(mensaje, USUARIO)
            _binding!!.cajaMensajes.setText("")

            // Enviamos la consulta del usuario al Bot
            val ingresarConsulta =
                QueryInput.newBuilder()
                    .setText(TextInput.newBuilder().setText(mensaje).setLanguageCode("es")).build()
            SolicitarTarea(requireActivity(), sesion!!, cliente!!, ingresarConsulta).execute()
        }
    }

    private fun iniciarAsistente() {
        try {
            // Archivo JSON de configuración de la cuenta de Dialogflow (Google Cloud Platform)
            val config = resources.openRawResource(R.raw.credenciales)

            // Leemos las credenciales de la cuenta de Dialogflow (Google Cloud Platform)
            val credenciales = GoogleCredentials.fromStream(config)
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"))

            // Leemos el 'projectId' el cual se encuentra en el archivo 'credenciales.json'
            val projectId = (credenciales as ServiceAccountCredentials).projectId

            // Construimos una configuración para acceder al servicio de Dialogflow (Google Cloud Platform)
            val generarConfiguracion: SessionsSettings.Builder = SessionsSettings.newBuilder()

            // Configuramos las sesiones que usaremos en la aplicación
            val configurarSesiones: SessionsSettings =
                generarConfiguracion.setCredentialsProvider(
                    FixedCredentialsProvider.create(
                        credenciales
                    )
                )
                    .build()
            cliente = SessionsClient.create(configurarSesiones)
            sesion = SessionName.of(projectId, uuid)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Función iniciarAsistenteVoz
    private fun iniciarAsistenteVoz() {

        asistente_voz = TextToSpeech(
            requireContext()
        ) { status ->
            if (status != TextToSpeech.ERROR) {
                asistente_voz?.language = Locale("es")
            }
        }

    }

    private fun agregarTexto(mensaje: String, type: Int) {

        // Coloco el FramLayout dentro de la variable layoutFrm
        val layoutFrm: FrameLayout

        // Según sea el rol, le cargamos el FrameLayout y llamamos a un método respectivo
        // Los métodos agregarTextoUsuario() y agregarTextoBot() los crearé más adelante
        when (type) {
            USUARIO -> layoutFrm = agregarTextoUsuario()
            BOT -> layoutFrm = agregarTextoBot()
            else -> layoutFrm = agregarTextoBot()
        }

        // Si el usuario hace clic en la caja de texto
        layoutFrm.isFocusableInTouchMode = true

        // Pasamos un LinearLayout
        _binding!!.linearChat.addView(layoutFrm)

        // Mostramos los textos de los mensajes en un TextView
        val textview = layoutFrm.findViewById<TextView>(R.id.msg_chat)
        textview.setText(mensaje)


        // Si el usuario sale del modo escritura, ocultamos el teclado del dispositivo
        // El método 'ocultarTeclado()' lo crearemos más adelante
        ConfigTeclado.ocultarTeclado(requireActivity())

        // Enfocamos el TextView Automáticamente
        layoutFrm.requestFocus()

        // Volvemos a cambiar el enfoque para editar el texto y continuar escribiendo
        _binding!!.cajaMensajes.requestFocus()

        // Si es un cliente el que envía un mensaje al Bot, cargamos el método 'TexToSpeech'
        // 'TexToSpeech' junto a otras métodos procesa los mensajes de voz que seran enviados al Bot
        if (type != USUARIO) asistente_voz?.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null)

    }

    // Colocamos los mensajes del Usuario en el layout 'mensaje_usuario'
    fun agregarTextoUsuario(): FrameLayout {
        val inflater = LayoutInflater.from(requireContext())
        return inflater.inflate(R.layout.mensaje_usuario, null) as FrameLayout
    }

    // Colocamos los mensajes del Bot en el layout 'mensaje_bot'
    fun agregarTextoBot(): FrameLayout {
        val inflater = LayoutInflater.from(requireContext())
        return inflater.inflate(R.layout.mensaje_bot, null) as FrameLayout
    }

    fun validar(response: DetectIntentResponse?) {
        Log.d("query result", response!!.queryResult.toString())
        try {
            if (response != null) {

                // fulfillmentText retorna un String (Texto) al usuario en la pantalla
                // fulfillmentMessagesList (Objeto) retorna una lista de objetos
                var respuestaBot: String = ""

                if (response.queryResult.fulfillmentText == " ")
                    respuestaBot =
                        response.queryResult.fulfillmentMessagesList[0].text.textList[0].toString()
                else
                    respuestaBot = response.queryResult.fulfillmentText

                // Pasamos el método agregarTexto()
                agregarTexto(respuestaBot, BOT)

            } else {
                // Mostramos un mensaje si el audio que envio el usuario no se entiende
                agregarTexto("No se entiende", BOT)
            }
        } catch (e: Exception) {
            // Mostramos al usuario el texto 'Por Favor, ingresa un mensaje'
            agregarTexto(getString(R.string.send_message), BOT)
        }
    }

    override fun onActivityResult(codigoSolicitud: Int, codigoResultado: Int, datos: Intent?) {
        super.onActivityResult(codigoSolicitud, codigoResultado, datos)

        // Obtenemos el resultado de nuestra actividad principal
        // Si la variable codigoResultado esta ok y la variable datos no es null
        when (codigoSolicitud) {
            ENTRADA_DE_VOZ -> {
                if (codigoResultado == AppCompatActivity.RESULT_OK
                    && datos != null
                ) {

                    // getStringArrayListExtra recupera datos extendidos del Intent
                    val resultado = datos.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    // El usuario puede agregar otro mensaje
                    _binding!!.cajaMensajes.text =
                        Editable.Factory.getInstance().newEditable(resultado?.get(0))

                    // El usuario puede hacer uso del micrófono
                    enviarMensaje(_binding!!.microfono)
                }
            }
        }
    }

    private fun enviarMensajeMicrofono(view: View) {

        // Llamamos al intento para reconocer voz del usuario y convertirla a texto
        val intento = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        // Definimos los modelos de reconocimiento de voz
        intento.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        // Le decimos que haga el reconocimiento de voz en el idioma local 'Locale.getDefault()'
        intento.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        // Si el usuario no habla algo, le mostramos el mensaje 'Di algo en el micrófono ...'
        intento.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "mensaje de voz"

        )

        // Si todo va bien, enviamos el audio del usuario al Bot
        try {
            startActivityForResult(intento, ENTRADA_DE_VOZ)
        }

        // Si el dispositivo del usuario no es compatible con la función del micrófono
        // Le mostramos el mensaje 'Tu teléfono no es compatible con la función de micrófono ...'
        // en un Toast
        catch (a: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "mensaje no adminitido",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // Si la aplicación es cerrada, detenemos el asistente de voz
        if (asistente_voz != null) {
            asistente_voz?.stop()
            asistente_voz?.shutdown()
        }
    }
}
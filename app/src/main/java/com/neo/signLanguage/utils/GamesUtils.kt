package com.neo.signLanguage.utils

import android.content.Context
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray
import com.neo.signLanguage.utils.Utils.Companion.messageToImages
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

class GamesUtils {
  companion object {
    val palabrasIngles = listOf(
      "time",
      "people",
      "way",
      "day",
      "man",
      "woman",
      "year",
      "good",
      "new",
      "first",
      "last",
      "long",
      "great",
      "own",
      "other",
      "old",
      "right",
      "big",
      "high",

      "small",
      "large",
      "next",
      "early",
      "young",
      "few",
      "public",
      "bad",
      "same",
      "able",
      "life",
      "work",
      "home",
      "city",
      "family",
      "friend",
      "food",
      "water",
      "music",
      "book",
      "mind",
      "heart",
      "love",
      "smile",
      "dream",
      "peace",
      "faith",
      "funny",
      "brave",
      "happy",
      "lucky",
      "strong",
      "wise",
      "beauty",
      "charm",
      "clean",
      "fresh",
      "quiet",
      "calm",
      "sweet",
      "gentle",
      "honest",
      "kind",
      "patient",
      "proud",
      "fun",
      "wild",
      "busy",
      "color",
      "sun",
      "moon",
      "star",
      "sky",
      "sea",
      "earth",
      "fire",
      "wind",
      "life",
      "light",
      "dark"
    )
    val palabrasEspanol = listOf(
      "tiempo",
      "gente",
      "camino",
      "dia",
      "hombre",
      "mujer",
      "año",
      "bueno",
      "nuevo",
      "primero",
      "ultimo",
      "largo",
      "genial",

      "propio",
      "otro",
      "viejo",
      "derecho",
      "grande",
      "alto",
      "pequeño",
      "grande",
      "proximo",
      "temprano",
      "joven",
      "pocos",
      "publico",
      "malo",
      "mismo",
      "capaz",
      "vida",
      "trabajo",
      "hogar",
      "ciudad",
      "familia",
      "amigo",
      "comida",
      "agua",
      "música",
      "libro",
      "mente",
      "corazón",
      "amor",
      "sonrisa",
      "sueño",
      "paz",
      "fe",
      "divertido",

      "valiente",
      "feliz",
      "afortunado",
      "fuerte",
      "sabio",
      "belleza",
      "encanto",
      "limpio",
      "fresco",
      "tranquilo",
      "calmado",
      "dulce",
      "suave",
      "honesto",
      "amable",
      "paciente",
      "orgulloso",
      "divertido",
      "salvaje",
      "ocupado",
      "color",
      "sol",
      "luna",
      "estrella",
      "cielo",
      "mar",
      "tierra",
      "fuego",
      "viento",
      "vida",
      "luz",
      "oscuridad",
      "sencillo",
      "alegre",
      "abierto",
      "fresco",
      "divertido",
      "rico",
      "agudo",
      "caliente",
      "indiferente",
      "último",
      "principal",
      "largo",
      "raro",
      "típico",
      "vivo",
      "joven"
    )

    fun getRandomToFindEquals(amount: Int): ArrayList<Sign> {
      val randomLetters = ArrayList<Sign>()
      val resortList = ArrayList<Sign>()
      val randomInts = generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(0 until getLetterArray(false).size)
      }
        .distinct()
        .take(amount)
        .toSet()
      val randomPosition = generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(0 until amount * 2)
      }
        .distinct()
        .take(amount * 2)
        .toSet()

      for (i in randomInts) {
        randomLetters.add(
          Sign(
            getLetterArray()[i].letter,
            getLetterArray()[i].image,
            "letter",
          ),
        )
        randomLetters.add(
          Sign(
            getLetterArray()[i].letter,
            getLetterArray()[i].image,
            "image",
          )
        )

      }
      for (i in randomPosition) {
        resortList.add(randomLetters[i])
      }
      return resortList
    }

    fun getRandomWord(): String {
      val index = Random.nextInt(palabrasIngles.size)
      return if (Locale.getDefault().language == "es")
        Utils.cleanString(palabrasEspanol[index])
      else
        palabrasIngles[index]
    }

    fun getRandomToFindCorrectLetter(amount: Int): Game {
      val randomLetters = ArrayList<Sign>()
      val randomInts = generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(0 until getLetterArray(false).size)
      }
        .distinct()
        .take(amount)
        .toSet()

      for (i in randomInts) {
        randomLetters.add(getLetterArray(false)[i])
      }

      return Game(
        randomLetters,
        randomLetters[(0 until randomLetters.size - 1).random()]
      )
    }

    fun generateOptionsToQuiz(): OptionsToQuiz {
      val lista = if (Locale.getDefault().language == "es") palabrasEspanol else palabrasIngles
      // Seleccionamos una opción al azar como la respuesta correcta
      val correctAnswer = lista.random()

      // Creamos una lista mutable para almacenar las opciones a mostrar al usuario
      val optionsToSelect = mutableListOf<String>()

      // Agregamos la respuesta correcta a la lista de opciones
      optionsToSelect.add(correctAnswer)

      // Agregamos otras tres opciones aleatorias que no sean la respuesta correcta
      while (optionsToSelect.size < 4) {
        val opcionAleatoria = lista.random()
        if (opcionAleatoria != correctAnswer && !optionsToSelect.contains(opcionAleatoria)) {
          optionsToSelect.add(opcionAleatoria)
        }
      }

      // Mezclamos aleatoriamente las opciones para que no siempre aparezcan en el mismo orden
      optionsToSelect.shuffle()

      // Devolvemos la respuesta correcta y la lista de opciones a mostrar al usuario
      return OptionsToQuiz(
        correctAnswer,
        optionsToSelect,
        messageToImages(correctAnswer)
      )
    }
  }

}

fun getWinIcons(): Int {
  val imagesArray = intArrayOf(
    R.drawable.ic_win_trophy,
    R.drawable.ic_victory2,
    R.drawable.ic_victory3,
  )
  val randomIndex = (imagesArray.indices).random()
  return imagesArray[randomIndex]
}

fun getLoseIcons(): Int {
  val imagesArray = intArrayOf(
    R.drawable.ic_sad,
    R.drawable.ic_sad2,
    R.drawable.ic_sad3,
  )
  val randomIndex = (imagesArray.indices).random()
  return imagesArray[randomIndex]
}

data class OptionsToQuiz(
  val correctAnswer: String,
  val options: List<String>,
  val signList: ArrayList<Sign>
)


@Parcelize
data class DialogGameDC(
  val title: String,
  val subtitle: String,
  val audio: Int,
  val image: Int,
  val buttonText: String,
  var gameResult: GameResult
) : Parcelable

enum class GameResult {
  WIN, LOSE, TIME_IS_UP
}
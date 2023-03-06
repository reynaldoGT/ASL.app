package com.neo.signLanguage.utils

import android.content.Context
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.*
import java.text.Normalizer

import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt

data class Game(
  val data: ArrayList<Sign>,
  val correctAnswer: Sign,
)

class DataSign {

  companion object {

    private const val letter = "letter"
    private const val number = "number"
    private const val space = "space"
    private val lettersArray = arrayListOf(
      Sign("a", R.drawable.ic_a_only_sing, letter),
      Sign("b", R.drawable.ic_b_only_sing, letter),
      Sign("c", R.drawable.ic_c_only_sing, letter),
      Sign("d", R.drawable.ic_d_only_sing, letter),
      Sign("e", R.drawable.ic_e_only_sing, letter),
      Sign("f", R.drawable.ic_f_only_sing, letter),
      Sign("g", R.drawable.ic_g_only_sing, letter),
      Sign("h", R.drawable.ic_h_only_sing, letter),
      Sign("i", R.drawable.ic_i_only_sing, letter),
      Sign("j", R.drawable.ic_j_only_sing, letter),
      Sign("k", R.drawable.ic_k_only_sing, letter),
      Sign("l", R.drawable.ic_l_only_sing, letter),
      Sign("m", R.drawable.ic_m_only_sing, letter),
      Sign("n", R.drawable.ic_n_only_sing, letter),
      Sign("ñ", R.drawable.ic_nn_letter, letter),
      Sign("o", R.drawable.ic_o_only_sing, letter),
      Sign("p", R.drawable.ic_p_only_sing, letter),
      Sign("q", R.drawable.ic_q_only_sing, letter),
      Sign("r", R.drawable.ic_r_only_sing, letter),
      Sign("s", R.drawable.ic_s_only_sing, letter),
      Sign("t", R.drawable.ic_t_only_sing, letter),
      Sign("u", R.drawable.ic_u_only_sing, letter),
      Sign("v", R.drawable.ic_v_only_sing, letter),
      Sign("w", R.drawable.ic_w_only_sing, letter),
      Sign("x", R.drawable.ic_x_only_sing, letter),
      Sign("y", R.drawable.ic_y_only_sing, letter),
      Sign("z", R.drawable.ic_z_only_sing, letter),
      //?  numbers
      Sign("1", R.drawable.ic_1_number, number),
      Sign("2", R.drawable.ic_2_number, number),
      Sign("3", R.drawable.ic_3_number, number),
      Sign("4", R.drawable.ic_4_number, number),
      Sign("5", R.drawable.ic_5_number, number),
      Sign("6", R.drawable.ic_6_number, number),
      Sign("7", R.drawable.ic_7_number, number),
      Sign("8", R.drawable.ic_8_number, number),
      Sign("9", R.drawable.ic_9_number, number),
      Sign("10", R.drawable.ic_10_number, number),
      Sign("0", R.drawable.ic_0_number, number),
    )

    private val letterWithSignArray = arrayListOf(
      Sign("A", R.drawable.ic_a_letter, letter),
      Sign("B", R.drawable.ic_b_letter, letter),
      Sign("C", R.drawable.ic_c_letter, letter),
      Sign("D", R.drawable.ic_d_letter, letter),
      Sign("E", R.drawable.ic_e_letter, letter),
      Sign("F", R.drawable.ic_f_letter, letter),
      Sign("G", R.drawable.ic_g_letter, letter),
      Sign("H", R.drawable.ic_h_letter, letter),
      Sign("I", R.drawable.ic_i_letter, letter),
      Sign("J", R.drawable.ic_j_letter, letter),
      Sign("K", R.drawable.ic_k_letter, letter),
      Sign("L", R.drawable.ic_l_letter, letter),
      Sign("M", R.drawable.ic_m_letter, letter),
      Sign("N", R.drawable.ic_n_letter, letter),
      Sign("O", R.drawable.ic_o_letter, letter),
      Sign("P", R.drawable.ic_p_letter, letter),
      Sign("Q", R.drawable.ic_q_letter, letter),
      Sign("R", R.drawable.ic_r_letter, letter),
      Sign("S", R.drawable.ic_s_letter, letter),
      Sign("T", R.drawable.ic_t_letter, letter),
      Sign("U", R.drawable.ic_u_letter, letter),
      Sign("V", R.drawable.ic_v_letter, letter),
      Sign("W", R.drawable.ic_w_letter, letter),
      Sign("X", R.drawable.ic_x_letter, letter),
      Sign("Y", R.drawable.ic_y_letter, letter),
      Sign("Z", R.drawable.ic_z_letter, letter),

      Sign("1", R.drawable.ic_1_number, number),
      Sign("2", R.drawable.ic_2_number, number),
      Sign("3", R.drawable.ic_3_number, number),
      Sign("4", R.drawable.ic_4_number, number),
      Sign("5", R.drawable.ic_5_number, number),
      Sign("6", R.drawable.ic_6_number, number),
      Sign("7", R.drawable.ic_7_number, number),
      Sign("8", R.drawable.ic_8_number, number),
      Sign("9", R.drawable.ic_9_number, number),
      Sign("10", R.drawable.ic_10_number, number),
      Sign("0", R.drawable.ic_0_number, number),
    )

    fun getLetterArray(addSpace: Boolean = true): ArrayList<Sign> {
      val array = ArrayList(lettersArray)

      if (addSpace) {
        array.add(Sign(" ", R.drawable.ic_space, space))
      }

      return array
    }

    fun getRandomToFindCorrectLetter(amount: Int): Game {
      val randomLetters = ArrayList<Sign>()
      val randomInts = generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(0 until this.getLetterArray(false).size)
      }
        .distinct()
        .take(amount)
        .toSet()

      for (i in randomInts) {
        randomLetters.add(this.getLetterArray(false)[i])
      }

      return Game(
        randomLetters,
        randomLetters[(0 until randomLetters.size - 1).random()]
      )
    }

    fun getRandomToFindEquals(amount: Int): Game {
      val randomLetters = ArrayList<Sign>()
      val resortList = ArrayList<Sign>()
      val randomInts = generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(0 until this.getLetterArray(false).size)
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
            this.getLetterArray()[i].letter,
            this.getLetterArray()[i].image,
            "letter",
          ),
        )
        randomLetters.add(
          Sign(
            this.getLetterArray()[i].letter,
            this.getLetterArray()[i].image,
            "image",
          )
        )

      }
      for (i in randomPosition) {
        resortList.add(randomLetters[i])
      }
      return Game(
        resortList,
        resortList[(0 until randomLetters.size - 1).random()]
      )
    }

    fun getLetterAndSignArray(): ArrayList<Sign> {
      return letterWithSignArray
    }

    fun getOnlyNumbers(): ArrayList<Sign> {
      val numbersArray = ArrayList<Sign>()
      numbersArray.add(Sign("0", R.drawable.ic_0_number, "number"))
      numbersArray.add(Sign("1", R.drawable.ic_1_number, "number"))
      numbersArray.add(Sign("2", R.drawable.ic_2_number, "number"))
      numbersArray.add(Sign("3", R.drawable.ic_3_number, "number"))
      numbersArray.add(Sign("4", R.drawable.ic_4_number, "number"))
      numbersArray.add(Sign("5", R.drawable.ic_5_number, "number"))
      numbersArray.add(Sign("6", R.drawable.ic_6_number, "number"))
      numbersArray.add(Sign("7", R.drawable.ic_7_number, "number"))
      numbersArray.add(Sign("8", R.drawable.ic_8_number, "number"))
      numbersArray.add(Sign("9", R.drawable.ic_9_number, "number"))
      numbersArray.add(Sign("10", R.drawable.ic_10_number, "number"))
      return numbersArray
    }

    fun getColorsList(context: Context): ArrayList<Color> {
      val colorList = ArrayList<Color>()
      colorList.add(
        Color(
          context.resources.getString(R.string.blue),
          R.color.primaryDarkColor
        )
      )
      colorList.add(Color(context.resources.getString(R.string.teal), R.color.teal))
      colorList.add(Color(context.resources.getString(R.string.indigo), R.color.indigo))
      colorList.add(
        Color(
          context.resources.getString(R.string.purple),
          R.color.purple_200
        )
      )
      colorList.add(Color(context.resources.getString(R.string.black), R.color.gray900))
      colorList.add(Color(context.resources.getString(R.string.gray), R.color.gray300))
      colorList.add(
        Color(
          context.resources.getString(R.string.green),
          R.color.green_dark
        )
      )
      colorList.add(
        Color(
          context.resources.getString(R.string.green_light),
          R.color.lightGreen
        )
      )
      colorList.add(
        Color(
          context.resources.getString(R.string.deep_orange),
          R.color.deep_orange
        )
      )
      colorList.add(Color(context.resources.getString(R.string.red), R.color.red_dark))
      colorList.add(Color(context.resources.getString(R.string.pink), R.color.pink))
      colorList.add(Color(context.resources.getString(R.string.orange), R.color.orange))
      colorList.add(Color(context.resources.getString(R.string.yellow), R.color.yellow))
      colorList.add(Color(context.resources.getString(R.string.brawn), R.color.brawn))
      return colorList
    }

    fun generateListImageSign(message: String): SendMessageDC {
      val arraySentenceSing = ArrayList<Sign>()
      val re = Regex("[^[A-Za-z0-9 ,ñÀ-ú]+\$]")
      var stringCleaned: String = message.trim().lowercase()
      stringCleaned = re.replace(stringCleaned, "") // works
      stringCleaned = stringCleaned.replace("\\s+".toRegex(), " ")

      stringCleaned = Normalizer.normalize(stringCleaned, Normalizer.Form.NFD)

      val stringArray = stringCleaned.toCharArray()
      for (i in stringArray) {
        for (letterPosition in getLetterArray()) {
          if (letterPosition.letter == i.toString()) {
            arraySentenceSing.add(letterPosition)
          }
        }
      }
      return SendMessageDC(arraySentenceSing, stringCleaned)
    }
  }
}

data class SendMessageDC(
  val data: ArrayList<Sign>,
  val stringCleaned: String,
)
package com.neo.signLanguage.utils

import android.content.Context
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.*
import com.orhanobut.logger.Logger

import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt

data class Game(
    val data: ArrayList<Sign>,
    val correctAnswer: String,
)

class DataSign {

    companion object {

        private const val letter = "letter"
        private const val number = "number"
        private const val space = "space"

        fun getLetterArray(): ArrayList<Sign> {
            val lettersArray = ArrayList<Sign>()
            lettersArray.add(Sign("b", R.drawable.ic_b_only_sing, letter))
            lettersArray.add(Sign("a", R.drawable.ic_a_only_sing, letter))
            lettersArray.add(Sign("c", R.drawable.ic_c_only_sing, letter))
            lettersArray.add(Sign("d", R.drawable.ic_d_only_sing, letter))
            lettersArray.add(Sign("e", R.drawable.ic_e_only_sing, letter))
            lettersArray.add(Sign("f", R.drawable.ic_f_only_sing, letter))
            lettersArray.add(Sign("g", R.drawable.ic_g_only_sing, letter))
            lettersArray.add(Sign("h", R.drawable.ic_h_only_sing, letter))
            lettersArray.add(Sign("i", R.drawable.ic_i_only_sing, letter))
            lettersArray.add(Sign("j", R.drawable.ic_j_only_sing, letter))
            lettersArray.add(Sign("k", R.drawable.ic_k_only_sing, letter))
            lettersArray.add(Sign("l", R.drawable.ic_l_only_sing, letter))
            lettersArray.add(Sign("m", R.drawable.ic_m_only_sing, letter))
            lettersArray.add(Sign("n", R.drawable.ic_n_only_sing, letter))
            lettersArray.add(Sign("ñ", R.drawable.ic_nn_letter, letter))
            lettersArray.add(Sign("o", R.drawable.ic_o_only_sing, letter))
            lettersArray.add(Sign("p", R.drawable.ic_p_only_sing, letter))
            lettersArray.add(Sign("q", R.drawable.ic_q_only_sing, letter))
            lettersArray.add(Sign("r", R.drawable.ic_r_only_sing, letter))
            lettersArray.add(Sign("s", R.drawable.ic_s_only_sing, letter))
            lettersArray.add(Sign("t", R.drawable.ic_t_only_sing, letter))
            lettersArray.add(Sign("u", R.drawable.ic_u_only_sing, letter))
            lettersArray.add(Sign("v", R.drawable.ic_v_only_sing, letter))
            lettersArray.add(Sign("w", R.drawable.ic_w_only_sing, letter))
            lettersArray.add(Sign("x", R.drawable.ic_x_only_sing, letter))
            lettersArray.add(Sign("y", R.drawable.ic_y_only_sing, letter))
            lettersArray.add(Sign("z", R.drawable.ic_z_only_sing, letter))
            lettersArray.add(Sign(" ", R.drawable.ic_space, space))

            //?  numbers
            lettersArray.add(Sign("1", R.drawable.ic_1_number, number))
            lettersArray.add(Sign("2", R.drawable.ic_2_number, number))
            lettersArray.add(Sign("3", R.drawable.ic_3_number, number))
            lettersArray.add(Sign("4", R.drawable.ic_4_number, number))
            lettersArray.add(Sign("5", R.drawable.ic_5_number, number))
            lettersArray.add(Sign("6", R.drawable.ic_6_number, number))
            lettersArray.add(Sign("7", R.drawable.ic_7_number, number))
            lettersArray.add(Sign("8", R.drawable.ic_8_number, number))
            lettersArray.add(Sign("9", R.drawable.ic_9_number, number))
            lettersArray.add(Sign("9", R.drawable.ic_9_number, number))
            lettersArray.add(Sign("10", R.drawable.ic_10_number, number))
            lettersArray.add(Sign("10", R.drawable.ic_10_number, number))
            lettersArray.add(Sign("0", R.drawable.ic_0_number, number))
            return lettersArray
        }

        fun getRandomLetters(amount: Int): Game {
            val randomLetters = ArrayList<Sign>()
            val randomInts = generateSequence {
                // this lambda is the source of the sequence's values
                Random.nextInt(0 until this.getLetterArray().size)
            }
                .distinct()
                .take(amount)
                .toSet()

            for (i in randomInts) {
                randomLetters.add(this.getLetterArray()[i])
            }

            return Game(
                randomLetters,
                randomLetters[(0 until randomLetters.size - 1).random()].letter
            )
        }

        fun getRandomToFindEquals(amount: Int): Game {
            val randomLetters = ArrayList<Sign>()
            val resortList = ArrayList<Sign>()
            val randomInts = generateSequence {
                // this lambda is the source of the sequence's values
                Random.nextInt(0 until this.getLetterArray().size)
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
                randomLetters.add(this.getLetterArray()[i])
                randomLetters.add(this.getLetterArray()[i])
            }
            for (i in randomPosition) {
                resortList.add(randomLetters[i])
            }
            return Game(
                resortList,
                resortList[(0 until randomLetters.size - 1).random()].letter
            )
        }

        fun getOnlyLetterArray(): ArrayList<Sign> {
            val lettersArray = ArrayList<Sign>()
            lettersArray.add(Sign("A", R.drawable.ic_a_letter, letter))
            lettersArray.add(Sign("B", R.drawable.ic_b_letter, letter))
            lettersArray.add(Sign("C", R.drawable.ic_c_letter, letter))
            lettersArray.add(Sign("D", R.drawable.ic_d_letter, letter))
            lettersArray.add(Sign("E", R.drawable.ic_e_letter, letter))
            lettersArray.add(Sign("F", R.drawable.ic_f_letter, letter))
            lettersArray.add(Sign("G", R.drawable.ic_g_letter, letter))
            lettersArray.add(Sign("H", R.drawable.ic_h_letter, letter))
            lettersArray.add(Sign("I", R.drawable.ic_i_letter, letter))
            lettersArray.add(Sign("J", R.drawable.ic_j_letter, letter))
            lettersArray.add(Sign("K", R.drawable.ic_k_letter, letter))
            lettersArray.add(Sign("L", R.drawable.ic_l_letter, letter))
            lettersArray.add(Sign("M", R.drawable.ic_m_letter, letter))
            lettersArray.add(Sign("N", R.drawable.ic_n_letter, letter))
            lettersArray.add(Sign("O", R.drawable.ic_o_letter, letter))
            lettersArray.add(Sign("P", R.drawable.ic_p_letter, letter))
            lettersArray.add(Sign("Q", R.drawable.ic_q_letter, letter))
            lettersArray.add(Sign("R", R.drawable.ic_r_letter, letter))
            lettersArray.add(Sign("S", R.drawable.ic_s_letter, letter))
            lettersArray.add(Sign("T", R.drawable.ic_t_letter, letter))
            lettersArray.add(Sign("U", R.drawable.ic_u_letter, letter))
            lettersArray.add(Sign("V", R.drawable.ic_v_letter, letter))
            lettersArray.add(Sign("W", R.drawable.ic_w_letter, letter))
            lettersArray.add(Sign("X", R.drawable.ic_x_letter, letter))
            lettersArray.add(Sign("Y", R.drawable.ic_y_letter, letter))
            lettersArray.add(Sign("Z", R.drawable.ic_z_letter, letter))

            lettersArray.add(Sign("a", R.drawable.ic_a_only_sing, letter))
            lettersArray.add(Sign("b", R.drawable.ic_b_only_sing, letter))
            lettersArray.add(Sign("c", R.drawable.ic_c_only_sing, letter))
            lettersArray.add(Sign("d", R.drawable.ic_d_only_sing, letter))
            lettersArray.add(Sign("e", R.drawable.ic_e_only_sing, letter))
            lettersArray.add(Sign("f", R.drawable.ic_f_only_sing, letter))
            lettersArray.add(Sign("g", R.drawable.ic_g_only_sing, letter))
            lettersArray.add(Sign("h", R.drawable.ic_h_only_sing, letter))
            lettersArray.add(Sign("i", R.drawable.ic_i_only_sing, letter))
            lettersArray.add(Sign("j", R.drawable.ic_j_only_sing, letter))
            lettersArray.add(Sign("k", R.drawable.ic_k_only_sing, letter))
            lettersArray.add(Sign("l", R.drawable.ic_l_only_sing, letter))
            lettersArray.add(Sign("m", R.drawable.ic_m_only_sing, letter))
            lettersArray.add(Sign("n", R.drawable.ic_n_only_sing, letter))
            lettersArray.add(Sign("ñ", R.drawable.ic_nn_letter, letter))
            lettersArray.add(Sign("o", R.drawable.ic_o_only_sing, letter))
            lettersArray.add(Sign("p", R.drawable.ic_p_only_sing, letter))
            lettersArray.add(Sign("q", R.drawable.ic_q_only_sing, letter))
            lettersArray.add(Sign("r", R.drawable.ic_r_only_sing, letter))
            lettersArray.add(Sign("s", R.drawable.ic_s_only_sing, letter))
            lettersArray.add(Sign("t", R.drawable.ic_t_only_sing, letter))
            lettersArray.add(Sign("u", R.drawable.ic_u_only_sing, letter))
            lettersArray.add(Sign("v", R.drawable.ic_v_only_sing, letter))
            lettersArray.add(Sign("w", R.drawable.ic_w_only_sing, letter))
            lettersArray.add(Sign("x", R.drawable.ic_x_only_sing, letter))
            lettersArray.add(Sign("y", R.drawable.ic_y_only_sing, letter))
            lettersArray.add(Sign("z", R.drawable.ic_z_only_sing, letter))

            return lettersArray
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
    }
}
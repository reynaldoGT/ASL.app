package com.neo.signLanguage.utils

import com.neo.signLanguage.R
import com.neo.signLanguage.models.Sign

class Shared {
    private var lettersArray = ArrayList<Sign>()
    private var numbersArray = ArrayList<Sign>()
    private val letter = "letter"
    private val number = "number"

    fun getLetterArray(): ArrayList<Sign> {
        this.fillLetterArray()
        return this.lettersArray
    }

    fun getOnlyLettersArray(): ArrayList<Sign> {
        this.fillOnlyLetterArray()
        return this.lettersArray
    }

    fun getOnlyNumbersArray(): ArrayList<Sign>{
        this.fillOnlyNumbers()
        return this.numbersArray
    }
    private fun fillLetterArray() {

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
    }

    private fun fillOnlyLetterArray() {

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


    }

    private fun fillOnlyNumbers() {
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
    }
}
package com.neo.signLanguage.models

import com.neo.signLanguage.R

class Letters {
    private var lettersArray = ArrayList<Sign>()

    init {
        this.createLetters()
    }

    private fun createLetters() {

        // letters
        val letter = "letter"
        val number = "number"

        lettersArray.add(Sign("b", R.drawable.ic_b_letter, letter))
        lettersArray.add(Sign("a", R.drawable.ic_a_letter, letter))
        lettersArray.add(Sign("c", R.drawable.ic_c_letter, letter))
        lettersArray.add(Sign("d", R.drawable.ic_d_letter, letter))
        lettersArray.add(Sign("e", R.drawable.ic_e_letter, letter))
        lettersArray.add(Sign("f", R.drawable.ic_f_letter, letter))
        lettersArray.add(Sign("g", R.drawable.ic_g_letter, letter))
        lettersArray.add(Sign("h", R.drawable.ic_h_letter, letter))
        lettersArray.add(Sign("i", R.drawable.ic_i_letter, letter))
        lettersArray.add(Sign("j", R.drawable.ic_j_letter, letter))
        lettersArray.add(Sign("k", R.drawable.ic_k_letter, letter))
        lettersArray.add(Sign("l", R.drawable.ic_l_letter, letter))
        lettersArray.add(Sign("m", R.drawable.ic_m_letter, letter))
        lettersArray.add(Sign("n", R.drawable.ic_n_letter, letter))
        lettersArray.add(Sign("o", R.drawable.ic_o_letter, letter))
        lettersArray.add(Sign("p", R.drawable.ic_p_letter, letter))
        lettersArray.add(Sign("q", R.drawable.ic_q_letter, letter))
        lettersArray.add(Sign("r", R.drawable.ic_r_letter, letter))
        lettersArray.add(Sign("s", R.drawable.ic_s_letter, letter))
        lettersArray.add(Sign("t", R.drawable.ic_t_letter, letter))
        lettersArray.add(Sign("u", R.drawable.ic_u_letter, letter))
        lettersArray.add(Sign("v", R.drawable.ic_v_letter, letter))
        lettersArray.add(Sign("w", R.drawable.ic_w_letter, letter))
        lettersArray.add(Sign("x", R.drawable.ic_x_letter, letter))
        lettersArray.add(Sign("y", R.drawable.ic_y_letter, letter))
        lettersArray.add(Sign("z", R.drawable.ic_z_letter, letter))
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
}
package com.neo.signLanguage.models

import com.neo.signLanguage.R

class Letters {
    private var lettersArray = ArrayList<Sing>()

    init {
        this.createLetters()
    }

    private fun createLetters() {

        // letters
        val letter = "letter"
        val number = "number"

        lettersArray.add(Sing("b", R.drawable.ic_b_letter, letter))
        lettersArray.add(Sing("a", R.drawable.ic_a_letter, letter))
        lettersArray.add(Sing("c", R.drawable.ic_c_letter, letter))
        lettersArray.add(Sing("d", R.drawable.ic_d_letter, letter))
        lettersArray.add(Sing("e", R.drawable.ic_e_letter, letter))
        lettersArray.add(Sing("f", R.drawable.ic_f_letter, letter))
        lettersArray.add(Sing("g", R.drawable.ic_g_letter, letter))
        lettersArray.add(Sing("h", R.drawable.ic_h_letter, letter))
        lettersArray.add(Sing("i", R.drawable.ic_i_letter, letter))
        lettersArray.add(Sing("j", R.drawable.ic_j_letter, letter))
        lettersArray.add(Sing("k", R.drawable.ic_k_letter, letter))
        lettersArray.add(Sing("l", R.drawable.ic_l_letter, letter))
        lettersArray.add(Sing("m", R.drawable.ic_m_letter, letter))
        lettersArray.add(Sing("n", R.drawable.ic_n_letter, letter))
        lettersArray.add(Sing("o", R.drawable.ic_o_letter, letter))
        lettersArray.add(Sing("p", R.drawable.ic_p_letter, letter))
        lettersArray.add(Sing("q", R.drawable.ic_q_letter, letter))
        lettersArray.add(Sing("r", R.drawable.ic_r_letter, letter))
        lettersArray.add(Sing("s", R.drawable.ic_s_letter, letter))
        lettersArray.add(Sing("t", R.drawable.ic_t_letter, letter))
        lettersArray.add(Sing("u", R.drawable.ic_u_letter, letter))
        lettersArray.add(Sing("v", R.drawable.ic_v_letter, letter))
        lettersArray.add(Sing("w", R.drawable.ic_w_letter, letter))
        lettersArray.add(Sing("x", R.drawable.ic_x_letter, letter))
        lettersArray.add(Sing("y", R.drawable.ic_y_letter, letter))
        lettersArray.add(Sing("z", R.drawable.ic_z_letter, letter))
        //?  numbers
        lettersArray.add(Sing("1", R.drawable.ic_1_number, number))
        lettersArray.add(Sing("2", R.drawable.ic_2_number, number))
        lettersArray.add(Sing("3", R.drawable.ic_3_number, number))
        lettersArray.add(Sing("4", R.drawable.ic_4_number, number))
        lettersArray.add(Sing("5", R.drawable.ic_5_number, number))
        lettersArray.add(Sing("6", R.drawable.ic_6_number, number))
        lettersArray.add(Sing("7", R.drawable.ic_7_number, number))
        lettersArray.add(Sing("8", R.drawable.ic_8_number, number))
        lettersArray.add(Sing("9", R.drawable.ic_9_number, number))
        lettersArray.add(Sing("9", R.drawable.ic_9_number, number))
        lettersArray.add(Sing("10", R.drawable.ic_10_number, number))
        lettersArray.add(Sing("10", R.drawable.ic_10_number, number))
        lettersArray.add(Sing("0", R.drawable.ic_0_number, number))

    }
}
package com.example.singlanguage

class Shared {
    var lettersArray = ArrayList<Letter>()


    fun getLetterArray(): ArrayList<Letter> {
        this.fillLetterArray()
        return this.lettersArray
    }

    fun getOnlyLettersArray(): ArrayList<Letter> {
        this.fillOnlyLetterArray()
        return this.lettersArray
    }

    private fun fillLetterArray() {
        lettersArray.add(Letter("a", R.drawable.ic_a_only_sing))
        lettersArray.add(Letter("b", R.drawable.ic_b_only_sing))
        lettersArray.add(Letter("c", R.drawable.ic_c_only_sing))
        lettersArray.add(Letter("d", R.drawable.ic_d_only_sing))
        lettersArray.add(Letter("e", R.drawable.ic_e_only_sing))
        lettersArray.add(Letter("f", R.drawable.ic_f_only_sing))
        lettersArray.add(Letter("g", R.drawable.ic_g_only_sing))
        lettersArray.add(Letter("h", R.drawable.ic_h_only_sing))
        lettersArray.add(Letter("i", R.drawable.ic_i_only_sing))
        lettersArray.add(Letter("j", R.drawable.ic_j_only_sing))
        lettersArray.add(Letter("k", R.drawable.ic_k_only_sing))
        lettersArray.add(Letter("l", R.drawable.ic_l_only_sing))
        lettersArray.add(Letter("m", R.drawable.ic_m_only_sing))
        lettersArray.add(Letter("n", R.drawable.ic_n_only_sing))
        lettersArray.add(Letter("ñ", R.drawable.ic_nn_letter))
        lettersArray.add(Letter("o", R.drawable.ic_o_only_sing))
        lettersArray.add(Letter("p", R.drawable.ic_p_only_sing))
        lettersArray.add(Letter("q", R.drawable.ic_q_only_sing))
        lettersArray.add(Letter("r", R.drawable.ic_r_only_sing))
        lettersArray.add(Letter("s", R.drawable.ic_s_only_sing))
        lettersArray.add(Letter("t", R.drawable.ic_t_only_sing))
        lettersArray.add(Letter("u", R.drawable.ic_u_only_sing))
        lettersArray.add(Letter("v", R.drawable.ic_v_only_sing))
        lettersArray.add(Letter("w", R.drawable.ic_w_only_sing))
        lettersArray.add(Letter("x", R.drawable.ic_x_only_sing))
        lettersArray.add(Letter("y", R.drawable.ic_y_only_sing))
        lettersArray.add(Letter("z", R.drawable.ic_z_only_sing))

        //?  numbers
        lettersArray.add(Letter("1", R.drawable.ic_1_number))
        lettersArray.add(Letter("2", R.drawable.ic_2_number))
        lettersArray.add(Letter("3", R.drawable.ic_3_number))
        lettersArray.add(Letter("4", R.drawable.ic_4_number))
        lettersArray.add(Letter("5", R.drawable.ic_5_number))
        lettersArray.add(Letter("6", R.drawable.ic_6_number))
        lettersArray.add(Letter("7", R.drawable.ic_7_number))
        lettersArray.add(Letter("8", R.drawable.ic_8_number))
        lettersArray.add(Letter("9", R.drawable.ic_9_number))
        lettersArray.add(Letter("9", R.drawable.ic_9_number))
        lettersArray.add(Letter("10", R.drawable.ic_10_number))
        lettersArray.add(Letter("10", R.drawable.ic_10_number))
        lettersArray.add(Letter("0", R.drawable.ic_0_number))
    }

    private fun fillOnlyLetterArray() {


        lettersArray.add(Letter("a", R.drawable.ic_a_letter))
        lettersArray.add(Letter("b", R.drawable.ic_b_letter))
        lettersArray.add(Letter("c", R.drawable.ic_c_letter))
        lettersArray.add(Letter("d", R.drawable.ic_d_letter))
        lettersArray.add(Letter("e", R.drawable.ic_e_letter))
        lettersArray.add(Letter("f", R.drawable.ic_f_letter))
        lettersArray.add(Letter("g", R.drawable.ic_g_letter))
        lettersArray.add(Letter("h", R.drawable.ic_h_letter))
        lettersArray.add(Letter("i", R.drawable.ic_i_letter))
        lettersArray.add(Letter("j", R.drawable.ic_j_letter))
        lettersArray.add(Letter("k", R.drawable.ic_k_letter))
        lettersArray.add(Letter("l", R.drawable.ic_l_letter))
        lettersArray.add(Letter("m", R.drawable.ic_m_letter))
        lettersArray.add(Letter("n", R.drawable.ic_n_letter))
        lettersArray.add(Letter("o", R.drawable.ic_o_letter))
        lettersArray.add(Letter("p", R.drawable.ic_p_letter))
        lettersArray.add(Letter("q", R.drawable.ic_q_letter))
        lettersArray.add(Letter("r", R.drawable.ic_r_letter))
        lettersArray.add(Letter("s", R.drawable.ic_s_letter))
        lettersArray.add(Letter("t", R.drawable.ic_t_letter))
        lettersArray.add(Letter("u", R.drawable.ic_u_letter))
        lettersArray.add(Letter("v", R.drawable.ic_v_letter))
        lettersArray.add(Letter("w", R.drawable.ic_w_letter))
        lettersArray.add(Letter("x", R.drawable.ic_x_letter))
        lettersArray.add(Letter("y", R.drawable.ic_y_letter))
        lettersArray.add(Letter("z", R.drawable.ic_z_letter))

        lettersArray.add(Letter("a", R.drawable.ic_a_only_sing))
        lettersArray.add(Letter("b", R.drawable.ic_b_only_sing))
        lettersArray.add(Letter("c", R.drawable.ic_c_only_sing))
        lettersArray.add(Letter("d", R.drawable.ic_d_only_sing))
        lettersArray.add(Letter("e", R.drawable.ic_e_only_sing))
        lettersArray.add(Letter("f", R.drawable.ic_f_only_sing))
        lettersArray.add(Letter("g", R.drawable.ic_g_only_sing))
        lettersArray.add(Letter("h", R.drawable.ic_h_only_sing))
        lettersArray.add(Letter("i", R.drawable.ic_i_only_sing))
        lettersArray.add(Letter("j", R.drawable.ic_j_only_sing))
        lettersArray.add(Letter("k", R.drawable.ic_k_only_sing))
        lettersArray.add(Letter("l", R.drawable.ic_l_only_sing))
        lettersArray.add(Letter("m", R.drawable.ic_m_only_sing))
        lettersArray.add(Letter("n", R.drawable.ic_n_only_sing))
        lettersArray.add(Letter("ñ", R.drawable.ic_nn_letter))
        lettersArray.add(Letter("o", R.drawable.ic_o_only_sing))
        lettersArray.add(Letter("p", R.drawable.ic_p_only_sing))
        lettersArray.add(Letter("q", R.drawable.ic_q_only_sing))
        lettersArray.add(Letter("r", R.drawable.ic_r_only_sing))
        lettersArray.add(Letter("s", R.drawable.ic_s_only_sing))
        lettersArray.add(Letter("t", R.drawable.ic_t_only_sing))
        lettersArray.add(Letter("u", R.drawable.ic_u_only_sing))
        lettersArray.add(Letter("v", R.drawable.ic_v_only_sing))
        lettersArray.add(Letter("w", R.drawable.ic_w_only_sing))
        lettersArray.add(Letter("x", R.drawable.ic_x_only_sing))
        lettersArray.add(Letter("y", R.drawable.ic_y_only_sing))
        lettersArray.add(Letter("z", R.drawable.ic_z_only_sing))

        //  numbers sings

        lettersArray.add(Letter("0", R.drawable.ic_0_number))
        lettersArray.add(Letter("1", R.drawable.ic_1_number))
        lettersArray.add(Letter("2", R.drawable.ic_2_number))
        lettersArray.add(Letter("3", R.drawable.ic_3_number))
        lettersArray.add(Letter("4", R.drawable.ic_4_number))
        lettersArray.add(Letter("5", R.drawable.ic_5_number))
        lettersArray.add(Letter("6", R.drawable.ic_6_number))
        lettersArray.add(Letter("7", R.drawable.ic_7_number))
        lettersArray.add(Letter("8", R.drawable.ic_8_number))
        lettersArray.add(Letter("9", R.drawable.ic_9_number))
        lettersArray.add(Letter("10", R.drawable.ic_10_number))


    }
}
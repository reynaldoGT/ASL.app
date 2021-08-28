package com.neo.signLanguage

class Shared {
    private var lettersArray = ArrayList<Sing>()
    private var numbersArray = ArrayList<Sing>()
    private val letter = "Letra: "
    private val number = "Número: "

    fun getLetterArray(): ArrayList<Sing> {
        this.fillLetterArray()
        return this.lettersArray
    }

    fun getOnlyLettersArray(): ArrayList<Sing> {
        this.fillOnlyLetterArray()
        return this.lettersArray
    }

    fun getOnlyNumbersArray(): ArrayList<Sing>{
        this.fillOnlyNumbers()
        return this.numbersArray
    }
    private fun fillLetterArray() {

        lettersArray.add(Sing("b", R.drawable.ic_b_only_sing, letter))
        lettersArray.add(Sing("a", R.drawable.ic_a_only_sing, letter))
        lettersArray.add(Sing("c", R.drawable.ic_c_only_sing, letter))
        lettersArray.add(Sing("d", R.drawable.ic_d_only_sing, letter))
        lettersArray.add(Sing("e", R.drawable.ic_e_only_sing, letter))
        lettersArray.add(Sing("f", R.drawable.ic_f_only_sing, letter))
        lettersArray.add(Sing("g", R.drawable.ic_g_only_sing, letter))
        lettersArray.add(Sing("h", R.drawable.ic_h_only_sing, letter))
        lettersArray.add(Sing("i", R.drawable.ic_i_only_sing, letter))
        lettersArray.add(Sing("j", R.drawable.ic_j_only_sing, letter))
        lettersArray.add(Sing("k", R.drawable.ic_k_only_sing, letter))
        lettersArray.add(Sing("l", R.drawable.ic_l_only_sing, letter))
        lettersArray.add(Sing("m", R.drawable.ic_m_only_sing, letter))
        lettersArray.add(Sing("n", R.drawable.ic_n_only_sing, letter))
        lettersArray.add(Sing("ñ", R.drawable.ic_nn_letter, letter))
        lettersArray.add(Sing("o", R.drawable.ic_o_only_sing, letter))
        lettersArray.add(Sing("p", R.drawable.ic_p_only_sing, letter))
        lettersArray.add(Sing("q", R.drawable.ic_q_only_sing, letter))
        lettersArray.add(Sing("r", R.drawable.ic_r_only_sing, letter))
        lettersArray.add(Sing("s", R.drawable.ic_s_only_sing, letter))
        lettersArray.add(Sing("t", R.drawable.ic_t_only_sing, letter))
        lettersArray.add(Sing("u", R.drawable.ic_u_only_sing, letter))
        lettersArray.add(Sing("v", R.drawable.ic_v_only_sing, letter))
        lettersArray.add(Sing("w", R.drawable.ic_w_only_sing, letter))
        lettersArray.add(Sing("x", R.drawable.ic_x_only_sing, letter))
        lettersArray.add(Sing("y", R.drawable.ic_y_only_sing, letter))
        lettersArray.add(Sing("z", R.drawable.ic_z_only_sing, letter))

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

    private fun fillOnlyLetterArray() {

        lettersArray.add(Sing("A", R.drawable.ic_a_letter, letter))
        lettersArray.add(Sing("B", R.drawable.ic_b_letter, letter))
        lettersArray.add(Sing("C", R.drawable.ic_c_letter, letter))
        lettersArray.add(Sing("D", R.drawable.ic_d_letter, letter))
        lettersArray.add(Sing("E", R.drawable.ic_e_letter, letter))
        lettersArray.add(Sing("F", R.drawable.ic_f_letter, letter))
        lettersArray.add(Sing("G", R.drawable.ic_g_letter, letter))
        lettersArray.add(Sing("H", R.drawable.ic_h_letter, letter))
        lettersArray.add(Sing("I", R.drawable.ic_i_letter, letter))
        lettersArray.add(Sing("J", R.drawable.ic_j_letter, letter))
        lettersArray.add(Sing("K", R.drawable.ic_k_letter, letter))
        lettersArray.add(Sing("L", R.drawable.ic_l_letter, letter))
        lettersArray.add(Sing("M", R.drawable.ic_m_letter, letter))
        lettersArray.add(Sing("N", R.drawable.ic_n_letter, letter))
        lettersArray.add(Sing("O", R.drawable.ic_o_letter, letter))
        lettersArray.add(Sing("P", R.drawable.ic_p_letter, letter))
        lettersArray.add(Sing("Q", R.drawable.ic_q_letter, letter))
        lettersArray.add(Sing("R", R.drawable.ic_r_letter, letter))
        lettersArray.add(Sing("S", R.drawable.ic_s_letter, letter))
        lettersArray.add(Sing("T", R.drawable.ic_t_letter, letter))
        lettersArray.add(Sing("U", R.drawable.ic_u_letter, letter))
        lettersArray.add(Sing("V", R.drawable.ic_v_letter, letter))
        lettersArray.add(Sing("W", R.drawable.ic_w_letter, letter))
        lettersArray.add(Sing("X", R.drawable.ic_x_letter, letter))
        lettersArray.add(Sing("Y", R.drawable.ic_y_letter, letter))
        lettersArray.add(Sing("Z", R.drawable.ic_z_letter, letter))

        lettersArray.add(Sing("a", R.drawable.ic_a_only_sing, letter))
        lettersArray.add(Sing("b", R.drawable.ic_b_only_sing, letter))
        lettersArray.add(Sing("c", R.drawable.ic_c_only_sing, letter))
        lettersArray.add(Sing("d", R.drawable.ic_d_only_sing, letter))
        lettersArray.add(Sing("e", R.drawable.ic_e_only_sing, letter))
        lettersArray.add(Sing("f", R.drawable.ic_f_only_sing, letter))
        lettersArray.add(Sing("g", R.drawable.ic_g_only_sing, letter))
        lettersArray.add(Sing("h", R.drawable.ic_h_only_sing, letter))
        lettersArray.add(Sing("i", R.drawable.ic_i_only_sing, letter))
        lettersArray.add(Sing("j", R.drawable.ic_j_only_sing, letter))
        lettersArray.add(Sing("k", R.drawable.ic_k_only_sing, letter))
        lettersArray.add(Sing("l", R.drawable.ic_l_only_sing, letter))
        lettersArray.add(Sing("m", R.drawable.ic_m_only_sing, letter))
        lettersArray.add(Sing("n", R.drawable.ic_n_only_sing, letter))
        lettersArray.add(Sing("ñ", R.drawable.ic_nn_letter, letter))
        lettersArray.add(Sing("o", R.drawable.ic_o_only_sing, letter))
        lettersArray.add(Sing("p", R.drawable.ic_p_only_sing, letter))
        lettersArray.add(Sing("q", R.drawable.ic_q_only_sing, letter))
        lettersArray.add(Sing("r", R.drawable.ic_r_only_sing, letter))
        lettersArray.add(Sing("s", R.drawable.ic_s_only_sing, letter))
        lettersArray.add(Sing("t", R.drawable.ic_t_only_sing, letter))
        lettersArray.add(Sing("u", R.drawable.ic_u_only_sing, letter))
        lettersArray.add(Sing("v", R.drawable.ic_v_only_sing, letter))
        lettersArray.add(Sing("w", R.drawable.ic_w_only_sing, letter))
        lettersArray.add(Sing("x", R.drawable.ic_x_only_sing, letter))
        lettersArray.add(Sing("y", R.drawable.ic_y_only_sing, letter))
        lettersArray.add(Sing("z", R.drawable.ic_z_only_sing, letter))

        //  numbers sings

       /* lettersArray.add(Sing("0", R.drawable.ic_0_number, "number"))
        lettersArray.add(Sing("1", R.drawable.ic_1_number, "number"))
        lettersArray.add(Sing("2", R.drawable.ic_2_number, "number"))
        lettersArray.add(Sing("3", R.drawable.ic_3_number, "number"))
        lettersArray.add(Sing("4", R.drawable.ic_4_number, "number"))
        lettersArray.add(Sing("5", R.drawable.ic_5_number, "number"))
        lettersArray.add(Sing("6", R.drawable.ic_6_number, "number"))
        lettersArray.add(Sing("7", R.drawable.ic_7_number, "number"))
        lettersArray.add(Sing("8", R.drawable.ic_8_number, "number"))
        lettersArray.add(Sing("9", R.drawable.ic_9_number, "number"))
        lettersArray.add(Sing("10", R.drawable.ic_10_number, "number"))*/
    }

    private fun fillOnlyNumbers() {
        numbersArray.add(Sing("0", R.drawable.ic_0_number, "number"))
        numbersArray.add(Sing("1", R.drawable.ic_1_number, "number"))
        numbersArray.add(Sing("2", R.drawable.ic_2_number, "number"))
        numbersArray.add(Sing("3", R.drawable.ic_3_number, "number"))
        numbersArray.add(Sing("4", R.drawable.ic_4_number, "number"))
        numbersArray.add(Sing("5", R.drawable.ic_5_number, "number"))
        numbersArray.add(Sing("6", R.drawable.ic_6_number, "number"))
        numbersArray.add(Sing("7", R.drawable.ic_7_number, "number"))
        numbersArray.add(Sing("8", R.drawable.ic_8_number, "number"))
        numbersArray.add(Sing("9", R.drawable.ic_9_number, "number"))
        numbersArray.add(Sing("10", R.drawable.ic_10_number, "number"))
    }
}
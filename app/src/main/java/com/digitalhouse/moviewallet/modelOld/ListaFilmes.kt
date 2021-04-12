package com.digitalhouse.moviewallet.data


import com.digitalhouse.moviewallet.R

object ListaFilmes {

    private fun filmes(): MutableList<Movie> {

        val listaDeFilmes = mutableListOf<Movie>()

        listaDeFilmes.add(Movie("Deadpool", R.drawable.deadpool))
        listaDeFilmes.add(Movie("ToyStory 4", R.drawable.toystory))
        listaDeFilmes.add(Movie("Ilha do Medo", R.drawable.ilha_do_medo))
        listaDeFilmes.add(Movie("Garota Exemplar", R.drawable.garota_exemplar))
        listaDeFilmes.add(Movie("Psicose", R.drawable.psicose))
        listaDeFilmes.add(Movie("Se7en - Os Sete Crimes Capitais", R.drawable.seven))
        listaDeFilmes.add(Movie("O Silêncio dos Inocentes", R.drawable.silencio_dos_inocentes))
        listaDeFilmes.add(Movie("O Sexto Sentido", R.drawable.sexto_sentido))
        listaDeFilmes.add(Movie("Rastros de um Sequestro", R.drawable.rastros_de_um_sequestro))
        listaDeFilmes.add(Movie("Corra!", R.drawable.corra))

        return listaDeFilmes

    }

    fun getListaDefilmeAcao(): MutableList<Movie> {
        return filmes()
    }
    fun getListaDefilmeComedia(): MutableList<Movie> {
        return filmes()
    }
    fun getListaDefilmeTerror(): MutableList<Movie> {
        return filmes()
    }
    fun getListaDefilmeRomance(): MutableList<Movie> {
        return filmes()
    }
    fun getListaDefilmeSuspense(): MutableList<Movie> {
        return filmes()
    }
    fun getListaDefilmeDrama(): MutableList<Movie> {
        return filmes()
    }


}


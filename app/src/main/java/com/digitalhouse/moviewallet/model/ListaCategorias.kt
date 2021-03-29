package com.digitalhouse.moviewallet.data

object ListaCategorias {

    private fun Categorias(): MutableList<Category> {

        val listaDeCategorias = mutableListOf<Category>()

        listaDeCategorias.add(Category("Ação", getListaDefilmeAcao()))
        listaDeCategorias.add(Category("Comedia", getListaDefilmeComedia()))
        listaDeCategorias.add(Category("Terror", getListaDefilmeTerror()))
        listaDeCategorias.add(Category("Romance", getListaDefilmeRomance()))
        listaDeCategorias.add(Category("Suspense", getListaDefilmeSuspense()))
        listaDeCategorias.add(Category("Drama", getListaDefilmeDrama()))

        return listaDeCategorias
    }

    private fun getListaDefilmeAcao(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeAcao()
    }
    private fun getListaDefilmeComedia(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeComedia()
    }
    private fun getListaDefilmeTerror(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeTerror()
    }
    private fun getListaDefilmeRomance(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeRomance()
    }
    private fun getListaDefilmeSuspense(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeSuspense()
    }
    private fun getListaDefilmeDrama(): MutableList<Movie> {
        return ListaFilmes.getListaDefilmeDrama()
    }

    fun getCategorias() : MutableList<Category> {
        return Categorias()
    }


}
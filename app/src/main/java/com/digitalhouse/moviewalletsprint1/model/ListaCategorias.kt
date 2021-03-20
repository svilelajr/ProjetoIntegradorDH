package com.digitalhouse.moviewallet.data

object ListaCategorias {

    private fun Categorias(): MutableList<Category> {

        val listaDeCategorias = mutableListOf<Category>()

        listaDeCategorias.add(Category("Ação", getFilmes()))
        listaDeCategorias.add(Category("Comedia", getFilmes()))
        listaDeCategorias.add(Category("Terror", getFilmes()))
        listaDeCategorias.add(Category("Romance", getFilmes()))
        listaDeCategorias.add(Category("Suspense", getFilmes()))
        listaDeCategorias.add(Category("Drama", getFilmes()))

        return listaDeCategorias
    }

    private fun getFilmes(): MutableList<Movie> {

        val lista = ListaFilmes.getListaDefilmes()

        return lista
    }

    fun getCategorias() : MutableList<Category> {

        val listaDeCategorias = Categorias()

        return listaDeCategorias
    }


}
package com.andresen.libraryRepositories.helper.network

interface ConnectionService {
    fun isConnectedToInternet(): Boolean
}
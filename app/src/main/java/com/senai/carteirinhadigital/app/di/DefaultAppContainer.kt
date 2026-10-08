package com.senai.carteirinhadigital.app.di

import com.senai.carteirinhadigital.core.auth.SessionTokenStore
import com.senai.carteirinhadigital.core.network.NetworkClient
import com.senai.carteirinhadigital.feature.login.data.remote.service.AuthApi
import com.senai.carteirinhadigital.feature.login.data.repository.ApiLoginRepositoryImpl
import com.senai.carteirinhadigital.feature.login.data.repository.FakeLoginRepository
import com.senai.carteirinhadigital.feature.login.domain.repository.LoginRepository
import com.senai.carteirinhadigital.feature.unidadecurriculares.data.remote.service.UnidadeCurricularApi
import com.senai.carteirinhadigital.feature.unidadecurriculares.data.repository.ApiUnidadeCurricularRepositoryImpl
import com.senai.carteirinhadigital.feature.unidadecurriculares.domain.repository.UnidadeCurricularRepository

class DefaultAppContainer : AppContainer {
    override val sessionTokenStore : SessionTokenStore = SessionTokenStore()
    private val networkClient =
        NetworkClient(
            baseUrl = BASE_URL,
            sessionTokenStore = sessionTokenStore
        )

    private val authApi : AuthApi by lazy {
        networkClient.createPublic(
            AuthApi::class.java
        )
    }
    private val unidadeCurricularApi : UnidadeCurricularApi by lazy {
        networkClient.createAuthenticated(
                UnidadeCurricularApi::class.java
            )
    }
    override val loginRepository : LoginRepository by lazy {
        if (USE_FAKE_LOGIN_REPOSITORY ) {
            FakeLoginRepository()
        } else {
            ApiLoginRepositoryImpl(
                api =authApi
            )
        }
    }
    override val unidadeCurricularRepository : UnidadeCurricularRepository by lazy {
        ApiUnidadeCurricularRepositoryImpl(
            api = unidadeCurricularApi
        )
    }
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        private const val USE_FAKE_LOGIN_REPOSITORY = false
    }
}
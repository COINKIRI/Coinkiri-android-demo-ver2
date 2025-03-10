package com.coinkiri.coinkiri.domain.token.repository

interface TokenRepository {

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun setTokens(accessToken: String, refreshToken: String)

    fun clearInfo()
}

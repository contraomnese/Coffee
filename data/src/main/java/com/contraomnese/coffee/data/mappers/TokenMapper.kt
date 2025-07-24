package com.contraomnese.coffee.data.mappers

import com.contraomnese.coffee.data.network.model.TokenNetwork
import com.contraomnese.coffee.data.storage.model.TokenEntity
import com.contraomnese.coffee.domain.auth.model.Token

fun TokenNetwork.toDomain(): Token = Token(
    token = token, lifeTime = tokenLifetime
)

fun Token.toNetwork(): TokenNetwork = TokenNetwork(
    token = token, tokenLifetime = lifeTime
)

fun TokenEntity.toDomain(): Token = Token(
    token = token, lifeTime = expiryMillis
)

fun Token.toStorage(): TokenEntity = TokenEntity (
    token = token, expiryMillis = lifeTime
)
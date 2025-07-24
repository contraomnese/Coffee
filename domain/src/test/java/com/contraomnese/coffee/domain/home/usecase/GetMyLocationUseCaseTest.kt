package com.contraomnese.coffee.domain.home.usecase

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.home.model.MyLocationDomainModel
import com.contraomnese.coffee.domain.home.repository.MyLocationsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

private const val FIRST_LOCATION_ID = "1"
private const val FIRST_LOCATION_NAME = "firstName"
private const val BAD_LOCATION_ID = "bad id"


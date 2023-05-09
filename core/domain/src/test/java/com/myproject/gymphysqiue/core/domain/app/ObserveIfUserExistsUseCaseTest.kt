package com.myproject.gymphysqiue.core.domain.app

import com.myproject.gymphysique.core.model.UserData
import com.myproject.gymphysique.core.testing.repository.FakeUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ObserveIfUserExistsUseCaseTest {

    private val userRepository by lazy { FakeUserRepository() }
    private lateinit var observeIfUserExistsUseCase: ObserveIfUserExistsUseCase

    @Before
    fun setup() {
        observeIfUserExistsUseCase = ObserveIfUserExistsUseCase(userRepository)
    }

    @Test
    fun `when user exists, should return true`() = runTest {
        userRepository.saveUser(userData)
        val result = observeIfUserExistsUseCase()

        assertTrue(result.first())
    }

    @Test
    fun `when user doesnt exist, should return false`() = runTest {
        val result = observeIfUserExistsUseCase()

        assertFalse(result.first())
    }
}

private val userData = UserData(
    firstName = "testFirstname",
    surname = "testSurname",
    age = 99,
    height = 100,
    gender = "Male",
    uri = "testUri"
)

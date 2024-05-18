package com.myproject.gymphysique.accountsetup.viewModel

import com.myproject.gymphysique.accountsetup.receiver.RemindersManager
import com.myproject.gymphysique.core.model.UserData
import com.myproject.gymphysique.core.testing.FakeUserRepository
import com.myproject.gymphysique.core.testing.util.MainDispatcherRule
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AccountSetupViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeRemindersManager: RemindersManager by lazy { mockk() }
    private val fakeUserRepository = FakeUserRepository()
    private val saveUserDataUseCase by lazy { spyk(SaveUserDataUseCase(fakeUserRepository)) }

    private lateinit var viewModel: AccountSetupViewModel

    @Before
    fun setup() {
        viewModel = AccountSetupViewModel(
            saveUserDataUseCase,
            fakeRemindersManager
        )
    }

    @Test
    fun `onFirstNameChange updates state with non-error when valid length`() = runTest {
        // Given
        val validFirstName = "Alice"

        // When
        viewModel.onFirstNameChange(validFirstName)

        // Then
        val state =
            viewModel.state.first()
        assertFalse(state.firstnameError)
        assertEquals(validFirstName, state.firstName)
    }

    @Test
    fun `onFirstNameChange updates state with error when too short`() = runTest {
        // Given
        val shortFirstName = "A"

        // When
        viewModel.onFirstNameChange(shortFirstName)

        // Then
        val state =
            viewModel.state.first()
        assertTrue(state.firstnameError)
        assertEquals(shortFirstName, state.firstName)
    }

    @Test
    fun `onFirstNameChange updates state with error when too long`() = runTest {
        // Given
        val longFirstName = "A very very long name that exceeds max length"

        // When
        viewModel.onFirstNameChange(longFirstName)

        // Then
        val state =
            viewModel.state.first()
        assertTrue(state.firstnameError)
        assertEquals(longFirstName, state.firstName)
    }

    @Test
    fun `onSurname updates state with non-error when valid length`() = runTest {
        // Given
        val validSurname = "Kowalski"

        // When
        viewModel.onSurnameChange(validSurname)

        // Then
        val state =
            viewModel.state.first()
        assertFalse(state.surnameError)
        assertEquals(validSurname, state.surname)
    }

    @Test
    fun `onSurname updates state with error when too short`() = runTest {
        // Given
        val shortSurname = "A"

        // When
        viewModel.onSurnameChange(shortSurname)

        // Then
        val state =
            viewModel.state.first()
        assertTrue(state.surnameError)
        assertEquals(shortSurname, state.surname)
    }

    @Test
    fun `onSurname updates state with error when too long`() = runTest {
        // Given
        val longSurname = "A very very long surname that exceeds max length"

        // When
        viewModel.onSurnameChange(longSurname)

        // Then
        val state = viewModel.state.first()
        assertTrue(state.surnameError)
        assertEquals(longSurname, state.surname)
    }

    @Test
    fun `onAgeChange leaves state unchanged when age is empty`() = runTest {
        // Given
        val emptyAge = ""

        // When
        viewModel.onAgeChange(emptyAge)

        // Then
        val state = viewModel.state.value
        assertTrue(state.ageError)
    }

    @Test
    fun `onAgeChange updates state correctly for a valid age`() = runTest {
        // Given
        val validAge = "30"

        // When
        viewModel.onAgeChange(validAge)

        // Then
        val state = viewModel.state.value
        assertEquals(validAge, state.age)
        assertFalse(state.ageError)
    }

    @Test
    fun `onAgeChange sets error when age is out of range`() = runTest {
        // Given
        val outOfRangeAge = "200"

        // When
        viewModel.onAgeChange(outOfRangeAge)

        // Then
        val state = viewModel.state.value
        assertEquals(outOfRangeAge, state.age)
        assertTrue(state.ageError)
    }

    @Test
    fun `onSaveSelected starts reminder and saves user data when no errors present`() = runTest {
        // Given
        val firstName = "John"
        val surname = "Doe"
        val age = 18
        val height = 180
        val mockUserData: UserData = mockk()
        every { fakeRemindersManager.startReminder() } returns Unit
        coEvery {
            saveUserDataUseCase(any(), any(), any(), any(), any(), any())
        } returns Result.success(mockUserData)

        // When
        viewModel.onFirstNameChange(firstName)
        viewModel.onSurnameChange(surname)
        viewModel.onAgeChange(age.toString())
        viewModel.onHeightChange(height.toString())
        viewModel.onSaveSelected()

        // Then
        verify { fakeRemindersManager.startReminder() }
        coVerify { saveUserDataUseCase(any(), any(), any(), any(), any(), any()) }
    }

    @Test
    fun `onSaveSelected does not start reminder nor saves user data when errors are present`() =
        runTest {
            // Given
            val invalidFirstName = "A"
            val invalidSurname = "A"
            val invalidAge = "abc"
            val invalidHeight = "abc"
            val mockUserData: UserData = mockk()
            every { fakeRemindersManager.startReminder() } returns Unit
            coEvery {
                saveUserDataUseCase(any(), any(), any(), any(), any(), any())
            } returns Result.success(mockUserData)

            viewModel.onFirstNameChange(invalidFirstName)
            viewModel.onSurnameChange(invalidSurname)
            viewModel.onAgeChange(invalidAge)
            viewModel.onHeightChange(invalidHeight)

            // When
            viewModel.onSaveSelected()

            // Then
            verify(exactly = 0) { fakeRemindersManager.startReminder() }
            coVerify(exactly = 0) { saveUserDataUseCase(any(), any(), any(), any(), any(), any()) }
        }
}
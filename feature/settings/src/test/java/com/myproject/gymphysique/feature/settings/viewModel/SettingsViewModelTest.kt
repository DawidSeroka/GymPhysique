package com.myproject.gymphysique.feature.settings.viewModel

import android.net.Uri
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.core.testing.FakeUserRepository
import com.myproject.gymphysique.core.testing.util.MainDispatcherRule
import com.myproject.gymphysqiue.core.domain.settings.GetUserUseCase
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository by lazy { FakeUserRepository() }
    private val saveUserDataUseCase by lazy { SaveUserDataUseCase(userRepository) }
    private val getUserUseCase by lazy { GetUserUseCase(userRepository) }

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(
            saveUserDataUseCase = saveUserDataUseCase,
            getUserUseCase = getUserUseCase
        )
    }

    @Test
    fun `when onFirstNameChange, firstName is empty, then firstName updated`() {
        val expectedResult = "TestName"
        viewModel.onFirstNameChange(expectedResult)

        assertEquals(expectedResult, viewModel.state.value.firstName)
    }

    @Test
    fun `when onSurnameChange, surname is empty, then surname updated`() {
        val expectedResult = "TestName"
        viewModel.onSurnameChange(expectedResult)

        assertEquals(expectedResult, viewModel.state.value.surname)
    }

    @Test
    fun `when onHeightChange, height empty, then height updated`() {
        val expectedResult = "52"
        viewModel.onHeightChange(expectedResult)

        assertEquals(expectedResult, viewModel.state.value.height)
    }

    @Test
    fun `when onAgeChange, age is empty, then age updated`() {
        val expectedResult = "42"
        viewModel.onAgeChange(expectedResult)

        assertEquals(expectedResult, viewModel.state.value.age)
    }

    @Test
    fun `when onGenderSelected set gender MALE, gender default is OTHER, then gender is MALE and expanded false`() {
        val expectedResult = Gender.MALE
        viewModel.onGenderSelected(expectedResult)

        assertEquals(expectedResult, viewModel.state.value.gender)
        assertEquals(false, viewModel.state.value.expanded)
    }

    @Test
    fun `when onSaveUserDataResultReset, then saveUserDataResult is null`() {
        val expectedResult = null
        viewModel.onSaveUserDataResultReset()

        assertEquals(expectedResult, viewModel.state.value.saveUserDataResult)
    }

    @Test
    fun `when onDropdownSelected, expanded is default false, then expanded is true`() {
        val expectedResult = true
        viewModel.onDropdownSelected()

        assertEquals(expectedResult, viewModel.state.value.expanded)
    }

    @Test
    fun `when onImageUriSelected, uri is default null, then uri is updated`() {
        val expectedURI: Uri = mockk()
        viewModel.onImageUriSelected(expectedURI)

        assertEquals(expectedURI, viewModel.state.value.selectedImageUri)
    }

    @Test
    fun `when onSaveSelected, textFields has errors, saveUserDataResult is not updated = null`() {
        val expectedResult = null
        viewModel.onSaveSelected()

        assertEquals(expectedResult, viewModel.state.value.saveUserDataResult)
    }

    @Test
    fun `when onSaveSelected, textFields does not have errors, saveUserDataResult is updated != null`() {
        viewModel.onFirstNameChange("Test")
        viewModel.onSurnameChange("Test")
        viewModel.onHeightChange("142")
        viewModel.onAgeChange("42")
        viewModel.onSaveSelected()

        assertNotNull(viewModel.state.value.saveUserDataResult)
    }
}

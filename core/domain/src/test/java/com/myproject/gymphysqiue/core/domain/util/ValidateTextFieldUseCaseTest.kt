package com.myproject.gymphysqiue.core.domain.util

import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ValidateTextFieldUseCaseTest {

    private lateinit var useCase: ValidateTextFieldUseCase

    @Before
    fun setup() {
        useCase = ValidateTextFieldUseCase()
    }

    // Firstname
    @Test
    fun `when textFieldType is Firstname and string length is shorter than minLength, should return Error`() {
        val minLength = Constants.firstnameMinLenght
        val textField = TextFieldType.Firstname("t")
        val result = useCase(textField)

        assertTrue(textField.value.length < minLength)
        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Firstname and string length is greater than maxLength, should return Error`() {
        val maxLength = Constants.firstnameMaxLenght
        val textField = TextFieldType.Firstname("t".repeat(21))
        val result = useCase(textField)

        assertTrue(textField.value.length > maxLength)
        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Firstname and string length is exactly minLength, should return Error`() {
        val minLength = Constants.firstnameMinLenght
        val textField = TextFieldType.Firstname("t".repeat(minLength))
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Firstname and string length is exactly maxLength, should return Error`() {
        val maxLength = Constants.firstnameMaxLenght
        val textField = TextFieldType.Firstname("t".repeat(maxLength))
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is FirstName and string is between 2 and 20, should return Correct`() {
        val minLength = Constants.firstnameMinLenght
        val maxLength = Constants.firstnameMaxLenght
        val textField = TextFieldType.Firstname("t".repeat(maxLength - minLength + 1))
        val result = useCase(textField)

        assertTrue(textField.value.length > minLength)
        assertTrue(textField.value.length < maxLength)
        assertTrue(result is ValidateResult.Correct)
    }

    // Surname
    @Test
    fun `when textFieldType is Surname and string length is shorter than minLength, should return Error`() {
        val minLength = Constants.surnameMinLenght
        val textField = TextFieldType.Surname("t")
        val result = useCase(textField)

        assertTrue(textField.value.length < minLength)
        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Surname and string length is greater than maxLength, should return Error`() {
        val maxLength = Constants.surnameMaxLenght
        val textField = TextFieldType.Surname("t".repeat(21))
        val result = useCase(textField)

        assertTrue(textField.value.length > maxLength)
        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Surname and string length is exactly minLength, should return Error`() {
        val minLength = Constants.surnameMinLenght
        val textField = TextFieldType.Surname("t".repeat(minLength))
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Surname and string length is exactly maxLength, should return Error`() {
        val maxLength = Constants.surnameMaxLenght
        val textField = TextFieldType.Surname("t".repeat(maxLength))
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Surname and string is between 2 and 20, should return Correct`() {
        val minLength = Constants.surnameMinLenght
        val maxLength = Constants.surnameMaxLenght
        val textField = TextFieldType.Surname("t".repeat(maxLength - minLength + 1))
        val result = useCase(textField)

        assertTrue(textField.value.length > minLength)
        assertTrue(textField.value.length < maxLength)
        assertTrue(result is ValidateResult.Correct)
    }

    // Age
    @Test
    fun `when textFieldType is Age and int is less than minAge, should return Error`() {
        val minAge = Constants.ageMinValue
        val textField = TextFieldType.Age(minAge - 1)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Age and int is greater than maxAge, should return Error`() {
        val maxAge = Constants.ageMaxValue
        val textField = TextFieldType.Age(maxAge + 1)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Age and int is exactly minAge, should return Error`() {
        val minAge = Constants.ageMinValue
        val textField = TextFieldType.Age(minAge)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Age and int is exactly maxAge, should return Error`() {
        val maxAge = Constants.ageMaxValue
        val textField = TextFieldType.Age(maxAge)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Age and int is between min and maxAge, should return Correct`() {
        val maxAge = Constants.ageMaxValue
        val minAge = Constants.ageMinValue
        val textField = TextFieldType.Age(maxAge - minAge + 1)
        val result = useCase(textField)

        assertTrue(textField.value > minAge)
        assertTrue(textField.value < maxAge)
        assertTrue(result is ValidateResult.Correct)
    }

    // Height
    @Test
    fun `when textFieldType is Height and int is less than minHeight, should return Error`() {
        val minAge = Constants.heightMinValue
        val textField = TextFieldType.Height(minAge - 1)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Height and int is greater than maxHeight, should return Error`() {
        val maxAge = Constants.heightMaxValue
        val textField = TextFieldType.Height(maxAge + 1)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Height and int is exactly minHeight, should return Error`() {
        val minAge = Constants.heightMinValue
        val textField = TextFieldType.Height(minAge)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Height and int is exactly maxHeight, should return Error`() {
        val maxAge = Constants.heightMaxValue
        val textField = TextFieldType.Height(maxAge)
        val result = useCase(textField)

        assertTrue(result is ValidateResult.Error)
    }

    @Test
    fun `when textFieldType is Height and int is between minHeight and maxHeight, should return Correct`() {
        val maxAge = Constants.heightMaxValue
        val minAge = Constants.heightMinValue
        val textField = TextFieldType.Height(maxAge - minAge + 1)
        val result = useCase(textField)

        assertTrue(textField.value > minAge)
        assertTrue(textField.value < maxAge)
        assertTrue(result is ValidateResult.Correct)
    }
}

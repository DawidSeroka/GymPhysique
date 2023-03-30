// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("kotlin")
}
dependencies{
    implementation(libs.kotlinx.datetime)

}
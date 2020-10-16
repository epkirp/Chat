package com.example.domain.errors.sealed

sealed class PropertyPath(open val propertyPath: String) {

    object Email : PropertyPath("email")
    object Password : PropertyPath("password")
    object NewPassword : PropertyPath("new_password")
    object ConfirmPassword : PropertyPath("confirm_password")
    object UserName : PropertyPath("username")
    object Birthday : PropertyPath("birthday")
    object EmptyPropertyPath : PropertyPath("")

    companion object {

        fun stringPropertyPathToPropertyPath(propertyPath: String): PropertyPath {
            return when (propertyPath) {
                "email" -> Email
                "password" -> Password
                "new_password" -> NewPassword
                "confirm_password" -> ConfirmPassword
                "username" -> UserName
                "birthday" -> Birthday
                else -> EmptyPropertyPath
            }
            //throw IllegalStateException(PROPERTY_PATH_ERROR)
        }

        fun propertyPathToStringPropertyPath(propertyPath: PropertyPath): String {
            return when (propertyPath) {
                Email -> "email"
                Password -> "password"
                NewPassword -> "new_password"
                ConfirmPassword -> "confirm_password"
                UserName -> "username"
                Birthday -> "birthday"
                else -> ""
            }
        }

        fun getAllPropertyPaths(): List<String> {
            return arrayListOf(
                "email",
                "password",
                "new_password",
                "confirm_password",
                "username",
                "birthday"
            )
        }
    }
}
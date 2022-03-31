package com.xeross.srapp.ui.auth.register.exceptions

import com.xeross.srapp.R
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes

enum class ExceptionRegisterTypes(val resId: Int?, val textInputTypes: AuthTextInputTypes?) {
    
    CONFIRM_PASSWORD_IS_DIFFERENT(R.string.confirm_password_is_different, AuthTextInputTypes.CONFIRM_PASSWORD),
    INVALID_PASSWORD(R.string.invalid_password, AuthTextInputTypes.PASSWORD),
    INVALID_EMAIL(R.string.invalid_email, AuthTextInputTypes.EMAIL),
    INVALID_PSEUDO(R.string.invalid_pseudo, AuthTextInputTypes.PSEUDO),
    SUCCESS(null, null),
    
}
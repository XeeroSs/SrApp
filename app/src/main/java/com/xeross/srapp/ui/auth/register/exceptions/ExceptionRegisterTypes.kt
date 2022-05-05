package com.xeross.srapp.ui.auth.register.exceptions

import com.xeross.srapp.R
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes

enum class ExceptionRegisterTypes(val resId: Int?, val textInputTypes: AuthTextInputTypes?) {
    
    CONFIRM_PASSWORD_IS_DIFFERENT(R.string.confirm_password_is_different, AuthTextInputTypes.CONFIRM_PASSWORD),
    INVALID_PASSWORD(R.string.invalid_password, AuthTextInputTypes.PASSWORD),
    INVALID_EMAIL(R.string.invalid_email, AuthTextInputTypes.EMAIL),
    INVALID_PSEUDO(R.string.invalid_pseudo, AuthTextInputTypes.PSEUDO),
    EMAIL_OR_PASSWORD_INCORRECT(R.string.email_or_password_incorrect, AuthTextInputTypes.EMAIL),
    EMAIL_ALREADY_USE(R.string.email_is_already_in_use_by_another_account, AuthTextInputTypes.EMAIL),
    AUTH_FAILED(R.string.auth_failed, AuthTextInputTypes.EMAIL),
    SUCCESS(null, null),
    
}
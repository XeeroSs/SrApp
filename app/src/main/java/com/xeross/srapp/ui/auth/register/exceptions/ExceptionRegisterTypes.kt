package com.xeross.srapp.ui.auth.register.exceptions

import com.xeross.srapp.R
import com.xeross.srapp.ui.auth.register.types.RegisterTextInputTypes

enum class ExceptionRegisterTypes(val resId: Int?, val textInputTypes: RegisterTextInputTypes?) {
    
    CONFIRM_PASSWORD_IS_DIFFERENT(R.string.confirm_password_is_different, RegisterTextInputTypes.CONFIRM_PASSWORD),
    INVALID_PASSWORD(R.string.invalid_password, RegisterTextInputTypes.PASSWORD),
    INVALID_EMAIL(R.string.invalid_email, RegisterTextInputTypes.EMAIL),
    INVALID_PSEUDO(R.string.invalid_pseudo, RegisterTextInputTypes.PSEUDO),
    SUCCESS(null, null),
    
}
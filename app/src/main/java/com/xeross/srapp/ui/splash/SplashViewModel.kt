package com.xeross.srapp.ui.splash

import com.xeross.srapp.base.BaseFirebaseViewModel


class SplashViewModel : BaseFirebaseViewModel() {
    
     fun isAuth(): Boolean {
        return getAuth().currentUser != null
    }
    
}
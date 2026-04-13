package com.cristiano.livvichallenge.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Routes(val route: String) {
    data object LoginEmail : Routes("login_email")
    data object LoginPassword : Routes("login_password/{email}") {
        fun createRoute(email: String): String = "login_password/$email"
    }

    data object SignUp : Routes("sign_up")
    data object Doors : Routes("doors")
    data object DoorEvents : Routes("door_events/{doorId}") {
        fun createRoute(doorId: Int): String = "door_events/$doorId"
    }


}


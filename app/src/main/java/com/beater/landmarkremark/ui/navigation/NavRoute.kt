package com.beater.landmarkremark.ui.navigation

import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.utils.URL_UTF8_CHARSET
import com.google.gson.Gson
import java.net.URLEncoder

sealed class NavRoute(val route: String) {
    object Splash : NavRoute("splash")
    object FlowSelection : NavRoute("flow_selection")
    object SignUp : NavRoute("sign_up")
    object SignIn : NavRoute("sign_in")
    object MyNotes : NavRoute("my_notes")
    object SearchNotes : NavRoute("search_notes")

    object NoteDetail : NavRoute("note_detail") {
        const val note = "note"

        fun withArgs(note: Note): String {
            val noteJson = Gson().toJson(note)
            val noteJsonEncoded = URLEncoder.encode(noteJson, URL_UTF8_CHARSET)
            return "$route/$noteJsonEncoded"
        }
    }

    object CustomMap : NavRoute("custom_map")

    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}

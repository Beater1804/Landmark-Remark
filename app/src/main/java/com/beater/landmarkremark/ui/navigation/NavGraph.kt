package com.beater.landmarkremark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.ui.screens.custom_map.CustomMapScreen
import com.beater.landmarkremark.ui.screens.flow_selection.FlowSelectionScreen
import com.beater.landmarkremark.ui.screens.my_notes.MyNotesScreen
import com.beater.landmarkremark.ui.screens.note_detail.NoteDetailScreen
import com.beater.landmarkremark.ui.screens.search_notes.SearchNotesScreen
import com.beater.landmarkremark.ui.screens.sign_in.SignInScreen
import com.beater.landmarkremark.ui.screens.sign_up.SignUpScreen
import com.beater.landmarkremark.ui.screens.splash.SplashScreen
import com.beater.landmarkremark.utils.URL_UTF8_CHARSET
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        addSplashScreen(navController = navController, navGraphBuilder = this)
        addFlowSelectionScreen(navController = navController, navGraphBuilder = this)
        addSignUpScreen(navController = navController, navGraphBuilder = this)
        addSignInScreen(navController = navController, navGraphBuilder = this)
        addMyNotesScreen(navController = navController, navGraphBuilder = this)
        addSearchNotesScreen(navController = navController, navGraphBuilder = this)
        addNoteDetailScreen(navController = navController, navGraphBuilder = this)
        addCustomMapScreen(navController = navController, navGraphBuilder = this)
    }
}

fun addSplashScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.Splash.route) {
        SplashScreen(navController = navController)
    }
}

fun addFlowSelectionScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.FlowSelection.route) {
        FlowSelectionScreen(navController = navController)
    }
}

fun addSignUpScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.SignUp.route) {
        SignUpScreen(navController = navController)
    }
}

fun addSignInScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.SignIn.route) {
        SignInScreen(navController = navController)
    }
}

fun addMyNotesScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.MyNotes.route) {
        MyNotesScreen(navController = navController)
    }
}

fun addSearchNotesScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.SearchNotes.route) {
        SearchNotesScreen(navController = navController)
    }
}

fun addNoteDetailScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(
        route = NavRoute.NoteDetail.withArgsFormat(NavRoute.NoteDetail.note),
        arguments = listOf(
            navArgument(NavRoute.NoteDetail.note) {
                type = NavType.StringType
            }
        )
    ) {
        val noteJsonEncoded = it.arguments?.getString(NavRoute.NoteDetail.note) ?: ""
        val noteJson = URLDecoder.decode(noteJsonEncoded, URL_UTF8_CHARSET)
        val note = Gson().fromJson(noteJson, Note::class.java)

        NoteDetailScreen(navController = navController, note = note)
    }
}

fun addCustomMapScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.CustomMap.route) {
        CustomMapScreen(navController = navController)
    }
}
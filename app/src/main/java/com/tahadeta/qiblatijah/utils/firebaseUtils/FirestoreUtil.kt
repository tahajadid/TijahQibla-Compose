package com.tahadeta.qiblatijah.utils.firebaseUtils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.firestore.FirebaseFirestore
import com.tahadeta.qiblatijah.BuildConfig
import com.tahadeta.qiblatijah.viewModel.HomeViewModel

object FirestoreUtil {

    @SuppressLint("StaticFieldLeak")
    val firestore = FirebaseFirestore.getInstance()

    @Composable
    fun FetchVersionFromFirestore(homeViewModel: HomeViewModel) {

        val versionCollection = firestore.collection("version")

        LaunchedEffect(Unit) {
            versionCollection.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val remoteVersion = document.toObject(VersionResult::class.java)
                        Log.d("TestResult", "version : $remoteVersion")
                        // update UiState in homeView
                        homeViewModel.updateUpdateAppView(
                            !BuildConfig.VERSION_NAME.equals(remoteVersion.versionName),
                            remoteVersion.apkUrl
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle error
                    Log.d("TestResult","exception : "+exception.toString())
                }
        }

    }
}

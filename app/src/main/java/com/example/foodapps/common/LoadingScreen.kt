package com.example.foodapps.common
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases

@Composable
fun LoadingScreen(
    readAppEntry: AppEntryUseCases,
    onLoadingComplete: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        readAppEntry.readAppEntry().collect{
//            it.toString()
            isChecked = it
            onLoadingComplete(it)
        }
    }

    if (!isChecked) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


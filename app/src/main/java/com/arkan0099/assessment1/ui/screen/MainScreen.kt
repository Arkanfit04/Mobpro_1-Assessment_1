package com.arkan0099.assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arkan0099.assessment1.R
import com.arkan0099.assessment1.navigation.Screen
import com.arkan0099.assessment1.ui.theme.AssessmentTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))

    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var tanggal by rememberSaveable { mutableStateOf("") }
    var tanggalEmptyError by remember { mutableStateOf(false) }
    var tanggalInvalidError by remember { mutableStateOf(false) }

    var bulan by rememberSaveable { mutableStateOf("") }
    var bulanEmptyError by remember { mutableStateOf(false) }
    var bulanInvalidError by remember { mutableStateOf(false) }

    var tahun by rememberSaveable { mutableStateOf("") }
    var tahunEmptyError by remember { mutableStateOf(false) }
    var tahunInvalidError by remember { mutableStateOf(false) }

    var gen by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.gencount_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tanggal,
            onValueChange = { tanggal = it },
            label = { Text(text = stringResource(R.string.tanggal)) },
            supportingText = {
                ErrorHint(tanggalEmptyError, tanggalInvalidError)
            },
            isError = tanggalEmptyError || tanggalInvalidError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bulan,
            onValueChange = { bulan = it },
            label = { Text(text = stringResource(R.string.bulan)) },
            supportingText = {
                ErrorHint(bulanEmptyError, bulanInvalidError)
            },
            isError = bulanEmptyError || bulanInvalidError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tahun,
            onValueChange = { tahun = it },
            label = { Text(text = stringResource(R.string.tahun)) },
            supportingText = {
                ErrorHint(tahunEmptyError, tahunInvalidError)
            },
            isError = tahunEmptyError || tahunInvalidError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val day = tanggal.toIntOrNull()
                val month = bulan.toIntOrNull()
                val year = tahun.toIntOrNull()

                // Validasi
                tanggalEmptyError = tanggal.isBlank()
                tanggalInvalidError = !tanggalEmptyError && (day == null || day !in 1..31)

                bulanEmptyError = bulan.isBlank()
                bulanInvalidError = !bulanEmptyError && (month == null || month !in 1..12)

                tahunEmptyError = tahun.isBlank()
                tahunInvalidError = !tahunEmptyError && (year == null || year < 1900)

                gen = if (
                    !tanggalEmptyError && !tanggalInvalidError &&
                    !bulanEmptyError && !bulanInvalidError &&
                    !tahunEmptyError && !tahunInvalidError
                ) {
                    "Kamu termasuk: ${getGeneration(year!!)}"
                } else {
                    ""
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if (gen.isNotEmpty()) {
            Text(
                text = gen,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Button(
                onClick = {
                    val day = tanggal.toIntOrNull() ?: return@Button
                    val month = bulan.toIntOrNull() ?: return@Button
                    val year = tahun.toIntOrNull() ?: return@Button

                    val generationName = getGeneration(year)
                    val message = context.getString(
                        R.string.bagikan_template,
                        day, month, year,
                        generationName
                    )
                    shareData(context, message)
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Bagikan")
            }
        }
    }
}

// Fungsi untuk menentukan generasi
fun getGeneration(year: Int): String {
    return when (year) {
        in 2013..2025 -> "Gen Alpha"
        in 1997..2012 -> "Gen Z"
        in 1981..1996 -> "Millennial"
        in 1965..1980 -> "Gen X"
        in 1946..1964 -> "Baby Boomer"
        else -> "Unknown Generation"
    }
}

fun shareData(context: Context, message: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Bagikan hasil ke:")
    context.startActivity(shareIntent)
}



@Composable
fun ErrorHint(isEmpty: Boolean, isInvalid: Boolean) {
    when {
        isEmpty -> Text(
            text = stringResource(R.string.input_invalid),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall
        )
        isInvalid -> Text(
            text = stringResource(R.string.input_invalid_value),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall
        )
    }
}




@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    AssessmentTheme {
        MainScreen(rememberNavController())
    }
}
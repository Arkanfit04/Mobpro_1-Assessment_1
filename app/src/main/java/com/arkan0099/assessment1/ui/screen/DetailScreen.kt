package com.arkan0099.assessment1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.arkan0099.assessment1.R
import com.arkan0099.assessment1.util.ViewModelFactory

const val KEY_ID_CATATAN = "idCatatan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var task by rememberSaveable { mutableStateOf("") }
    var detail by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("/") }
    var selectedstatus by rememberSaveable { mutableStateOf("Belum Selesai") }

    val liststatus = listOf("Belum Selesai", "Selesai")


    LaunchedEffect(true) {
            if (id == null) return@LaunchedEffect
            val data = viewModel.getCatatan(id) ?: return@LaunchedEffect
            task = data.task
            detail = data.detail
            deadline = data.deadline
            selectedstatus = data.status
        }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_catatan))
                    else
                    Text(text = stringResource(id = R.string.edit_catatan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (task.isBlank() || detail.isBlank() || deadline.length < 10 || selectedstatus.isBlank()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                        viewModel.insert(task, detail, deadline, selectedstatus)
                    } else {
                        viewModel.update(id, task, detail, deadline, selectedstatus)
                    }
                        navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeteleAction {
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = task,
                onValueChange = { task = it },
                label = { Text(text = stringResource(R.string.task)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = detail,
                onValueChange = { detail = it },
                label = { Text(text = stringResource(R.string.isi_detail)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxHeight(0.3f)
            )

            TextField(
                value = deadline,
                onValueChange = {
                    deadline = formatDateInput(it)
                },
                label = { Text("Deadline (dd/MM/yyyy)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Text(text = "Status Task", style = MaterialTheme.typography.titleMedium)

            liststatus.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedstatus == status,
                    onClick = { selectedstatus = status }
                )
                Text(
                    text = status,
                    modifier = Modifier.clickable { selectedstatus = status })
                }
            }
        }
    }
}

fun formatDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }
    val minYear = 2025
    val maxYear = 2100

    var day = ""
    var month = ""
    var year: String

    if (digits.isNotEmpty()) {
        day = digits.take(2)
        if (day.length == 2) {
            val dayInt = day.toInt().coerceAtMost(31)
            day = dayInt.toString().padStart(2, '0')
        }
    }

    if (digits.length >= 3) {
        month = digits.substring(2).take(2)
        if (month.length == 2) {
            val monthInt = month.toInt().coerceAtMost(12)
            month = monthInt.toString().padStart(2, '0')
        }
    }

    if (digits.length > 4) {
        year = digits.substring(4).take(4)
        if (year.length == 4) {
            val yearInt = year.toInt().coerceIn(minYear, maxYear)
            year = yearInt.toString()
        }
    } else {
        // Auto default ke 2025
        year = minYear.toString()
    }

    return buildString {
        if (day.isNotEmpty()) append(day)
        if (month.isNotEmpty()) append("/$month")
        if (year.isNotEmpty()) append("/$year")
    }
}


@Composable
fun DeteleAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

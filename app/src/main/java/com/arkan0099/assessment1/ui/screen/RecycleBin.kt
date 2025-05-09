package com.arkan0099.assessment1.ui.screen

import RecycleViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun RecycleBinScreen(viewModel: RecycleViewModel) {
    val notes by viewModel.getRecycleBinNotes().collectAsState(initial = emptyList())

    LazyColumn {
        items(notes) { note ->
            ListItem(catatan = note, onClick = { /* bisa kosong atau buat aksi */ })
            Row {
                Button(onClick = { viewModel.restoreNote(note.id) }) {
                    Text(text = "Restore")
                }
                Button(onClick = { viewModel.deleteForever(note.id) }) {
                    Text(text = "Hapus Permanen")
                }
            }
        }
    }
}

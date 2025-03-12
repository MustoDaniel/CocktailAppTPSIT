package com.example.cocktailapp.items

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cocktailapp.viewModels.ApplicationViewModel

@Composable
fun OptionList(options: List<String>, appViewModel : ApplicationViewModel): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(appViewModel.selectedOption) }

    Box {

        Button(onClick = { expanded = true }) {
            Text(selectedOption)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    selectedOption = option
                    expanded = false
                })
            }
        }
    }

    return selectedOption
}


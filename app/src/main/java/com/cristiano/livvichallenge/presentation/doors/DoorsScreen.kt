package com.cristiano.livvichallenge.presentation.doors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cristiano.livvichallenge.ui.components.AppBatteryIndicator
import com.cristiano.livvichallenge.ui.components.AppPaginationBar
import com.cristiano.livvichallenge.ui.theme.NavyBlue
import com.cristiano.livvichallenge.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoorsScreen(
    viewModel: DoorsViewModel,
    onDoorClick: (Int) -> Unit = {},
    onLogout: () -> Unit = {},
    onSessionExpired: () -> Unit = {}
) {

    BackHandler(enabled = true) {
        // não faz nada
    }


    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.shouldLogout) {
        if (state.shouldLogout) {
            onSessionExpired()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doors") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.logout()
                            onLogout()
                        }
                    ) {
                        Icon(

                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )

        },
        bottomBar = {
            if (!state.isLoading && state.doors.isNotEmpty()) {
                AppPaginationBar(
                    currentPage = state.currentPage,
                    totalPages = state.totalPages,
                    onPreviousClick = viewModel::goToPreviousPage,
                    onNextClick = viewModel::goToNextPage
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    modifier = Modifier.weight(1f),
                    label = { Text("Search") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = viewModel::searchDoors,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = NavyBlue,
                        contentColor = White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",

                        )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.errorMessage != null -> {
                    Column {
                        Text(state.errorMessage ?: "Error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = viewModel::searchDoors) {
                            Text("Try again")
                        }
                    }
                }

                else -> {
                    if (state.doors.isEmpty()) {
                        Text(
                            text = if (state.searchQuery.isBlank()) {
                                "No doors found."
                            } else {
                                "No results for \"${state.searchQuery}\"."
                            }
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(state.doors) { door ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onDoorClick(door.id) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 10.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 72.dp, bottom = 20.dp)
                                        ) {
                                            Text(text = door.name)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(text = door.address)
                                        }

                                        AppBatteryIndicator(
                                            batteryLevel = door.battery,
                                            modifier = Modifier.align(Alignment.BottomEnd)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
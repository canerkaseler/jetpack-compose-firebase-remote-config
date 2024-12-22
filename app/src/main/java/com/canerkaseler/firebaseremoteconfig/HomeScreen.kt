package com.canerkaseler.firebaseremoteconfig

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        modifier = modifier,
        isVisibleBreakingNewsMessage = screenState.isVisibleBreakingNewsMessage,
        breakingNewsMessage = screenState.breakingNewsMessage,
        breakingNewsCount = screenState.breakingNewsCount,
    )
}

@Composable
private fun HomeScreenContent(
    isVisibleBreakingNewsMessage: Boolean,
    breakingNewsMessage: String,
    breakingNewsCount: Long,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Message: $breakingNewsMessage",
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Visibility: $isVisibleBreakingNewsMessage",
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Count: $breakingNewsCount",
                fontSize = 16.sp,
            )
            BreakingNewsCard(
                modifier = Modifier.padding(top = 32.dp),
                isVisibleBreakingNewsMessage = isVisibleBreakingNewsMessage,
                breakingNewsMessage = breakingNewsMessage,
                breakingNewsCount = breakingNewsCount,
            )
        }
    }
}

@Composable
private fun BreakingNewsCard(
    isVisibleBreakingNewsMessage: Boolean,
    breakingNewsMessage: String,
    breakingNewsCount: Long,
    modifier: Modifier = Modifier,
) {
    if (!isVisibleBreakingNewsMessage) {
        return
    }

    Card(
        modifier = modifier
            .clip(
                shape = MaterialTheme.shapes.large
            )
            .shadow(
                elevation = 14.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp)
        ) {
            Text(
                text = "$breakingNewsCount $breakingNewsMessage",
                fontSize = 16.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(alignment = Alignment.End),
                onClick = { },
            ) {
                Text(
                    text = "Read",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        isVisibleBreakingNewsMessage = true,
        breakingNewsMessage = "breaking news from Türkiye!",
        breakingNewsCount = 7,
    )
}
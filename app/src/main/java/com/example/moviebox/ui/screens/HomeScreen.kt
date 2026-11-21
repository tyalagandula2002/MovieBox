
package com.example.moviebox.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.data.MovieInfo
import com.example.moviebox.data.sampleMovies

private val HomeGold = Color(0xFFFFD700)
private val HomePurple = Color(0xFF38205E)

@Composable
fun HomeScreen(
    onReviewsClick: () -> Unit,
    onWriteReview: (MovieInfo) -> Unit
) {

    var showMovies by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }

    var selectedMovie by remember {
        mutableStateOf<MovieInfo?>(null)
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF38205E),
            Color(0xFF8155A5),
            Color(0xFFFFC76D)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!showMovies) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "🎬",
                fontSize = 65.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to MovieBox",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Discover. Watch. Review.",
                fontSize = 18.sp,
                color = Color(0xFFFFE4B5)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "⭐ Featured Movie",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            HomeMovieCard(
                movie = sampleMovies[0],
                onClick = {
                    selectedMovie = sampleMovies[0]
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { showMovies = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGold,
                    contentColor = HomePurple
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Explore Movies",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = onReviewsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "My Reviews",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }

        } else {

            TextButton(
                onClick = { showMovies = false }
            ) {
                Text("← Back to Home", color = Color.White)
            }

            Text(
                text = "Explore Movies",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search Movies") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = HomeGold,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = HomeGold,
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            val filteredMovies = sampleMovies.filter {
                it.title.contains(search, ignoreCase = true) ||
                        it.genre.contains(search, ignoreCase = true)
            }

            if (filteredMovies.isEmpty()) {
                Text("No movies found", color = Color.White)
            }

            filteredMovies.forEach { movie ->

                HomeMovieCard(
                    movie = movie,
                    onClick = {
                        selectedMovie = movie
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    selectedMovie?.let { movie ->

        AlertDialog(
            onDismissRequest = {
                selectedMovie = null
            },
            title = {
                Text(movie.title)
            },
            text = {
                Column {
                    Text("Year: ${movie.year}")
                    Text("Genre: ${movie.genre}")
                    Text("Rating: ⭐ ${movie.rating}")

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(movie.description)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedMovie = null
                        onWriteReview(movie)
                    }
                ) {
                    Text("Write Review")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedMovie = null
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun HomeMovieCard(
    movie: MovieInfo,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                HomePurple,
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {

        Text(
            text = "🎥 ${movie.title}",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = HomeGold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${movie.year} • ${movie.genre}",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "⭐ ${movie.rating} / 10",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tap to view details",
            color = HomeGold,
            fontSize = 12.sp
        )
    }
}

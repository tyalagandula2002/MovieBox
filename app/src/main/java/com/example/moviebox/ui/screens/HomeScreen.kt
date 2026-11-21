
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

private val Gold = Color(0xFFFFD700)
private val Purple = Color(0xFF38205E)

private data class Movie(
    val title: String,
    val year: String,
    val genre: String,
    val rating: String,
    val description: String
)

private val movies = listOf(
    Movie(
        "Interstellar", "2014", "Sci-Fi", "8.7",
        "Explorers travel through space to save humanity."
    ),
    Movie(
        "Inception", "2010", "Sci-Fi", "8.8",
        "A thief enters people's dreams to steal secrets."
    ),
    Movie(
        "The Dark Knight", "2008", "Action", "9.0",
        "Batman faces the dangerous criminal Joker."
    ),
    Movie(
        "Oppenheimer", "2023", "Drama", "8.3",
        "The story of the scientist behind the atomic bomb."
    )
)

@Composable
fun HomeScreen() {

    var page by remember { mutableStateOf("home") }
    var search by remember { mutableStateOf("") }
    var selectedMovie by remember {
        mutableStateOf<Movie?>(null)
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

        if (page == "home") {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "🎬",
                fontSize = 70.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

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

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "⭐ Featured Movie",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            MovieCard(
                movie = movies[0],
                onClick = {
                    selectedMovie = movies[0]
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { page = "movies" },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Purple
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Explore Movies",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = { page = "reviews" },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "My Reviews",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

        if (page == "movies") {

            BackButton {
                page = "home"
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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            val filteredMovies = movies.filter { movie ->
                movie.title.contains(search, ignoreCase = true) ||
                        movie.genre.contains(search, ignoreCase = true)
            }

            if (filteredMovies.isEmpty()) {
                Text(
                    text = "No movies found",
                    color = Color.White
                )
            }

            filteredMovies.forEach { movie ->

                MovieCard(
                    movie = movie,
                    onClick = {
                        selectedMovie = movie
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (page == "reviews") {

            BackButton {
                page = "home"
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "📝",
                fontSize = 65.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "My Reviews",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "No reviews yet!",
                fontSize = 18.sp,
                color = Color.White
            )
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
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Purple,
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {

        Text(
            text = "🎥 ${movie.title}",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Gold
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
            color = Gold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BackButton(
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "← Back to Home",
            color = Color.White
        )
    }
}

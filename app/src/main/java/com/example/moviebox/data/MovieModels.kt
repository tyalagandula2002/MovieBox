
package com.example.moviebox.data

data class MovieInfo(
    val title: String,
    val year: String,
    val genre: String,
    val rating: String,
    val description: String
)

data class UserReview(
    val movieTitle: String,
    val stars: Int,
    val comment: String
)

val sampleMovies = listOf(
    MovieInfo(
        "Interstellar", "2014", "Sci-Fi", "8.7",
        "Explorers travel through space to save humanity."
    ),
    MovieInfo(
        "Inception", "2010", "Sci-Fi", "8.8",
        "A thief enters people's dreams to steal secrets."
    ),
    MovieInfo(
        "The Dark Knight", "2008", "Action", "9.0",
        "Batman faces the dangerous criminal Joker."
    ),
    MovieInfo(
        "Oppenheimer", "2023", "Drama", "8.3",
        "The story of the scientist behind the atomic bomb."
    )
)

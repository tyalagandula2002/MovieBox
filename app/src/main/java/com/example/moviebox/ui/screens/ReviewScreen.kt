
package com.example.moviebox.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.data.UserReview
import com.example.moviebox.data.sampleMovies

@Composable
fun ReviewScreen(
    reviews: SnapshotStateList<UserReview>,
    initialMovieTitle: String?,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val gold = Color(0xFFFFD700)
    val purple = Color(0xFF38205E)

    var selectedMovie by remember(initialMovieTitle) {
        mutableStateOf(
            initialMovieTitle ?: sampleMovies.first().title
        )
    }

    var stars by remember {
        mutableIntStateOf(0)
    }

    var reviewText by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var editingIndex by remember {
        mutableIntStateOf(-1)
    }

    BackHandler {
        onBack()
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
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // Back Button
        TextButton(
            onClick = onBack
        ) {
            Text(
                text = "← Back to Home",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Screen Heading
        Text(
            text = "📝 Movie Reviews",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Rate movies and share your thoughts.",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Review Form Heading
        Text(
            text = if (editingIndex >= 0)
                "Edit Review"
            else
                "Write a Review",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = gold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Movie Selection
        Text(
            text = "Select Movie",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {

            OutlinedButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$selectedMovie  ▼",
                    color = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                sampleMovies.forEach { movie ->

                    DropdownMenuItem(
                        text = {
                            Text(movie.title)
                        },
                        onClick = {
                            selectedMovie = movie.title
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Star Rating
        Text(
            text = "Your Rating",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (star in 1..5) {

                Text(
                    text = if (star <= stars) "★" else "☆",
                    fontSize = 36.sp,
                    color = if (star <= stars)
                        gold else Color.White,
                    modifier = Modifier.clickable {
                        stars = star
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Review Text
        OutlinedTextField(
            value = reviewText,
            onValueChange = {
                reviewText = it
            },
            label = {
                Text("Write your review")
            },
            placeholder = {
                Text("What did you think about this movie?")
            },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = gold,
                unfocusedBorderColor = Color.White,
                focusedLabelColor = gold,
                unfocusedLabelColor = Color.White,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray,
                cursorColor = gold
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Save or Update Review
        Button(
            enabled = stars > 0 && reviewText.isNotBlank(),
            onClick = {

                val review = UserReview(
                    movieTitle = selectedMovie,
                    stars = stars,
                    comment = reviewText.trim()
                )

                if (editingIndex in reviews.indices) {

                    reviews[editingIndex] = review

                } else {

                    reviews.add(review)
                }

                stars = 0
                reviewText = ""
                editingIndex = -1
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = gold,
                contentColor = purple
            ),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = if (editingIndex >= 0)
                    "Update Review"
                else
                    "Save Review",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Cancel Editing
        if (editingIndex >= 0) {

            TextButton(
                onClick = {
                    editingIndex = -1
                    stars = 0
                    reviewText = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel Editing",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        HorizontalDivider(
            color = Color.White.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Saved Reviews Heading
        Text(
            text = "My Reviews (${reviews.size})",
            fontSize = 23.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (reviews.isEmpty()) {

            Text(
                text = "No reviews yet. Write your first review!",
                color = Color.White,
                fontSize = 16.sp
            )

        } else {

            reviews.forEachIndexed { index, review ->

                // Review Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            purple,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(18.dp)
                ) {

                    Text(
                        text = review.movieTitle,
                        color = gold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "★".repeat(review.stars) +
                                "☆".repeat(5 - review.stars),
                        color = gold,
                        fontSize = 23.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = review.comment,
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Edit, Delete and Share Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Edit Review
                        TextButton(
                            onClick = {

                                selectedMovie = review.movieTitle
                                stars = review.stars
                                reviewText = review.comment
                                editingIndex = index
                            }
                        ) {
                            Text(
                                text = "Edit",
                                color = gold
                            )
                        }

                        // Delete Review
                        TextButton(
                            onClick = {

                                reviews.removeAt(index)

                                if (editingIndex == index) {

                                    editingIndex = -1
                                    stars = 0
                                    reviewText = ""

                                } else if (editingIndex > index) {

                                    editingIndex--
                                }
                            }
                        ) {
                            Text(
                                text = "Delete",
                                color = Color.White
                            )
                        }

                        // NEW FEATURE: Share Review
                        TextButton(
                            onClick = {

                                val shareText = """
                                    🎬 MovieBox Review

                                    Movie: ${review.movieTitle}

                                    Rating:
                                    ${"★".repeat(review.stars)}${"☆".repeat(5 - review.stars)}

                                    My Review:
                                    ${review.comment}

                                    Shared from MovieBox
                                """.trimIndent()

                                val shareIntent =
                                    Intent(Intent.ACTION_SEND).apply {

                                        type = "text/plain"

                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            shareText
                                        )
                                    }

                                val chooserIntent =
                                    Intent.createChooser(
                                        shareIntent,
                                        "Share Movie Review"
                                    )

                                context.startActivity(
                                    chooserIntent
                                )
                            }
                        ) {
                            Text(
                                text = "↗ Share",
                                color = gold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

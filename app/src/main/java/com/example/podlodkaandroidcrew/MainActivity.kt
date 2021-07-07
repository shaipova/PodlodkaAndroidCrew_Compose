package com.example.podlodkaandroidcrew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = ViewModel()
            SessionListScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun SessionListScreen(
    viewModel: ViewModel
) {
    Column {
        SearchField(
            leadingIcon = { Icon(Icons.Filled.Search, "Search", tint = Color.LightGray) },
            viewModel = viewModel
        )
        FavRowScroll()
        SessionsLazyScroll(viewModel)
    }
}

@Composable
fun SearchField(
    leadingIcon: (@Composable () -> Unit)? = null,
    viewModel: ViewModel
) {
    val textSearch by viewModel.search.observeAsState()

    OutlinedTextField(
        value = textSearch.orEmpty(),
        onValueChange = { viewModel.setSearch(it) },
        label = { Text("Поиск") },
        maxLines = 1,
        leadingIcon = leadingIcon,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun SessionCard(
    speaker: String,
    timeInterval: String,
    description: String,
    imageUrl: String,
    viewModel: ViewModel
) {

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpeakerPhoto(imageUrl = imageUrl)

                Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                    TextSpeaker(speaker)
                    TextTimeInterval(timeInterval = timeInterval)
                    TextDescription(
                        description = description,
                        TextOverflow.Ellipsis,
                        2
                    )

                }

//                val fav by viewModel.fav.observeAsState()
//
//                if (fav != true) {
//                    Image(
//                        painterResource(id = R.drawable.ic_baseline_favorite_border_24),
//                        contentDescription = "Favorite Border Icon",
//                        modifier = Modifier
//                            .size(25.dp)
//                            .clickable { viewModel.onFav() }
//                    )
//                } else {
//                    Image(
//                        painterResource(id = R.drawable.ic_baseline_favorite_24),
//                        contentDescription = "Favorite Icon",
//                        modifier = Modifier
//                            .size(25.dp)
//                            .clickable { viewModel.onFav() }
//                    )
//                }
            }
        }
    }
}


@Composable
fun SpeakerPhoto(imageUrl: String) {
    Image(
        painter = rememberGlidePainter(request = imageUrl),
        contentDescription = "Speaker photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(end = 16.dp)
            .clip(CircleShape)
            .size(60.dp)

    )
}

@Composable
fun FavRowScroll() {
    Box(modifier = Modifier.padding(16.dp)) {
        Column {
            Text(
                text = "Избранное",
                fontSize = 20.sp,
                fontFamily = fontFamilyRoboto,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))

            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier.horizontalScroll(scrollState)
            ) {
                FavCard()
                FavCard()
                FavCard()
            }
        }

    }
}

@Composable
fun SessionsLazyScroll(viewModel: ViewModel) {
    Box(modifier = Modifier.padding(16.dp)) {
        Column {
            Text(
                text = "Сессии",
                fontSize = 20.sp,
                fontFamily = fontFamilyRoboto,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))

            LazyColumn {
                items(MockSessions)
                { session ->
                    SessionCard(
                        speaker = session.speaker,
                        timeInterval = session.timeInterval,
                        description = session.description,
                        imageUrl = session.imageUrl,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun FavCard() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(140.dp)
            .width(140.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.padding(5.dp)) {
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                TextTimeInterval(timeInterval = "18:00 - 19:00")
                TextDate(date = "19 апреля")
                TextSpeaker("Денис Неклюдов")
                TextDescription(
                    description = "Доклад: Камасутра с CameraX. Распознавание поз. И многое-многое-многое другое, чего вы не ожидали",
                    TextOverflow.Ellipsis,
                    3
                )
            }
        }
    }
}


val fontFamilyRoboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal)
)

@Composable
fun TextDescription(
    description: String,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLines: Int
) {
    Text(
        text = description,
        color = Color.Black,
        fontSize = 12.sp,
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Light,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextSpeaker(speaker: String) {
    Text(
        text = speaker,
        color = Color.Black,
        fontSize = 15.sp,
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun TextDate(date: String) {
    Text(
        text = date,
        color = Color.Black,
        fontSize = 12.sp,
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Light
    )
}

@Composable
fun TextTimeInterval(timeInterval: String) {
    Text(
        text = timeInterval,
        color = Color.Black,
        fontSize = 15.sp,
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Bold
    )
}



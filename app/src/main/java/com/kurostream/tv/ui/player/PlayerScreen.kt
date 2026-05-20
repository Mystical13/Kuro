package com.kurostream.tv.ui.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Text
import com.kurostream.tv.domain.model.StreamSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    animeId: String,
    episodeNumber: Int,
    onNavigateBack: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val streams by viewModel.streams.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var selectedSource by remember { mutableStateOf<StreamSource?>(null) }
    
    // Track play status
    var isPlaying by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }
    var bufferPercentage by remember { mutableStateOf(0) }
    
    // Controls Visibility
    var showControls by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).apply {
            // Memory efficient buffer of 8s to handle 1GB FireTV devices cleanly
            setLoadControl(
                androidx.media3.exoplayer.DefaultLoadControl.Builder()
                    .setBufferDurationsMs(8000, 16000, 1000, 2000)
                    .build()
            )
        }.build()
    }

    LaunchedEffect(animeId, episodeNumber) {
        viewModel.loadStreams(animeId, episodeNumber)
    }

    // Auto-select first stream if none is selected
    LaunchedEffect(streams) {
        if (streams.isNotEmpty() && selectedSource == null) {
            selectedSource = streams.first()
        }
    }

    // Setup player source when selected source changes
    LaunchedEffect(selectedSource) {
        selectedSource?.let { source ->
            exoPlayer.stop()
            exoPlayer.clearMediaItems()
            val mediaItem = MediaItem.fromUri(source.url)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            isPlaying = true
        }
    }

    // Position & Duration tracking loop
    LaunchedEffect(exoPlayer, isPlaying) {
        while (isPlaying) {
            currentPosition = exoPlayer.currentPosition
            duration = exoPlayer.duration
            bufferPercentage = exoPlayer.bufferedPercentage
            delay(1000)
        }
    }

    // Auto-hide controls loop
    LaunchedEffect(showControls) {
        if (showControls) {
            delay(6000)
            showControls = false
        }
    }

    // Listener to update playback state on event
    val playerListener = remember {
        object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
            override fun onPlaybackStateChanged(playbackState: Int) {
                duration = exoPlayer.duration
            }
        }
    }

    DisposableEffect(exoPlayer) {
        exoPlayer.addListener(playerListener)
        onDispose {
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }

    BackHandler {
        if (showControls) {
            showControls = false
        } else {
            onNavigateBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { showControls = true }
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color(0xFFA66DFF))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Searching stream sources...", color = Color.White, fontSize = 16.sp)
                }
            }
        } else if (selectedSource == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No available stream sources found.", color = Color.Gray, fontSize = 18.sp)
            }
        } else {
            // Actual ExoPlayer video container
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        this.useController = false
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Dynamic Custom UI Control HUD over the video
            if (showControls) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xB3000000),
                                    Color.Transparent,
                                    Color(0xCC000000)
                                )
                            )
                        )
                ) {
                    // Header Bar (Back Button + Title Info)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 48.dp, vertical = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlayerIconButton(
                            icon = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            onClick = onNavigateBack
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Column {
                            Text(
                                text = "Episode $episodeNumber",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Source: ${selectedSource?.provider} (${selectedSource?.quality})",
                                fontSize = 14.sp,
                                color = Color(0xFFA66DFF)
                            )
                        }
                    }

                    // Bottom controls & progress
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 36.dp, start = 48.dp, end = 48.dp)
                    ) {
                        // Source Switcher Lane
                        if (streams.size > 1) {
                            Text(
                                text = "Stream Sources",
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            TvLazyRow(
                                contentPadding = PaddingValues(bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(streams) { source ->
                                    val isCurrent = source == selectedSource
                                    var isFocused by remember { mutableStateOf(false) }
                                    Box(
                                        modifier = Modifier
                                            .onFocusChanged { isFocused = it.isFocused }
                                            .border(
                                                width = 2.dp,
                                                color = if (isFocused) Color(0xFFA66DFF) else if (isCurrent) Color(0x99A66DFF) else Color.Transparent,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .background(
                                                color = if (isCurrent) Color(0x33A66DFF) else Color(0x1F222B),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .clickable { selectedSource = source }
                                            .padding(horizontal = 16.dp, vertical = 6.dp)
                                            .focusable()
                                    ) {
                                        Text(
                                            text = "[${source.quality}] ${source.provider}",
                                            color = if (isCurrent || isFocused) Color.White else Color.Gray,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // Progress timeline bar
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = formatTime(currentPosition),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .padding(horizontal = 12.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFF333333))
                            ) {
                                // Buffer range
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth((bufferPercentage / 100f).coerceIn(0f, 1f))
                                        .fillMaxHeight()
                                        .background(Color(0xFF555555))
                                )
                                // Active played position
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(if (duration > 0) (currentPosition.toFloat() / duration).coerceIn(0f, 1f) else 0f)
                                        .fillMaxHeight()
                                        .background(Color(0xFFA66DFF))
                                )
                            }
                            Text(
                                text = formatTime(duration),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // HUD action row (Replay10, Play/Pause, Forward10)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PlayerIconButton(
                                icon = Icons.Default.Replay10,
                                contentDescription = "Rewind 10s",
                                onClick = { exoPlayer.seekTo((exoPlayer.currentPosition - 10000).coerceAtLeast(0L)) }
                            )

                            Spacer(modifier = Modifier.width(32.dp))

                            PlayerIconButton(
                                icon = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                isPrimary = true,
                                onClick = {
                                    if (isPlaying) {
                                        exoPlayer.pause()
                                    } else {
                                        exoPlayer.play()
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(32.dp))

                            PlayerIconButton(
                                icon = Icons.Default.Forward10,
                                contentDescription = "Forward 10s",
                                onClick = { exoPlayer.seekTo((exoPlayer.currentPosition + 10000).coerceAtMost(exoPlayer.duration)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerIconButton(
    icon: ImageVector,
    contentDescription: String,
    isPrimary: Boolean = false,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .size(if (isPrimary) 64.dp else 48.dp)
            .clip(CircleShape)
            .background(
                if (isFocused) Color(0xFFA66DFF)
                else if (isPrimary) Color(0xFF2E1B4E)
                else Color(0x33000000)
            )
            .border(
                width = 2.dp,
                color = if (isFocused) Color.White else Color.Transparent,
                shape = CircleShape
            )
            .onFocusChanged { isFocused = it.isFocused }
            .clickable(onClick = onClick)
            .focusable(),
        contentAlignment = Alignment.Center
    ) {
        androidx.tv.material3.Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isFocused) Color.Black else Color.White,
            modifier = Modifier.size(if (isPrimary) 32.dp else 24.dp)
        )
    }
}

private fun formatTime(ms: Long): String {
    if (ms <= 0L) return "00:00"
    val totalSecs = ms / 1000
    val hours = totalSecs / 3600
    val minutes = (totalSecs % 3600) / 60
    val seconds = totalSecs % 60
    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFA66DFF)
) {
    val transition = rememberInfiniteTransition(label = "loading")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes { durationMillis = 1000 },
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )
    Canvas(modifier = modifier.size(40.dp)) {
        drawArc(
            color = color,
            startAngle = angle,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

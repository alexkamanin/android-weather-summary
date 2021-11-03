package ru.shurick.enterprise.summary.base.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween

const val ANIMATION_DURATION = 400

@OptIn(ExperimentalAnimationApi::class)
val enterFadeIn = fadeIn(animationSpec = TweenSpec(durationMillis = ANIMATION_DURATION))

@OptIn(ExperimentalAnimationApi::class)
val enterExpand = expandVertically(animationSpec = tween(ANIMATION_DURATION))

@OptIn(ExperimentalAnimationApi::class)
val exitFadeOut = fadeOut(animationSpec = TweenSpec(durationMillis = ANIMATION_DURATION))

@OptIn(ExperimentalAnimationApi::class)
val exitCollapse = shrinkVertically(animationSpec = tween(ANIMATION_DURATION))
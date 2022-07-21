package com.hackbyte.recibot.navigation

import com.ramcosta.composedestinations.annotation.NavGraph


@AuthenticationNavGraph
@NavGraph
annotation class AppNavGraph(val start: Boolean = false)

@NavGraph
annotation class BottomNavGraph(val start: Boolean = false)


@NavGraph
annotation class AuthenticationNavGraph(val start: Boolean = false)
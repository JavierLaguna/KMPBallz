package dev.jlaguna.kmpballz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
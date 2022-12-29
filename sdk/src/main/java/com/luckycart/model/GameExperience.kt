package com.luckycart.model

data class GameExperience(
    val experienceId: String,
    val experienceUrl: String,
    val expiresAt: String,
    val generatedAt: String,
    val images: Images,
    val operationId: String
)
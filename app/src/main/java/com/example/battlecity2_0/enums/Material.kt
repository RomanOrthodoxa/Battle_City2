package com.example.battlecity2_0.enums

enum class Material(val tankCanGoThrough:Boolean) {
    EMPTY(true),
    BRICK(false),
    CONCRETE(false),
    GRASS(true)
}
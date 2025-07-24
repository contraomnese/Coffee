package com.contraomnese.coffee.core.ui.utils

import androidx.annotation.DrawableRes
import com.contraomnese.coffee.design.R

sealed class CoffeePlaceholder {

    @get:DrawableRes
    abstract val small: Int

    @get:DrawableRes
    abstract val medium: Int

    @get:DrawableRes
    abstract val large: Int

    data class Espresso(
        override val small: Int = R.drawable.espresso_small,
        override val medium: Int = R.drawable.espresso_medium,
        override val large: Int = R.drawable.espresso_large,
    ): CoffeePlaceholder()

    data class Cappuccino(
        override val small: Int = R.drawable.cappuccino_small,
        override val medium: Int = R.drawable.cappuccino_medium,
        override val large: Int = R.drawable.cappuccino_large,
    ): CoffeePlaceholder()

    data class Chocolate(
        override val small: Int = R.drawable.chocolate_small,
        override val medium: Int = R.drawable.chocolate_medium,
        override val large: Int = R.drawable.cappuccino_large,
    ): CoffeePlaceholder()

    data class Latte(
        override val small: Int = R.drawable.latte_small,
        override val medium: Int = R.drawable.latte_medium,
        override val large: Int = R.drawable.latte_large,
    ): CoffeePlaceholder()

    data class Default(
        override val small: Int = R.drawable.latte_small,
        override val medium: Int = R.drawable.latte_medium,
        override val large: Int = R.drawable.latte_large,
    ): CoffeePlaceholder()

    companion object {
        fun getRandomPlaceholder(): CoffeePlaceholder {
            val placeholders = listOf(
                Espresso(),
                Cappuccino(),
                Chocolate(),
                Latte()
            )
            return placeholders.random()
        }
    }
}

fun getPlaceholderByWidth(placeholder: CoffeePlaceholder, screenWidth: Int): Int {

    return when {
        screenWidth < 360 -> placeholder.small
        screenWidth in 360..600 -> placeholder.medium
        else -> placeholder.large
    }
}
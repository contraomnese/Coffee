package com.contraomnese.coffee.map.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.caverock.androidsvg.SVG
import com.yandex.runtime.image.ImageProvider
import kotlin.math.abs


class MapMarkerImageProvider(
    private val context: Context,
    private val label: String = "Location",
    private val backgroundColor: Int,
) : ImageProvider() {

    override fun getId(): String = "marker_with_icon_$label"

    override fun getImage(): Bitmap {
        val metrics = DisplayMetrics()
        val wm = ContextCompat.getSystemService(context, WindowManager::class.java)!!
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getMetrics(metrics)

        val density = metrics.density

        val textPaint = Paint().apply {
            textSize = FONT_SIZE * density
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
            isAntiAlias = true
            color = backgroundColor
        }

        val textHeight = abs(textPaint.fontMetrics.top) + abs(textPaint.fontMetrics.bottom)
        val iconSize = 36 * density

        // Общие размеры
        val radius = (iconSize / 2 + 8 * density)
        val width = (radius * 2).toInt()
        val height = (radius * 2 + textHeight + 8 * density).toInt()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Нарисовать внешний круг
        val strokePaint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
        }
        canvas.drawCircle(width / 2f, radius, radius, strokePaint)

        // Загрузить и отрисовать SVG по центру
        val svg = SVG.getFromAsset(context.assets, "coffee.svg")
        val iconLeft = (width - iconSize) / 2
        val iconTop = radius - iconSize / 2

        svg.setDocumentWidth(iconSize)
        svg.setDocumentHeight(iconSize)
        canvas.save()
        canvas.translate(iconLeft, iconTop)
        svg.renderToCanvas(canvas)
        canvas.restore()

        // Нарисовать текст под иконкой
        canvas.drawText(
            label,
            width / 2f,
            height - 4 * density,
            textPaint
        )

        return bitmap
    }

    companion object {
        private const val FONT_SIZE = 14f
    }
}
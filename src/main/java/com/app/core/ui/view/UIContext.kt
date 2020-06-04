package com.app.core.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.content.res.AppCompatResources
import android.util.Base64
import android.util.DisplayMetrics
import android.util.StateSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.webkit.URLUtil
import com.app.core.log.Logger
import java.io.UnsupportedEncodingException
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM: String = "AES"

@ColorInt
fun <T : Context> T.themeColor(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}

fun <T : Context> T.font(path: String?): Typeface? {
    if (!path.isNullOrBlank()) {
        return UIFont.load(assets, path)
    }
    return null
}

fun <T : Context> T.density(): Float {
    return resources.displayMetrics.density
}

@Px
fun <T : Context> T.width(): Int {
    return resources.displayMetrics.widthPixels
}

@Px
fun <T : Context> T.height(): Int {
    return resources.displayMetrics.heightPixels
}

@Px
fun <T : Context> T.realheight(): Int {
    if (this is Activity) {
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.defaultDisplay.getRealMetrics(metrics)
        }
        return metrics.heightPixels
    } else {
        return resources.displayMetrics.heightPixels
    }
}

@Px
fun <T : Context> T.realWidth(): Int {
    if (this is Activity) {
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.defaultDisplay.getRealMetrics(metrics)
        }
        return metrics.widthPixels
    } else {
        return resources.displayMetrics.widthPixels
    }
}

fun <T : Context> T.isNavigationBarShown(): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
    return id > 0 && resources.getBoolean(id);
}

@Px
fun <T : Context> T.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

@Px
fun <T : Context> T.dip(dp: Float): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

@Px
fun <T : Context> T.sip(sp: Float): Int {
    return (sp * resources.displayMetrics.scaledDensity).toInt()
}

fun <T : Context> T.pid(px: Int): Float {
    return (px.toFloat() / resources.displayMetrics.density)
}

fun <T : Context> T.pis(px: Int): Float {
    return (px.toFloat() / resources.displayMetrics.scaledDensity)
}

@Px
fun <T : Context> T.dimen(@DimenRes rDimen: Int): Int {
    return try {
        resources.getDimensionPixelSize(rDimen)
    } catch (e: Resources.NotFoundException) {
        0
    }
}

fun <T : Context> T.dimenFloat(@DimenRes dimenFloat: Int): Float {
    val outValue = TypedValue()
    resources.getValue(dimenFloat, outValue, true)
    return outValue.float
}

fun <T : Context> T.drawable(@DrawableRes rDrawable: Int): Drawable? {
    return AppCompatResources.getDrawable(this, rDrawable)
}

fun <T : Context> T.transparent(): Drawable {
    return ColorDrawable(Color.TRANSPARENT)
}

@ColorRes
fun <T : Context> T.transparentRes(): Int {
    return android.R.color.transparent
}

fun <T : Context> T.colorDrawable(@ColorRes rColor: Int): Drawable {
    return ColorDrawable(color(rColor))
}

@ColorInt
fun <T : Context> T.color(value: String?): Int? {
    return value?.let {
        val colorParsed = if (it.contains("#")) it else "#$it"
        try {
            Color.parseColor(colorParsed)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

@ColorInt
fun <T : Context> T.color(@ColorRes rColor: Int): Int {
    return ContextCompat.getColor(this, rColor)
}

@ColorInt
fun <T : Context> T.colorInt(@ColorRes rColor: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(resources, rColor, theme)
}

fun <T : Context> T.text(@StringRes resText: Int): String {
    return try {
        val sequence: CharSequence = resources.getText(resText)
        sequence.toString()
    } catch (e: Resources.NotFoundException) {
        ""
    }
}

fun <T : Context> T.circle(@ColorRes rColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.setColor(color(rColor))
    shape.setStroke(border, color(rBorderColor))
    return shape
}

fun <T : Context> T.circle(
    color: String? = "",
    border: Int = 0,
    borderColor: String? = ""
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.setColor(color(color) ?: android.R.color.transparent)
    shape.setStroke(border,color(borderColor) ?: android.R.color.transparent)
    return shape
}


fun <T : Context> T.circle(@ColorRes rColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        circle(rColor, border, rSelectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, circle(rColor, border, rBorderColor))
    return out
}

fun <T : Context> T.circle(@ColorRes rColor: Int, @ColorRes rSelectedColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        circle(rSelectedColor, border, rSelectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, circle(rColor, border, rBorderColor))
    return out
}

fun <T : Context> T.circle(
    color: String? = "",
    border: Int = 0,
    borderColor: String? = "",
    selectedBorderColor: String? = ""
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        circle(color, border, selectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, circle(color, border, borderColor))
    return out
}

fun <T : Context> T.circle(
    color: String? = "",
    selectedColor: String? = "",
    border: Int = 0,
    borderColor: String? = "",
    selectedBorderColor: String? = ""
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        circle(selectedColor, border, selectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, circle(color, border, borderColor))
    return out
}

fun <T : Context> T.circleGradient(@ColorRes startColor: Int, @ColorRes endColor: Int, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP): Drawable {
    val shape = GradientDrawable(orientation, intArrayOf(colorInt(startColor), colorInt(endColor)))
    shape.shape = GradientDrawable.OVAL
    shape.gradientType = GradientDrawable.LINEAR_GRADIENT
    shape.gradientRadius = 90f
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.circleGradient(
    rStartColor: String? = "",
    rEndColor: String? = "",
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP
): Drawable {
    val shape = GradientDrawable(
        orientation,
        intArrayOf(
            color(rStartColor) ?: colorInt(android.R.color.transparent),
            color(rEndColor) ?: colorInt(android.R.color.transparent)
        )
    )
    shape.shape = GradientDrawable.OVAL
    shape.gradientType = GradientDrawable.LINEAR_GRADIENT
    shape.gradientRadius = 90f
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.circleShadow(startShadowColor: String? = "", endShadowColor: String? = "", color: String? = "", selectedColor: String? = "" , elevation: Float = 0f, border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""): Drawable {
    val out = StateListDrawable()
    val item = LayerDrawable(arrayOf(circleGradient(rStartColor= startShadowColor, rEndColor = endShadowColor), circle(color, border = border, borderColor = borderColor)))
    item.setLayerInset(0, dip(elevation), dip(elevation), 0, 0)
    item.setLayerInset(1, 0, 0, dip(elevation), dip(elevation))
    val itemSelect = LayerDrawable(arrayOf(circleGradient(rStartColor= startShadowColor, rEndColor = endShadowColor), circle(selectedColor, border = border, borderColor = selectedBorderColor)))
    itemSelect.setLayerInset(0, dip(elevation), dip(elevation), 0, 0)
    itemSelect.setLayerInset(1, 0, 0, dip(elevation), dip(elevation))
    out.addState(intArrayOf(android.R.attr.state_pressed),itemSelect)
    out.addState(StateSet.WILD_CARD, item)
    return out
}


fun <T : Context> T.squareGradient(
    @ColorRes rStartColor: Int, @ColorRes rEndColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP
): Drawable {
    val shape =
        GradientDrawable(orientation, intArrayOf(colorInt(rStartColor), colorInt(rEndColor)))
    shape.gradientType = GradientDrawable.LINEAR_GRADIENT
    shape.gradientRadius = 90f
    shape.cornerRadius = radius
    shape.setStroke(border, color(rBorderColor))
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.squareGradient(
    rStartColor: String? = "", rEndColor: String? = "", radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP, angle: Float = 90f
): Drawable {
    val shape = GradientDrawable(
        orientation,
        intArrayOf(
            color(rStartColor) ?: colorInt(android.R.color.transparent),
            color(rEndColor) ?: colorInt(android.R.color.transparent)
        )
    )
    shape.gradientType = GradientDrawable.LINEAR_GRADIENT
    shape.gradientRadius = angle
    shape.cornerRadius = radius
    shape.setStroke(border, color(rBorderColor))
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.square(
    color: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = ""
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setColor(color(color) ?: android.R.color.transparent)
    shape.cornerRadius = radius
    shape.setStroke(border, color(borderColor) ?: android.R.color.transparent)
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.square(
    color: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        square(color, radius, border, selectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, square(color, radius, border, borderColor))
    return out
}
fun <T : Context> T.square(
    color: String? = "", selectedColor: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        square(selectedColor, radius, border, selectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, square(color, radius, border, borderColor))
    return out
}

fun <T : Context> T.square(
    @ColorRes rColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setColor(color(rColor))
    shape.cornerRadius = radius
    shape.setStroke(border, color(rBorderColor))
    shape.setBounds(0, 0, 0, 0)
    return shape
}

fun <T : Context> T.squareShadow(startShadowColor: String? = "", endShadowColor: String? = "", color: String? = "", selectedColor: String? = "" ,radius: Float = 0f, elevation: Float = 0f): Drawable {
    val out = StateListDrawable()
    val item = LayerDrawable(arrayOf(squareGradient(rStartColor= startShadowColor, rEndColor = endShadowColor, radius = dip(radius).toFloat()), square(color, radius = dip(radius).toFloat())))
    item.setLayerInset(0, dip(elevation), dip(elevation), 0, 0)
    item.setLayerInset(1, 0, 0, dip(elevation), dip(elevation))
    val itemSelect = LayerDrawable(arrayOf(squareGradient(rStartColor= startShadowColor, rEndColor = endShadowColor, radius = dip(radius).toFloat()), square(selectedColor, radius = dip(radius).toFloat())))
    itemSelect.setLayerInset(0, dip(elevation), dip(elevation), 0, 0)
    itemSelect.setLayerInset(1, 0, 0, dip(elevation), dip(elevation))
    out.addState(intArrayOf(android.R.attr.state_pressed),itemSelect)
    out.addState(StateSet.WILD_CARD, item)
    return out
}

fun <T : Context> T.square(
    @ColorRes rColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        square(rColor, radius, border, rSelectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, square(rColor, radius, border, rBorderColor))
    return out
}


fun <T : Context> T.square(
    @ColorRes rColor: Int, @ColorRes rSelectedColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent
): StateListDrawable {
    val out = StateListDrawable()
    out.addState(
        intArrayOf(android.R.attr.state_pressed),
        square(rSelectedColor, radius, border, rSelectedBorderColor)
    )
    out.addState(StateSet.WILD_CARD, square(rColor, radius, border, rBorderColor))
    return out
}

fun <T : Context> T.circleImage(
    bitmap: Bitmap,
    size: Int, @ColorRes rColor: Int = android.R.color.transparent
): Bitmap {
    val result: Bitmap = if (bitmap.width != size || bitmap.height != size) {
        Bitmap.createScaledBitmap(bitmap, size, size, false)
    } else {
        bitmap
    }
    val out = Bitmap.createBitmap(result.width, result.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(out)
    val paint = Paint()
    val rect = Rect(0, 0, result.width, result.height)
    paint.isAntiAlias = true
    paint.isFilterBitmap = true
    paint.isDither = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color(rColor)
    canvas.drawCircle(
        result.width / 2 + 0.7f,
        result.height / 2 + 0.7f,
        result.height / 2 + 0.1f,
        paint
    )
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(result, rect, rect, paint)

    return out
}

fun <T : Context> T.circleWithText(
    size: Int, @ColorRes rCircleColor: Int,
    text: String,
    sizeText: Int, @ColorRes rColorText: Int = android.R.color.white,
    typeface: Typeface = Typeface.SANS_SERIF
): Drawable {
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color(android.R.color.transparent))

    val pCircle = Paint()
    pCircle.style = Paint.Style.FILL
    pCircle.color = color(rCircleColor)
    pCircle.isAntiAlias = true

    val radius = Math.min(canvas.width, canvas.height) / 2
    val pad = 0
    canvas.drawCircle(
        (canvas.width / 2).toFloat(),
        (canvas.height / 2).toFloat(),
        (radius - pad).toFloat(),
        pCircle
    )

    val pText = Paint(Paint.ANTI_ALIAS_FLAG)
    pText.typeface = typeface
    pText.color = color(rColorText)
    pText.textSize = sizeText.toFloat()

    val bound = Rect()
    pText.getTextBounds(text, 0, text.length, bound)
    val x = ((bitmap.width - bound.width()) / 2).toFloat()
    val y = ((bitmap.height + bound.height()) / 2).toFloat()

    canvas.drawText(text, x, y, pText)

    return BitmapDrawable(resources, bitmap)
}

fun <T : Context> T.squareWithText(
    @ColorRes rCircleColor: Int,
    text: String,
    sizeText: Int, @ColorRes rColorText: Int = android.R.color.white,
    typeface: Typeface = Typeface.SANS_SERIF
): Drawable {
    val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color(android.R.color.transparent))

    val pCircle = Paint()
    pCircle.style = Paint.Style.FILL
    pCircle.color = color(rCircleColor)
    pCircle.isAntiAlias = true

    canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), pCircle)

    val pText = Paint(Paint.ANTI_ALIAS_FLAG)
    pText.typeface = typeface
    pText.color = color(rColorText)
    pText.textSize = sizeText.toFloat()

    val bound = Rect()
    pText.getTextBounds(text, 0, text.length, bound)
    val x = ((canvas.width - bound.width()) / 2).toFloat()
    val y = ((canvas.height + bound.height()) / 2).toFloat()

    canvas.drawText(text, x, y, pText)

    return BitmapDrawable(resources, bitmap)
}


fun <T : Context> T.line(
    width: Int,
    height: Int,
    borderWidthInPixel: Int = 0, @ColorRes rColor: Int = android.R.color.transparent, @ColorRes rColorBorder: Int = android.R.color.white
): Drawable {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color(rColor))

    val pLine = Paint()
    pLine.style = Paint.Style.STROKE
    pLine.strokeWidth = borderWidthInPixel.toFloat()
    pLine.color = color(rColorBorder)
    pLine.isAntiAlias = false

    canvas.drawLine(0f, 0f, width.toFloat(), 0f, pLine)

    return BitmapDrawable(resources, bitmap)
}

fun <T : Context> T.squareDualSide(
    width: Int,
    height: Int,
    borderWidthInPixel: Int = 0, @ColorRes rColor: Int = android.R.color.transparent, @ColorRes rColorBorder: Int = android.R.color.white
): Drawable {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color(rColor))

    val pLine = Paint()
    pLine.style = Paint.Style.STROKE
    pLine.strokeWidth = borderWidthInPixel.toFloat()
    pLine.color = color(rColorBorder)
    pLine.isAntiAlias = false

    canvas.drawLine(0f, 0f, 0f, height.toFloat(), pLine)
    canvas.drawLine(
        (width - borderWidthInPixel).toFloat(),
        0f,
        (width - borderWidthInPixel).toFloat(),
        height.toFloat(),
        pLine
    )

    return BitmapDrawable(resources, bitmap)
}

@MainThread
fun <T : Context> T.startBrowser(url: String?) {
    if (!url.isNullOrBlank()) {
        var newUrl = url.trim { it <= ' ' }
        if (!newUrl.toLowerCase().contains("http")) {
            newUrl = "http://$newUrl"
        }
        if (URLUtil.isHttpUrl(newUrl) || URLUtil.isHttpsUrl(newUrl)) {
            val browser = Intent(Intent.ACTION_VIEW, Uri.parse(newUrl))
            browser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(browser)
        }
    }
}

@MainThread
fun <T : Context> T.rateApp() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (ex: android.content.ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }

}

@MainThread
fun <T : Context> T.sendMail(mail: String, subject: String?, message: String?) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.type = "message/rfc822"
    intent.data = Uri.parse("mailto:$mail")
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

@MainThread
fun <T : Context> T.callNumber(phoneNum: String?) {
    val uri = "tel:" + phoneNum?.trim { it <= ' ' }
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse(uri)
    startActivity(intent)
}

@MainThread
fun <T : Context> T.getInflater(): LayoutInflater? {
    return getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
}

@MainThread
fun <T : Context> T.getDeviceName(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        Settings.Global.getString(contentResolver, "device_name")
    } else {
        Build.MODEL
    }
}

@MainThread
fun <T : Context> T.getDeviceModel(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.startsWith(manufacturer)) {
        model
    } else {
        "$manufacturer $model"
    }
}

@MainThread
fun <T : Context> T.getAppVersion(): String {
    try {
        val info = packageManager.getPackageInfo(packageName, 0)
        return info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        return ""
    }

}

@SuppressLint("HardwareIds")
@MainThread
fun <T : Context> T.getDeviceId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun <T : Context> T.isPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

fun Double.round(places: Int): Double {
    if (places < 0) throw IllegalArgumentException()

    var bd = BigDecimal(this)
    bd = bd.setScale(places, RoundingMode.HALF_UP)
    return bd.toDouble()
}

fun Double.calculateRoundingPlaces(): Double {
    var digit = 2.0
    if (this >= -1 && this <= 1) {
        digit = calculateDigit()
    }
    return round(digit.toInt())
}

fun Double.calculateRounding(): String {
    var digit = 2.0

    if (this >= -1 && this <= 1) {
        digit = calculateDigit()
    }
    val pattern = StringBuilder(if (this < 1) "0." else "#.")
    var i = 0
    while (i < digit) {
        pattern.append(if (i == 0) "0" else "#")
        i++
    }
    val format = DecimalFormat.getInstance(Locale.UK) as DecimalFormat
    format.applyPattern(pattern.toString())
    return format.format(this)
}

fun Double.calculateDigit(): Double {
    return if (this == 0.0) {
        0.0
    } else {
        Math.ceil(-Math.log10(Math.abs(this)) + 3)
    }
}

fun String.generateKey(): SecretKey {
    return SecretKeySpec(toByteArray(), ALGORITHM)
}

@SuppressLint("GetInstance")
fun SecretKey.encryptMsg(value: String?): String {
    val cipher: Cipher
    try {
        cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, this)
        val encryptedByteValue = cipher.doFinal(value?.toByteArray(charset("utf-8")))
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
    } catch (e: NoSuchAlgorithmException) {
        Logger.w(e.message)
    } catch (e: NoSuchPaddingException) {
        Logger.w(e.message)
    } catch (e: InvalidKeyException) {
        Logger.w(e.message)
    } catch (e: BadPaddingException) {
        Logger.w(e.message)
    } catch (e: IllegalBlockSizeException) {
        Logger.w(e.message)
    } catch (e: UnsupportedEncodingException) {
        Logger.w(e.message)
    }

    return ""
}

@SuppressLint("GetInstance")
fun SecretKey.decryptMsg(value: String?): String {
    val cipher: Cipher
    try {
        cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, this)
        val decryptedValue64 = Base64.decode(value, Base64.DEFAULT)
        val decryptedByteValue = cipher.doFinal(decryptedValue64)
        return String(decryptedByteValue, Charset.forName("UTF-8"))
    } catch (e: NoSuchAlgorithmException) {
        Logger.w(e.message)
    } catch (e: InvalidKeyException) {
        Logger.w(e.message)
    } catch (e: NoSuchPaddingException) {
        Logger.w(e.message)
    } catch (e: BadPaddingException) {
        Logger.w(e.message)
    } catch (e: UnsupportedEncodingException) {
        Logger.w(e.message)
    } catch (e: IllegalBlockSizeException) {
        Logger.w(e.message)
    }
    return ""
}

fun Context.isS8Phone(): Boolean {
    val s = if (isPortrait()) height() else width()
    val h = if (isPortrait()) width() else height()
    //Trigger for s8 plus and S8
    return s == 1396 && h == 720 || s == 2094 && h == 1080 || s == 2792 && h == 1440
}

fun <T : Context> T.getRatio(): Float {
    val metrics = resources.displayMetrics
    return metrics.heightPixels.toFloat() / metrics.widthPixels.toFloat()
}

fun osVersion(): String {
    return Build.VERSION.RELEASE
}

fun loop(block: () -> Unit, period: Long): Timer {
    val timer = Timer()
    val timerTask = object : TimerTask() {
        override fun run() {
            block()
        }
    }
    timer.scheduleAtFixedRate(timerTask, 0, period)
    return timer
}

fun Context.calculateColumn(columnWidth: Float): Int {
    return (width() / columnWidth + 0.5).toInt()
}




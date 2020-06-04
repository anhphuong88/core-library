package com.app.core.ui.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.app.core.ui.layout.Wrap
import com.app.core.ui.layout.collapseUI
import com.app.core.ui.layout.create
import com.app.core.ui.layout.ui
import com.app.core.ui.view.color
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView

fun imageClearCache(url: String?) {
    url?.let {
        val imagePipeline = Fresco.getImagePipeline()
        val uri = Uri.parse(url)
        if (uri != null) {
            if (imagePipeline.isInBitmapMemoryCache(uri)) {
                imagePipeline.evictFromCache(uri)
            }
            if (imagePipeline.isInDiskCacheSync(uri)) {
                imagePipeline.evictFromDiskCache(uri)
            }
        }
    }
}

fun imageUri(resId: Int): Uri {
    return Uri.Builder()
        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
        .path(resId.toString())
        .build()
}

fun GenericDraweeHierarchyBuilder.configCircle(context: Context?) {
    val color = context?.color(android.R.color.transparent)
    val roundingParams = RoundingParams.fromCornersRadius(5f)
    color?.let { roundingParams.setBorder(it, 0.0f) }
    roundingParams.roundAsCircle = true
    desiredAspectRatio = 1f
    this.roundingParams = roundingParams
}

fun GenericDraweeHierarchyBuilder.configPlaceHolder(drawable: Drawable): GenericDraweeHierarchyBuilder {
    placeholderImage = drawable
    return this
}

fun Context.imageBuilder(
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0
): GenericDraweeHierarchyBuilder {
    val builder = GenericDraweeHierarchyBuilder.newInstance(resources)
        .setActualImageScaleType(scaleType)
        .setFailureImageScaleType(scaleType)
        //setBackground(UIContext.transparent(context))
        .setPlaceholderImageScaleType(scaleType)
    return if (resDrawableHolder > 0) builder.setPlaceholderImage(resDrawableHolder) else builder
}

fun <T: ViewGroup> T.cImage(
    action: SimpleDraweeView.(ViewGroup.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return create({SimpleDraweeView(context)}, {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun RelativeLayout.cImage(
    action: SimpleDraweeView.(RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return create(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun LinearLayout.cImage(
    action: SimpleDraweeView.(LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return create(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun FrameLayout.cImage(
    action: SimpleDraweeView.(FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return create(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun <T: ViewGroup> T.image(
    action: SimpleDraweeView.(ViewGroup.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui({SimpleDraweeView(this)}, {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun RelativeLayout.image(
    action: SimpleDraweeView.(RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun LinearLayout.image(
    action: SimpleDraweeView.(LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun FrameLayout.image(
    action: SimpleDraweeView.(FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        action(this, it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun ConstraintLayout.image(
    action: SimpleDraweeView.(ConstraintLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun CoordinatorLayout.image(
    action: SimpleDraweeView.(CoordinatorLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun CollapsingToolbarLayout.image(
    action: SimpleDraweeView.(CollapsingToolbarLayout.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return collapseUI(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

fun RecyclerView.image(
    action: SimpleDraweeView.(RecyclerView.LayoutParams, GenericDraweeHierarchyBuilder) -> Unit,
    scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.FOCUS_CROP,
    resDrawableHolder: Int = 0,
    width: Int = Wrap,
    height: Int = Wrap
): SimpleDraweeView {
    return ui(SimpleDraweeView(context), {
        val builder = context.imageBuilder(scaleType, resDrawableHolder)
        this.action(it, builder)
        hierarchy = builder.build()
    }, width, height)
}

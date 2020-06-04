package com.app.core.ui.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.core.action.Action3;
import com.app.core.ui.view.UIContext;
import com.app.core.ui.view.UIView;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

public final class UIImageView {

    public static void clearCache(@NonNull String url) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        if (uri != null) {
            if (imagePipeline.isInBitmapMemoryCache(uri)) {
                imagePipeline.evictFromCache(uri);
            }
        }
    }

    public static Uri getImageUri(@NonNull Context context, int resId) {
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(resId))
                .build();
    }

    public static void download(@NonNull Context context, @NonNull SimpleDraweeView imageView, String url) {
        ImageRetry.download(context, imageView, url);
    }

    public static void resizeDownload(@NonNull Context context, @NonNull SimpleDraweeView imageView, String url, int w, int h) {
        ImageRetry.resizeImageDownload(context, imageView, url, w, h);
    }

    private static GenericDraweeHierarchyBuilder builder(@NonNull Context context, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder) {
        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setActualImageScaleType(scaleType)
                .setFailureImageScaleType(scaleType)
//                .setBackground(UIContext.transparent(context))
                .setPlaceholderImageScaleType(scaleType);
        return resDrawableHolder > 0 ? builder.setPlaceholderImage(resDrawableHolder) : builder;
    }

    public static void configPlaceHolder(@NonNull GenericDraweeHierarchyBuilder builder, @NonNull Drawable drawable) {
        builder.setPlaceholderImage(drawable);
    }

    public static void configCircle(@NonNull GenericDraweeHierarchyBuilder builder, @NonNull Context context) {
        int color = UIContext.resColor(context, android.R.color.transparent);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(color, 0.0f);
        roundingParams.setRoundAsCircle(true);
        builder.setDesiredAspectRatio(1f);
        builder.setRoundingParams(roundingParams);
    }

    public static SimpleDraweeView create(@NonNull RelativeLayout parent, Action3<SimpleDraweeView, RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.create(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView create(@NonNull RelativeLayout parent, Action3<SimpleDraweeView, RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return create(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }

    public static SimpleDraweeView create(@NonNull LinearLayout parent, Action3<SimpleDraweeView, LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.create(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView create(@NonNull LinearLayout parent, Action3<SimpleDraweeView, LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return create(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }

    public static SimpleDraweeView create(@NonNull FrameLayout parent, Action3<SimpleDraweeView, FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.create(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView create(@NonNull FrameLayout parent, Action3<SimpleDraweeView, FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return create(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }

    public static SimpleDraweeView add(@NonNull RelativeLayout parent, Action3<SimpleDraweeView, RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.add(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView add(@NonNull RelativeLayout parent, Action3<SimpleDraweeView, RelativeLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return add(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }

    public static SimpleDraweeView add(@NonNull LinearLayout parent, Action3<SimpleDraweeView, LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.add(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView add(@NonNull LinearLayout parent, Action3<SimpleDraweeView, LinearLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return add(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }

    public static SimpleDraweeView add(@NonNull FrameLayout parent, Action3<SimpleDraweeView, FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, @NonNull ScalingUtils.ScaleType scaleType, int resDrawableHolder, int width, int height) {
        return UIView.add(parent, new SimpleDraweeView(parent.getContext()), (image, param) -> {
            GenericDraweeHierarchyBuilder builder = builder(parent.getContext(), scaleType, resDrawableHolder);
            action.invoke(image, param, builder);
            image.setHierarchy(builder.build());
        }, width, height);
    }

    public static SimpleDraweeView add(@NonNull FrameLayout parent, Action3<SimpleDraweeView, FrameLayout.LayoutParams, GenericDraweeHierarchyBuilder> action, int resDrawableHolder, int width, int height) {
        return add(parent, action, ScalingUtils.ScaleType.FOCUS_CROP, resDrawableHolder, width, height);
    }
}

package com.app.core.ui.image;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.app.core.log.Logger;
import com.app.core.ui.view.UIContext;
import com.app.core.util.StringUtil;

import java.util.List;

/**
 * Created by PhuongNA2 on 9/8/2017.
 */

public final class ImageRetry {
    /* Spent for try download image success*/
    private Integer photosSuccess = 0;

    private static void resizeImageDownload(@NonNull Context context, @NonNull SimpleDraweeView imageView, BaseControllerListener<ImageInfo> listener, String url, int dimenW, int dimenH) {
        if (!StringUtil.empty(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
//                        .setRequestPriority(Priority.MEDIUM)
//                        .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                        .setLocalThumbnailPreviewsEnabled(true);
                if (dimenH > 0) {
                    int width;
                    if (dimenW > 0) {
                        width = UIContext.dimen(context, dimenW);
                    } else {
                        width = UIContext.screenWidth(context);
                    }
                    int height = UIContext.dimen(context, dimenH);
                    builder.setResizeOptions(new ResizeOptions(width, height));
                }
                ImageRequest request = builder.build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(imageView.getController())
                        .setControllerListener(listener)
                        .setAutoPlayAnimations(true)
                        .setImageRequest(request)
                        .build();
                imageView.setController(controller);
            }
        }
    }

    public static void resizeImageDownload(@NonNull Context context, @NonNull SimpleDraweeView imageView, String url, int dimenW, int dimenH) {
        if (!StringUtil.empty(url)) {
            BaseControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
                private int retry = 0;
                private int maxRetry = 10;

                @Override
                public void onFailure(String id, Throwable throwable) {
                    boolean isContinue = false;
                    Logger.w("onFailure: " + url + " " + throwable.getMessage());
                    if (retry < maxRetry && ((!StringUtil.empty(throwable.getMessage()) && (throwable.getMessage().contains("decod") || throwable.getMessage().contains("timeout") || throwable.getMessage().contains("corrupt"))) || throwable.getMessage() == null)) {
                        isContinue = true;
                        if (!StringUtil.empty(throwable.getMessage()) && throwable.getMessage().contains("decod")) {
                            maxRetry = 20;
                        }
                        retry++;
                    }
                    if (isContinue)
                        resizeImageDownload(context, imageView, this, url, dimenW, dimenH);
                }
            };
            resizeImageDownload(context, imageView, listener, url, dimenW, dimenH);
        }
    }

    public static void download(@NonNull Context context, @NonNull SimpleDraweeView imageView, String url) {
        resizeImageDownload(context, imageView, url, 0, 0);
    }

    private void resizeImageDownload(@NonNull Context context, @NonNull SimpleDraweeView imageView, int startPos, @NonNull BaseControllerListener<ImageInfo> listener, List<String> urls, int dimenW, int dimenH) {
        if (startPos < urls.size() && startPos < 4 && !StringUtil.empty(urls.get(startPos))) {
            Uri uri = Uri.parse(urls.get(startPos));
            if (uri != null) {
                ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
//                        .setRequestPriority(Priority.MEDIUM)
//                        .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                        .setLocalThumbnailPreviewsEnabled(true);
                if (dimenH > 0) {
                    int width;
                    if (dimenW > 0) {
                        width = UIContext.dimen(context, dimenW);
                    } else {
                        width = UIContext.screenWidth(context);
                    }
                    int height = UIContext.dimen(context, dimenH);
                    builder.setResizeOptions(new ResizeOptions(width, height));
                }
                ImageRequest request = builder.build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(imageView.getController())
                        .setControllerListener(listener)
                        .setAutoPlayAnimations(true)
                        .setImageRequest(request)
                        .build();
                imageView.setController(controller);
            }
        }
    }

    public void resizeImageDownload(@NonNull Context context, @NonNull SimpleDraweeView imageView, List<String> urls, int dimenW, int dimenH) {
        if (urls != null && urls.size() > 0 && photosSuccess < urls.size() && photosSuccess < 4) {
            BaseControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
                private int retry = 0;
                private int maxRetry = 10;

                @Override
                public void onFailure(String id, Throwable throwable) {
                    boolean isContinue = false;
                    Logger.w("onFailure: " + urls.size() + ", " + urls.get(photosSuccess) + " " + throwable.getMessage());
                    if (retry < maxRetry && ((!StringUtil.empty(throwable.getMessage()) && (throwable.getMessage().contains("decod") || throwable.getMessage().contains("timeout") || throwable.getMessage().contains("corrupt"))) || throwable.getMessage() == null)) {
                        isContinue = true;
                        if (!StringUtil.empty(throwable.getMessage()) && throwable.getMessage().contains("decod")) {
                            maxRetry = 20;
                        }
                        retry++;
                    }
                    if (!isContinue && photosSuccess < urls.size() - 1 && photosSuccess < 3) {
                        photosSuccess++;
                        isContinue = true;
                    }
                    if (isContinue)
                        resizeImageDownload(context, imageView, photosSuccess, this, urls, dimenW, dimenH);
                }
            };
            resizeImageDownload(context, imageView, photosSuccess, listener, urls, dimenW, dimenH);
        }
    }

    public void download(@NonNull Context context, @NonNull SimpleDraweeView imageView, List<String> urls) {
        resizeImageDownload(context, imageView, urls, 0, 0);
    }
}

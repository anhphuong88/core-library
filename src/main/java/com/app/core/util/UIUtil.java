package com.app.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;

import com.app.core.log.Logger;
import com.app.core.ui.view.UIContext;
import com.app.core.ui.view.UIView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class UIUtil {
    private static final String ALGORITHM = "AES";

    @MainThread
    public static void startBrowser(@NonNull Context context, @NonNull String url) {
        if (!StringUtil.emptyTrim(url)) {
            String newUrl = url.trim();
            if (!newUrl.toLowerCase().contains("http")) {
                newUrl = "http://" + newUrl;
            }
            if (URLUtil.isHttpUrl(newUrl) || URLUtil.isHttpsUrl(newUrl)) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(newUrl));
                context.startActivity(browser);
            }
        }
    }

    @MainThread
    public static void rateApp(@NonNull Context context) {
        String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException ex) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @MainThread
    public static void sendMail(@NonNull Context context, @NonNull String mail, @Nullable String subject, @Nullable String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:" + mail));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @MainThread
    public static void callNumber(@NonNull Context context, @NonNull String phoneNum) {
        String uri = "tel:" + phoneNum.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    @MainThread
    @Nullable
    public static LayoutInflater getInflater(@NonNull Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @MainThread
    public static String getDeviceName(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getString(context.getContentResolver(), "device_name");
        } else {
            return Build.MODEL;
        }
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    @MainThread
    public static String getAppVersion(@NonNull Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    @SuppressLint("HardwareIds")
    @MainThread
    public static String getDeviceId(@NonNull Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @MainThread
    public static void setCursorColor(@NonNull EditText view, @ColorRes int rColor) {
        try {
            int color = UIView.colorInt(view, rColor);
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                Drawable[] drawables = {drawable, drawable};

                // Set the drawables
                field = editor.getClass().getDeclaredField("mCursorDrawable");
                field.setAccessible(true);
                field.set(editor, drawables);
            }
        } catch (Exception ignored) {
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double calculateRoundingPlaces(double value) {
        double digit = 2;

        if (value >= -1 && value <= 1) {
            digit = calculateDigit(value);
        }

        return round(value, (int) digit);

//        double multiplier = Math.pow(10, digit);
//
//        return Math.round(value * multiplier) / multiplier;
    }

    public static String calculateRounding(double value) {
        double digit = 2;

        if (value >= -1 && value <= 1) {
            digit = calculateDigit(value);
        }
        StringBuilder pattern = new StringBuilder(value < 1 ? "0." : "#.");
        for (int i = 0; i < digit; i++) {
            pattern.append(i == 0 ? "0" : "#");
        }
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.UK);
        format.applyPattern(pattern.toString());
        return format.format(value);
    }

    private static double calculateDigit(double value) {
        if (value == 0) {
            return 0;
        } else {
            return Math.ceil(-Math.log10(Math.abs(value)) + 3);
        }
    }


    public static SecretKey generateKey(String password) {
        return new SecretKeySpec(password.getBytes(), ALGORITHM);
    }

    public static String encryptMsg(@NonNull String message, @NonNull SecretKey secret) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            byte[] encryptedByteValue = cipher.doFinal(message.getBytes("utf-8"));
            return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            Logger.w(e.getMessage());
        }
        return "";
    }

    public static String decryptMsg(@NonNull String cipherText, @NonNull SecretKey secret) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] decryptedValue64 = Base64.decode(cipherText, Base64.DEFAULT);
            byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
            return new String(decryptedByteValue, "utf-8");
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            Logger.w(e.getMessage());
        }
        return "";
    }

    public static boolean isPortrait(@NonNull Context context) {
        return UIContext.resources(context).getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isS8Phone(@NonNull Context context) {
        int s = isPortrait(context) ? UIContext.screenHeight(context) : UIContext.screenWidth(context);
        int h = isPortrait(context) ? UIContext.screenWidth(context) : UIContext.screenHeight(context);
        //Trigger for s8 plus and S8
        return ((s == 1396 && h == 720) || (s == 2094 && h == 1080) || (s == 2792 && h == 1440));
    }

    public static float getRatio(@NonNull Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return ((float) metrics.heightPixels / (float) metrics.widthPixels);
    }

    public static void loop(@NonNull Runnable run, long delay) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                run.run();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, delay);
    }

    public static String decodeBase64(@NonNull String base64) {
        // Receiving side
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        try {
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

package com.app.core.constant;

public class DefaultConstants {
    public interface TabChange {
        String MESSAGE_KEY = "NavigationBaseTabChange";
    }

    public interface ActionList {
        String UPDATE = "update";
        String UPDATE_ITEM = "update_item";
        String REMOVELAST = "removeLast";
        String RESET = "reset";
        String RELOAD = "reload";
    }

    public interface ActionListener {
        String POP_FRAGMENT = "pop_fragment";
        String LOADING = "loading";
        String KEYBOARD = "keyboardChange";
        String KEYBOARD_CLOSE = "close_keyboard";
        String KEYBOARD_CLOSE_NONE_CLEAR = "close_keyboard_none_clear";
    }

    public interface Preference {
        String KEY = "APP";
    }

    public interface RequestCode {
        int SHARED_SOCIAL = 2000;
        int RESULT_GOOGLE_SIGN_IN = 9001;
        int RESULT_PHONE = 99;
        int RESULT_LOGIN_UPDATE = 100;
        int RESULT_PROFILE = 101;
        int LOCATION = 1001;
        int LOCATION_UPDATE = 1002;
        int CHOOSE_IMAGE = 1003;
        int READ_CONTACTS = 1004;
        int READ_EXTERNAL_STORAGE = 10033;
        int SEND_SMS = 1005;
        int READ_CAMERA = 1006;
        int CHAT_CHOOSE_IMAGE = 1007;
        int CHAT_TAKE_PICTURE = 1008;
        int WRITE_EXTERNAL = 1009;
        int NOTIFICATION = 1010;
        int READ_CALENDAR = 1011;
        int WRITE_CALENDAR = 1012;
        int REMINDER = 1013;
        int TIME_OUT = 401;
    }
}

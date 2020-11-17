package com.jrk.mood4food;

import android.content.Context;

public class App extends android.app.Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}

package com.example.uberclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("etO5xuTEtrDZzAm7FnhWoeph7m2wTJ40FZYGCTfY")
                // if defined
                .clientKey("vrupi1MSPEzKoe7vFhlawOwrhj6nRu3dqiv6qoyv")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

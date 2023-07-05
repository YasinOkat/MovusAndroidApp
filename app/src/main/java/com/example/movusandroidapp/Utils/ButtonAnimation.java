package com.example.movusandroidapp.Utils;

import android.view.View;

public class ButtonAnimation {

    public static void animateButton(View button) {
        button.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    button.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start();
                })
                .start();

        button.animate()
                .alpha(0.5f)
                .setDuration(200)
                .withEndAction(() -> {
                    button.animate()
                            .alpha(1f)
                            .setDuration(200)
                            .start();
                })
                .start();
    }
}

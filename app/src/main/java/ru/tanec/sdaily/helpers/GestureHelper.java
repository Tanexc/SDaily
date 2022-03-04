package ru.tanec.sdaily.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GestureHelper implements OnTouchListener {
    private final GestureDetector gestureDetector;

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(@NotNull View v, @NotNull MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    public GestureHelper(@NotNull Context context) {
        this.gestureDetector = new GestureDetector(context, new GestureHelper.GestureListener());
    }

    private final class GestureListener extends SimpleOnGestureListener {
        public boolean onDown(@NotNull MotionEvent e) {
            return false;
        }

        public boolean onFling(@Nullable MotionEvent e1, @Nullable MotionEvent e2, float velocityX, float velocityY) {
            if (e1 != null && e2 != null) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > (float) 80 && Math.abs(velocityX) > (float) 100) {
                    if (diffX > (float) 0) {
                        GestureHelper.this.onSwipeRight();
                    } else {
                        GestureHelper.this.onSwipeLeft();
                    }
                } else if (Math.abs(diffY) > (float) 100 && Math.abs(velocityY) > (float) 100) {
                    if (diffY > (float) 0) {
                        GestureHelper.this.onSwipeBottom();
                    } else {
                        GestureHelper.this.onSwipeTop();
                    }
                }

                return true;
            } else {
                return false;
            }
        }

        public GestureListener() {
        }
    }
}

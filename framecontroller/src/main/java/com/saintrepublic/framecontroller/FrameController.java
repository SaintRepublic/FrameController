package com.saintrepublic.framecontroller;

/*
 * Copyright 2019 SaintRepublic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;

public class FrameController extends FrameLayout implements Controller {

    /**
     * This class is a custom view based on FrameLayout.
     * Developed by SaintRepublic.
     *
     * FrameController is a library that will help
     * you to work with multi-layer app interfaces.
     *
     * It`s easy to inflate and control
     * a lot of layouts in single activity.
     *
     * To work with this library you should use Controller interface.
     * For more specifications check a Controller interface class.
     *
     * This library contains an Animus library.
     * Animus is an another library developed by SaintRepublic.
     * If you use both of them
     * you should remove Animus library
     * and use Animus that packed into FrameController.
     *
     * Also you can get sources and samples of this library
     * on GitHub:
     * https://github.com/SaintRepublic/FrameController
     */

    public final static int ANIMATION_NONE = -1;
    public final static int ANIMATION_FADE = 0;
    public final static int ANIMATION_SCALE = 1;
    public final static int ANIMATION_SWIPE = 2;
    public final static int ANIMATION_MOVE_RIGHT = 3;
    public final static int ANIMATION_MOVE_BOTTOM = 4;
    public final static int ANIMATION_MOVE_LEFT = 5;
    public final static int ANIMATION_MOVE_TOP = 6;
    public final static int ANIMATION_SCROLL_VERTICAL = 7;
    public final static int ANIMATION_SCROLL_HORIZONTAL = 8;

    public final static int SPEED_SLOW = 0;
    public final static int SPEED_NORMAL = 1;
    public final static int SPEED_FAST = 2;

    private Context context;
    private Animus animus;
    private FCController fc;

    private FrameLayout currentContainer;
    private OnSwitchListener switchListener;

    public FrameController(Context context) {
        super(context);
        init(context);
    }

    public FrameController(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    public FrameController(Context context, AttributeSet attrSet, int defStyle) {
        super(context, attrSet, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        animus = new Animus();
        fc = new FCController(context);

        switchListener = null;
        currentContainer = null;
        setSwitchAnimation(-1, false);
        clear();
    }

    @Override
    public FrameController getFrameController() {
        return this;
    }

    //==================================== Working with layouts ====================================

    @Override
    public FrameLayout addViewToNewContainer(View view) {
        return addViewToNewContainer(view, -1, null);
    }

    @Override
    public FrameLayout addViewToNewContainer(View view, Object tag) { return addViewToNewContainer(view, -1, tag); }

    @Override
    public FrameLayout addViewToNewContainer(View view, int containerPosition, @Nullable Object tag) {
        FrameLayout container = fc.createNewContainer();
        container.addView(view);
        container.setTag(tag);
        try {
            addView(container, containerPosition);
        } catch (Exception e) {
            Log.e("FrameController: ", "addViewToNewContainer: ", e);
        }
        return container;
    }

    private FrameLayout addViewToNewContainer(@LayoutRes int layoutID, int containerPosition, @Nullable Object tag) {
        FrameLayout container = fc.createNewContainer();
        View layout = LayoutInflater.from(context).inflate(layoutID, container, false);
        container.setTag(tag);
        container.addView(layout);
        try {
            addView(container, containerPosition);
        } catch (Exception e) {
            Log.e("FrameController: ", "addViewToNewContainer: ", e);
        }
        return container;
    }

    @Override
    public FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID) {
        View layout = LayoutInflater.from(context).inflate(layoutID, null);
        return addViewToNewContainer(layoutID, -1, null);
    }

    @Override
    public FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, @Nullable Object tag) {
        return addViewToNewContainer(layoutID, -1, tag);
    }

    @Override
    public FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, int containerPosition, @Nullable Object tag) {
        return addViewToNewContainer(layoutID, containerPosition, tag);
    }

    @Override
    public FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, ViewGroup.LayoutParams layoutParams, @Nullable Object tag) {
        View layout = LayoutInflater.from(context).inflate(layoutID, null);
        layout.setLayoutParams(layoutParams);
        return addViewToNewContainer(layout, tag);
    }

    @Override
    public FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, ViewGroup.LayoutParams layoutParams, int containerPosition, @Nullable Object tag) {
        View layout = LayoutInflater.from(context).inflate(layoutID, null);
        layout.setLayoutParams(layoutParams);
        return addViewToNewContainer(layout, containerPosition, tag);
    }

    @Override
    public int getContainersCount() {
        return getChildCount();
    }

    @Override
    public FrameLayout getCurrentContainer() {return currentContainer;}

    @Override
    public int getCurrentPosition() {return indexOfChild(currentContainer);}

    @Override
    public boolean isCurrentFirst() {return (getCurrentPosition() == 0);}

    @Override
    public boolean isCurrentLast() {return (getCurrentPosition() == getContainersCount()-1);}

    @Override
    public boolean isOut() {return (currentContainer == null);}

    @Override
    public FrameLayout getContainerWithTag(Object tag) { return (FrameLayout)findViewWithTag(tag); }

    @Override
    public FrameLayout getContainerAtPosition(int positionIndex) { return (FrameLayout)getChildAt(positionIndex); }

    @Override
    public int getPositionOfContainer(FrameLayout container) { return indexOfChild(container); }

    @Override
    public void removeContainerAtPosition(int positionIndex) {
        if (positionIndex == getCurrentPosition()) {
            if (positionIndex == 0) goToNext();
            else goToPrevious();
            currentContainer = null;
        }
        fc.rmContainer(getContainerAtPosition(positionIndex));
        removeViewAt(positionIndex);
    }

    @Override
    public void removeAllContainers() {
        currentContainer = null;
        removeAllViews();
    }

    @Override
    public void clear() {
        currentContainer = null;
        removeAllViews();
        fc.rmAll();
    }

    @Override
    public void fillFromSavedContainers() {
        for (FrameLayout c : fc.getListContainers()) {
            c.setVisibility(INVISIBLE);
            addView(c);
        }
    }

    //================================== Working with configuration ================================

    @Override
    public void setContainersBackground(int color) {
        fc.config.setBackgroundColor(color);
    }

    @Override
    public void setContainersBackground(Drawable drawable) {
        fc.config.setBackgroundDrawable(drawable);
    }

    @Override
    public void setContainersMargins(int all) {
        fc.config.setMargins(all, all, all, all);
    }

    @Override
    public void setContainersMargins(int left, int top, int right, int bottom) {
        fc.config.setMargins(left, top, right, bottom);
    }

    @Override
    public void setContainersPadding(int all) {
        fc.config.setPaddings(all, all, all, all);
    }

    @Override
    public void setContainersPadding(int left, int top, int right, int bottom) {
        fc.config.setPaddings(left, top, right, bottom);
    }

    @Override
    public void setSwitchAnimation(int AnimationType, boolean enableScaleModifier) {
        fc.config.setSwitchAnimation(AnimationType);
        fc.config.setScaleMod(enableScaleModifier);
    }

    @Override
    public int getSwitchAnimationType() {
        return fc.config.getSwitchAnimation();
    }

    @Override
    public boolean isScaleModifierEnabled() {
        return fc.config.isScaleMod();
    }

    @Override
    public void setAnimationSpeed(int speed) {
        fc.config.setAnimationSpeed(speed);
    }

    @Override
    public int getAnimationSpeed() {
        return fc.config.getAnimationSpeed();
    }

    //===================================== Working with callbacks =================================

    public interface OnSwitchListener {
        /**
         * Triggered when switch started
         *
         * @param currentContainer start container
         * @param currentPosition position of current container
         */
        void onSwitchStarted(@Nullable FrameLayout currentContainer, int currentPosition);

        /**
         * Triggered when target container becomes visible
         *
         * @param targetContainer required container
         * @param targetPosition position of required container
         */
        void onTargetReached(@Nullable FrameLayout targetContainer, int targetPosition);

        /**
         * Triggered when all switching animations ends
         *
         * @param isOut true if out reached (all containers becomes invisible) else false
         */
        void onAnimationEnds(boolean isOut);
    }

    @Override
    public void setSwitchListener(OnSwitchListener listener) {
        this.switchListener = listener;
    }

    /*public interface OnContainerEventListener {
        public void onEnter(FrameLayout container);
        public void onOpened(FrameLayout container);
        public void onOut(FrameLayout container);
    }

    public void setContainerEventListener(OnContainerEventListener listener) {
        this.eventListener = listener;
    }*/

    //==================================== Working with navigation =================================

    private FrameLayout cContainer = null;
    private int targetPosition = -1;
    private int nextPosition = -1;
    private int action = 0;
    private boolean isBlocked = false;
    private boolean isSetGone;
    private boolean isScaleMod = false;
    private Handler animator = new Handler();

    @Override
    public boolean goTo(@IntRange(from = 0) int position) {
        return goTo(position, false, false, false);
    }

    @Override
    public boolean goTo(FrameLayout container) {
        return goTo(indexOfChild(container), false, false, false);
    }

    @Override
    public boolean goToContainerWithTag(Object tag) {return goTo(getContainerWithTag(tag));}

    @Override
    public boolean goFastTo(int position) {
        return goTo(position, true, false, false);
    }

    @Override
    public boolean goFastTo(FrameLayout container) {
        return goTo(indexOfChild(container), true, false, false);
    }

    @Override
    public boolean goFastToContainerWithTag(Object tag) {return goFastTo(getContainerWithTag(tag));}

    @Override
    public boolean goToFirst(boolean goFast) { return goTo(0, goFast, false, false); }

    @Override
    public boolean goToLast(boolean goFast) { return goTo(getChildCount()-1, goFast, false, false); }

    @Override
    public boolean goToNext() { return goTo(getCurrentPosition()+1, false, false, false);}

    @Override
    public boolean goToPrevious() {return goTo(getCurrentPosition()-1, false, false, false);}

    @Override
    public boolean goOut(boolean goFast, boolean setVisibilityGone) {
        if (!isOut()) {
            return goTo(-1, goFast, true, setVisibilityGone);
        }
        else {
            return false;
        }
    }

    private boolean goTo(int position, boolean isFast, boolean isOut, boolean isSetGone) {
        if (isBlocked) return false;

        if (!isOut && (position >= getChildCount() || position < 0)) {
            return false;
        }

        int cPosition;

        if (currentContainer == null) {
            cPosition = -1;
        }
        else {
            cPosition = indexOfChild(currentContainer);
        }

        if (cPosition == position) {
            return false;
        }

        setVisibility(VISIBLE);
        this.isSetGone = isSetGone;

        targetPosition = position;
        cContainer = currentContainer;

        switch (fc.config.getSwitchAnimation()) {
            case ANIMATION_NONE:
            case ANIMATION_FADE: {
                isFast = true;
                break;
            }
        }

        animator.removeCallbacksAndMessages(null);

        if (cPosition < position) {
            if (isFast) prepareNext(targetPosition);
            else prepareNext(cPosition+1);
        }
        else {
            if (isFast) preparePrev(targetPosition);
            else preparePrev(cPosition-1);
        }

        startAnimation();
        return true;
    }

    private void prepareNext(int position) {
        nextPosition = position;
        action = 0;
    }

    private void preparePrev(int position) {
        nextPosition = position;
        action = 1;
    }

    private void startAnimation() {

        if (switchListener != null)
        switchListener.onSwitchStarted(currentContainer, indexOfChild(currentContainer));

        currentContainer = (FrameLayout)getChildAt(targetPosition);
        animus.setInterpolator(Animus.Interpolators.FASTOUT_SLOWIN);

        setSpeed();
        isScaleMod = fc.config.isScaleMod();

        switch (fc.config.getSwitchAnimation()) {
            case ANIMATION_NONE: {
                animator.post(none);
                break;
            }
            case ANIMATION_FADE: {
                animus.setInterpolator(Animus.Interpolators.LINEAR);
                animator.post(fade);
                break;
            }
            case ANIMATION_SCALE: {
                animator.post(scale);
                break;
            }
            case ANIMATION_SWIPE: {
                animus.setInterpolator(Animus.Interpolators.LINEAR);
                animator.post(swipe);
                break;
            }
            case ANIMATION_MOVE_RIGHT:
            case ANIMATION_MOVE_BOTTOM:
            case ANIMATION_MOVE_LEFT:
            case ANIMATION_MOVE_TOP: {
                moveAnim = fc.config.getSwitchAnimation();
                animator.post(move);
                break;
            }
            case ANIMATION_SCROLL_VERTICAL:
            case ANIMATION_SCROLL_HORIZONTAL: {
                animus.setInterpolator(Animus.Interpolators.LINEAR);
                animator.post(scroll);
                break;
            }
        }
    }

    private void setSpeed() {
        switch (fc.config.getSwitchAnimation()) {
            case ANIMATION_FADE:
            case ANIMATION_SCALE: {
                if (getAnimationSpeed() == SPEED_FAST) {duration=350; delay = 140;}
                if (getAnimationSpeed() == SPEED_NORMAL) {duration=600; delay = 240;}
                if (getAnimationSpeed() == SPEED_SLOW) {duration=1000; delay = 400;}
                break;
            }

            case ANIMATION_SWIPE:
            case ANIMATION_SCROLL_VERTICAL:
            case ANIMATION_SCROLL_HORIZONTAL: {
                if (getAnimationSpeed() == SPEED_FAST) {duration=200; delay = 200;}
                if (getAnimationSpeed() == SPEED_NORMAL) {duration=400; delay = 400;}
                if (getAnimationSpeed() == SPEED_SLOW) {duration=600; delay = 600;}
                break;
            }
            case ANIMATION_MOVE_RIGHT:
            case ANIMATION_MOVE_BOTTOM:
            case ANIMATION_MOVE_LEFT:
            case ANIMATION_MOVE_TOP: {
                if (getAnimationSpeed() == SPEED_FAST) {duration=350; delay = 140;}
                if (getAnimationSpeed() == SPEED_NORMAL) {duration=500; delay = 200;}
                if (getAnimationSpeed() == SPEED_SLOW) {duration=800; delay = 300;}
                break;
            }
        }
    }

    //====================================== Animations ============================================

    private boolean out = false;
    private int duration = 0;
    private int delay = 0;

    private Runnable none = new Runnable() {
        @Override
        public void run() {
            if (cContainer!=null)
                cContainer.setVisibility(GONE);

            if (currentContainer != null)
                currentContainer.setVisibility(VISIBLE);

            if (switchListener !=null) {
                switchListener.onTargetReached(currentContainer, targetPosition);
                switchListener.onAnimationEnds(isOut());
            }

            if (isSetGone)
                setVisibility(GONE);
        }
    };

    private Runnable fade = new Runnable() {
        @Override
        public void run() {

            final View next = currentContainer;
            final View prev = cContainer;
            AnimationSet fade = new AnimationSet(false);

            Animation.AnimationListener listener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (prev!=null) prev.setVisibility(GONE);
                    if (isSetGone) setVisibility(GONE);
                    if (switchListener != null) switchListener.onAnimationEnds(isOut());
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            };

            if (action == 0) {
                if (next != null) {
                    if (isScaleMod) fade.addAnimation(animus.scaleFrom0To1(duration, false));
                    fade.addAnimation(animus.show(duration, false));
                    fade.setAnimationListener(listener);

                    next.setVisibility(VISIBLE);
                    next.startAnimation(fade);
                }
            }
            else {
                if (prev != null) {
                    if (isScaleMod) fade.addAnimation(animus.scaleFrom1To0(duration, false));
                    fade.addAnimation(animus.hide(duration, false));
                    fade.setAnimationListener(listener);

                    if (next != null) next.setVisibility(VISIBLE);
                    prev.startAnimation(fade);
                }
            }

            if (switchListener != null)
                switchListener.onTargetReached(currentContainer, targetPosition);
        }
    };

    private Runnable scale = new Runnable() {
        @Override
        public void run() {
            isBlocked = true;

            Animation anim;
            final View next = getChildAt(nextPosition);
            final View prev = cContainer;

            if (action == 0) {
                if (nextPosition != targetPosition) { nextPosition++; out = false; }
                else out = true;

                if (next != null) {
                    anim = animus.scaleFrom0To1(duration, false);
                    if (out) anim.setAnimationListener(getAnimationListener(next, prev, true));
                    else anim.setAnimationListener(getAnimationListener(next, prev, false));

                    next.startAnimation(anim);
                }
            }
            else {
                if (nextPosition != targetPosition) { nextPosition--; out = false; }
                else out = true;

                if (prev != null) {
                    anim = animus.scaleFrom1To0(duration, false);
                    if (out) anim.setAnimationListener(getAnimationListener(next, prev, true));
                    else anim.setAnimationListener(getAnimationListener(next, prev, false));

                    prev.startAnimation(anim);
                }
            }

            if (out) {
                if (switchListener != null)
                    switchListener.onTargetReached(currentContainer, targetPosition);
            }
            else animator.postDelayed(this, delay);
        }
    };

    private Runnable swipe = new Runnable() {
        @Override
        public void run() {

            isBlocked = true;
            View next = getChildAt(nextPosition);
            View prev = cContainer;

            AnimationSet nextAnim = new AnimationSet(false);
            AnimationSet prevAnim = new AnimationSet(false);

            if (action == 0) {
                if (isScaleMod) {
                    nextAnim.addAnimation(animus.scale(0.3f, 1.0f, 0.3f, 1.0f, 1.0f, 1.0f, duration, false));
                    prevAnim.addAnimation(animus.scale(1.0f, 0.2f, 1.0f, 0.2f, 0.0f, 1.0f, (int)(duration*0.8), true));
                }
                nextAnim.addAnimation(animus.fromRightOfParent(duration, false));
                prevAnim.addAnimation(animus.toLeftOfParent(duration, false));
                nextAnim.addAnimation(animus.rotate(20, 0, 0.5f, 1.0f, duration, false));
                prevAnim.addAnimation(animus.rotate(0, -20, 0.5f, 1.0f, duration, false));

                if (nextPosition != targetPosition) { nextPosition++; out = false; }
                else out = true;
            }
            else {
                if (isScaleMod) {
                    nextAnim.addAnimation(animus.scale(0.3f, 1.0f, 0.3f, 1.0f, 0.0f, 1.0f, duration, false));
                    prevAnim.addAnimation(animus.scale(1.0f, 0.2f, 1.0f, 0.2f, 1.0f, 1.0f, (int)(duration*0.8), true));
                }
                nextAnim.addAnimation(animus.fromLeftOfParent(duration, false));
                prevAnim.addAnimation(animus.toRightOfParent(duration, false));
                nextAnim.addAnimation(animus.rotate(-20, 0, 0.5f, 1.0f, duration, false));
                prevAnim.addAnimation(animus.rotate(0, 20, 0.5f, 1.0f, duration, false));

                if (nextPosition != targetPosition) { nextPosition--; out = false; }
                else out = true;
            }

            if (isScaleMod) {
                nextAnim.addAnimation(animus.scale(0.3f, 1.0f, 0.3f, 1.0f, 0.5f, 0.5f, duration, false));
                prevAnim.addAnimation(animus.scale(1.0f, 0.3f, 1.0f, 0.3f, 0.5f, 0.5f, duration, false));
            }

            if (prev != null) {
                if (out) prevAnim.setAnimationListener(getAnimationListener(next, prev, true));
                else prevAnim.setAnimationListener(getAnimationListener(next, prev, false));
                prev.startAnimation(prevAnim);
            }
            else if (out) nextAnim.setAnimationListener(getAnimationListener(next, prev, true));
                 else nextAnim.setAnimationListener(getAnimationListener(next, prev, false));

            if (next != null) next.startAnimation(nextAnim);

            if (out) {
                if (switchListener != null)
                    switchListener.onTargetReached(currentContainer, targetPosition);
            }
            else animator.postDelayed(this, delay);
        }
    };

    private int moveAnim;

    private Runnable move = new Runnable() {
        @Override
        public void run() {
            isBlocked = true;

            AnimationSet anim = new AnimationSet(false);
            View next = getChildAt(nextPosition);
            View prev = cContainer;

            if (action == 0) {
                if (nextPosition != targetPosition) { nextPosition++; out = false; }
                else out = true;

                if (next != null) {
                    switch (moveAnim){
                        case ANIMATION_MOVE_RIGHT: {
                            anim.addAnimation(animus.fromRightOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(0.6f, 1.0f, 0.6f, 1.0f, 1.0f, 0.5f, duration, false));
                            break; }
                        case ANIMATION_MOVE_BOTTOM: {
                            anim.addAnimation(animus.fromBottomOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(0.6f, 1.0f, 0.6f, 1.0f, 0.5f, 1.0f, duration, false));
                            break; }
                        case ANIMATION_MOVE_LEFT: {
                            anim.addAnimation(animus.fromLeftOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(0.6f, 1.0f, 0.6f, 1.0f, 0.0f, 0.5f, duration, false));
                            break; }
                        case ANIMATION_MOVE_TOP: {
                            anim.addAnimation(animus.fromTopOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(0.6f, 1.0f, 0.6f, 1.0f, 0.5f, 0.0f, duration, false));
                            break; }
                        default: {
                            anim.addAnimation(animus.fromRightOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(0.6f, 1.0f, 0.6f, 1.0f, 1.0f, 0.5f, duration, false));
                            break; }
                    }
                    if (out) anim.setAnimationListener(getAnimationListener(next, prev, true));
                    else anim.setAnimationListener(getAnimationListener(next, prev, false));
                    next.startAnimation(anim);
                }
            }
            else {
                if (nextPosition != targetPosition) { nextPosition--;out = false; }
                else out = true;

                if (prev != null) {
                    switch (moveAnim) {
                        case ANIMATION_MOVE_RIGHT: {
                            anim.addAnimation(animus.toRightOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(1.0f, 0.6f, 1.0f, 0.6f, 1.0f, 0.5f, duration, false));
                            break; }
                        case ANIMATION_MOVE_BOTTOM: {
                            anim.addAnimation(animus.toBottomOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(1.0f, 0.6f, 1.0f, 0.6f, 0.5f, 1.0f, duration, false));
                            break; }
                        case ANIMATION_MOVE_LEFT: {
                            anim.addAnimation(animus.toLeftOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(1.0f, 0.6f, 1.0f, 0.6f, 0.0f, 0.5f, duration, false));
                            break; }
                        case ANIMATION_MOVE_TOP: {
                            anim.addAnimation(animus.toTopOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(1.0f, 0.6f, 1.0f, 0.6f, 0.5f, 0.0f, duration, false));
                            break; }
                        default: {
                            anim.addAnimation(animus.toRightOfParent(duration, false));
                            if (isScaleMod) anim.addAnimation(animus.scale(1.0f, 0.6f, 1.0f, 0.6f, 1.0f, 0.5f, duration, false));
                            break; }
                    }
                    if (out) anim.setAnimationListener(getAnimationListener(next, prev, true));
                    else anim.setAnimationListener(getAnimationListener(next, prev, false));
                    prev.startAnimation(anim);
                }
            }

            if (out) {
                if (switchListener != null)
                    switchListener.onTargetReached(currentContainer, targetPosition);
            }
            else animator.postDelayed(this, delay);
        }
    };

    private Runnable scroll = new Runnable() {
        @Override
        public void run() {

            isBlocked = true;
            View next = getChildAt(nextPosition);
            View prev = cContainer;

            AnimationSet nextAnim = new AnimationSet(false);
            AnimationSet prevAnim = new AnimationSet(false);

            if (action == 0) {
                if (fc.config.getSwitchAnimation() == ANIMATION_SCROLL_VERTICAL) {
                    nextAnim.addAnimation(animus.fromBottomOfParent(duration, false));
                    prevAnim.addAnimation(animus.toTopOfParent(duration, false));
                    if (isScaleMod) {
                        nextAnim.addAnimation(animus.scale(0.7f, 1.0f, 0.7f, 1.0f, 0.5f, 1.0f, duration, false));
                        prevAnim.addAnimation(animus.scale(1.0f, 0.7f, 1.0f, 0.7f, 0.5f, 0.0f, duration, false));
                    }
                }
                else {
                    nextAnim.addAnimation(animus.fromRightOfParent(duration, false));
                    prevAnim.addAnimation(animus.toLeftOfParent(duration, false));
                    if (isScaleMod) {
                        nextAnim.addAnimation(animus.scale(0.7f, 1.0f, 0.7f, 1.0f, 1.0f, 0.5f, duration, false));
                        prevAnim.addAnimation(animus.scale(1.0f, 0.7f, 1.0f, 0.7f, 0.0f, 0.5f, duration, false));
                    }
                }

                if (nextPosition != targetPosition) { nextPosition++; out = false; }
                else out = true;
            }
            else {
                if (fc.config.getSwitchAnimation() == ANIMATION_SCROLL_VERTICAL) {
                    nextAnim.addAnimation(animus.fromTopOfParent(duration, false));
                    prevAnim.addAnimation(animus.toBottomOfParent(duration, false));
                    if (isScaleMod) {
                        nextAnim.addAnimation(animus.scale(0.7f, 1.0f, 0.7f, 1.0f, 0.5f, 0.0f, duration, false));
                        prevAnim.addAnimation(animus.scale(1.0f, 0.7f, 1.0f, 0.7f, 0.5f, 1.0f, duration, false));
                    }
                }
                else {
                    nextAnim.addAnimation(animus.fromLeftOfParent(duration, false));
                    prevAnim.addAnimation(animus.toRightOfParent(duration, false));
                    if (isScaleMod) {
                        nextAnim.addAnimation(animus.scale(0.7f, 1.0f, 0.7f, 1.0f, 0.0f, 0.5f, duration, false));
                        prevAnim.addAnimation(animus.scale(1.0f, 0.7f, 1.0f, 0.7f, 1.0f, 0.5f, duration, false));
                    }
                }

                if (nextPosition != targetPosition) { nextPosition--; out = false; }
                else out = true;
            }

            if (prev != null) {
                if (out) prevAnim.setAnimationListener(getAnimationListener(next, prev, true));
                else prevAnim.setAnimationListener(getAnimationListener(next, prev, false));
                prev.startAnimation(prevAnim);
            }
            else if (out) nextAnim.setAnimationListener(getAnimationListener(next, prev, true));
                 else nextAnim.setAnimationListener(getAnimationListener(next, prev, false));

            if (next != null) next.startAnimation(nextAnim);

            if (out) {
                if (switchListener != null)
                    switchListener.onTargetReached(currentContainer, targetPosition);
            }
            else animator.postDelayed(this, delay);
        }
    };

    private Animation.AnimationListener getAnimationListener(final View next, final View prev, final boolean unblock) {

        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (next != null)
                next.setVisibility(VISIBLE);
                cContainer = (FrameLayout)next;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (prev != null) prev.setVisibility(GONE);
                if (out) {
                    if (unblock) isBlocked = false;
                    if (isSetGone) setVisibility(GONE);
                    if (switchListener != null) switchListener.onAnimationEnds(isOut());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        };
    }
}



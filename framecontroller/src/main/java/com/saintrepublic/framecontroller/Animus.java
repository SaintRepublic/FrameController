package com.saintrepublic.framecontroller;

/*
 * Copyright 2020 SaintRepublic
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

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Animus {

    /*
     * Animus - is a simple Java class to simplify the views animations.
     * Developed by SaintRepublic.
     *
     * You can use it in your projects as library
     * via Gradle:
     * implementation 'com.saintrepublic.animus:animus:1.1'
     *
     * Or check it on GitHub:
     * https://github.com/SaintRepublic/Animus
     */

    /**
     * Default interpolator for all animations
     */
    private Interpolator currentInterpolator;
    private int cyclesCount;

    /**
     * By default set a Linear Interpolator
     */
    public Animus() {
        currentInterpolator = new LinearInterpolator();
    }

    /**
     * Class with animation interpolators id as constants
     */
    public class Interpolators {
        public final static int ACCELERATE = 0;
        public final static int DECELERATE = 1;
        public final static int ACCELERATE_DECELERATE = 2;
        public final static int ANTICIPATE = 3;
        public final static int OVERSHOOT = 4;
        public final static int ANTICIPATE_OVERSHOOT = 5;
        public final static int BOUNCE = 6;
        public final static int CYCLE = 7;
        public final static int LINEAR = 8;
        public final static int FASTOUT_LINEARIN = 9;
        public final static int FASTOUT_SLOWIN = 10;
        public final static int LINEAROUT_SLOWIN = 11;
    }

    /**
     * Get new instance of interpolator by id
     *
     * @param interpolator_id Interpolator id
     * @return New interpolator instance
     */
    public Interpolator getInterpolator(int interpolator_id) {
        switch (interpolator_id) {
            case 0: { return new AccelerateInterpolator();}
            case 1: { return new DecelerateInterpolator();}
            case 2: { return new AccelerateDecelerateInterpolator();}
            case 3: { return new AnticipateInterpolator();}
            case 4: { return new OvershootInterpolator();}
            case 5: { return new AnticipateOvershootInterpolator();}
            case 6: { return new BounceInterpolator();}
            case 7: { return new CycleInterpolator(cyclesCount);}
            case 8: { return new LinearInterpolator();}
            case 9: { return new FastOutLinearInInterpolator();}
            case 10: { return new FastOutSlowInInterpolator();}
            case 11: { return new LinearOutSlowInInterpolator();}
            default: { return new LinearInterpolator();}
        }
    }

    /**
     * Set default animations interpolator
     *
     * @param Animus_Interpolators_ID Use com.saintrepublic.scrollablecontainers.Animus.Interpolators.{INTERPOLATOR} to get id
     */
    public void setInterpolator(int Animus_Interpolators_ID) {
        currentInterpolator = getInterpolator(Animus_Interpolators_ID);
        cyclesCount = 1;
    }

    public void setCycleInterpolatorCycles(int cycles) {
        cyclesCount = cycles;
    }

    /**
     * Animate moving with custom parameters relative to self
     *
     * @param fx staring horizontal factor
     * @param tx ending horizontal factor
     * @param fy starting vertical factor
     * @param ty ending vertical factor
     * @param duration
     * @param fillAfter
     * @return TranslateAnimation
     */
    final public Animation move(float fx, float tx, float fy, float ty, int duration, boolean fillAfter){
        Animation customMove = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fx,
                Animation.RELATIVE_TO_SELF, tx,
                Animation.RELATIVE_TO_SELF, fy,
                Animation.RELATIVE_TO_SELF, ty);
        customMove.setDuration(duration);
        customMove.setFillAfter(fillAfter);
        customMove.setInterpolator(currentInterpolator);
        return customMove;
    }

    /**
     * Animate moving with custom parameters relative to parent layout
     *
     * @param fx staring horizontal factor
     * @param tx ending horizontal factor
     * @param fy starting vertical factor
     * @param ty ending vertical factor
     * @param duration
     * @param fillAfter
     * @return TranslateAnimation
     */
    final public Animation moveToParent(float fx, float tx, float fy, float ty, int duration, boolean fillAfter){
        Animation customMove = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fx,
                Animation.RELATIVE_TO_PARENT, tx,
                Animation.RELATIVE_TO_PARENT, fy,
                Animation.RELATIVE_TO_PARENT, ty);
        customMove.setDuration(duration);
        customMove.setFillAfter(fillAfter);
        customMove.setInterpolator(currentInterpolator);
        return customMove;
    }

    /**
     * Animate moving with custom parameters
     * relative to self at start of animation
     * relative to parent at the end of animation
     *
     * @param fx staring horizontal factor relative to self
     * @param tx ending horizontal factor relative to parent
     * @param fy starting vertical factor relative to self
     * @param ty ending vertical factor relative to parent
     * @param duration
     * @param fillAfter
     * @return TranslateAnimation
     */
    final public Animation moveSelfToParent(float fx, float tx, float fy, float ty, int duration, boolean fillAfter){
        Animation customMove = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fx,
                Animation.RELATIVE_TO_PARENT, tx,
                Animation.RELATIVE_TO_SELF, fy,
                Animation.RELATIVE_TO_PARENT, ty);
        customMove.setDuration(duration);
        customMove.setFillAfter(fillAfter);
        customMove.setInterpolator(currentInterpolator);
        return customMove;
    }

    /**
     * Animate moving with custom parameters
     * relative to parent at start of animation
     * relative to self at the end of animation
     *
     * @param fx staring horizontal factor relative to parent
     * @param tx ending horizontal factor relative to self
     * @param fy starting vertical factor relative to parent
     * @param ty ending vertical factor relative to self
     * @param duration
     * @param fillAfter
     * @return TranslateAnimation
     */
    final public Animation moveParentToSelf(float fx, float tx, float fy, float ty, int duration, boolean fillAfter){
        Animation customMove = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fx,
                Animation.RELATIVE_TO_SELF, tx,
                Animation.RELATIVE_TO_PARENT, fy,
                Animation.RELATIVE_TO_SELF, ty);
        customMove.setDuration(duration);
        customMove.setFillAfter(fillAfter);
        customMove.setInterpolator(currentInterpolator);
        return customMove;
    }

    /**
     * Animate moving to left of self
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toLeft(int duration, boolean fillAfter){
        Animation toLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        toLeft.setDuration(duration);
        toLeft.setFillAfter(fillAfter);
        toLeft.setInterpolator(currentInterpolator);
        return toLeft;
    }

    /**
     * Animate moving beyond left bounds of parent layout
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toLeftOfParent(int duration, boolean fillAfter){
        Animation toLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        toLeft.setDuration(duration);
        toLeft.setFillAfter(fillAfter);
        toLeft.setInterpolator(currentInterpolator);
        return toLeft;
    }

    /**
     * Animate moving from left of self to default
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromLeft(int duration, boolean fillAfter){
        Animation fromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromLeft.setDuration(duration);
        fromLeft.setFillAfter(fillAfter);
        fromLeft.setInterpolator(currentInterpolator);
        return fromLeft;
    }

    /**
     * Animate moving from left of parent
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromLeftOfParent(int duration, boolean fillAfter){
        Animation fromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromLeft.setDuration(duration);
        fromLeft.setFillAfter(fillAfter);
        fromLeft.setInterpolator(currentInterpolator);
        return fromLeft;
    }

    /**
     * Animate moving to right of self
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toRight(int duration, boolean fillAfter){
        Animation toRight = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        toRight.setDuration(duration);
        toRight.setFillAfter(fillAfter);
        toRight.setInterpolator(currentInterpolator);
        return toRight;
    }

    /**
     * Animate moving beyond right bounds of parent layout
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toRightOfParent(int duration, boolean fillAfter){
        Animation toRight = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        toRight.setDuration(duration);
        toRight.setFillAfter(fillAfter);
        toRight.setInterpolator(currentInterpolator);
        return toRight;
    }

    /**
     * Animate moving from right of self to default
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromRight(int duration, boolean fillAfter){
        Animation fromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromRight.setDuration(duration);
        fromRight.setFillAfter(fillAfter);
        fromRight.setInterpolator(currentInterpolator);
        return fromRight;
    }

    /**
     * Animate moving from right of parent
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromRightOfParent(int duration, boolean fillAfter){
        Animation fromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromRight.setDuration(duration);
        fromRight.setFillAfter(fillAfter);
        fromRight.setInterpolator(currentInterpolator);
        return fromRight;
    }

    /**
     * Animate moving to bottom of self
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toBottom(int duration, boolean fillAfter){
        Animation toBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        toBottom.setDuration(duration);
        toBottom.setFillAfter(fillAfter);
        toBottom.setInterpolator(currentInterpolator);
        return toBottom;
    }

    /**
     * Animate moving beyond bottom bounds of parent layout
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toBottomOfParent(int duration, boolean fillAfter){
        Animation toBottomOfParent = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f);
        toBottomOfParent.setDuration(duration);
        toBottomOfParent.setFillAfter(fillAfter);
        toBottomOfParent.setInterpolator(currentInterpolator);
        return toBottomOfParent;
    }

    /**
     * Animate moving from bottom of self to default
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromBottom(int duration, boolean fillAfter){
        Animation fromBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromBottom.setDuration(duration);
        fromBottom.setFillAfter(fillAfter);
        fromBottom.setInterpolator(currentInterpolator);
        return fromBottom;
    }

    /**
     * Animate moving from bottom of parent
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromBottomOfParent(int duration, boolean fillAfter){
        Animation fromBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromBottom.setDuration(duration);
        fromBottom.setFillAfter(fillAfter);
        fromBottom.setInterpolator(currentInterpolator);
        return fromBottom;
    }

    /**
     * Animate moving to top of self
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toTop(int duration, boolean fillAfter){
        Animation toTop = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        toTop.setDuration(duration);
        toTop.setFillAfter(fillAfter);
        toTop.setInterpolator(currentInterpolator);
        return toTop;
    }

    /**
     * Animate moving beyond top bounds of parent layout
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation toTopOfParent(int duration, boolean fillAfter){
        Animation toTop = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        toTop.setDuration(duration);
        toTop.setFillAfter(fillAfter);
        toTop.setInterpolator(currentInterpolator);
        return toTop;
    }

    /**
     * Animate moving from top of self to default
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromTop(int duration, boolean fillAfter){
        Animation fromTop = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromTop.setDuration(duration);
        fromTop.setFillAfter(fillAfter);
        fromTop.setInterpolator(currentInterpolator);
        return fromTop;
    }

    /**
     * Animate moving from top of parent
     *
     * @param duration
     * @param fillAfter
     * @return Translate animation
     */
    final public Animation fromTopOfParent(int duration, boolean fillAfter){
        Animation fromTop = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        fromTop.setDuration(duration);
        fromTop.setFillAfter(fillAfter);
        fromTop.setInterpolator(currentInterpolator);
        return fromTop;
    }

    /**
     * Animate opacity changing from visibility to invisibility
     *
     * @param duration
     * @param fillAfter
     * @return AlphaAnimation
     */
    final public Animation hide(int duration, boolean fillAfter){
        Animation toInvis = new AlphaAnimation(1.0f, 0.0f);
        toInvis.setDuration(duration);
        toInvis.setFillAfter(fillAfter);
        toInvis.setInterpolator(currentInterpolator);
        return toInvis;
    }

    /**
     * Animate opacity changing from invisibility to visibility
     *
     * @param duration
     * @param fillAfter
     * @return AlphaAnimation
     */
    final public Animation show(int duration, boolean fillAfter){
        Animation fromInvis = new AlphaAnimation(0.0f, 1.0f);
        fromInvis.setDuration(duration);
        fromInvis.setFillAfter(fillAfter);
        fromInvis.setInterpolator(currentInterpolator);
        return fromInvis;
    }

    /**
     * Animate opacity changing with custom parameters
     *
     * @param fromAlpha starting opacity
     * @param toAlpha ending opacity
     * @param duration
     * @param fillAfter
     * @return AlphaAnimation
     */
    final public Animation changeAlpha(int fromAlpha, int toAlpha, int duration, boolean fillAfter){
        Animation alpha = new AlphaAnimation(fromAlpha, toAlpha);
        alpha.setDuration(duration);
        alpha.setFillAfter(fillAfter);
        alpha.setInterpolator(currentInterpolator);
        return alpha;
    }

    /**
     * Animate scale with custom parameters
     *
     * @param fx horizontal factor at start of animation
     * @param tx horizontal factor at the end
     * @param fy vertical factor at start of animation
     * @param ty vertical factor at the and
     * @param pivotX pivot horizontal position
     * @param pivotY pivot vertical position
     * @param duration
     * @param fillAfter
     * @return ScaleAnimation
     */
    final public Animation scale(float fx, float tx, float fy, float ty, float pivotX, float pivotY, int duration, boolean fillAfter){
        Animation customScale = new ScaleAnimation(fx, tx, fy, ty, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        customScale.setDuration(duration);
        customScale.setFillAfter(fillAfter);
        customScale.setInterpolator(currentInterpolator);
        return customScale;
    }

    /**
     * Animate scale from 0 to default size
     *
     * @param duration
     * @param fillAfter
     * @return ScaleAnimation
     */
    final public Animation scaleFrom0To1(int duration, boolean fillAfter){

        Animation ScaleFrom0To1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
        Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ScaleFrom0To1.setDuration(duration);
        ScaleFrom0To1.setFillAfter(fillAfter);
        ScaleFrom0To1.setInterpolator(currentInterpolator);
        return ScaleFrom0To1;
    }

    /**
     * Animate scale from defalut size to 0
     *
     * @param duration
     * @param fillAfter
     * @return ScaleAnimation
     */
    final public Animation scaleFrom1To0(int duration, boolean fillAfter){

        Animation ScaleFrom1To0 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
        Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ScaleFrom1To0.setDuration(duration);
        ScaleFrom1To0.setFillAfter(fillAfter);
        ScaleFrom1To0.setInterpolator(currentInterpolator);
        return ScaleFrom1To0;
    }

    /**
     * Animate scale from default size double size
     *
     * @param duration
     * @param fillAfter
     * @return ScaleAnimation
     */
    final public Animation scaleFrom1To2(int duration, boolean fillAfter){

        Animation ScaleFrom1To2 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
        Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ScaleFrom1To2.setDuration(duration);
        ScaleFrom1To2.setFillAfter(fillAfter);
        ScaleFrom1To2.setInterpolator(currentInterpolator);
        return ScaleFrom1To2;
    }

    /**
     * Animate scale from double size to default size
     *
     * @param duration
     * @param fillAfter
     * @return ScaleAnimation
     */
    final public Animation scaleFrom2To1(int duration, boolean fillAfter){

        Animation ScaleFrom2To1 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
        Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ScaleFrom2To1.setDuration(duration);
        ScaleFrom2To1.setFillAfter(fillAfter);
        ScaleFrom2To1.setInterpolator(currentInterpolator);
        return ScaleFrom2To1;
    }

    /**
     * Animate rotation with custom parameters with pivot relative to self
     *
     * @param fd starting degrees
     * @param td ending degrees
     * @param pivotX horizontal pivot position relative to self
     * @param pivotY vertical pivot position relative to self
     * @param duration
     * @param fillAfter
     * @return RotateAnimation
     */
    final public Animation rotate(float fd, float td, float pivotX, float pivotY, int duration, boolean fillAfter) {
        Animation rotate = new RotateAnimation(fd, td, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        rotate.setDuration(duration);
        rotate.setFillAfter(fillAfter);
        rotate.setInterpolator(currentInterpolator);
        return rotate;
    }

    /**
     * Animate rotation with custom parameters with pivot relative to parent layout
     *
     * @param fd starting degrees
     * @param td ending degrees
     * @param pivotX horizontal pivot position relative to parent
     * @param pivotY vertical pivot position relative to parent
     * @param duration
     * @param fillAfter
     * @return RotateAnimation
     */
    final public Animation rotateToParent(float fd, float td, float pivotX, float pivotY, int duration, boolean fillAfter) {
        Animation rotate = new RotateAnimation(fd, td, Animation.RELATIVE_TO_PARENT, pivotX, Animation.RELATIVE_TO_PARENT, pivotY);
        rotate.setDuration(duration);
        rotate.setFillAfter(fillAfter);
        rotate.setInterpolator(currentInterpolator);
        return rotate;
    }
}

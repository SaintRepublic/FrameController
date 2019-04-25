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

import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public interface Controller {

    /**
     * This class is an interface of FrameController class.
     *
     * You should use it to working with FrameController:
     * "Controller controller = new FrameController(context);"
     *
     * Also you can specify FrameController in your xml-layouts.
     * So you can use it as:
     * "Controller controller = findViewById(R.id.frameController);"
     *
     * MAKE SURE THAT FrameController CONTAINS ONLY FrameLayout CHILD!!!
     */

    int ANIMATION_NONE = -1;
    int ANIMATION_FADE = 0;
    int ANIMATION_SCALE = 1;
    int ANIMATION_SWIPE = 2;
    int ANIMATION_MOVE_RIGHT = 3;
    int ANIMATION_MOVE_BOTTOM = 4;
    int ANIMATION_MOVE_LEFT = 5;
    int ANIMATION_MOVE_TOP = 6;
    int ANIMATION_SCROLL_VERTICAL = 7;
    int ANIMATION_SCROLL_HORIZONTAL = 8;

    /**
     * Returns assigned FrameController
     *
     * @return this FrameController
     */
    FrameController getFrameController();

    /**
     * Create and add on top of container collection
     * new invisible container(FrameLayout)
     * and put your view into it
     *
     * @param view any view
     * @return created container (FrameLayout)
     */
    FrameLayout addViewToNewContainer(View view);

    /**
     * Create and add on top of container collection
     * new invisible container(FrameLayout) with your tag
     * and put your view into it
     *
     * @param view any view
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addViewToNewContainer(View view, Object tag);

    /**
     * Create a new invisible container(FrameLayout) with your tag,
     * add it to container collection in position that you requires
     * and put your view into it
     *
     * @param view any view
     * @param containerPosition position in containers group at which to add your view
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addViewToNewContainer(View view, int containerPosition, @Nullable Object tag);

    /**
     * Create and add on top of container collection
     * new invisible container(FrameLayout)
     * and inflate into it your xml layout resource
     *
     * @param layoutID layout resource id
     * @return created container (FrameLayout)
     */
    FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID);

    /**
     * Create and add on top of container collection
     * new invisible container(FrameLayout) with your tag
     * and inflate into it your xml layout resource
     *
     * @param layoutID layout resource id
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, Object tag);

    /**
     * Create a new invisible container(FrameLayout) with your tag,
     * add it to container collection in position that you requires
     * and inflate into it your xml layout resource
     *
     * @param layoutID layout resource id
     * @param containerPosition layoutParams which will set to your layout
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, int containerPosition, @Nullable Object tag);

    /**
     * Create and add on top of container collection
     * new invisible container(FrameLayout) with your tag,
     * inflate into it your xml layout resource
     * and set your layout parameters to inflated layout
     *
     * @param layoutID layout resource id
     * @param layoutParams layoutParams which will set to your layout
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, ViewGroup.LayoutParams layoutParams, @Nullable Object tag);

    /**
     * Create a new invisible container(FrameLayout) with your tag,
     * add it to container collection in position that you requires
     * and inflate into it your xml layout resource
     * and set your layout parameters to inflated layout
     *
     * @param layoutID layout resource id
     * @param containerPosition layoutParams which will set to your layout
     * @param layoutParams layoutParams which will set to your layout
     * @param tag any tag
     * @return created container (FrameLayout)
     */
    FrameLayout addLayoutToNewContainer(@LayoutRes int layoutID, ViewGroup.LayoutParams layoutParams, int containerPosition, @Nullable Object tag);

    /**
     * Returns the count of containers that contains in FrameController now;
     *
     * @return count of containers
     */
    int getContainersCount();

    /**
     * Returns the container(FrameLayout) which opened now
     *
     * @return opened container(FrameLayout)
     */
    FrameLayout getCurrentContainer();

    /**
     * Return position of current container
     *
     * @return current position index
     */
    int getCurrentPosition();
    /**
     * Return true if current container is first in collection (position 0)
     *
     * @return true if current position equals 0 else false
     */
    boolean isCurrentFirst();

    /**
     * Return true if current container is last in collection (position = containers count-1)
     *
     * @return true if current position is last else false
     */
    boolean isCurrentLast();

    /**
     * Check if current container is null and if current position equals -1
     *
     * @return true if current position equals -1 else return false
     */
    boolean isOut();

    /**
     * Find container from container collection with specified tag.
     *
     * If exists return this container(FrameLayout)
     * else return null
     *
     * @param tag tag to find container
     * @return container(FrameLayout) with specified tag or null
     */
    FrameLayout getContainerWithTag(Object tag);

    /**
     * Find container in specified position from container collection
     *
     * If exists return this container(FrameLayout)
     * else return null
     *
     * @param positionIndex position of required container in container collection
     * @return container(FrameLayout) in specified position or null
     */
    FrameLayout getContainerAtPosition(int positionIndex);

    /**
     * Return position index of container specified container
     *
     * If exists return container`s position index
     * else return -1
     *
     * @param container container whose position you want to get
     * @return position index or -1
     */
    int getPositionOfContainer(FrameLayout container);

    /**
     * Remove container in specified position
     *
     * If container to remove is current container
     * Frame controller will automatically go to previous container
     *
     * If container to remove is current container and it is first (position 0)
     * Frame controller will automatically go to next container
     *
     * @param positionIndex position of container
     */
    void removeContainerAtPosition(int positionIndex);

    /**
     * Remove all containers contained in FrameController
     *
     * ATTENTION!!!
     * Containers won`t be removed from container collection!
     *
     * You can use fillFromSavedContainers() to restore
     * all containers from collection back to FrameController
     *
     * or use clear() instead to permanently remove all containers
     */
    void removeAllContainers();

    /**
     * Permanently remove all containers from both FrameController and container collection
     */
    void clear();

    /**
     * Restore all containers from collection back to FrameController
     */
    void fillFromSavedContainers();

    /**
     * Set background color for all containers (existing and future)
     *
     * @param color color to use as the background or null to remove the background
     */
    void setContainersBackground(int color);

    /**
     * Set background drawable for all containers (existing and future)
     *
     * @param drawable drawable to use as the background or null to remove the background
     */
    void setContainersBackground(Drawable drawable);

    /**
     * Set margins for all containers (existing and future)
     *
     * @param all margins dp
     */
    void setContainersMargins(int all);

    /**
     * Set margins for all containers (existing and future)
     *
     * @param left left margins dp
     * @param top top margins dp
     * @param right right margins dp
     * @param bottom bottom margins dp
     */
    void setContainersMargins(int left, int top, int right, int bottom);

    /**
     * Set padding for all containers (existing and future)
     *
     * @param all padding dp
     */
    void setContainersPadding(int all);

    /**
     * Set padding for all containers (existing and future)
     *
     * @param left left padding dp
     * @param top top padding dp
     * @param right right padding dp
     * @param bottom bottom padding dp
     */
    void setContainersPadding(int left, int top, int right, int bottom);

    /**
     * Set which type of animation will be used for switching containers
     *
     * Use types constants as Controller.ANIMATION_...
     *
     * @param AnimationType index of animation type
     */
    void setSwitchAnimation(int AnimationType);

    /**
     * Return index of current animation type
     *
     * @return index of current animation type
     */
    int getSwitchAnimationType();

    /**
     * Set FrameController.OnSwitchListener to FrameController
     *
     * @param listener set new FrameController.OnSwitchListener
     */
    void setSwitchListener(FrameController.OnSwitchListener listener);

    /**
     * Switch to container at specified position
     *
     * If animation type is NONE or FADE it use Fast switch instead
     *
     * @param position position index of target container
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goTo(@IntRange(from = 0) int position);

    /**
     * Find position and switch to specified container
     *
     * If animation type is NONE or FADE it use Fast switch instead
     *
     * @param container target container
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goTo(FrameLayout container);

    /**
     * Find container with specified tag and switch to it
     *
     * If animation type is NONE or FADE it use Fast switch instead
     *
     * @param tag target container`s tag
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goToContainerWithTag(Object tag);

    /**
     * Skip other container and switch directly to container at specified position
     *
     * @param position position index of target container
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goFastTo(int position);

    /**
     * Find position and switch directly to specified container
     *
     * @param container target container
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goFastTo(FrameLayout container);

    /**
     * Find container with specified tag and switch to it directly
     *
     * @param tag target container`s tag
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goFastToContainerWithTag(Object tag);

    /**
     * Switch to first container in container collection
     *
     * @param goFast if true will used fast switch
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goToFirst(boolean goFast);

    /**
     * Switch to last container in container collection
     *
     * @param goFast if true will used fast switch
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goToLast(boolean goFast);

    /**
     * Switch to next container in container collection
     *
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goToNext();

    /**
     * Switch to previous container in container collection
     *
     * @return True if switching started or False if cannot start switching
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goToPrevious();

    /**
     * Switch to -1 position - all containers will hidden
     *
     * @param goFast if true will used fast switch
     * @param setVisibilityGone set FrameController visibility as GONE after the going out
     * @return True if switching started or False if already in out
     *         (Also may return False if invoked when Swipe or Scroll animations running)
     */
    boolean goOut(boolean goFast, boolean setVisibilityGone);
}

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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class FCController {

    private Context context;
    Config config;
    private List<FrameLayout> listContainers;

    FCController(Context context) {
        this.context = context;
        config = new Config();
        listContainers = new ArrayList<>();
    }

    //========================================= Views builders =====================================

    FrameLayout createNewContainer() {
        FrameLayout container = new FrameLayout(context);
        container.setVisibility(View.INVISIBLE);

        if (config.useDrawable) container.setBackground(config.backgroundDrawable);
        else container.setBackgroundColor(config.backgroundColor);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(config.margins[0], config.margins[1], config.margins[2], config.margins[3]);

        container.setLayoutParams(lp);
        container.setPadding(config.paddings[0], config.paddings[1], config.paddings[2], config.paddings[3]);
        container.setClickable(true);
        container.setFocusable(true);
        container.setLongClickable(true);

        listContainers.add(container);
        return container;
    }

    //================================== Working with Containers ===================================

    List<FrameLayout> getListContainers() {
        return listContainers;
    }

    void rmContainer(View container) {
        listContainers.remove(container);
    }

    void rmAll() {
        listContainers.clear();
    }

    void addToList(FrameLayout cont) {listContainers.add(cont);}

    int cCount() {
        return listContainers.size();
    }

    //======================================== Drawing =============================================

    private void reBackground() {
        if (listContainers != null && listContainers.size() != 0) {
            for (View container : listContainers) {
                if (config.useDrawable) container.setBackground(config.backgroundDrawable);
                else container.setBackgroundColor(config.backgroundColor);
            }
        }
    }

    private void reMargins() {
        if (listContainers != null && listContainers.size() != 0) {
            for (View container : listContainers) {

                LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                margins.setMargins(config.margins[0], config.margins[1], config.margins[2], config.margins[3]);
                container.setLayoutParams(margins);
            }
        }
    }

    private void rePaddings() {
        if (listContainers != null && listContainers.size() != 0) {
            for (View container : listContainers) {
                container.setPadding(config.paddings[0], config.paddings[1], config.paddings[2], config.paddings[3]);
            }
        }
    }

    //===================================== Configuration ==========================================

    class Config {

        private boolean useDrawable;
        private Drawable backgroundDrawable;
        private int backgroundColor;
        private int animation;
        private int speed;

        private int[] margins;
        private int[] paddings;

        Config() {
            setDefaultConfig();
        }

        void setDefaultConfig() {
            useDrawable = false;
            animation = -1;
            speed = 1;
            backgroundColor = Color.argb(255, 255, 255, 255);
            setMargins(0,0,0,0);
            setPaddings(0, 0, 0, 0);
        }

        void setBackgroundDrawable(Drawable drawable) {
            useDrawable = true;
            this.backgroundDrawable = drawable;
            reBackground();
        }

        void setBackgroundColor(int color) {
            useDrawable = false;
            this.backgroundColor = color;
            reBackground();
        }

        void setMargins(int left, int top, int right, int bottom) {
            margins = new int[4];
            margins[0] = left;
            margins[1] = top;
            margins[2] = right;
            margins[3] = bottom;
            reMargins();
        }

        void setPaddings(int left, int top, int right, int bottom) {
            paddings = new int[4];
            paddings[0] = left;
            paddings[1] = top;
            paddings[2] = right;
            paddings[3] = bottom;
            rePaddings();
        }

        void setSwitchAnimation(int type) {
            this.animation = type;
        }

        int getSwitchAnimation() {
            return animation;
        }

        void setAnimationSpeed(int speed) {
            this.speed = speed;
        }

        int getAnimationSpeed() {
            return speed;
        }
    }
}
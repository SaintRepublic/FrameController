package com.saintrepublic.framecontrollersample;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.saintrepublic.framecontroller.Controller;
import com.saintrepublic.framecontroller.FrameController;

public class MainActivity extends AppCompatActivity {

    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Get instance of FrameController class
        // specified in the activity_main layout
        // and get interface "Controller" of it
        controller = findViewById(R.id.frameController);

        // Set some preferences
        controller.setContainersBackground(ContextCompat.getColor(this, R.color.colorPurple));
        controller.setContainersMargins(30);

        // Set the animation for switching container
        controller.setSwitchAnimation(Controller.ANIMATION_MOVE_RIGHT);
        controller.setAnimationSpeed(Controller.SPEED_NORMAL);

        // Set new switch listener
        controller.setSwitchListener(new FrameController.OnSwitchListener() {

            /**
             * @param currentContainer It is a container that was opened
             *                         before switching was started (may be null)
             *
             * @param position It is a position of container that was opened
             *                 before switching was started (may be -1)
             */
            @Override
            public void onSwitchStarted(@Nullable FrameLayout currentContainer, int position) {

                // Do something when switching is started
            }

            /**
             * @param targetContainer It is a container that was required
             *                        (may be null if was invoked goOut or all containers was removed)
             *
             * @param position It is a position of container that was required
             *                 (may be -1 if was invoked goOut or all containers was removed)
             */
            @Override
            public void onTargetReached(@Nullable FrameLayout targetContainer, int position) {

                // Do something when required container becomes visible

                Toast.makeText(getApplicationContext(), "Target in position "+position+" reached", Toast.LENGTH_SHORT).show();
            }

            /**
             * Triggered when all switching animations ends
             *
             * @param isOut true if out reached (all containers becomes invisible) else false
             */
            @Override
            public void onAnimationEnds(boolean isOut) {

            }
        });

        // Create new view
        Button newButton = new Button(this);
        newButton.setText("Add layout 1");
        newButton.setOnClickListener(newBtnClick);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;

        newButton.setLayoutParams(lp);

        // Create first container and add our view to it
        controller.addViewToNewContainer(newButton);
    }

    /**
     * Create second container by inflating layout_1 and go to next container
     */
    private View.OnClickListener newBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            controller.addLayoutToNewContainer(R.layout.layout_1);
            controller.goToNext();
        }
    };

    /**
     * Create one more container by inflating layout_2 with tag "layout 2" in position 1
     * and go to first existing container with this tag
     */
    public void btnLayout1Click(View v) {

        controller.addLayoutToNewContainer(R.layout.layout_2, 1, "layout 2");
        controller.goToContainerWithTag("layout 2");
    }

    /**
     * Go back to position -1 (all containers will hidden)
     */
    public void btnLayout2Click(View v) {

       controller.goToLast(false);
    }

    /**
     * Go to next container until last container will reached
     * When last container is reached - go to first container
     */
    public void btnNextClick(View v) {

        if (!controller.isCurrentLast())
            controller.goToNext();
        else
            controller.goToFirst(false);
    }

}

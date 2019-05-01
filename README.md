# FrameController
FrameController is a library that will help you to work with multi-layer app interfaces.

Version: `1.0.3`

<img src="https://github.com/SaintRepublic/FrameController/blob/master/sample/framecontroller.gif" width="250">

## How to use
Implement it in your project via Gradle:
```java
implementation 'com.saintrepublic.framecontroller:framecontroller:1.0.3'
```

Create new instance in your activity:
```java
FrameController frameController = new FrameController(context);
```
or use it in your xml layout:
```xml
<com.saintrepublic.framecontroller.FrameController
        android:id="@+id/frameController"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

Then in your activity create a Controller interface instance to work with your FrameController:
```java
Controller controller = findViewById(R.id.frameController);
```

*If you include views into FrameController in xml layout, make sure that FrameController contains only FrameLayout child!*

## Example
```java
public class MainActivity extends AppCompatActivity implements FrameController.OnSwitchListener {

Controller controller;

@Override
protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     controller = findViewById(R.id.frameController);
     controller.setSwitchAnimation(Controller.ANIMATION_SWIPE);

     controller.addLayoutToNewContainer(R.layout.layout_1);
     controller.addLayoutToNewContainer(R.layout.layout_2);
     controller.addLayoutToNewContainer(R.layout.layout_3, "Third container`s tag");
}

public void onClick(View clickedButton) {
     
     controller.goToContainerWithTag("Third container`s tag");
}

@Override
public void onSwitchStarted(@Nullable FrameLayout currentContainer, int currentPosition) {

}

@Override
public void onTargetReached(@Nullable FrameLayout targetContainer, int targetPosition) {
    
    Toast.makeText(getApplicationContext(), "Target in position "+targetPosition+" reached", Toast.LENGTH_SHORT).show();
}

}
```
## Attention
This library contains an Animus library. [Check it on GitHub.](https://github.com/SaintRepublic/Animus)

Animus is an another library developed by SaintRepublic.

If you implement both of them you can remove Animus library and use Animus that packed into FrameController.

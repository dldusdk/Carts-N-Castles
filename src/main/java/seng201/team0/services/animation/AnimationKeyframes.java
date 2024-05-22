package seng201.team0.services.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
public class AnimationKeyframes {
    private static boolean done;
        public static void swapImagesWithDelay(ImageView imageView, Image newImage, Duration delay) {
                imageView.setImage(newImage);
                Timeline timeline = new Timeline(new KeyFrame(delay, event -> imageView.setImage(newImage)));
                timeline.play();
        }

        public static void addHideAnimation(ImageView imageView, Duration delay) {
            Timeline timeline = new Timeline(new KeyFrame(delay, event -> imageView.setVisible(false)));
            timeline.play();
        }
}


package seng201.team0.services.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * This class allows for images to be feed in and animated in a timeline,
 * so they can play in a sequential order and with a duration in seconds.
 *
 * <p>Timeline and Keyframe classes allow for animations to be independent of framerate
 *  which is great for handling simple animations.
 *  Although it is currently just used for the explosion of the cart,
 *  there are plenty more applications. </p>
 *
 * @author Gordon Homewood
 *
 * */
public class AnimationKeyframes {
    /**
     * Swaps images with a given delay, allowing them to be played sequentially.
     *
     * @param imageView container for image
     * @param newImage next image in frame sequence
     * @param delay usually provided in seconds, so animation can be altered to play
     *              at different rates
     *
     * @author Gordon Homewood
     */
        public static void swapImagesWithDelay(ImageView imageView, Image newImage, Duration delay) {

                imageView.setImage(newImage);
                Timeline timeline = new Timeline(new KeyFrame(delay, event -> imageView.setImage(newImage)));
                timeline.play();
        }
    /**
     * Hides the animation or final frame on when called, useful when moving things off-screen
     *
     * @param imageView image to be hidden
     * @param delay Duration before hide
     * @author Gordon Homewood
     */
        public static void addHideAnimation(ImageView imageView, Duration delay) {

            Timeline timeline = new Timeline(new KeyFrame(delay, event -> imageView.setVisible(false)));
            timeline.play();
        }
}


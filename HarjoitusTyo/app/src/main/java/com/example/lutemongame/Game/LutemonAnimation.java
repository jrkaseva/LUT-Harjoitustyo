package com.example.lutemongame.Game;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;

import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.R;

@SuppressWarnings("ALL")
public class LutemonAnimation {
    private Animation fightAnimation;
    private Animation leftAnimation;
    private Animation rightAnimation;
    private Animation zoomAnimation;
    private Animation slideAnimation;
    private Animation slideFromRightAnimation;
    private Animation fadeAnimation;
    private Animation bounceAnimation;
    private Animation rotateAnimation;
    private Animation seqAnimation;
    private Animation hitRightAnimation;
    private Animation hitLeftAnimation;

    private Animation favoriteAnimation;
    // Constructors
    public LutemonAnimation() {
    }

    public LutemonAnimation(Context context) {
        this.fightAnimation = AnimationUtils.loadAnimation(context, R.anim.fight_animation);
        this.leftAnimation = AnimationUtils.loadAnimation(context, R.anim.move_left_animation);
        this.rightAnimation = AnimationUtils.loadAnimation(context, R.anim.move_right_animation);
        this.zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_animation);
        this.slideAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_animation);
        this.slideFromRightAnimation = AnimationUtils.loadAnimation(context, R.anim.slidefromright_animation);
        this.fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
        this.bounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
        this.rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
        this.seqAnimation = AnimationUtils.loadAnimation(context, R.anim.sequential_animation);
        this.hitRightAnimation = AnimationUtils.loadAnimation(context, R.anim.move_hit_right_animation);
        this.hitLeftAnimation = AnimationUtils.loadAnimation(context, R.anim.move_hit_left_animation);
    }

    public void setFavoriteAnimation(Lutemon creatures,Context context){
        switch (creatures.getColor()){
            case "Black":
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
            case "Pink":
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
            case "Orange":
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
            case "Green":
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
            case "White":
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
            default:
                this.favoriteAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            break;
        }
    }

    public void setSlideFromRightAnimation(Animation slideFromRightAnimation) {
        this.slideFromRightAnimation = slideFromRightAnimation;
    }

    public Animation getFavoriteAnimation() {
        return favoriteAnimation;
    }


    public Animation getFightAnimation() {
        return fightAnimation;
    }

    public void setFightAnimation(Animation fightAnimation) {
        this.fightAnimation = fightAnimation;
    }

    public Animation getLeftAnimation() {
        return leftAnimation;
    }

    public void setLeftAnimation(Animation leftAnimation) {
        this.leftAnimation = leftAnimation;
    }

    public Animation getRightAnimation() {
        return rightAnimation;
    }

    public void setRightAnimation(Animation rightAnimation) {
        this.rightAnimation = rightAnimation;
    }

    public Animation getZoomAnimation() {
        return zoomAnimation;
    }

    public void setZoomAnimation(Animation zoomAnimation) {
        this.zoomAnimation = zoomAnimation;
    }

    public Animation getSlideAnimation() {
        return slideAnimation;
    }

    public void setSlideAnimation(Animation slideAnimation) {
        this.slideAnimation = slideAnimation;
    }
    public Animation getSlideFromRightAnimation() {
        return slideFromRightAnimation;
    }

    public void getSlideFromRightAnimation(Animation getSlideFromRightAnimation) {
        this.slideFromRightAnimation = slideFromRightAnimation;
    }
    public Animation getFadeAnimation() {
        return fadeAnimation;
    }

    public void setFadeAnimation(Animation fadeAnimation) {
        this.fadeAnimation = fadeAnimation;
    }

    public Animation getBounceAnimation() {
        return bounceAnimation;
    }

    public void setBounceAnimation(Animation bounceAnimation) {
        this.bounceAnimation = bounceAnimation;
    }

    public Animation getRotateAnimation() {
        return rotateAnimation;
    }

    public void setRotateAnimation(Animation rotateAnimation) {
        this.rotateAnimation = rotateAnimation;
    }

    public Animation getSeqAnimation() {
        return seqAnimation;
    }

    public void setSeqAnimation(Animation seqAnimation) {
        this.seqAnimation = seqAnimation;
    }
    public Animation getHitRightAnimation() {
        return hitRightAnimation;
    }

    public void setHitRightAnimation(Animation hitRightAnimation) {
        this.hitRightAnimation = hitRightAnimation;
    }
    public Animation getHitLeftAnimation() {
        return hitLeftAnimation;
    }

    public void setHitLeftAnimation(Animation hitLeftAnimation) {
        this.hitLeftAnimation = hitLeftAnimation;
    }

    @NonNull
    @Override
    public String toString() {
        return "LutemonAnimation{" +
                "fightAnimation=" + fightAnimation +
                ", leftAnimation=" + leftAnimation +
                ", rightAnimation=" + rightAnimation +
                ", zoomAnimation=" + zoomAnimation +
                ", slideAnimation=" + slideAnimation +
                ", fadeAnimation=" + fadeAnimation +
                ", bounceAnimation=" + bounceAnimation +
                ", rotateAnimation=" + rotateAnimation +
                '}';
    }

    public void loserBoogie(){
        Animation mani = new RotateAnimation(0.0f,360.0f,0.5f,0.5f);

    }

    public void winningDance(){

    }
    public void fight(){

    }
}

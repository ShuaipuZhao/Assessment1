package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Slime {
    float x;
    float y;
    Rectangle body;
    float w;
    float h;

    private Texture SlimeSheet;
    private TextureRegion[] SlimeFrames;
    private Animation SlimeAnimation;
    private TextureRegion SlimecurrentFrame;
    private float SlimestateTime;

    public Slime(float x, float y){
        this.x = x;
        this.y = y;
        create();
    }

    public void create(){

        SlimeSheet=new Texture(Gdx.files.internal("slime.png"));
        TextureRegion[][] temp = TextureRegion.split(SlimeSheet, SlimeSheet.getWidth() / 2, SlimeSheet.getHeight() / 1);
        SlimeFrames = new TextureRegion[2 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                SlimeFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        SlimeAnimation=new Animation(0.4f,SlimeFrames);
        SlimestateTime=0.0f;

        this.w = 160f;
        this.h = 80f;
        body = new Rectangle();
        body.setSize(w,h);
        body.setPosition(x,y);
    }


    public void draw(float x, SpriteBatch batch, float delta){

        SlimecurrentFrame = (TextureRegion) SlimeAnimation.getKeyFrame(SlimestateTime, true);
        SlimestateTime += delta;
        this.x += x;
        body.setPosition(this.x,this.y);
        Gdx.app.log("x","x: " + this.x);
        batch.draw(SlimecurrentFrame, this.x, this.y);
    }
}

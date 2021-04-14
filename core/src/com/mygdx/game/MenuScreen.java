package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
    MyGdxGame game;

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    public MenuScreen(MyGdxGame game){

        this.game=game;
    }

    public void create(){

        Gdx.app.log("MenuScreen","menuScreen create");

        batch = new SpriteBatch();
        //components style
        skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        stage = new Stage();

        //a click button
        final TextButton PlayButton = new TextButton("Play", skin, "default");
        final TextButton ExitButton = new TextButton("Exit", skin, "default");
        final Label GameName=new Label("Super Brits",skin,"default");

        //set the format for words
        GameName.setFontScale(5.0f);
        PlayButton.getLabel().setFontScale(3.0f);
        ExitButton.getLabel().setFontScale(3.0f);

        //set the size of button
        PlayButton.setWidth(400f);
        PlayButton.setHeight(100f);
        PlayButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 50f);

        ExitButton.setWidth(400f);
        ExitButton.setHeight(100f);
        ExitButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 200f);

        GameName.setWidth(400f);
        GameName.setHeight(100f);
        GameName.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 + 150f);

        //add button to the stage
        stage.addActor(PlayButton);
        stage.addActor(ExitButton);
        stage.addActor(GameName);
        Gdx.input.setInputProcessor(stage);

        //click event handler for buttons
        PlayButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
            }
        });

        ExitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float f) {
        //UI
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ","menuScreen show called");
        create();
    }

    @Override
    public void hide() {

        Gdx.app.log("MenuScreen: ","menuScreen hide called");
    }


}
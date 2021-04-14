package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

public class MyGdxGame extends Game implements ApplicationListener {


	// The class with the menu
	public static MenuScreen menuScreen;

	// The class with the game
	public static GameScreen gameScreen;

	
	@Override
	public void create () {

		//implement multiple screen game
		Gdx.app.log("MyGdxGame: "," create");
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		Gdx.app.log("MyGdxGame: ","about to change screen to menuScreen");
		// Change screens to the menu
		setScreen(menuScreen);
		Gdx.app.log("MyGdxGame: ","changed screen to menuScreen");



	}

	@Override
	public void render () {
		super.render();


	}
	
	@Override
	public void dispose () {

		super.dispose();
	}

	@Override
	public void resize(int width, int height) {

		super.resize(width, height);
	}

	@Override
	public void pause () {

		super.pause();
	}

	@Override
	public void resume () {

		super.resume();
	}
}

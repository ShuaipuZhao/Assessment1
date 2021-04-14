

package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.JViewport;

public class GameScreen implements Screen {
    //running page divide to 4*2
    private static final int FRAME_COLS =4;
    private static final int FRAME_ROWS =2;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private Animation walkAnimation;
    private TextureRegion currentFrame;
    private int frameIndex;
    private float stateTime;

    private Music music;
    private Sound sound;

    private Stage stage;

    SpriteBatch spriteBatch;
    SpriteBatch UiBatch;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    MyGdxGame game;

    public GameScreen(MyGdxGame game) {

        this.game = game;
    }
    public void create() {
        Gdx.app.log("GameScreen: ","gameScreen create");

        //Rendering
        spriteBatch=new SpriteBatch();
//        UiBatch=new SpriteBatch();

        //Initiate the TiledMap and its renderer
        tiledMap = new TmxMapLoader().load("level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);



        //take out each frame put into to array to display
        walkSheet=new Texture(Gdx.files.internal("running.png"));
        TextureRegion[][] temp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        walkAnimation=new Animation(0.033f,walkFrames);
        stateTime=0.0f;



          music=Gdx.audio.newMusic(Gdx.files.internal("fanfare.ogg"));
          music.setLooping(true);
          music.play();
//          sound=Gdx.audio.newSound(Gdx.files.internal(""));



  }
    public void render(float f) {
        Gdx.app.log("GameScreen: ","gameScreen render");
        Gdx.app.log("GameScreen: ","About to call gameScreen");
        Gdx.app.log("GameScreen: ","menuScreen started");


        //Clear the screen before drawing.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); //Allows transparent sprites/tiles
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

        // Render Map Here
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


//        // Change screens to the menu
//        Gdx.app.log("MyGdxGame: ","changed screen to menuScreen");
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        camera.update();

        spriteBatch.draw(currentFrame,1,1);
		spriteBatch.end();


    }

    @Override
    public void dispose() {
        if(music!=null){
            music.dispose();
        }
        spriteBatch.dispose();

        // Dispose of the TileMap during application disposal
        tiledMap.dispose();


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
        Gdx.app.log("GameScreen: ","gameScreen show called");
        create();
    }
    
    @Override
    public void hide() {

        Gdx.app.log("GameScreen: ","gameScreen hide called");
    }
}
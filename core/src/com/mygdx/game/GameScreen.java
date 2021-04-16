

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.DropMode;
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

    private Texture JumpSheet;
    private TextureRegion[] JumpFrames;
    private Animation JumpAnimation;
    private TextureRegion JumpcurrentFrame;
    private float JumpstateTime;

    private Texture DropSheet;
    private TextureRegion[] DropFrames;
    private Animation DropAnimation;
    private TextureRegion DropcurrentFrame;
    private float DropstateTime;



    private Texture DeadSheet;
    private TextureRegion[] DeadFrames;
    private Animation DeadAnimation;
    private TextureRegion DeadcurrentFrame;
    private float DeadstateTime;

    private Texture BeeSheet;
    private TextureRegion[] BeeFrames;
    private Animation BeeAnimation;
    private TextureRegion BeecurrentFrame;
    private float BeestateTime;

    private  float heightM,widthM;

    private Music music;
    private Sound sound;

    private Stage stage;
    private Skin skin;


    SpriteBatch spriteBatch;
    SpriteBatch UiBatch;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    MyGdxGame game;

    private float playerX;
    private float playerY;
    private float slimeX=400f;

    enum GameSta{
        RUN,
        PAUSE,
        JUMP,
        DROP,
        FINISH,
        DEAD
    }

    GameSta state;
    GameSta prestate;

    TextButton PauseButton;
    TextButton NextButton;
    TextButton RestartButton;
    TextButton BackButton;
    TextButton JumpButton;
    Label Finishtext;

    Rectangle body;

    Slime s;
    public GameScreen(MyGdxGame game) {

        this.game = game;
    }
    public void create() {

        state = GameSta.RUN;
        prestate = state;
        Gdx.app.log("GameScreen: ","gameScreen create");


        //Rendering
        spriteBatch=new SpriteBatch();
        skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        stage = new Stage();



        final TextButton ContinueButton = new TextButton("Continue", skin, "default");
        ContinueButton.getLabel().setFontScale(3.0f);
        ContinueButton.setWidth(400f);
        ContinueButton.setHeight(100f);
        ContinueButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 + 250f);
        stage.addActor(ContinueButton);
        Gdx.input.setInputProcessor(stage);
        ContinueButton.setVisible(false);
        ContinueButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {

                state = prestate;
                ContinueButton.setVisible(false);
                PauseButton.setVisible(true);
            }
        });
        Finishtext = new Label("You Win",skin,"default");
        Finishtext.setFontScale(5.0f);
        Finishtext.setWidth(400f);
        Finishtext.setHeight(100f);
        Finishtext.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 + 150f);
        Finishtext.setVisible(false);


        RestartButton = new TextButton("Restart", skin, "default");
        RestartButton.getLabel().setFontScale(3.0f);
        RestartButton.setWidth(400f);
        RestartButton.setHeight(100f);
        RestartButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 100f);
        RestartButton.setVisible(false);
        stage.addActor(RestartButton);

        RestartButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
            }
        });

        NextButton = new TextButton("Next Level", skin, "default");
        NextButton.getLabel().setFontScale(3.0f);
        NextButton.setWidth(400f);
        NextButton.setHeight(100f);
        NextButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 250f);
        NextButton.setVisible(false);
        stage.addActor(NextButton);

        NextButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {

            }
        });

        BackButton = new TextButton("Back to menu", skin, "default");
        BackButton.getLabel().setFontScale(3.0f);
        BackButton.setWidth(400f);
        BackButton.setHeight(100f);
        BackButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 400f);
        BackButton.setVisible(false);
        stage.addActor(BackButton);

        BackButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
            }
        });


        PauseButton = new TextButton("Pause", skin, "default");
        JumpButton = new TextButton("Jump", skin, "default");
        PauseButton.getLabel().setFontScale(3.0f);
        PauseButton.setWidth(400f);
        PauseButton.setHeight(100f);
        PauseButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 + 250f);
        stage.addActor(PauseButton);
        JumpButton.getLabel().setFontScale(3.0f);
        JumpButton.setWidth(1000f);
        JumpButton.setHeight(100f);
        JumpButton.setPosition(Gdx.graphics.getWidth()/2 - 500f, Gdx.graphics.getHeight()/2 - 500f);
        stage.addActor(JumpButton);
        Gdx.input.setInputProcessor(stage);

        PauseButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                state = GameSta.PAUSE;
                PauseButton.setVisible(false);
                ContinueButton.setVisible(true);
            }
        });

        JumpButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                // jump
                state = GameSta.JUMP;
                prestate = GameSta.JUMP;
                sound.play(20f);
            }
        });


        //Initiate the TiledMap and its renderer
        tiledMap = new TmxMapLoader().load("level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);
        camera.position.y=600;
        camera.position.x = w/4;
        playerX = camera.position.x;
        playerY = camera.position.y;


        body = new Rectangle();
        body.setSize(256f,256f);
        body.setPosition(playerX,playerY);


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


        //take out each frame put into to array to display
        JumpSheet=new Texture(Gdx.files.internal("jumping start.png"));
        temp = TextureRegion.split(JumpSheet, JumpSheet.getWidth() / 3, JumpSheet.getHeight() / 1);
        JumpFrames = new TextureRegion[3 * 1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                JumpFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        JumpAnimation=new Animation(0.2f,JumpFrames);
        JumpstateTime=0.0f;




        DropSheet=new Texture(Gdx.files.internal("jumping end.png"));
        temp = TextureRegion.split(DropSheet, DropSheet.getWidth() / 2, DropSheet.getHeight() / 1);
        DropFrames = new TextureRegion[2 * 1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                DropFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        DropAnimation=new Animation(0.4f,DropFrames);
        DropstateTime=0.0f;





        BeeSheet=new Texture(Gdx.files.internal("bee.png"));
        temp = TextureRegion.split(BeeSheet, BeeSheet.getWidth() / 2, BeeSheet.getHeight() / 1);
        BeeFrames = new TextureRegion[2 * 1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                BeeFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        BeeAnimation=new Animation(0.4f,BeeFrames);
        BeestateTime=0.0f;

        DeadSheet=new Texture(Gdx.files.internal("deading.png"));
        temp = TextureRegion.split(DeadSheet, DeadSheet.getWidth() / 3, DeadSheet.getHeight() / 1);
        DeadFrames = new TextureRegion[3 * 1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                DeadFrames[index++] = temp[i][j];
            }
        }
        //set the speed for each frame
        DeadAnimation=new Animation(0.4f,DeadFrames);
        DeadstateTime=0.0f;



        music=Gdx.audio.newMusic(Gdx.files.internal("fanfare.ogg"));
        music.setLooping(true);
        music.play();
        //Boing Raw Copyright 2005 cfork <http://freesound.org/people/cfork/> Boing Jump Copyright 2012 Iwan Gabovitch <http://qubodup.net>
        sound=Gdx.audio.newSound(Gdx.files.internal("qubodup-cfork-ccby3-jump.ogg"));


        s = new Slime(1000f,Gdx.graphics.getWidth() / 4-135f);
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



        if(state == GameSta.RUN ){
            stateTime += Gdx.graphics.getDeltaTime();
            camera.position.x += 10;
            playerX = camera.position.x - Gdx.graphics.getWidth() / 4;
            slimeX= -1.5f;
        }else if(state == GameSta.JUMP){
            JumpcurrentFrame = (TextureRegion) JumpAnimation.getKeyFrame(JumpstateTime, false);
            JumpstateTime += Gdx.graphics.getDeltaTime();
            camera.position.x += 10;
            playerX = camera.position.x - Gdx.graphics.getWidth() / 4;
            slimeX= -1.5f;
        } else if(state == GameSta.DROP){
            DropcurrentFrame = (TextureRegion) DropAnimation.getKeyFrame(DropstateTime, false);
            DropstateTime += Gdx.graphics.getDeltaTime();
            camera.position.x += 10;
            playerX = camera.position.x - Gdx.graphics.getWidth() / 4;
            slimeX= -1.5f;
        }else if(state == GameSta.PAUSE){
            stateTime += 0;
            camera.position.x += 0;
            playerX = camera.position.x - Gdx.graphics.getWidth() / 4;
            slimeX-=0;
        }

        if(state == GameSta.RUN){
            playerY = Gdx.graphics.getHeight() /2-130f;
        }else if(state == GameSta.JUMP){

            playerY += 4;
            if(playerY > 600){
                state = GameSta.DROP;
                prestate = GameSta.DROP;
                DropcurrentFrame = (TextureRegion) DropAnimation.getKeyFrame(DropstateTime, false);
                // reset jump animation
                JumpstateTime=0.0f;
            }
        }else if(state == GameSta.DROP){
            playerY -= 4;
            if(playerY <= Gdx.graphics.getHeight() /2-130f){
                state = GameSta.RUN;
                prestate = GameSta.RUN;
                DropstateTime=0.0f;
            }
        }
       if(playerX + Gdx.graphics.getWidth() >=17500f){
           state=GameSta.FINISH;
           stateTime += 0;
           camera.position.x += 0;
           NextButton.setVisible(true);
           RestartButton.setVisible(true);
           BackButton.setVisible(true);
           JumpButton.setVisible(false);
           PauseButton.setVisible(false);
       }

        camera.update();
        body.setPosition(playerX,playerY);
        //Gdx.app.log("location:","x: "+body.x + "y: "+body.y );



        if(state == GameSta.RUN || state == GameSta.PAUSE|| state == GameSta.FINISH){
            if(state == GameSta.FINISH){

            }
            spriteBatch.draw(currentFrame, playerX , playerY);

        }else if(state == GameSta.JUMP){

            spriteBatch.draw(JumpcurrentFrame, playerX , playerY);
        }
        else if(state == GameSta.DROP){

            spriteBatch.draw(DropcurrentFrame, playerX , playerY);
        }


        if(body.contains(s.body) == true){
            state = GameSta.DEAD;
            Gdx.app.log("hit","Hittttttt");
        }
        s.draw(slimeX,spriteBatch,f);
        //spriteBatch.draw(SlimecurrentFrame, Gdx.graphics.getWidth()*5+500f+slimeX, Gdx.graphics.getWidth() / 4-135f);
        //spriteBatch.draw(SlimecurrentFrame, Gdx.graphics.getWidth()*3+1500f+slimeX, Gdx.graphics.getWidth() / 4-135f);
		spriteBatch.end();

        spriteBatch.begin();
        stage.draw();
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
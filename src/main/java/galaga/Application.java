package galaga;

import galaga.UI.Button;
import galaga.constants.InputMode;
import galaga.engine.Engine;
import galaga.engine.InputHandler;
import galaga.player.Player;
import galaga.singletons.HighscoreHandler;
import galaga.singletons.SpriteHandler;
import galaga.util.Color;
import galaga.util.ExceptionHandler;
import galaga.util.InputsConfig;
import javafx.scene.input.KeyCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import processing.core.*;
import processing.event.KeyEvent;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import static galaga.util.EasyPrint.p;
import static galaga.util.EasyPrint.pcol;

public class Application extends PApplet {

    private InputHandler inputHandler;
    private Engine engine;
    private int prevMillis;
    private InputMode inputContext;
    private final int windowWidth = 600;
    private final int windowHeight = 800;
    static final File spriteDirectory = new File("src\\main\\resources\\galaga\\sprites");
    static final File highscoreDirectory = new File("scores\\");
    private final Button gameButton = new Button(new PVector(windowWidth/2, windowHeight-640+(900-windowHeight)), new PVector(256.0f, 128.0f), "PLAY");
    private final Button highscoreButton = new Button(new PVector(windowWidth/2, windowHeight-480+(900-windowHeight)), new PVector(340.0f, 128.0f), "HIGHSCORES");
    private final Button exitButton = new Button(new PVector(windowWidth/2, windowHeight-320+(900-windowHeight)), new PVector(256.0f, 128.0f), "EXIT");
    private final Button menuButton = new Button(new PVector(windowWidth/2, windowHeight-100), new PVector(256.0f, 128.0f), "BACK");
    private String inputBox = "";

    public void init() {
        try {
//            this.engine.init(this.inputHandler, windowWidth, windowHeight);

            // setup keys
            InputsConfig.load("src\\main\\resources\\galaga\\inputsConfig");

            for (String key : InputsConfig.getRightKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.RELEASE);
            }

            for (String key : InputsConfig.getLeftKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.RELEASE);
            }

            for (String key : InputsConfig.getUpKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.RELEASE);
            }

            for (String key : InputsConfig.getDownKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.RELEASE);
            }

            for (String key : InputsConfig.getShootKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.INGAME, KeyEvent.RELEASE);
            }

            for (String key : InputsConfig.getAcceptKey()) {
                if (KeyCode.getKeyCode(key) == null) throw new RuntimeException();
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.MAIN_MENU, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.ENDSCORE_MENU, KeyEvent.PRESS);
                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.HIGHSCORE_MENU, KeyEvent.PRESS);
//                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.MAIN_MENU, KeyEvent.RELEASE);
//                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.ENDSCORE_MENU, KeyEvent.RELEASE);
//                this.inputHandler.setupKey(KeyCode.getKeyCode(key).getCode(), InputMode.HIGHSCORE_MENU, KeyEvent.RELEASE);
            }

        } catch (Exception e) {
            ExceptionHandler.printCriticalInfo(e);
        }

        pcol(Color.CYAN, this.inputHandler.getKeyMap());
    }


    public void renderGameFrame(float delta) {
        // draw background
        this.image(SpriteHandler.getSprite("background").get(0), 0, -windowHeight + (millis()/4)%windowHeight);
        this.image(SpriteHandler.getSprite("background").get(0), 0, (millis()/4)%windowHeight);
        this.tint(255, 16.0f+64.0f*(float)(1+Math.cos(HALF_PI+0.4f*millis()/1000.0f))/2.0f);
        this.image(SpriteHandler.getSprite("planet").get(0), windowWidth/5+Math.round(4.0f*Math.sin(0.5f*millis()/1000.0f)),
                windowHeight-128+Math.round(12.0f*Math.cos(0.2f*millis()/1000.0f)));
        this.tint(255);
        // draw all objects
        this.engine.draw(this);
        // draw score
        this.fill(255, 127);
        this.textSize(48.0f);
        this.text("SCORE "+ Math.round(this.engine.getPlayer().getScore()), 16.0f, 32.0f);
        // draw HP
        int playerHP = Math.round(this.engine.getPlayer().getShip().getHealth());

        for(int i = 0; i < 3; i++) { // draw 3 starting hearts
            if (i < playerHP)   this.image(SpriteHandler.getSprite("heart").get((millis()/500)%2), 16.0f + 32.0f*i, 48.0f);
            else                this.image(SpriteHandler.getSprite("empty-heart").get((millis()/500)%2), 16.0f + 32.0f*i, 48.0f);
        }
        for(int i = 3; i < playerHP; i++) { // draw armor hearts
            this.image(SpriteHandler.getSprite("armor-heart").get((millis()/500)%2), 16.0f + 32.0f*i, 48.0f);
        }
    }


    public void renderMenuFrame(float delta) {
        // draw background
        this.image(SpriteHandler.getSprite("background").get(0), 0, -windowHeight + (millis()/8)%windowHeight);
        this.image(SpriteHandler.getSprite("background").get(0), 0, (millis()/8)%windowHeight);
        // draw title
        this.fill(255, 255);
        this.textSize(80.0f);
        this.textAlign(CENTER, CENTER);
        this.text("EPIC SPACE GAME", windowWidth/2, 32.0f);
        // draw nickname
        this.textSize(32.0f);
        this.text("Type your nickname to start", windowWidth/2, 92.0f);
        if (this.inputBox.length() != 0) {
            this.text("Good luck", windowWidth/2, 108.0f);
        }
        this.textSize(80.0f);
        this.text(this.inputBox, windowWidth/2, 128.0f);
        // draw buttons
        this.textAlign(LEFT);
        PVector v, s;
        float mX = this.mouseX; float mY = this.mouseY;

        v = this.gameButton.getPosition();
        s = this.gameButton.getSize();
        this.gameButton.setHovered(         mX >= v.x-s.x/2 && mX < v.x+s.x/2 && mY > v.y-s.y/2 && mY < v.y+s.y/2);
        this.gameButton.draw(this);

        v = this.highscoreButton.getPosition();
        s = this.highscoreButton.getSize();
        this.highscoreButton.setHovered(    mX >= v.x-s.x/2 && mX < v.x+s.x/2 && mY > v.y-s.y/2 && mY < v.y+s.y/2);
        this.highscoreButton.draw(this);

        v = this.exitButton.getPosition();
        s = this.exitButton.getSize();
        this.exitButton.setHovered(         mX >= v.x-s.x/2 && mX < v.x+s.x/2 && mY > v.y-s.y/2 && mY < v.y+s.y/2);

        this.exitButton.draw(this);

        if (this.gameButton.isHovered() || this.highscoreButton.isHovered() || this.exitButton.isHovered()) cursor(HAND);
        else                                                                                                cursor(ARROW);

        // draw default settings tip
        this.textAlign(LEFT);
        this.textSize(32.0f);
        this.fill(255, 127);
        this.text("DEFAULT CONTROLS", 30.0f, windowHeight-144.0f+((900-windowHeight)*0.3f));
        this.textSize(24.0f-((900-windowHeight)*0.04f));
        float offset1 = (windowHeight/900.0f); float offset2 = (900-windowHeight)*0.1f;
        this.text("FIRE", 30.0f, windowHeight-120.0f*offset1+offset2);  this.text("ENTER or BACKSPACE", 110.0f, windowHeight-120.0f*offset1+offset2);
        this.text("UP", 30.0f, windowHeight-96.0f*offset1+offset2);     this.text("W or UPARROW", 110.0f, windowHeight-96.0f*offset1+offset2);
        this.text("LEFT", 30.0f, windowHeight-72.0f*offset1+offset2);   this.text("D or LEFTARROW", 110.0f, windowHeight-72.0f*offset1+offset2);
        this.text("RIGHT", 30.0f, windowHeight-48.0f*offset1+offset2);  this.text("A or RIGHTARROW", 110.0f, windowHeight-48.0f*offset1+offset2);
        this.text("DOWN", 30.0f, windowHeight-24.0f*offset1+offset2);   this.text("S or DOWNARROW", 110.0f, windowHeight-24.0f*offset1+offset2);
        this.fill(255, 255);
    }


    public void renderHighscoreFrame(float delta) {
        // draw background
        this.image(SpriteHandler.getSprite("background").get(0), 0, -windowHeight + (millis()/8)%windowHeight);
        this.image(SpriteHandler.getSprite("background").get(0), 0, (millis()/8)%windowHeight);

        // draw highscore container
        this.textSize(64.0f);
        this.rectMode(CENTER);
        this.textAlign(LEFT, CENTER);
        this.fill(255, 127);
        this.rect(this.windowWidth/2, this.windowHeight/2-88, this.windowWidth-64, this.windowHeight-224, 32);

        float offset = windowHeight/900.0f;
        for(int i = 0; i < Math.min(10, HighscoreHandler.getScores().size()); i++) {
            this.textAlign(CENTER, CENTER);
            this.text((i+1), 92, 48 + (35+32*offset)*offset*i);
            this.textAlign(LEFT, CENTER);
            this.text(HighscoreHandler.getScores().get(i).first, 132, 48 + (35+32*offset)*offset*i);
            this.text(HighscoreHandler.getScores().get(i).second, 320, 48 + (35+32*offset)*offset*i);
        }

        // draw buttons
        PVector v, s;
        float mX = this.mouseX; float mY = this.mouseY;

        v = this.menuButton.getPosition();
        s = this.menuButton.getSize();
        this.menuButton.setHovered(         mX >= v.x-s.x/2 && mX < v.x+s.x/2 && mY > v.y-s.y/2 && mY < v.y+s.y/2);
        this.menuButton.draw(this);

        if (this.menuButton.isHovered())    cursor(HAND);
        else                                cursor(ARROW);
    }


    public void renderEndscoreFrame(float delta) {
        this.rectMode(CORNER); // reset
        this.textAlign(LEFT, LEFT); // reset
        this.renderGameFrame(delta);

        // draw score container
        this.textSize(64.0f);
        this.rectMode(CENTER);
        this.textAlign(LEFT, CENTER);
        this.fill(255, 127);
        this.rect(this.windowWidth/2, this.windowHeight/2-128, this.windowWidth-64, this.windowHeight-420, 32);
        this.textAlign(CENTER);
        this.textSize(128.0f);
        this.text("YOU DIED", this.windowWidth/2, 160);
        this.textSize(48.0f);
        this.text("YOUR FINAL SCORE", this.windowWidth/2, 192);
        this.textSize(80.0f);
        this.text(Math.round(this.engine.getPlayer().getScore()), this.windowWidth/2, 250);
        if (HighscoreHandler.getScores().size() > 0) {
            this.text("TOP SCORE", this.windowWidth/2, 360);
            this.textSize(48.0f);
            this.text(HighscoreHandler.getScores().get(0).first + " " + HighscoreHandler.getScores().get(0).second, this.windowWidth/2, 392);
        }

        // draw buttons
        this.textAlign(LEFT, CENTER);
        PVector v, s;
        float mX = this.mouseX; float mY = this.mouseY;

        v = this.menuButton.getPosition();
        s = this.menuButton.getSize();
        this.menuButton.setHovered(         mX >= v.x-s.x/2 && mX < v.x+s.x/2 && mY > v.y-s.y/2 && mY < v.y+s.y/2);
        this.menuButton.draw(this);

        if (this.menuButton.isHovered())    cursor(HAND);
        else                                cursor(ARROW);
    }


    public void startGame() {
        cursor(ARROW); // reset
        this.rectMode(CORNER); // reset
        this.textAlign(LEFT, LEFT); // reset
        this.engine = new Engine(new Player(this.inputBox));
        this.engine.init(this.inputHandler, this.windowWidth, this.windowHeight);
        this.inputContext = InputMode.INGAME;
    }


    public void nicknameUpdate(KeyEvent event) {
        if (event.getKeyCode() == 8 && this.inputBox.length() > 0) { // backspace
            this.inputBox = this.inputBox.substring(0, this.inputBox.length()-1);
            return;
        }
        if (this.inputBox.length() == 4) return; // max 4 characters

        if (Pattern.matches("[a-zA-Z]", Character.toString(event.getKey())))
            this.inputBox += event.getKey();
    }

    // overrides

    @Override
    public void settings() {
        this.size(windowWidth, windowHeight);
    }

    @Override
    public void setup() {
        this.frameRate(60);
        // load sprites
        SpriteHandler.init();
        for (final File f : spriteDirectory.listFiles()) {
            PImage image = this.loadImage(f.getAbsolutePath());
            SpriteHandler.registerSprite(f.getName().split("_")[0], image);
        }
        pcol(Color.WEAK_CYAN, SpriteHandler.getSprites());
        // load highscores
        HighscoreHandler.init();
        for (final File f : highscoreDirectory.listFiles()) {
            try {
                // create json parser
                JSONParser parser = new JSONParser();
                // create JSONObject instance
                JSONObject obj = (JSONObject)parser.parse(new FileReader(f.getAbsolutePath()));
                // load settings
                JSONArray highscores = (JSONArray)obj.get("highscores");
                for(Object o : highscores) {
                    JSONObject score = (JSONObject)o;
                    HighscoreHandler.registerScore((String)score.get("nickname"), (int)(long)score.get("score"), false);
                }
            } catch (Exception e) {
                ExceptionHandler.printCriticalInfo(e);
            }
        }
        // load font
        PFont font = createFont("src\\main\\resources\\galaga\\font.ttf", 128, false);
        this.textFont(font);
        // init
        this.init();
    }

    @Override
    public void draw() {
        int millis = millis();
        float delta = (millis - prevMillis) / 1000.0f;
        prevMillis = millis;

        this.inputHandler.processEvents(this.inputContext, millis);

        switch (this.inputContext) {
            case INGAME -> {
                this.renderGameFrame(delta);
                this.engine.process(delta);
                if (this.engine.getPlayer().getShip().getHealth() <= 0) {
                    this.inputContext = InputMode.ENDSCORE_MENU;
                    // save data when player dies
                    HighscoreHandler.registerScore(this.engine.getPlayer().getNickname(), Math.round(this.engine.getPlayer().getScore()), true);
                }
            }
            case MAIN_MENU -> {this.renderMenuFrame(delta);}
            case HIGHSCORE_MENU -> {this.renderHighscoreFrame(delta);}
            case ENDSCORE_MENU -> {this.renderEndscoreFrame(delta); this.engine.process(delta);}
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
//        if (event.getKeyCode() == 10) pcol(Color.YELLOW, "ENTER");
//        else pcol(Color.YELLOW, event.getKey());
        if (this.inputContext == InputMode.MAIN_MENU)  {
            this.nicknameUpdate(event);
        }
        this.inputHandler.registerEvent(event.getKeyCode(), this.inputContext, KeyEvent.PRESS, millis());
    }

    @Override
    public void keyReleased(KeyEvent event) {
//        if (event.getKeyCode() == 10) pcol(Color.RED, "ENTER");
//        else pcol(Color.RED, event.getKey());
        this.inputHandler.registerEvent(event.getKeyCode(), this.inputContext, KeyEvent.RELEASE, millis());
    }

    /**
     *
     */
    @Override
    public void mouseClicked() {
        if (this.inputContext == InputMode.INGAME) return; // skip if in game
        // check each button
        if (this.gameButton.isHovered() && this.inputBox.length() != 0) {this.gameButton.setHovered(false); this.startGame(); return;}
        if (this.highscoreButton.isHovered()) {this.highscoreButton.setHovered(false); this.inputContext = InputMode.HIGHSCORE_MENU; return;}
        if (this.exitButton.isHovered()) {this.exit(); return;}

        if (this.menuButton.isHovered()) {
            this.menuButton.setHovered(false); this.inputContext = InputMode.MAIN_MENU; return;
        }
    }

    // constructors

    public Application() {
        this.inputHandler = new InputHandler();
        this.inputContext = InputMode.MAIN_MENU; // TODO menu
    }
}
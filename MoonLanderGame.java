package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private int score;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isGameStopped;




    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();


    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2, 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);

        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
    }


    private void createGame() {
        createGameObjects();
        setTurnTimer(50);

        score = 1000;
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;

        drawScene();
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellColor(i, j, Color.BLACK);
            }
        }

        landscape.draw(this);
        rocket.draw(this);


    }

    @Override
    public void onTurn(int step) {

        if (score > 0) score--;
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && y < HEIGHT - 1  && y >= 0 && x < WIDTH - 1){
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP:
                isUpPressed = true;
                break;
            case LEFT:
                isLeftPressed = true;
                isRightPressed = false;
                break;
            case RIGHT:
                isRightPressed = true;
                isLeftPressed = false;
                break;
            case SPACE:
                if(isGameStopped) createGame();
                break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {

        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
        else {
            switch (key) {
                case UP:
                    isUpPressed = false;
                    break;
                case LEFT:
                    isLeftPressed = false;
                    break;
                case RIGHT:
                    isRightPressed = false;
                    break;
            }
        }
    }


    private void check()
    {
        if (rocket.isCollision(platform) && rocket.isStopped()) {
            win();
        } else if (rocket.isCollision(landscape)) {
            gameOver();
        }
    }


    private void win()
    {
        isGameStopped = true;
        rocket.land();
        showMessageDialog(Color.ALICEBLUE, "YOU WIN !", Color.BISQUE, 50);
        stopTurnTimer();


    }

    private void gameOver()
    {
        isGameStopped = true;
        rocket.crash();
        showMessageDialog(Color.FIREBRICK, " IT'S  A PITY \n YOU LOSE", Color.ORANGE, 55);
        stopTurnTimer();
         score = 0;

    }


}
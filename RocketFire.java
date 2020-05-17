package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

import java.util.List;

public class RocketFire extends GameObject{

    private List<int[][]> frames;
    private int frameIndex = 0;
    private boolean isVisible;



    public RocketFire(List<int[][]> frames) {
        super(0, 0, frames.get(0));
        this.frames = frames;
    }

    private void nextFrame()
    {
        frameIndex++;
        if (frameIndex >= frames.size()) frameIndex = 0;
        matrix = frames.get(frameIndex);
    }

    @Override
    public void draw(Game game) {
        if (isVisible){
            nextFrame();
            super.draw(game);
        }
    }

    public void show(){
        isVisible = true;
    }
    public void hide(){
        isVisible = false;
    }
}

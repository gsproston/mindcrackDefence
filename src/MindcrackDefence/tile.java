package MindcrackDefence;

import java.awt.Rectangle;

public class tile {
    public float xpos, ypos;
    public boolean highlight = false;
    public boolean selected = false;
    public boolean containsEnemy = false;
    public boolean containsPlayer = false;
    public boolean movement = false;
    public int movesLeft = 0;
    public int player = -1;
    public int movesTo = -1;
    public int dist = -1;
    public tile(float newXpos, float newYpos) {
        xpos = newXpos;
        ypos = newYpos;
    }
    
    public float getXpos() {
        return xpos;
    }
    public float getYpos() {
        return ypos;
    }
    public boolean getHighlight() {
        return highlight;
    }
    public void setHighlight(boolean isHighlight) {
        highlight = isHighlight;
    }
    public boolean getSelected() {
        return selected;
    }
    public void setSelected(boolean isSelected) {
        selected = isSelected;
    }
    public boolean getContainsEnemy() {
        return containsEnemy;
    }
    public void setContainsEnemy(boolean nowContainsEnemy) {
        containsEnemy = nowContainsEnemy;
    }
    public boolean getContainsPlayer() {
        return containsPlayer;
    }
    public void setContainsPlayer(boolean nowContainsPlayer) {
        containsPlayer = nowContainsPlayer;
    }
    public boolean getMovement() {
        return movement;
    }
    public void setMovement(boolean newMovement) {
        movement = newMovement;
    }
    public int getMovesLeft() {
        return movesLeft;
    }
    public void setMovesLeft(int newMoves) {
        movesLeft = newMoves;
    }
    public int getPlayer() {
        return player;
    }
    public void setPlayer(int newPlayer) {
        player = newPlayer;
    }
    public int getMovesTo() {
        return movesTo;
    }
    public void setMovesTo(int moves) {
        movesTo = moves;
    }
    public int getDist() {
        return dist;
    }
    public void setDist(int newDist) {
        dist = newDist;
    }
    
}

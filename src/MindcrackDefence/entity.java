package MindcrackDefence;

public class entity {
    public float speed;
    public float xpos = 0F, ypos = 0F;
    private int arrayCount = 0;
    private int tilex, tiley;
    public int tile = 0;
    public int moves;
    public int arrayPos;
    public int moveArray[];
    public String path, name;
    public boolean moving = false;
        
    public void move(int tilx, int tily) {
        tilex = tilx;
        tiley = tily;
        if(moveArray[arrayCount] == 2) {
            moveNorth();
        } else if (moveArray[arrayCount] == 3) {
            moveEast();
        } else if (moveArray[arrayCount] == 0) {
            moveSouth();
        } else if (moveArray[arrayCount] == 1) {
            moveWest();
        } else {
            moving = false;
        }
    }
    public void moveNorth() {
        ypos = ypos-speed;
        if(ypos<-32) {
            ypos = 0;
            tile = tile-tilex;
            if(arrayCount>0) {
                arrayCount--;
            } else {
                moving = false;
            }
        }
    }
    public void moveEast() {
        xpos = xpos+speed;
        if(xpos>32) {
            xpos = 0;
            tile++;
            if(arrayCount>0) {
                arrayCount--;
            } else {
                moving = false;
            }
        }
    }
    public void moveSouth() {
        ypos = ypos+speed;
        if(ypos>32) {
            ypos = 0;
            tile = tile+tilex;
            if(arrayCount>0) {
                arrayCount--;
            } else {
                moving = false;
            }
        }
    }
    public void moveWest() {
        xpos = xpos-speed;
        if(xpos<-32) {
            xpos = 0;
            tile--;
            if(arrayCount>0) {
                arrayCount--;
            } else {
                moving = false;
            }
        }

    }
    public void nextTile() {
        if(arrayCount>0) {
            arrayCount--;
        } else {
            moving = false;
        }
    }
    
    public float getXpos() {
        return xpos;
    }
    public float getYpos() {
        return ypos;
    }
    public String getPath() {
        return path;
    }
    public String getName() {
        return name;
    }
    public int getTile() {
        return tile;
    }
    public int getMoves() {
        return moves;
    }
    public void setMoves(int newMoves) {
        moves = newMoves;
    }
    public void setTile(int newTile) {
        tile = newTile;
    }
    public int getArrayPos() {
        return arrayPos;
    }
    public void setArrayPos(int newArrayPos) {
        arrayPos = newArrayPos;
    }
    public void setMoveArray(int... array) {
        moveArray = array;
        arrayCount = moveArray.length-1;
    }
    public boolean getMoving() {
        return moving;
    }
    public void setMoving(boolean nowMoving) {
        moving = nowMoving;
    }
}

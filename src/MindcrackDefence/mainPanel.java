package MindcrackDefence;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class mainPanel extends JPanel implements ActionListener {
        JFrame frame;
        JPanel panel = this;
        private Timer timer;
        Toolkit t = Toolkit.getDefaultToolkit();
        PointerInfo p = MouseInfo.getPointerInfo();
        Point mouseLocation = p.getLocation();
        Point mouseCentre = new Point(500,500);
        Point hotSpot = new Point(0,0);
        public ArrayList mindcrackers = new ArrayList();
        public tile tile[];
        public map map = new map();
        mouseListener mouseListener = new mouseListener(this);
        private float xscale, yscale;
        public int highlightedTile, selectedTile, playerNum, entityArrayLength;
        private int mapNum = 0;
        private int row = 0, collumn = 0;
        public int tileSize = 32;
        private int oldSelect = -1, playersMoving = 0;
        private int moveArray[];
        private int entityType[], entityRef[], entityTile[];
        private String entityPath[];
        private boolean movement = false;
               
        //images        
        Image mapImage = t.getImage(map.getPath(mapNum));
        Image tileImage = t.getImage(System.getProperty("user.dir")+"/sprites/tiles/tile.png");
        Image highlightedTileImage = t.getImage(System.getProperty("user.dir")+"/sprites/tiles/highlighted.png");
        Image selectedTileImage = t.getImage(System.getProperty("user.dir")+"/sprites/tiles/selected.png");
        Image movementTileImage = t.getImage(System.getProperty("user.dir")+"/sprites/tiles/movement.png");
        
        public mainPanel(JFrame usedFrame) {
                frame = usedFrame;
                addKeyListener(new mainPanel.TAdapter());
                setSize(800,450);
        	setFocusable(true);
                requestFocusInWindow();
        	setDoubleBuffered(true);
        	setVisible(true);
                timer = new Timer(5, this);
        	timer.start(); 
                xscale = getWidth()/800F;
                yscale = getHeight()/450F;
                tile = new tile[map.getTilex(mapNum)*map.getTiley(mapNum)];
                //tile array
                for(int count=0; count<map.getTiley(mapNum); count++) {
                    for(int count2=0; count2<map.getTilex(mapNum); count2++) {
                        tile[map.getTilex(mapNum)*row+collumn] = (new tile(map.getTileXpos(mapNum)+tileSize*collumn, map.getTileYpos(mapNum)+tileSize*row));
                        collumn++;
                    }
                    collumn=0;
                    row++;
                }
                newMindcracker("test",68);
                newMindcracker("test2",85);
                sortEntities();
        }
        
        public void paint(Graphics g) {
                float changex, changey;
                super.paint(g);
        	Graphics2D g2 = (Graphics2D) g;
                AffineTransform originalTransform = g2.getTransform();
                
                g2.drawImage(mapImage,newXpos(map.getXpos(mapNum)),newYpos(map.getYpos(mapNum)),newXscale(mapImage.getWidth(panel)),newYscale(mapImage.getHeight(panel)),panel);
                g2.setColor(Color.gray);
                for(int count=0; count<(map.getTilex(mapNum)*map.getTiley(mapNum)); count++) {
                    g2.drawImage(tileImage, newXpos((int)tile[count].getXpos()), newYpos((int)tile[count].getYpos()), newXscale(tileSize), newYscale(tileSize), panel);
                    if(tile[count].getHighlight() == true && tile[count].getSelected() != true) {
                        g2.drawImage(highlightedTileImage, newXpos((int)tile[count].getXpos()), newYpos((int)tile[count].getYpos()), newXscale(tileSize), newYscale(tileSize), panel);
                    }
                    else if(tile[count].getSelected() == true) {
                        g2.drawImage(selectedTileImage, newXpos((int)tile[count].getXpos()), newYpos((int)tile[count].getYpos()), newXscale(tileSize), newYscale(tileSize), panel);
                    }
                    if(tile[count].getMovement() == true) {
                        movementTileImage = t.getImage(System.getProperty("user.dir")+"/sprites/tiles/movement.png");
                        g2.drawImage(movementTileImage, newXpos((int)tile[count].getXpos()), newYpos((int)tile[count].getYpos()), newXscale(tileSize), newYscale(tileSize), panel);
                    }
                }
                g2.setColor(Color.black);
                //player -tile 33,54
                for(int count=0; count<entityArrayLength; count++) {
                    if(entityType[count] == 0) { //player
                        player m = (player) mindcrackers.get(entityRef[count]);
                        changex = m.getXpos();
                        changey = m.getYpos();
                    } else {
                        changex = 0;
                        changey = 0;
                    }
                    Image entityImage = t.getImage(entityPath[count]);
                    g2.drawImage(entityImage, newXpos(tile[entityTile[count]].getXpos()-33+changex), newYpos(tile[entityTile[count]].getYpos()-54+changey), newXscale(entityImage.getWidth(panel)), newYscale(entityImage.getHeight(panel)), panel);
                }
                
                g2.finalize();
        }
        
        public void actionPerformed(ActionEvent e) {
                xscale = getWidth()/800F;
                yscale = getHeight()/450F;
                //mouse stuff
                PointerInfo p = MouseInfo.getPointerInfo();
                Point mouseLocation = p.getLocation();  
                mouseLocation = new Point((int) mouseLocation.x-panel.getLocationOnScreen().x,(int) mouseLocation.y-panel.getLocationOnScreen().y); //mouse location on game screen
                //end mouse stuff
                for(int count=0; count<(map.getTilex(mapNum)*map.getTiley(mapNum)); count++) {
                    Rectangle tileArea = new Rectangle((int)tile[count].getXpos(), (int)tile[count].getYpos(), tileSize, tileSize);
                    if(tileArea.contains(mouseLocation.x, mouseLocation.y)) {
                        tile[count].setHighlight(true);
                        highlightedTile = count;
                    } else {
                        tile[count].setHighlight(false);
                        if(highlightedTile == count) {
                            highlightedTile = -1;
                        }
                    }
                }
                playersMoving = 0;
                for(int count=0; count<mindcrackers.size(); count++) {
                    player m = (player) mindcrackers.get(count);
                    if(m.getMoving() == true) {
                        m.move(map.getTilex(mapNum), map.getTiley(mapNum));
                        sortEntities();
                        playersMoving++;
                    } else {
                        tile[m.getTile()].setContainsPlayer(true);
                        tile[m.getTile()].setPlayer(m.arrayPos);
                    }
                }
                repaint();
    	}
               
        public void newMindcracker(String mindcracker, int tileNum) {
            int nameCount = 0;
            for(int count=0; count<mindcrackers.size(); count++) {
                player m = (player) mindcrackers.get(count);
                if(m.getName() == mindcracker) {
                    nameCount++;
                }
            }
            if(nameCount == 0) {
                player test = new player(mindcracker,tileNum);
                mindcrackers.add(test);
                test.setArrayPos(mindcrackers.size()-1);
                tile[tileNum].setContainsPlayer(true);
                tile[tileNum].setPlayer(mindcrackers.size()-1);
            }
        }
        public void removeMindcracker(int mindNum) {
            player removal = (player) mindcrackers.get(mindNum);
            tile[removal.getTile()].setContainsPlayer(false);
            tile[removal.getTile()].setPlayer(-1);
            mindcrackers.remove(mindNum);
            for(int count=mindNum; count<mindcrackers.size(); count++) {
                player m = (player) mindcrackers.get(count);
                m.setArrayPos(m.getArrayPos()-1);
                tile[m.getTile()].player = m.getArrayPos();
            }
        }
        
        public void detectMovement(int currentTile, int moves) {
            tile[currentTile].setMovesLeft(moves);
            tile[currentTile].setMovement(true);
            if(tile[currentTile].getMovesLeft() > 0) {
                int nextTile = getNorthTile(currentTile);
                if(nextTile > -1 && (tile[nextTile].getContainsPlayer() == false || tile[nextTile].getPlayer() == playerNum) && tile[nextTile].getContainsEnemy() == false && (tile[nextTile].getDist() > tile[currentTile].getDist()+1 || tile[nextTile].getDist() == -1)) {
                    tile[nextTile].setMovesTo(2);
                    tile[nextTile].setDist(tile[currentTile].getDist()+1);
                    detectMovement(nextTile,moves-1);
                }
                nextTile = getEastTile(currentTile);
                if(nextTile > -1 && (tile[nextTile].getContainsPlayer() == false || tile[nextTile].getPlayer() == playerNum) && tile[nextTile].getContainsEnemy() == false && (tile[nextTile].getDist() > tile[currentTile].getDist()+1 || tile[nextTile].getDist() == -1)) {
                    tile[nextTile].setMovesTo(3);
                    tile[nextTile].setDist(tile[currentTile].getDist()+1);
                    detectMovement(nextTile,moves-1);
                }
                nextTile = getSouthTile(currentTile);
                if(nextTile > -1 && (tile[nextTile].getContainsPlayer() == false || tile[nextTile].getPlayer() == playerNum) && tile[nextTile].getContainsEnemy() == false && (tile[nextTile].getDist() > tile[currentTile].getDist()+1 || tile[nextTile].getDist() == -1)) {
                    tile[nextTile].setMovesTo(0);
                    tile[nextTile].setDist(tile[currentTile].getDist()+1);
                    detectMovement(nextTile,moves-1);
                }
                nextTile = getWestTile(currentTile);
                if(nextTile > -1 && (tile[nextTile].getContainsPlayer() == false || tile[nextTile].getPlayer() == playerNum) && tile[nextTile].getContainsEnemy() == false && (tile[nextTile].getDist() > tile[currentTile].getDist()+1 || tile[nextTile].getDist() == -1)) {
                    tile[nextTile].setMovesTo(1);
                    tile[nextTile].setDist(tile[currentTile].getDist()+1);
                    detectMovement(nextTile,moves-1);
                }
            }
        }
        public void removeMovement() {
            for(int count=0; count<map.getTiley(mapNum)*map.getTilex(mapNum); count++) {
                tile[count].movement = false;
                tile[count].setDist(-1);
            }
        }
        public void movePrep(int tileNum) {
            player m = (player) mindcrackers.get(playerNum);
            moveArray = new int[tile[tileNum].getDist()];
            int arrayCount = tile[tileNum].getDist();
            for(int count = 0; count < arrayCount; count++) {
                moveArray[count] = tile[tileNum].getMovesTo();
                if(tile[tileNum].getMovesTo() == 0) {
                    tileNum = getNorthTile(tileNum);
                }
                else if(tile[tileNum].getMovesTo() == 1) {
                    tileNum = getEastTile(tileNum);
                }
                else if(tile[tileNum].getMovesTo() == 2) {
                    tileNum = getSouthTile(tileNum);
                }
                else if(tile[tileNum].getMovesTo() == 3) {
                    tileNum = getWestTile(tileNum);
                }
            }
            m.setMoveArray(moveArray);
            m.setMoving(true);
        }
        public void sortEntities() {
            int mainArrayCount = 0;
            entityArrayLength = mindcrackers.size();
            entityType = new int[entityArrayLength]; //0-player 1-enemy 2-obs
            entityRef = new int[entityArrayLength];
            entityTile = new int[entityArrayLength];
            entityPath = new String[entityArrayLength];
            int typeStore, refStore, tileStore, typ, ref, til;
            String pathStore, pat;
            for(int count3=0; count3<entityArrayLength; count3++) {
                entityTile[count3] = -1;
            }
            for(int count=0; count<mindcrackers.size(); count++) {
                player m = (player) mindcrackers.get(count);
                typ = 0;
                ref = m.getArrayPos();
                til = m.getTile();
                pat = m.getPath();
                while(til >= entityTile[mainArrayCount] && mainArrayCount < entityArrayLength && entityTile[mainArrayCount] != -1) {
                    mainArrayCount++;
                }
                if(entityTile[mainArrayCount] == -1) {
                    entityType[mainArrayCount] = 0;
                    entityRef[mainArrayCount] = m.getArrayPos();
                    entityTile[mainArrayCount] = m.getTile();
                    entityPath[mainArrayCount] = m.getPath();
                } else if (mainArrayCount == entityArrayLength) {
                    entityType[mainArrayCount-1] = 0;
                    entityRef[mainArrayCount-1] = m.getArrayPos();
                    entityTile[mainArrayCount-1] = m.getTile();
                    entityPath[mainArrayCount-1] = m.getPath();
                } else if (til < entityTile[mainArrayCount]) {
                    typeStore  = entityType[mainArrayCount];
                    refStore = entityRef[mainArrayCount];
                    tileStore = entityTile[mainArrayCount];
                    pathStore = entityPath[mainArrayCount];
                    entityType[mainArrayCount] = typ;
                    entityRef[mainArrayCount] = ref;
                    entityTile[mainArrayCount] = til;
                    entityPath[mainArrayCount] = pat;
                    while(mainArrayCount+1 < entityArrayLength) {
                        mainArrayCount++;
                        typ = typeStore;
                        ref = refStore;
                        til = tileStore;
                        pat = pathStore;
                        typeStore  = entityType[mainArrayCount];
                        refStore = entityRef[mainArrayCount];
                        tileStore = entityTile[mainArrayCount];
                        pathStore = entityPath[mainArrayCount];
                        entityType[mainArrayCount] = typ;
                        entityRef[mainArrayCount] = ref;
                        entityTile[mainArrayCount] = til;
                        entityPath[mainArrayCount] = pat;
                    }
                }
            }
        }
        
        public int getNorthTile(int thisTile) {
            if(thisTile > (map.getTilex(mapNum)-1)) {
                return thisTile-map.getTilex(mapNum);
            } else {
                return -1;
            }
        }
        public int getEastTile(int thisTile) {
            if((thisTile+1) % map.getTilex(mapNum) == 0) {
                return -1;
            } else {
                return thisTile+1;
            }
        }
        public int getSouthTile(int thisTile) {
            if(thisTile < (map.getTilex(mapNum)*(map.getTiley(mapNum)-1))) {
                return thisTile+map.getTilex(mapNum);
            } else {
                return -1;
            }
        }
        public int getWestTile(int thisTile) {
            if(thisTile % map.getTilex(mapNum) == 0) {
                return -1;
            } else {
                return thisTile-1;
            }
        }
        public int newXpos(float oldx) { // repositions the images depending on frame size
            float newx = oldx*xscale;
            return (int)Math.floor(newx);
        }
        public int newYpos(float oldy) {
            float newy = oldy*yscale;
            return (int)Math.floor(newy);
        }
        public int newXscale(float oldx) { // resizes the images depending on frame size
            float newx = oldx*xscale;
            return (int)Math.ceil(newx);
        }
        public int newYscale(float oldy) {
            float newy = oldy*yscale;
            return (int)Math.ceil(newy);
        }
        private float getCentrex(Image image) {
            float xpos = newXscale(image.getWidth(panel))/2;
            return xpos;
        }
        private float getCentrey(Image image) {
            float ypos = newYscale(image.getHeight(panel))/2;
            return ypos;
        }
        
        public void click() {   
            if(playersMoving == 0) {
                if(highlightedTile > -1) {
                    movement = tile[highlightedTile].getMovement();
                    if(tile[highlightedTile].getMovement() == true && highlightedTile != oldSelect) {
                        tile[oldSelect].setContainsPlayer(false);
                        movePrep(highlightedTile);
                    }
                    if(oldSelect > -1) {
                        tile[oldSelect].setSelected(false);
                        removeMovement();
                    }
                    if(highlightedTile != oldSelect && movement == false) {
                        selectedTile = highlightedTile;
                        tile[highlightedTile].setSelected(true);
                        if(tile[highlightedTile].getContainsPlayer() == true) {
                            tile[highlightedTile].setDist(0);
                            playerNum = tile[highlightedTile].getPlayer();
                            tile[highlightedTile].setDist(0);
                            player m = (player) mindcrackers.get(tile[highlightedTile].getPlayer());
                            detectMovement(highlightedTile, m.getMoves()); 
                        }
                        oldSelect = highlightedTile;
                    } else {
                        oldSelect = -1;
                    }
                }
            }
        }
        
        private class TAdapter extends KeyAdapter {
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    
                }
                public void keyReleased(KeyEvent e) {
                    int key = e.getKeyCode();

                }
    }
}

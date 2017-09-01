package MindcrackDefence;

public class map {
    public float xpos[], ypos[], tileXpos[], tileYpos[];
    public static float changex, changey;
    private int tilex[], tiley[];
    private String path[];
    private int numMaps = 1;
    
    public map() {
        xpos = new float[numMaps];
        ypos = new float[numMaps];
        tileXpos = new float[numMaps];
        tileYpos = new float[numMaps];
        tilex = new int[numMaps];
        tiley = new int[numMaps];
        path = new String[numMaps];
        changex = 0F;
        changey = 0F;
        
        //start array

        xpos[0] = 0F;
        ypos[0] = 0F;
        tileXpos[0] = 80F;
        tileYpos[0] = 60F;
        tilex[0] = 16;
        tiley[0] = 8;
        path[0] = System.getProperty("user.dir")+"/sprites/maps/test.png";
       
        //array end        
    }
    
    public float getXpos(int mapNum) {
        return xpos[mapNum];
    }
    public float getYpos(int mapNum) {
        return ypos[mapNum];
    }
    public float getTileXpos(int mapNum) {
        return tileXpos[mapNum];
    }
    public float getTileYpos(int mapNum) {
        return tileYpos[mapNum];
    }
    public int getTilex(int mapNum) {
        return tilex[mapNum];
    }
    public int getTiley(int mapNum) {
        return tiley[mapNum];
    }
    public String getPath(int mapNum) {
        return path[mapNum];
    }
    
}

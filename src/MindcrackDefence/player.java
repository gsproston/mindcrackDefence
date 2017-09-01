package MindcrackDefence;

public class player extends entity {
    
    public player(String mindcracker, int newTile) {
        speed = 1F;
        moves = 3;
        tile = newTile;
        name = mindcracker;
        path = System.getProperty("user.dir")+"/sprites/players/"+mindcracker+".png"; 
    } 
    
    public void reset() {
        moves = 3;
    }
    
}

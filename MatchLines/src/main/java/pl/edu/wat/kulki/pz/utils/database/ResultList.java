package pl.edu.wat.kulki.pz.utils.database;

import java.util.List;

public class ResultList {

    public static List<String> player;
    public static List<String> score;

    public ResultList(List<String> playerName,List<String> points){
        player = playerName;
        score = points;
    }

    public String[][] toArray(){
        String[][] array = new String[player.size()][2];
        for(int i =0; i<player.size();i++) {
            array[i][0] = player.get(i).toString();
            array[i][1] = score.get(i).toString();
        }
        return array;
    }
}

package pl.edu.wat.kulki.pz.utils.curiosity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class CuriosityLoader {

    public static GoldPrice[] getPriceOfGold(String adress) throws IOException{
        URL url = new URL(adress);
        InputStreamReader isr = new InputStreamReader(url.openStream());
        BufferedReader br = new BufferedReader(isr);
        String response = br.lines().collect(Collectors.joining());
        Gson g = new Gson();
        return g.fromJson(response,GoldPrice[].class);
    }
}

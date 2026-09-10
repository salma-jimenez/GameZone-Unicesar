
package com.mycompany.gamezone.persistence;

import com.mycompany.gamezone.model.Console;
import com.mycompany.gamezone.model.Product;
import com.mycompany.gamezone.model.VideoGame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class ProductRepository {
    private static final String filePath = "products.txt";
    
    public void save(List<Product> products){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Product p : products) {
                if(p instanceof VideoGame game){
                    bw.write("VIDEOGAME;"+p.getId() + ";" + p.getTitle() + ";"
                    + p.getPrice()+";"+p.getQuantityAvailable()+";"+game.getAgeRating()
                    +";"+game.getGenre()+";"+game.getPlatform());
                }else if(p instanceof Console console) {
                    bw.write("CONSOLE;"+p.getId() + ";" + p.getTitle() + ";"
                    + p.getPrice()+";"+p.getQuantityAvailable()+";"+console.getBrand()
                    +";"+ console.getGeneration()+";"+console.getModel());
                }
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Product> load(){
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()){
            return products;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");
                String type = data[0];
                String id     = data[1];
                String title = data[2];
                String price  = data[3];
                String quantityAvailable = data[4];
                if(type.equals("VIDEOGAME")){
                    String ageRating = data[5];
                    String genre = data[6];
                    String platform = data[7];
                    VideoGame videoGame = new VideoGame(platform, genre, ageRating, id, title, 
                            Double.parseDouble(price), Integer.parseInt(quantityAvailable));
                    products.add(videoGame);
                }else if (type.equals("CONSOLE")){
                    String brand = data[5];
                    String generation = data[6];
                    String model = data[7];
                    Console console = new Console(brand, model, generation, id, title, 
                            Double.parseDouble(price), Integer.parseInt(quantityAvailable));
                    products.add(console);
                }                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }
}


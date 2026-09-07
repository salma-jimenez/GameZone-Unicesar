
package com.mycompany.gamezone.persistence;

import com.mycompany.gamezone.model.Console;
import com.mycompany.gamezone.model.Product;
import com.mycompany.gamezone.model.VideoGame;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
    
    public void load(){
        
    }
}


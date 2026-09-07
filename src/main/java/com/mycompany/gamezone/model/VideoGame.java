
package com.mycompany.gamezone.model;

/**
 *
 * @author Alejandro
 */
public class VideoGame extends Product{
    private String platform;
    private String genre;
    private String ageRating;

    public VideoGame(String platform, String genre, String ageRating, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    @Override
    public String getDescription() {
        return ("Videojuego: "+ this.getTitle()+"\nPlataforma: "+this.platform + 
                "\nGenero: " + this.genre + "\nClasificación de edad: "+ this.ageRating
                + "\nPrecio: "+ this.getPrice());
    }
}

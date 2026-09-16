
package com.gamezone.model;

/**
 * Represents a video game product in the GameZone store.
 * Extends the abstract {@link Product} class by adding platform, genre, and age rating properties.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class VideoGame extends Product{
    private String platform;
    private String genre;
    private String ageRating;

    /**
     * Constructs a new VideoGame instance with full product details.
     * @param platform the gaming platform
     * @param genre the game genre
     * @param ageRating the recommended age rating
     * @param id the unique identifier of the product
     * @param title the title of the video game
     * @param price the price of the video game
     * @param quantityAvailable the available stock quantity
     */
    public VideoGame(String platform, String genre, String ageRating, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    /**
     * Gets the gaming platform.
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the gaming platform.
     * @param platform the new gaming platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Gets the game genre.
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the game genre.
     * @param genre the new game genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the age rating of the game.
     * @return the age rating
     */
    public String getAgeRating() {
        return ageRating;
    }

    /**
     * Sets the age rating of the game.
     * @param ageRating the new age rating
     */
    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    /**
     * {@inheritDoc}
     * Returns a formatted summary including the video game details.
     * 
     * @return a formatted string with video game details
     */
    @Override
    public String getDescription() {
        return ("Videojuego: "+ this.getTitle()+"\nPlataforma: "+this.platform + 
                "\nGenero: " + this.genre + "\nClasificación de edad: "+ this.ageRating
                + "\nPrecio: "+ this.getPrice());
    }
}

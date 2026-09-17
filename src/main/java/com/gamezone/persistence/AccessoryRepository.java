package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles persistence of Accessory objects in a CSV file. Uses a type
 * discriminator column to distinguish Controller, Cable, and Memory when
 * reloading records from disk.
 *
 * @author Salomejimenez
 */
public class AccessoryRepository {

    private static String FILE_PATH = "data/accessories.csv";
    private static String CONTROLLER_TYPE = "CONTROLLER";
    private static String CABLE_TYPE = "CABLE";
    private static String MEMORY_TYPE = "MEMORY";
    private static String CONSOLE_SEPARATOR = ";";

    /**
     * Persists the given list of accessories, overwriting the CSV file. Each
     * row includes a type discriminator so the concrete subclass can be
     * reconstructed on load.
     *
     * @param accessories the accessories to save
     */
    public void saveAll(List<Accessory> accessories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Accessory accessory : accessories) {
                writer.println(buildLine(accessory));
            }
        } catch (IOException e) {
            System.out.println("Error saving accessories: " + e.getMessage());
        }
    }

    /**
     * Loads all accessories from the CSV file, reconstructing the correct
     * concrete subclass (Controller, Cable, or Memory) for each row based on
     * the type discriminator.
     *
     * @return the list of accessories found in the file, or an empty list if
     * the file does not exist
     */
    public List<Accessory> loadAll() {
        List<Accessory> accessories = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return accessories;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                accessories.add(parseLine(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading accessories: " + e.getMessage());
        }

        return accessories;
    }

    /**
     * Builds a single CSV line for the given accessory, including its type
     * discriminator and type-specific fields.
     *
     * @param accessory the accessory to serialize
     * @return the CSV-formatted line
     */
    private String buildLine(Accessory accessory) {
        String consoles = String.join(CONSOLE_SEPARATOR, accessory.getCompatibleConsoles());
        String common = accessory.getId() + ","
                + accessory.getTitle() + ","
                + accessory.getPrice() + ","
                + accessory.getQuantityAvailable() + ","
                + consoles;

        if (accessory instanceof Controller controller) {
            return CONTROLLER_TYPE + "," + common + "," + controller.getConnectionType() + ",";
        } else if (accessory instanceof Cable cable) {
            return CABLE_TYPE + "," + common + "," + cable.getLengthInMeters() + "," + cable.getConnectorType();
        } else {
            Memory memory = (Memory) accessory;
            return MEMORY_TYPE + "," + common + "," + memory.getCapacityInGB() + "," + memory.getMemoryType();
        }
    }

    /**
     * Parses a single CSV line back into the correct concrete Accessory
     * subclass, based on its type discriminator.
     *
     * @param line the CSV line to parse
     * @return the reconstructed Accessory
     */
    private Accessory parseLine(String line) {
        String[] fields = line.split(",", -1);
        String type = fields[0];
        String id = fields[1];
        String title = fields[2];
        double price = Double.parseDouble(fields[3]);
        int quantityAvailable = Integer.parseInt(fields[4]);
        List<String> compatibleConsoles = fields[5].isBlank()
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(fields[5].split(CONSOLE_SEPARATOR)));

        if (type.equals(CONTROLLER_TYPE)) {
            String connectionType = fields[6];
            return new Controller(connectionType, compatibleConsoles, id, title, price, quantityAvailable);
        } else if (type.equals(CABLE_TYPE)) {
            double lengthInMeters = Double.parseDouble(fields[6]);
            String connectorType = fields[7];
            return new Cable(lengthInMeters, connectorType, compatibleConsoles, id, title, price, quantityAvailable);
        } else if (type.equals(MEMORY_TYPE)) {
            int capacityInGB = Integer.parseInt(fields[6]);
            String memoryType = fields[7];
            return new Memory(capacityInGB, memoryType, compatibleConsoles, id, title, price, quantityAvailable);
        } else {
            throw new IllegalArgumentException("Unknown accessory type: " + type);
        }

    }

}

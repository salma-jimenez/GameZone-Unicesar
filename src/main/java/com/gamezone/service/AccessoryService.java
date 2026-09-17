package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccessoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the business logic for managing accessories: registering new
 * controllers, cables, and memories, listing and filtering them, and
 * checking compatibility with consoles.
 *
 * @author Salomejimenez
 */
public class AccessoryService {

    private AccessoryRepository accessoryRepository;

    /**
     * Creates the service, injecting the repository used for persistence.
     *
     * @param accessoryRepository the repository used to load/save accessories
     */
    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    /**
     * Alias method for ConsoleUI compatibility.
     */
    public List<Accessory> getAllAccessories() {
        return listAllAccessories();
    }

    /**
     * Deletes an accessory by its unique identifier.
     *
     * @param id the accessory's identifier
     */
    public void deleteAccessory(String id) {
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.removeIf(a -> a.getId().equals(id));
        accessoryRepository.saveAll(accessories);
    }

    /**
     * Registers a new controller, persisting it along with the existing
     * accessories.
     */
    public Controller registerController(String id, String title, double price, int quantityAvailable,
                                          String connectionType, List<String> compatibleConsoles) {
        Controller controller = new Controller(connectionType, compatibleConsoles, id, title, price, quantityAvailable);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(controller);
        accessoryRepository.saveAll(accessories);
        return controller;
    }

    /**
     * Registers a new cable, persisting it along with the existing
     * accessories.
     */
    public Cable registerCable(String id, String title, double price, int quantityAvailable,
                                double lengthInMeters, String connectorType, List<String> compatibleConsoles) {
        Cable cable = new Cable(lengthInMeters, connectorType, compatibleConsoles, id, title, price, quantityAvailable);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(cable);
        accessoryRepository.saveAll(accessories);
        return cable;
    }

    /**
     * Registers a new memory accessory, persisting it along with the
     * existing accessories.
     */
    public Memory registerMemory(String id, String title, double price, int quantityAvailable,
                                  int capacityInGB, String memoryType, List<String> compatibleConsoles) {
        Memory memory = new Memory(capacityInGB, memoryType, compatibleConsoles, id, title, price, quantityAvailable);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(memory);
        accessoryRepository.saveAll(accessories);
        return memory;
    }

    /**
     * Lists all registered accessories, of any type.
     */
    public List<Accessory> listAllAccessories() {
        return accessoryRepository.loadAll();
    }

    /**
     * Lists all accessories of a given type.
     */
    public List<Accessory> listAccessoriesByType(String type) {
        List<Accessory> result = new ArrayList<>();
        for (Accessory accessory : accessoryRepository.loadAll()) {
            if (matchesType(accessory, type)) {
                result.add(accessory);
            }
        }
        return result;
    }

    /**
     * Finds all accessories compatible with the given console.
     */
    public List<Accessory> findAccessoriesCompatibleWith(String consoleId) {
        List<Accessory> result = new ArrayList<>();
        for (Accessory accessory : accessoryRepository.loadAll()) {
            if (accessory.isCompatibleWith(consoleId)) {
                result.add(accessory);
            }
        }
        return result;
    }

    /**
     * Finds an accessory by its unique identifier.
     */
    public Accessory findById(String id) {
        for (Accessory accessory : accessoryRepository.loadAll()) {
            if (accessory.getId().equals(id)) {
                return accessory;
            }
        }
        return null;
    }

    /**
     * Updates the stock quantity of the given accessory.
     */
    public void updateStock(String accessoryId, int quantity) {
        List<Accessory> accessories = accessoryRepository.loadAll();
        for (Accessory accessory : accessories) {
            if (accessory.getId().equals(accessoryId)) {
                accessory.setQuantityAvailable(quantity);
                break;
            }
        }
        accessoryRepository.saveAll(accessories);
    }

    /**
     * Checks whether the given accessory matches the given type name.
     */
    private boolean matchesType(Accessory accessory, String type) {
        if (type.equalsIgnoreCase("CONTROLLER")) {
            return accessory instanceof Controller;
        } else if (type.equalsIgnoreCase("CABLE")) {
            return accessory instanceof Cable;
        } else if (type.equalsIgnoreCase("MEMORY")) {
            return accessory instanceof Memory;
        }
        return false;
    }
}
    
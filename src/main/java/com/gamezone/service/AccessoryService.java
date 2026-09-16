
package com.gamezone.service;import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccessoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 
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
     * Registers a new controller, persisting it along with the existing
     * accessories.
     *
     * @param id the controller's unique identifier
     * @param title the controller's title/name
     * @param price the controller's price
     * @param quantityAvailable the initial stock quantity
     * @param connectionType the controller's connection type (wireless/wired)
     * @param compatibleConsoles list of console names/models the controller is compatible with
     * @return the newly created Controller
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
     *
     * @param id the cable's unique identifier
     * @param title the cable's title/name
     * @param price the cable's price
     * @param quantityAvailable the initial stock quantity
     * @param lengthInMeters the cable's length in meters
     * @param connectorType the cable's connector type
     * @param compatibleConsoles list of console names/models the cable is compatible with
     * @return the newly created Cable
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
     *
     * @param id the memory's unique identifier
     * @param title the memory's title/name
     * @param price the memory's price
     * @param quantityAvailable the initial stock quantity
     * @param capacityInGB the memory's storage capacity in gigabytes
     * @param memoryType the memory's type (SD, microSD, internal, etc.)
     * @param compatibleConsoles list of console names/models the memory is compatible with
     * @return the newly created Memory
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
     *
     * @return the full list of accessories
     */
    public List<Accessory> listAllAccessories() {
        return accessoryRepository.loadAll();
    }

    /**
     * Lists all accessories of a given type.
     *
     * @param type the type to filter by: "CONTROLLER", "CABLE", or "MEMORY"
     * @return the accessories matching that type
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
     *
     * @param consoleId the console's identifier or name to check against
     * @return the accessories compatible with that console
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
     *
     * @param id the accessory's identifier
     * @return the matching Accessory, or null if none is found
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
     *
     * @param accessoryId the identifier of the accessory to update
     * @param quantity the new stock quantity to set
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
     *
     * @param accessory the accessory to check
     * @param type the type name: "CONTROLLER", "CABLE", or "MEMORY"
     * @return true if the accessory's concrete class matches the type
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
    
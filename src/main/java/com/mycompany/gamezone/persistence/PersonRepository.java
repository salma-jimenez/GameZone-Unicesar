package com.mycompany.gamezone.persistence;

import com.mycompany.gamezone.model.Customer;
import com.mycompany.gamezone.model.Person;
import com.mycompany.gamezone.model.Seller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


 /**
 * Handles saving and loading Person data (clients and sellers) to and from a
 * text file.
 *
 * @author Salomejimenez
 */
public class PersonRepository {

    private static final String filePath = "people.txt";

    /**
     * Saves the given list of people to the data file.
     *
     * @param people the list of people to save
     */
    public void save(List<Person> people) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Person p : people) {
                if (p instanceof Customer customer) {
                    bw.write("CUSTOMER;" + p.getId() + ";" + p.getName() + ";"
                            + p.getPhone() + ";" + customer.getEmail());
                } else if (p instanceof Seller seller) {
                    bw.write("SELLER;" + p.getId() + ";" + p.getName() + ";"
                            + p.getPhone() + ";" + seller.getEmployeeCode()
                            + ";" + seller.getShift());
                }
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all people stored in the data file.
     *
     * @return the list of people read from the file
     */
    public List<Person> load() {
        List<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                String type = fields[0];
                int id = Integer.parseInt(fields[1]);
                String name = fields[2];
                String phone = fields[3];

                if (type.equals("CUSTOMER")) {
                    String email = fields[4];
                    people.add(new Customer(id, name, phone, email));
                } else if (type.equals("SELLER")) {
                    int employeeCode = Integer.parseInt(fields[4]);
                    String shift = fields[5];
                    people.add(new Seller(id, name, phone, employeeCode, shift));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }
}

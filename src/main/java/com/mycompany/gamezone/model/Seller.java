package com.mycompany.gamezone.model;

/**
 *
 * @author Salomejimenez
 */
public class Seller extends Person {

    private int employeeCode;
    private String shift;

    /**
     * Creates a new Seller with the given identification and work data.
     *
     * @param id the person's unique identifier
     * @param name the person's full name
     * @param phone the person's contact phone number
     * @param employeeCode the vendor's employee code
     * @param shift the vendor's assigned work shift
     */
    public Seller(int id, String name, String phone, int employeeCode, String shift) {
        super(id, name, phone);
        if (employeeCode <= 0) {
            throw new IllegalArgumentException("El código de empleado debe ser mayor a cero.");
        }
        if (shift == null || shift.isBlank()) {
            throw new IllegalArgumentException("El turno es obligatorio.");
        }
        this.employeeCode = employeeCode;
        this.shift = shift;
    }

    /**
     * Returns the vendor's employee code.
     *
     * @return the employee code
     */
    public int getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Sets the vendor's employee code.
     *
     * @param employeeCode the new employee code to assign
     */
    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Returns the vendor's assigned work shift.
     *
     * @return the shift
     */
    public String getShift() {
        return shift;
    }

    /**
     * Sets the vendor's assigned work shift.
     *
     * @param shift the new shift to assign
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     *
     * Returns role-specific information about this seller, including employee
     * code and assigned shift.
     *
     * @return the role-specific description
     */
    @Override
    public String describeRole() {
        return ("Vendedor: " + this.getName() + "\nCódigo de empleado: " + this.employeeCode
                + "\nTurno: " + this.shift
                + "\nTeléfono: " + this.getPhone());
    }

}

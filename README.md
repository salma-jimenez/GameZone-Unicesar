# 🎮 GameZone-Unicesar - Sales and Commercial Modules Management System

Java-based system structured under a 4-layer architecture (Model, Persistence, Services, and User Interface) for the comprehensive management of the GameZone Unicesar store[cite: 1, 2, 3, 4, 5].

---

## 🚀 Requirements and Installation

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher[cite: 1, 2, 3, 4, 5].
* **Apache Maven:** Dependency management and build tool[cite: 1, 2, 3, 4, 5].

### Compilation and Execution
1. **Compile the project:**
   ```bash
   mvn clean compile
   ```
2. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.gamezone.Main"
   ```

---

## 📋 System Modules and Requirements

### 🔹 Requirement 1: Accessories Management
* **Overview:** Extension of the product catalogue to handle three types of accessories: Controllers (wireless/wired and compatibility), Cables (length and connector type), and Memory Cards (capacity and type)[cite: 2].
* **Key Features:**
  * Registration and inventory listing by accessory type[cite: 2].
  * Compatibility checking between specific accessories and consoles[cite: 2].
  * Unified integration with the sales module and automatic stock updates[cite: 2].
* **Data File:** Preloaded in `data/accessories.csv`[cite: 2].

### 🔹 Requirement 2: Promotions Management
* **Overview:** Dynamic promotional campaign module supporting three distinct discount calculation strategies: Percentage Discount, Category Discount, and Bulk Purchase Discount[cite: 5].
* **Key Features:**
  * Automatic selection of the active promotion offering the highest discount (`findBestPromotionFor`)[cite: 5].
  * Clear itemized breakdown displaying subtotal, applied promotion name, and final total on the sales receipt[cite: 5].
* **Data File:** Preloaded in `data/promotions.csv`[cite: 5].

### 🔹 Requirement 3: Returns and Balance Management
* **Overview:** Transactional module designed to handle partial or full product returns from previous sales[cite: 4].
* **Key Features:**
  * Strict 30-day post-purchase window validation[cite: 4].
  * Item verification ensuring returned products belong to the original sale[cite: 4].
  * Automatic inventory restoration (`restoreStock`)[cite: 4].
  * Monthly Balance Report generation (Total Sales - Total Returns)[cite: 4].
* **Data File:** Generated dynamically in `data/returns.csv` (No preloaded data required)[cite: 4].

### 🔹 Requirement 4: Warranty Management (Technical Leader)
* **Overview:** Formalization and lifecycle tracking of product warranties issued for sold consoles[cite: 3].
* **Key Features:**
  * **Basic Warranty:** Automatically issued at no extra cost, providing 6 months of standard coverage for consoles (`Console`)[cite: 3].
  * **Extended Warranty:** Optional 12-month extended coverage selectable at checkout, adding a 10% surcharge of the console price to the sale total[cite: 3].
  * **Queries & Reports:** Search by Sale ID and Product ID, report active warranties, and trigger alerts for warranties expiring within 30 days[cite: 3].
* **Data File:** Generated dynamically in `data/warranties.csv` (No preloaded data required)[cite: 3].

---

## 👥 Team Work Distribution and Responsibilities

Based on the official exam guidelines, the workload was distributed across all 4 architectural layers:

* **Technical Leader:**
  * Implemented module integration within `SaleService` and `ConsoleUI`[cite: 2, 3, 4, 5].
  * Developed Warranty Module integration (Requirement 4)[cite: 3].
  * Extended `ProductService` with inventory restoration logic (`restoreStock`)[cite: 4].
  * Coordinated system integration, reviewed and approved Pull Requests, and updated `README.md` documentation[cite: 2, 3, 4, 5].

* **Developer 1:**
  * Implemented domain hierarchies (`Product`, `Accessory`, `Promotion`, `Return`, `Warranty`)[cite: 2, 3, 4, 5].
  * Handled internal entity rules, calculation methods, and durations[cite: 2, 3, 4, 5].

* **Developer 2:**
  * Implemented persistence repositories and business logic service classes[cite: 2, 3, 4, 5].
  * Handled dynamic data filtering, query operations, and report calculations[cite: 3, 4, 5].

---

## 📁 Data Files (`data/`)

* `accessories.csv`: Preloaded initial catalog of accessories[cite: 2].
* `promotions.csv`: Preloaded initial setup of active promotional campaigns[cite: 5].
* *Note on Returns & Warranties:* Modules 3 and 4 operate as dynamic transactional logs created and updated automatically at runtime; no initial preloaded CSV files are required[cite: 3, 4].

---

## 📂 Layered Architecture

1. **`com.gamezone.model`:** Domain entities (`Product`, `Console`, `VideoGame`, `Accessory`, `Promotion`, `Return`, `Warranty`, `Sale`, `Person`)[cite: 1, 2, 3, 4, 5].
2. **`com.gamezone.persistence`:** File-based data storage and repository management[cite: 1, 2, 3, 4, 5].
3. **`com.gamezone.service`:** Business logic, validation rules, and transactional processing (`SaleService`, `WarrantyService`, `ProductService`, `ReturnService`, `PromotionService`, `AccessoryService`)[cite: 1, 2, 3, 4, 5].
4. **`com.gamezone.ui`:** User interface and console menu interactions via `ConsoleUI`[cite: 1, 2, 3, 4, 5].
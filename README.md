# Courier Company Desk Simulation  

This project is a **Java-based system** backed by a **PostgreSQL database**, simulating the daily operations of a courier company desk.  
It allows administrators, couriers, and customers to manage deliveries, track packages, and interact through a console-driven menu interface with secure, password-protected login.  

---

## Functionality  

- **User Authentication:**  
  - All users log in with credentials stored securely in the database.  
  - Access levels are role-based: *Admin*, *Courier*, or *Customer*.  

- **Role-Based Menus:**  
  - `WelcomePage` serves as the entry point for users to select and log into their role.  
  - `AdminMenu`, `CourierMenu`, and `CustomerMenu` provide separate interfaces for each role.  

- **Core Operations:**  
  - **Admin:** Manages users, couriers, and system statistics.  
  - **Courier:** Views assigned deliveries and updates package statuses.  
  - **Customer:** Books deliveries, views package details, and tracks shipments.  

- **Database Integration:**  
  - `DatabaseConnection` handles all PostgreSQL connections and SQL operations.  
  - Data for clients, packages, and delivery logs are stored and managed in relational tables.  

- **Package Tracking:**  
  - The `Package` class maintains delivery information and status transitions such as *Booked*, *In Transit*, *Delivered*, or *Returned*.  

---

## How It Works  

On startup, the `WelcomePage` prompts the user to log in or register.  
Once authenticated, the system loads the corresponding menu and actions for that role.  
Each menu action (booking, updating, viewing, or managing) executes SQL queries through the `DatabaseConnection` class, ensuring real-time synchronization with the PostgreSQL database.  
The modular design separates logic into role-specific classes (`AdminActions`, `CourierActions`, `CustomerActions`) for clarity and maintainability.  

---

## Technologies Used  

- **Language:** Java  
- **Interface:** Console-based menu system  
- **Database:** PostgreSQL (via JDBC)  
- **Architecture:** Modular and role-based  
  - `AdminMenu`, `CourierMenu`, `CustomerMenu` — user navigation.  
  - `AdminActions`, `CourierActions`, `CustomerActions` — operational logic.  
  - `DatabaseConnection` — persistent database management.  
  - `Package` — delivery data model.  

---

## Future Improvements  

- Add delivery route optimization and courier zone assignment.  
- Implement graphical (GUI) or web-based interface.  
- Introduce real-time delivery notifications via email or SMS.  
- Include financial reports and performance analytics.  

---

## Applications  

- **Educational Tool:** Demonstrates Java–PostgreSQL integration and secure console design.  
- **Courier Simulation:** Models authentication, logistics operations, and database-driven data flow.  
- **Business Prototype:** Scalable base for courier or parcel tracking management systems.  

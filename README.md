# Courier Company Desk Simulation

This project is a Java-based application backed by a PostgreSQL database, designed to simulate the front-desk operations of a courier company. It models real-world interactions between couriers, customers, and receptionists, managing deliveries, package statuses, and transaction records.

---

## Functionality

- Package Tracking:
  - Uses the SystemManager class to register, update, and retrieve package statuses by tracking ID.
- Controllers & Services:
  - `ControllerFactory` dynamically injects `CustomerService` and `CourierService` into the respective controllers.  
  - `CustomerInterfaceController` and `CourierInterfaceController` manage client and courier actions.
- Controllers & Services:  
  - `ControllerFactory` dynamically injects `CustomerService` and `CourierService` into the respective controllers.  
  - `CustomerInterfaceController` and `CourierInterfaceController` manage client and courier actions.  
- Database Integration:
  - PostgreSQL stores client, product, order, and log data with proper relational links.  
- Status Management:
  - Packages move through states: *Booked*, *In Transit*, *Delivered*, *Held*, or *Returned*.  
- User Roles:
  - Customers create and view deliveries.  
  - Couriers update delivery statuses.  
  - Receptionists and managers view operational and financial data.

---

## How It Works  

`SystemManager` maintains an internal list of all packages and provides methods to add, update, and query them.  
The controllers interact with the database through dedicated service classes to handle booking, delivery, and billing.  
`ControllerFactory` ensures that each controller receives the appropriate service dependencies for modular and testable code.  

---

## Technologies Used  

- Language: Java (JavaFX for UI)  
- Database: PostgreSQL (via JDBC)  
- Architecture: MVC-style layered structure  
  - `controller/` – Handles UI logic and interactions.  
  - `core/` – Business logic (`Package`, `SystemManager`).  
  - `service/` – Database communication and main program flow.
 
---

## Future Improvements  

- Integrate real courier and zone tracking.  
- Build a graphical dashboard for live monitoring.  
- Generate monthly and yearly reports automatically.

---

## Applications  

- Educational Tool: Demonstrates Java–PostgreSQL integration and OOP principles.  
- Courier Simulation: Models realistic package lifecycle and system management.  
- Business Prototype: Base for logistics or delivery management systems.  

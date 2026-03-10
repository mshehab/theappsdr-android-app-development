package edu.charlotte.evaluation02.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class DataService {
    private static DataService instance;
    private long randomSeed = 12345L;
    private Random random;
    private ArrayList<Employee> employees;
    public DataService() {
        generateEmployees();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    private void generateEmployees() {
        ArrayList<String> EMPLOYEE_NAMES = new ArrayList<>(Arrays.asList(
                "Alice Johnson", "Bob Smith", "Charlie Evans", "Diana Lee", "Ethan Davis",
                "Fiona Clark", "George Baker", "Hannah Scott", "Ian Wright", "Jenna Hall",
                "Kyle Adams", "Laura Turner", "Mike Nelson", "Nora Perez", "Oscar Hughes",
                "Paula Ramirez", "Quinn Foster", "Rita Cooper", "Sam Green", "Tina Brooks",
                "Uma Patel", "Victor Ward", "Wendy Cox", "Xander Reed", "Yara Sullivan", "Liam Carter",
                "Olivia Bennett", "Noah Mitchell", "Emma Richardson", "Ethan Parker", "Ava Thompson",
                "Mason Hughes", "Sophia Coleman", "Logan Foster", "Isabella Griffin", "Abigail Long",
                "Lucas Hayes", "Mia Simmons", "Benjamin Ward", "Charlotte Price", "Elijah Brooks", "Amelia Sanders",
                "James Powell", "Harper Bryant", "Alexander Butler", "Evelyn Fisher", "Daniel Henderson",
                "Matthew Patterson", "Emily Coleman", "Henry Russell", "Scarlett Griffin", "Jackson Myers",
                "Victoria Simmons", "Sebastian Cooper", "Madison Barnes", "Jack Jenkins", "Luna Ross",
                "Owen Perry", "Chloe Hayes", "Samuel Butler", "Grace Bennett", "David Hughes", "Lily Patterson",
                "Joseph Carter", "Zoe Richardson", "William Ward", "Hannah Cooper", "Michael Brooks",
                "Natalie Sanders", "Gabriel Foster", "Layla Russell", "Caleb Parker", "Aria Thompson",
                "Nathan Griffin", "Ella Myers"
        ));
        random = new Random(randomSeed);
        employees = new ArrayList<>();
        ArrayList<String> DEPARTMENTS = new ArrayList<>(Arrays.asList(
                "IT", "Finance", "HR", "Marketing", "Sales", "Operations", "Engineering", "Customer Support", "Legal"
        ));
        for (int i = 0; i < EMPLOYEE_NAMES.size(); i++) {
            String name = EMPLOYEE_NAMES.get(i);
            String department = DEPARTMENTS.get(random.nextInt(DEPARTMENTS.size()));
            ArrayList<Purchase> purchases = generatePurchases();
            employees.add(new Employee(name, department, purchases));
        }
    }

    private ArrayList<Purchase> generatePurchases() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        int numberOfPurchases = random.nextInt(3) + 2; // 2–4 purchases per employee

        for (int i = 0; i < numberOfPurchases; i++) {
            Date purchaseDate = randomDate();
            ArrayList<Item> items = generateItems();
            purchases.add(new Purchase(purchaseDate, items));
        }

        return purchases;
    }

    private ArrayList<Item> generateItems() {
        Map<String, Double> ITEM_CATALOG = new HashMap<>() {{
            put("Laptop", 899.99);
            put("Monitor", 249.99);
            put("Keyboard", 49.99);
            put("Mouse", 29.99);
            put("Desk Chair", 159.99);
            put("Printer", 199.99);
            put("USB Drive", 19.99);
            put("Headphones", 89.99);
            put("Notebook", 7.99);
            put("Pen Set", 12.49);
            put("Coffee Mug", 14.99);
            put("Phone Charger", 24.99);
            put("Backpack", 59.99);
            put("Tablet", 499.99);
            put("Desk Lamp", 39.99);
            put("Webcam", 79.99);
            put("External Hard Drive", 129.99);
            put("Wireless Router", 99.99);
            put("Office Desk", 299.99);
            put("Graphic Tablet", 219.99);
            put("Surge Protector", 22.99);
            put("Bluetooth Speaker", 69.99);
            put("Smartphone Stand", 15.99);
            put("Ethernet Cable", 9.99);
            put("Whiteboard", 89.99);
        }};
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<String> itemNames = new ArrayList<>(ITEM_CATALOG.keySet());
        int itemCount = random.nextInt(9) + 1; // 1–9 items per purchase
        HashSet<String> selectedItems = new HashSet<>();

        for (int i = 0; i < itemCount; i++) {
            String itemName = itemNames.get(random.nextInt(itemNames.size()));
            if (!selectedItems.contains(itemName)) {
                selectedItems.add(itemName);
                double price = ITEM_CATALOG.get(itemName);
                int quantity = random.nextInt(5) + 1; // 1–5 quantity
                items.add(new Item(itemName, price, quantity));
            }
        }

        return items;
    }

    private Date randomDate() {
        Calendar cal = Calendar.getInstance();
        int daysAgo = random.nextInt(365); // within last year
        cal.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return cal.getTime();
    }
}

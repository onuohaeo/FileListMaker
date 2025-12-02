import java.io.IOException;
import java.nio.file.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class FileListMaker {
    private static List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DEFAULT_FILENAME = "grocerylist.txt";



    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "V":
                    viewList();
                    break;
                case "M":
                    moveItem();
                    break;
                case "O":
                    promptToSave();
                    loadFile(DEFAULT_FILENAME);
                    break;
                case "S":
                    saveFile(DEFAULT_FILENAME);
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    promptToSave();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try this again.");
            }
        }
        scanner.close();
    }
    private static void showMenu() {
        System.out.println("\nOptions:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("V - View the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open list from disk");
        System.out.println("S - Save list to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
        System.out.print("Please enter your choice: ");
    }
    private static void addItem() {
        System.out.print("Please enter item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }
    private static void deleteItem() {
        System.out.print("Please enter item index to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }
    private static void insertItem() {
        System.out.print("Please enter item to insert: ");
        String item = scanner.nextLine();
        System.out.print("Please enter index to insert at: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }
    private static void viewList() {
        System.out.println("\nCurrent list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }



    private static void moveItem() {
        System.out.print("Please enter item index to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Please enter index to move to: ");
        int toIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }
    private static void loadFile(String filename) {
        Path file = Paths.get(filename);
        try {
            list = Files.readAllLines(file);
            needsToBeSaved = false;
            System.out.println("List loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }




    private static void saveFile(String filename) {
        Path file = Paths.get(filename);
        try {
            Files.write(file, list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            needsToBeSaved = false;
            System.out.println("List saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }
    private static void promptToSave() {
        if (needsToBeSaved) {
            System.out.print("You have some unsaved changes. Do you want to save them? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveFile(DEFAULT_FILENAME);
            }
        }
    }
}

package edu.arizona.josesosa.creational.builder;

import java.util.Scanner;

public class ContactTextImporter implements Contact.Importer {
    public String provideName() {
        return askFor("Name: ");
    }

    public String provideEmail() {
        return askFor("Email: ");
    }

    public String providePhone() {
        return askFor("Phone: ");
    }

    public void open() {
        showHeader();
    }

    public void close() {
        showBye();
    }

    private void showHeader() {
        System.out.println("===New Contact Form===");
    }

    private void showBye() {
        System.out.println("Bye!");
    }

    private String askFor(String prompt) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print(prompt);
            return sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException("Error getting " + prompt + " " + e);
        }
    }
}

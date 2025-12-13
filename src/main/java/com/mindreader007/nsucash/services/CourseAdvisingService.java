package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.model.Course;
import com.mindreader007.nsucash.model.AdvisingStudent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CourseAdvisingService {
    private final List<Course> catalog = new ArrayList<>();
    // store selections keyed by username
    private final Map<String, AdvisingStudent> selections = new HashMap<>();
    public static final int MANDATORY_FEE = 10000;

    public CourseAdvisingService() {
        seedCatalog();
    }

    private void seedCatalog() {
        catalog.add(new Course("CSE215", "Intro to OOPs by Java", 3));
        catalog.add(new Course("CSE115", "Intro to Programming", 3));
        catalog.add(new Course("MAT120", "Calculus I", 3));
        catalog.add(new Course("ENG111", "Public Speaking Course", 1));
        catalog.add(new Course("PHY107", "Physics", 1));
    }

    public List<Course> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    private AdvisingStudent ensureStudent(String username, String name) {
        return selections.computeIfAbsent(username, k -> new AdvisingStudent(username, name));
    }

    public List<Course> getSelectedCoursesFor(String username) {
        AdvisingStudent st = selections.get(username);
        if (st == null) return Collections.emptyList();
        return Collections.unmodifiableList(st.getSelectedCourses());
    }

    public void addCourseForUser(String username, String name, Course course) throws Exception {
        AdvisingStudent st = ensureStudent(username, name);
        st.addCourse(course);
    }

    public void removeCourseForUser(String username, String code) {
        AdvisingStudent st = selections.get(username);
        if (st != null) st.removeCourse(code);
    }

    public int calculateTuitionForUser(String username) {
        AdvisingStudent st = selections.get(username);
        if (st == null) return 0;
        return st.totalTuitionFee();
    }

    public int calculateTotalAmountForUser(String username) {
        return calculateTuitionForUser(username) + MANDATORY_FEE;
    }

    /**
     * Confirm payment: logs transaction and writes a receipt.
     * Returns receipt filename or null if something failed.
     */
    public String confirmPayment(String username, String displayName) {
        int total = calculateTotalAmountForUser(username);
        // Log transaction in DB
        TransactionsDAO.addTransaction(username, "advising", total);

        // deduct from UserSession's balance if present
        try {
            com.mindreader007.nsucash.model.User u = UserSession.getUser();
            if (u != null && u.getUsername().equals(username)) {
                double newBal = u.getBalance() - total;
                u.setBalance(newBal);
                UserSession.updateUser(u);
                // To persist balance in DB you'd need a UserDAO; not included here.
            }
        } catch (Exception e) {
            // ignore if we cannot update balance here
        }

        // write receipt (similar to your Payment.generateReceiptAndSave)
        try {
            AdvisingStudent st = selections.get(username);
            if (st == null) return null;

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "receipt_" + username + "_" + timestamp + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(center("NSUcash - Course Advising Payment Receipt", 60)); writer.newLine();
                writer.write("-----------------------------------------------"); writer.newLine();
                writer.write("Student Name: " + displayName); writer.newLine();
                writer.write("Username    : " + username); writer.newLine();
                writer.write("Date & Time : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); writer.newLine();
                writer.write("-----------------------------------------------"); writer.newLine();
                writer.write(String.format("%-10s %-30s %-6s %-8s", "Code", "Course Name", "Cr", "Cost")); writer.newLine();
                writer.write("-----------------------------------------------"); writer.newLine();

                for (Course c : st.getSelectedCourses()) {
                    writer.write(String.format("%-10s %-30s %-6d %-8d", c.getCode(), c.getName(), c.getCredits(), c.getFee()));
                    writer.newLine();
                }

                writer.write("-----------------------------------------------"); writer.newLine();
                writer.write(String.format("Total Credits: %d", st.totalCredits())); writer.newLine();
                writer.write(String.format("Tuition Fee  : %d BDT", st.totalTuitionFee())); writer.newLine();
                writer.write(String.format("Mandatory Fee: %d BDT", MANDATORY_FEE)); writer.newLine();
                writer.write(String.format("Final Amount : %d BDT", total)); writer.newLine();
                writer.write("-----------------------------------------------"); writer.newLine();
                writer.write(center("Payment Status: PAID", 60)); writer.newLine();
                writer.flush();
            }

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String center(String s, int width) {
        if (s.length() >= width) return s;
        int left = (width - s.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < left; ++i) sb.append(' ');
        sb.append(s);
        return sb.toString();
    }
}


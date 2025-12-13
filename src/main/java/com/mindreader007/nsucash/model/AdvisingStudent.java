package com.mindreader007.nsucash.model;

import java.util.ArrayList;
import java.util.List;

public class AdvisingStudent {
    private final String username;
    private final String name;
    private final List<Course> selectedCourses = new ArrayList<>();

    public AdvisingStudent(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void addCourse(Course c) throws Exception {
        for (Course e : selectedCourses) {
            if (e.getCode().equalsIgnoreCase(c.getCode())) {
                throw new Exception("Course with code " + c.getCode() + " already selected.");
            }
        }
        selectedCourses.add(c);
    }

    public void removeCourse(String code) {
        selectedCourses.removeIf(c -> c.getCode().equalsIgnoreCase(code));
    }

    public int totalCredits() {
        return selectedCourses.stream().mapToInt(Course::getCredits).sum();
    }

    public int totalTuitionFee() {
        return selectedCourses.stream().mapToInt(Course::getFee).sum();
    }
}

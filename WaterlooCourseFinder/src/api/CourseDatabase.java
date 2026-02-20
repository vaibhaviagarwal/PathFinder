package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Course;

public class CourseDatabase {
    private static List<Course> courses = new ArrayList<>();
    private static final Map<String, Course> courseIndex = new HashMap<>();

    // Load the JSON file once at startup (and again on refresh)
    public static void load(String fileName) {
        try (Reader reader = Files.newBufferedReader(resolveCourseFilePath(fileName))) {
            Gson gson = new Gson();
            List<Course> parsedCourses = gson.fromJson(reader, new TypeToken<List<Course>>() {}.getType());
            courses = parsedCourses == null ? new ArrayList<>() : parsedCourses;

            courseIndex.clear();
            for (Course course : courses) {
                String key = course.getCode();
                if (key != null && !key.isBlank()) {
                    courseIndex.put(key.toUpperCase(), course);
                }
            }

            System.out.println("Courses loaded: " + courses.size());
        } catch (IOException ioException) {
            System.err.println("Failed to load courses file: " + ioException.getMessage());
        } catch (RuntimeException parseException) {
            System.err.println("Failed to parse courses JSON: " + parseException.getMessage());
        }
    }

    private static Path resolveCourseFilePath(String fileName) throws IOException {
        Path[] candidatePaths = new Path[] {
                Paths.get(fileName),
                Paths.get("WaterlooCourseFinder", fileName),
                Paths.get(System.getProperty("user.dir"), fileName),
                Paths.get(System.getProperty("user.dir"), "WaterlooCourseFinder", fileName)
        };

        for (Path candidatePath : candidatePaths) {
            if (Files.exists(candidatePath)) {
                return candidatePath;
            }
        }

        throw new IOException("Could not find JSON file: " + fileName);
    }

    // Get course by combined code, e.g., "CS115"
    public static Course getCourse(String code) {
        if (code == null) {
            return null;
        }
        return courseIndex.get(code.trim().toUpperCase());
    }

    // Optional: get all courses
    public static List<Course> getAllCourses() {
        return Collections.unmodifiableList(courses);
    }
}

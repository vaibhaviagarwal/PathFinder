PathFinder
PathFinder contains a Java Swing app called WaterlooCourseFinder.
It lets you search University of Waterloo course information from a local JSON dataset.

Project Structure
WaterlooCourseFinder/ (Maven Java app)
CourseFinderUI.java (main UI entry point)
src/api/CourseDatabase.java (course loading and lookup logic)
src/model/Course.java (course data model)
waterloo_courses.json (local course dataset)
courses.json (additional dataset at workspace root)
Requirements
Java 17 or higher
Maven 3.8 or higher
Run the App
Open terminal in project root
Run:
cd WaterlooCourseFinder
Run:
mvn clean compile
Run:
java -cp target/classes:lib/gson-2.11.0.jar CourseFinderUI
Features
Search by course code (example: CS136)
Shows subject, number, title, prerequisites, corequisites, and antirequisites
Refreshes course data from waterloo_courses.json
Notes
The app uses local JSON data (no external API calls)
Keep waterloo_courses.json inside WaterlooCourseFinder so it loads correctly

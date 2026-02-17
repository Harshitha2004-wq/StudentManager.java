package studentmanagement;



	import java.io.*;
	import java.util.ArrayList;
	import java.util.Scanner;

	public class StudentManager {

	
	    static class Student {
	        private String id;
	        private String name;
	        private int age;

	        public Student(String id, String name, int age) {
	            this.id = id;
	            this.name = name;
	            this.age = age;
	        }

	        public String getId() { return id; }
	        public String getName() { return name; }
	        public int getAge() { return age; }

	        public void setAge(int age) { this.age = age; }

	        @Override
	        public String toString() {
	            return id + " | " + name + " | " + age;
	        }
	    }

	   
	    public static void saveStudents(ArrayList<Student> students) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"))) {
	            for (Student s : students) {
	                bw.write(s.getId() + "," + s.getName() + "," + s.getAge());
	                bw.newLine();
	            }
	            System.out.println("All students saved successfully!");
	        } catch (IOException e) {
	            System.out.println("Error saving students: " + e.getMessage());
	        }
	    }

	    
	    public static ArrayList<Student> loadStudents() {
	        ArrayList<Student> students = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length == 3) {
	                    students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2])));
	                }
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("No existing student file found, starting fresh.");
	        } catch (IOException e) {
	            System.out.println("Error loading students: " + e.getMessage());
	        }
	        return students;
	    }

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        ArrayList<Student> students = loadStudents();
	        int choice;

	        do {
	            System.out.println("\n--- Student Management ---");
	            System.out.println("1. Add Student");
	            System.out.println("2. View Students");
	            System.out.println("3. Update Age");
	            System.out.println("4. Exit");
	            System.out.print("Enter choice: ");
	            choice = sc.nextInt();
	            sc.nextLine(); // consume newline

	            switch (choice) {
	                case 1:
	                    System.out.print("Enter ID: ");
	                    String id = sc.nextLine();
	                    System.out.print("Enter Name: ");
	                    String name = sc.nextLine();
	                    System.out.print("Enter Age: ");
	                    int age = sc.nextInt();
	                    sc.nextLine(); // consume newline
	                    students.add(new Student(id, name, age));
	                    saveStudents(students);
	                    break;

	                case 2:
	                    System.out.println("\n--- All Students ---");
	                    for (Student s : students) {
	                        System.out.println(s);
	                    }
	                    break;

	                case 3:
	                    System.out.print("Enter ID to update age: ");
	                    String updateId = sc.nextLine();
	                    boolean found = false;
	                    for (Student s : students) {
	                        if (s.getId().equals(updateId)) {
	                            System.out.print("Enter new age: ");
	                            int newAge = sc.nextInt();
	                            sc.nextLine(); // consume newline
	                            s.setAge(newAge);
	                            saveStudents(students);
	                            System.out.println("Age updated!");
	                            found = true;
	                            break;
	                        }
	                    }
	                    if (!found) System.out.println("Student not found!");
	                    break;

	                case 4:
	                    System.out.println("Exiting program.");
	                    break;

	                default:
	                    System.out.println("Invalid choice!");
	            }

	        } while (choice != 4);

	        sc.close();
	    }
	
}

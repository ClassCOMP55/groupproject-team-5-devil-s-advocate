package starter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class jumpForce {
	
	ArrayList<String> force = new ArrayList<>();

	// PhysicsEngine line 151 
	// Adjust the mainEntity.yVel 
	// Attempt to read in value from jumpForce.txt to then place as the
	// Reference: https://www.baeldung.com/java-file-to-arraylist

	try (BufferedReader br = new BufferedReader(new FileReader("jumpForce.txt"))) { 
	    while (br.ready()) {
	    	force.add(br.readLine());
	    }
	}
}

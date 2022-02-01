package starter;

import java.io.FileReader;
import java.io.IOException;

/**
* @author www.codejava.net
*/
public class jumpForce {

   public static void main(String[] args) {
       try {
           FileReader reader = new FileReader("jumpForce.txt");
           int character;

           while ((character = reader.read()) != -1) {
               System.out.print((char) character);
           }
           reader.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}

/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


	// Reference: https://www.baeldung.com/java-file-to-arraylist

public class jumpForce {
	
	//ArrayList<String> force = new ArrayList<>();

	
	
	try (BufferedReader br = new BufferedReader(new FileReader("jumpForce.txt"))) { 
	    while (br.ready()) {
	    	force.add(br.readLine());
	    }
	}
	}
}
*/
	// PhysicsEngine line 151 
	// Adjust the mainEntity.yVel 
	// Attempt to read in value from jumpForce.txt to then place as the



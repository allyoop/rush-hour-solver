package rushhour;

import java.io.FileNotFoundException;

public class main {

    public static void main(String[] args) throws FileNotFoundException {

        Solver solver = new Solver();

        //solver.solveFromFile("A01.txt","A01.sol");

        //Test the A files
        for(int i = 0; i < 10; i++) {
            solver.solveFromFile("A0" + i + ".txt","A0" + i + ".sol");
        }
        solver.solveFromFile("A10.txt", "A10.sol");

        //Test the B files
        for(int i = 11; i <= 20; i++) {
            solver.solveFromFile("B" + i + ".txt", "B" + i + ".sol");
        }

        //Test the C files
        for(int i = 21; i < 30; i++) {
            solver.solveFromFile("C" + i + ".txt", "C" + i + ".sol");
        }

        //Test the D files
        for(int i = 30; i <= 35; i++) {
            solver.solveFromFile("D" + i + ".txt", "D" + i + ".sol");
        }

    }

}

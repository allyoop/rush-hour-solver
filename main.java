import java.io.FileNotFoundException;

/**
 * Where we can put test files
 *
 * HERE IS OUR PLAN:
 * (Kyle check here if you're looking for *THE PLAN*)
 *
 * 1. Make class BoardState: Holds value of a singular BoardState
 *          VARIABLES:
 *          the actual board (2D char array)
 *          e.g. ..DDD.
 *               M..Q..
 *               MXXQ..
 *               M...FF
 *               VVOO..
 *          the parent/previous BoardState (BoardState)
 *          the move made to get from the parent/previous BoardState to the current (string)
 *          METHODS:
 *          Constructors, setters, getters
 *
 * 2. Make class Car
 *          VARIABLES:
 *          name of the car, e.g. 'X' (char) --> DON'T PUT THIS, JUST NAME THE INSTANCE THE NAME OF THE CAR (e.g. X is the name of the instance of Car that holds the XX car information)
 *          current coordinates (2-element integer array)
 *              > horizontal = far left coordinate
 *              > vertical = top coordinate
 *          direction (boolean)
 *          car length (integer, 2 or 3)
 *          METHODS:
 *          Constructors, setters, getters
 *
 * 3. Make class RushHourSolver
 *      a) Reads file
 *      b) Full analysis of whole board
 *          > Make new Car instance for each car
 *          > Put all Car instances in a list (ArrayList)
 *      c) Makes and initializes first BoardState
 *      d) Create queue, add the first BoardState to the queue
 *      **START OF THE RUSH HOUR SOLVER/BFS**
 *      d) For-loop: for each member in the queue,
 *              re-analyze the coordinates of each Car on the current BoardState <-- Make separate method within RushHourSolver and call it with current BoardState (e.g. findCoordinates)
 *              For-loop: for each car,
 *                      move the car twice (one left/right OR one up/down) <-- OPTIONAL: Make this a separate method within RushHourSolver and call it (e.g. moveCar)
 *                      for each move (right and left/up and down):
 *                          check if the BoardState 2D array is already in the queue, if not:
 *                              create a new instance of BoardState accordingly
 *                              add new BoardState into the queue
 *                              check if that BoardState is solved <-- Make separate method within RushHourSolver and call it (e.g. isSolved)
 *
 * 4. Make method isSolved
 *      a) Checks if there is an X at [5][2] in the given 2D array
 *          if yes: return true
 *          if no: return false
 *
 * 5. Make method printSolution
 *      a) Create a string stack of steps
 *      b) Starting at the end/solved BoardState, loop through all the parents until the parent is NULL
 *      c) Pull from the stack, inputting it into txt file (printing it backwards)
 *
 * 6. Make method findCoordinates
 *      a) Iterates through all elements in the 2D array of the given board
 *      b) Re-sets the coordinates for each car in the Car ArrayList
 */

public class main {

    public static void main(String[] args) throws FileNotFoundException {

        //Test the A files
        for(int i = 0; i < 10; i++) {
            RushHourSolver solverA = new RushHourSolver("A0" + i + ".txt");
        }
        RushHourSolver solverA = new RushHourSolver("A10.txt");

        //Test the B files
        for(int i = 11; i <= 20; i++) {
            RushHourSolver solverB = new RushHourSolver("B" + i + ".txt");
        }

        //Test the C files
        for(int i = 21; i < 30; i++) {
            RushHourSolver solverC = new RushHourSolver("C" + i + ".txt");
        }

        //Test the D files
        for(int i = 30; i <= 35; i++) {
            RushHourSolver solverD = new RushHourSolver("D" + i + ".txt");
        }

        RushHourSolver solver = new RushHourSolver("A00.txt");
    }
}

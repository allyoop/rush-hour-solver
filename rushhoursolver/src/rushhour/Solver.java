package rushhour;

import java.io.*;
import java.util.*;

public class Solver {

	private final static int SIZE = 6;

	public static void solveFromFile(String inputPath, String outputPath) throws FileNotFoundException {

		/**
		 * THE FOLLOWING BLOCK OF CODE SETS UP THE FIRST BOARD FROM THE GIVEN FILE NAME,
		 * ANALYZES THE FIRST BOARD'S CARS, THEN STARTS THE QUEUE (WITH THIS INITIAL BOARD)
		 */

		//List of the heuristic measurements for each board in the boardStackQueue
		List<Integer> heuristics = new ArrayList<>();

		//Initialize the file and scanner; read the file and save the data read
		File board = new File(inputPath);
		Scanner reader = new Scanner(board);

		char[][] boardValues = new char[SIZE][SIZE];

		for (int y = 0; y < SIZE; y++) {
			String row = reader.nextLine();

			for (int x = 0; x < SIZE; x++) {
				boardValues[x][y] = row.charAt(x);

			}
		}

		//Start a queue for a BFS, and add the initial board state
		List<BoardState> boardStateQueue = new ArrayList();
		BoardState currBoard = new BoardState(boardValues);
		boardStateQueue.add(currBoard);
		heuristics.add(0);

		//Creating new instances of the class Car, which holds information for each Car
		char carName;
		int Length;
		boolean carVertical;
		int carXCoord;
		int carYCoord;

		int picker = 0;
		int numOfHeuristics = 1;


		//List of car names/chars that have already been added to carList
		List<Character> foundCars = new ArrayList<>();
		//List of Car instances
		List<Car> carList = new ArrayList<>();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (boardValues[x][y] != '.') {
					if (!foundCars.contains(boardValues[x][y])) {
						carList.add(createCar(boardValues, x, y, boardValues[x][y]));
						foundCars.add(boardValues[x][y]);
					}
				}
			}
		}

		/**
		 * PREV: INITIALIZING THE FIRST BOARD
		 *
		 * THE FOLLOWING BLOCK OF CODE LOOPS THESE STEPS:
		 * 1. PULL THE NEXT BOARD IN THE QUEUE
		 * 2. CHECK IF SAID BOARD IS SOLVED (RUN PRINT-SOLUTION THEN RETURN IF TRUE)
		 * 3. ANALYZE SAID BOARD'S CAR COORDINATES
		 * 4. CREATE NEW BOARDS BY MOVING EACH CAR BY 1 VALID MOVE
		 * 5. ADD SAID BOARD TO THE QUEUE
		 */

		for (int i = 0; ; i++) {
			/**
			 * 1. PULL THE NEXT BOARD IN THE QUEUE
			 */
			//Finds the first index of the lowest heuristic in the heuristic arraylist, pulls that index
			int next = lowestHeuristicFinder((ArrayList<Integer>) heuristics);
			currBoard = boardStateQueue.get(next);

			//Make the aforementioned heuristic position to value -3, denoting it as "processed"
			heuristics.set(next,-3);

			boardValues = currBoard.getBoardState();

			/**
			 * 2. CHECK IF SAID BOARD IS SOLVED (IF TRUE, RUN PRINT-SOLUTION THEN RETURN
			 */
			if (isSolved(boardValues)) {
				printSolution(currBoard, outputPath);
				return;
			}

			//Clear the foundCars list, to be reused when re-setting coordinates
			foundCars.clear();
			//Pointer to the current car being analyzed
			Car currCar = new Car();

			/**
			 * 3. ANALYZE SAID BOARD'S CAR COORDINATES
			 */
			//Re-setting each car's new coordinates in the current board
			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					if (boardValues[x][y] != '.') {
						if (!foundCars.contains(boardValues[x][y])) {
							foundCars.add(boardValues[x][y]);

							for (int j = 0; j < carList.size(); j++) {
								Car aCar = carList.get(j);
								if (aCar.getName() == boardValues[x][y]) {
									currCar = carList.get(j);
									break;
								}
							}

							currCar.setXCoordinate(x);
							currCar.setYCoordinate(y);
						}
					}
				}
			}

			/**
			 * 4. CREATE NEW BOARDS BY MOVING EACH CAR BY 1 VALID MOVE
			 *
			 *
			 */
			for(Car car : carList) {
				//Testing for valid movement if the car is vertical
				if(car.isVertical) {
					//Moving UP
					if(isLegal(currBoard.getBoardState(), car.getXCoordinates(), car.getYCoordinates() - 1)) {
						BoardState newBoardState = moveCarVertical(currBoard, car, car.getLength(), true);

						//Adding new board to the queue if it doesn't already exist in the queue
						if (!inQueue(newBoardState, boardStateQueue)){
							boardStateQueue.add(newBoardState);
							boolean coordinateAcquired = false;
							Car xCar = new Car();
							char xCarName = 'X';
							for (Car tempCar:carList){
								if (!coordinateAcquired) {
									if (tempCar.getName() == xCarName) {
										xCar = tempCar;
										coordinateAcquired = true;
									}
								}
							}

							int heuristicCounter = 0;
							for (int x = xCar.getXCoordinates(); x < 6; x++){
								if (newBoardState.getValue(x,2) != 'X' && newBoardState.getValue(x, 2) != '.'){
									heuristicCounter++;
								}
							}
							heuristics.add(numOfHeuristics, heuristicCounter + 5-xCar.getXCoordinates());
							numOfHeuristics++;
						}
					}

					//Moving DOWN
					if(isLegal(currBoard.getBoardState(), car.getXCoordinates(), car.getYCoordinates() + car.carLength)) {
						BoardState newBoardState = moveCarVertical(currBoard, car, car.getLength(), false);

						//Adding new board to the queue if it doesn't already exist in the queue
						if (!inQueue(newBoardState, boardStateQueue)) {
							boardStateQueue.add(newBoardState);
							boolean coordinateAcquired = false;
							Car xCar = new Car();
							char xCarName = 'X';
							for (Car tempCar:carList){
								if (!coordinateAcquired) {
									if (tempCar.getName() == xCarName) {
										xCar = tempCar;
										coordinateAcquired = true;
									}
								}
							}

							int heuristicCounter = 0;
							for (int x = xCar.getXCoordinates(); x < 6; x++){
								if (newBoardState.getValue(x,2) != 'X' && newBoardState.getValue(x, 2) != '.'){
									heuristicCounter++;
								}
							}
							heuristics.add(numOfHeuristics, heuristicCounter + 5-xCar.getXCoordinates());
							numOfHeuristics++;
						}
					}
				}

				//Testing for valid movement if the car is horizontal
				if(!car.isVertical) {
					//Moving LEFT
					if(isLegal(currBoard.getBoardState(), car.getXCoordinates() - 1, car.getYCoordinates())) {
						BoardState newBoardState = moveCarHorizontal(currBoard, car, car.getLength(), true);

						//Adding new board to the queue if it doesn't already exist in the queue
						if (!inQueue(newBoardState, boardStateQueue)){
							boardStateQueue.add(newBoardState);
							boolean coordinateAcquired = false;
							Car xCar = new Car();
							char xCarName = 'X';
							for (Car tempCar:carList){
								if (!coordinateAcquired) {
									if (tempCar.getName() == xCarName) {
										xCar = tempCar;
										coordinateAcquired = true;
									}
								}
							}

							int heuristicCounter = 0;
							for (int x = xCar.getXCoordinates(); x < 6; x++){
								if (newBoardState.getValue(x,2) != 'X' && newBoardState.getValue(x, 2) != '.'){
									heuristicCounter++;
								}
							}
							heuristics.add(numOfHeuristics, heuristicCounter + 5-xCar.getXCoordinates());
							numOfHeuristics++;
						}
					}

					//Moving RIGHT
					if(isLegal(currBoard.getBoardState(), car.getXCoordinates() + car.getLength(), car.getYCoordinates())) {
						BoardState newBoardState = moveCarHorizontal(currBoard, car, car.getLength(), false);

						//Adding new board to the queue if it doesn't already exist in the queue
						if (!inQueue(newBoardState, boardStateQueue)){
							boardStateQueue.add(newBoardState);
							boolean coordinateAcquired = false;
							Car xCar = new Car();
							char xCarName = 'X';
							for (Car tempCar:carList){
								if (!coordinateAcquired) {
									if (tempCar.getName() == xCarName) {
										xCar = tempCar;
										coordinateAcquired = true;
									}
								}
							}

							int heuristicCounter = 0;
							for (int x = xCar.getXCoordinates(); x < 6; x++){
								if (newBoardState.getValue(x,2) != 'X' && newBoardState.getValue(x, 2) != '.'){
									heuristicCounter++;
								}
							}
							heuristics.add(numOfHeuristics, heuristicCounter + 5-xCar.getXCoordinates());
							numOfHeuristics++;
						}
					}
				}
			}
		}
	}

	/**
	 * HELPER FUNCTION: Moves the car one space horizontally
	 * @param currBoard : the original/parent board of this new move
	 * @param currCar : the car being moved
	 * @param carLength : length of the car being moved
	 * @param left : the direction (true = left, false = right)
	 * @return : a new instance of BoardState, which is currBoard but with currCar moved accordingly (and the appropriate parentBoard & parentMove)
	 */
	private static BoardState moveCarHorizontal(BoardState currBoard, Car currCar, int carLength, boolean left) {
		//Recreate currBoard in a new 2D array, newBoardValues
		char[][] newBoardValues = new char[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				newBoardValues[i][j] = currBoard.getValue(i,j);
			}
		}

		//newX/Y are the new moved position of the car
		int newX = -1;
		int newY = -1;
		//oldX/Y are the old position of the car (will be changed to '.')
		int oldX = -1;
		int oldY = -1;

		char direction = '0';

		//Update newBoardValues accordingly (if legal)
		if(left) {
			newX = currCar.getXCoordinates() - 1;
			newY = currCar.getYCoordinates();
			oldX = currCar.getXCoordinates() + carLength - 1;
			oldY = currCar.getYCoordinates();
			direction = 'L';
		}
		if(!left) {
			newX = currCar.getXCoordinates() + carLength;
			newY = currCar.getYCoordinates();
			oldX = currCar.getXCoordinates();
			oldY = currCar.getYCoordinates();
			direction = 'R';
		}
		if(newX != -1 && newY != -1 && oldX != -1 && oldY != -1) {
			newBoardValues[newX][newY] = currCar.getName();
			newBoardValues[oldX][oldY] = '.';
			String parentMove = new StringBuilder().append(currCar.getName()).append(direction).append('1').toString();


			BoardState newBoard = new BoardState(newBoardValues, currBoard, parentMove);
			return newBoard;
		}

		return null;
	}

	/**
	 * HELPER FUNCTION: Moves the car one space vertically
	 * @param currBoard : the original/parent board of this new move
	 * @param currCar : the car being moved
	 * @param carLength : the length of the car
	 * @param up : the direction the car is moving (true = up, false = down)
	 * @return : a new instance of BoardState, which is the same as currBoard but with the car moved accordingly (and the appropriate parentBoard & parentMove)
	 */
	private static BoardState moveCarVertical(BoardState currBoard, Car currCar, int carLength, boolean up) {
		//Recreate currBoard in a new 2D array, newBoardValues
		char[][] newBoardValues = new char[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				newBoardValues[i][j] = currBoard.getValue(i,j);
			}
		}

		//newX/Y are the new moved position of the car
		int newX = -1;
		int newY = -1;
		//oldX/Y are the old position of the car (will be changed to '.')
		int oldX = -1;
		int oldY = -1;

		char direction = '0';

		//Update newBoardValues accordingly (if legal)
		if(up) {
			newX = currCar.getXCoordinates();
			newY = currCar.getYCoordinates() - 1;
			oldX = currCar.getXCoordinates();
			oldY = currCar.getYCoordinates() + carLength - 1;
			direction = 'U';
		}
		if(!up) {
			newX = currCar.getXCoordinates();
			newY = currCar.getYCoordinates() + carLength;
			oldX = currCar.getXCoordinates();
			oldY = currCar.getYCoordinates();
			direction = 'D';
		}
		if(newX != -1 && newY != -1 && oldX != -1 && oldY != -1) {
			newBoardValues[newX][newY] = currCar.getName();
			newBoardValues[oldX][oldY] = '.';
			String parentMove = new StringBuilder().append(currCar.getName()).append(direction).append('1').toString();

			BoardState newBoard = new BoardState(newBoardValues, currBoard, parentMove);
			return newBoard;
		}

		return null;
	}

	/**
	 * HELPER FUNCTION: Checks if given position is empty
	 * @param boardValues : the board state that is being checked
	 * @param x : x coordinate of the position being checked
	 * @param y : y coordinate of the position being checked
	 * @return : returns true if the space is empty, false if there is something in the way (another car or the edge of the board)
	 */
	public static boolean isLegal(char[][] boardValues, int x, int y) {
		if ((x >= 6) || (y >= 6) || (x < 0) || (y < 0)) {
			return false;
		}
		if (boardValues[x][y] != '.') {
			return false;
		}
		return true;
	}

	/**
	 * HELPER FUNCTION: Creates a new instance of car, based on the boardState
	 * @param board : the board being analyzed
	 * @param xC : far left x coordinate
	 * @param yC : the top y coordinate
	 * @param name : the char representing the car
	 * @return : returns an instance of Car, made using the given arguments
	 */
	private static Car createCar(char[][] board, int xC, int yC, char name) {
		boolean isVertical;
		int carLength;

		//Sets isVertical
		if (((yC + 1) < 6) && (board[xC][yC + 1] == name)) {
			isVertical = true;
		} else {
			isVertical = false;
		}

		//Sets carLength
		if (isVertical) {
			if (((yC + 2) < 6) && (board[xC][yC + 2] == name)) {
				carLength = 3;
			} else {
				carLength = 2;
			}
		} else {
			if (((xC + 2) < 6) && (board[xC + 2][yC] == name)) {
				carLength = 3;
			} else {
				carLength = 2;
			}
		}

		//Creates a new instance of Car, and returns it
		Car newCar = new Car(isVertical, carLength, name);
		newCar.setXCoordinate(xC);
		newCar.setYCoordinate(yC);

		return newCar;
	}

	/**
	 * HELPER FUNCTION: Checks the (5,2) position to see if the XX car has reached the end
	 * @param board : the board being checked
	 * @return : returns true if solved, false if not solved
	 */
	private static boolean isSolved(char[][] board) {
		return (board[5][2] == 'X' || board[5][2] == 'x');
	}

	/**
	 * HELPER FUNCTION: Prints the final solution into a file based on the given name
	 * @param solvedBoard : the BoardState of the solved board, which will help trace back the parentMoves
	 * @param solutionName : name of the file that will be outputted into
	 * @throws FileNotFoundException : if the file solutionName is not found/fails
	 */
	private static void printSolution(BoardState solvedBoard, String solutionName) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(solutionName);

		//A stack to hold all the moves to get from the start to end
		Stack<String> solutionList = new Stack<String>();
		//Pointer to the current BoardState being processed
		BoardState currBoard = solvedBoard;

		//Iterate through all the parent boards and add the parentMove to the Stack (in reverse order)
		while (currBoard != null) {
			solutionList.push(currBoard.getParentMove());
			currBoard = currBoard.getParentBoardState();
		}

		solutionList.pop();

		while(!solutionList.isEmpty()) {
			out.println(solutionList.pop());
		}

		out.close();
	}

	/**
	 * HELPER FUNCTION: Finds the index of the member in list A with the lowest heuristic measurement
	 * @param A : the list being iterated through
	 * @return : returns the index of the lowest heuristic measurement
	 */
	private static int lowestHeuristicFinder(ArrayList<Integer> A){
		int counter = -1;
		int index = -1;
		while (index == -1){
			 counter++;
			 index = A.indexOf(counter);
		}
		return index;
	}

	/**
	 * DEBUGGER FUNCTION: Prints a whole board (2D char array)
	 * @param board : board to be printed
	 */
	private static void printBoard(char[][] board) {
		System.out.println("Printing board:");
		for(int i = 0; i < SIZE; i++) {
			System.out.println("[ " + board[0][i] + " " + board[1][i] + " " + board[2][i] + " " + board[3][i] + " " + board[4][i] + " " + board[5][i] + " ]");
		}
	}

	/**
	 * DEBUGGER FUNCTION: Prints all boards in the queue currently
	 * @param boardList : the queue list
	 */
	private static void printBoardQueue(List<BoardState> boardList) {
		System.out.println("PRINTING BOARD QUEUE");
		for(int i = 0; i < boardList.size(); i++) {
			printBoard(boardList.get(i).getBoardState());
		}
	}

	/**
	 * HELPER FUNCTION: Checks if the given board (the literal 2D char array) is already in another BoardState in boardStateQueue
	 * @param newBoard : the board that we are comparing against the queue
	 * @param boardStateQueue : the current queue
	 * @return : true if it's already in the queue, false if it isn't in the queue
	 */
	private static boolean inQueue(BoardState newBoard, List<BoardState> boardStateQueue) {
		for(BoardState board : boardStateQueue) {
			if(newBoard.isEqual(board)) {
				return true;
			}
		}
		return false;
	}
}

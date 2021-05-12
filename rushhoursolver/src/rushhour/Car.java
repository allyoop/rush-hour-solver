package rushhour;

public class Car {
    //Data stored for each car in a board
    //carCoordinates is dynamic and changed as needed, using the setter
    //isVertical and carLength are static (set once and never changes)
    int xCoordinate;
    int yCoordinate;
    boolean isVertical;
    int carLength;
    char carName;

    //Constructor, sets isVertical and carLength
    //carCoordinates are set later (and frequently)
    public Car(boolean vertical, int cLen, char name) {
        isVertical = vertical;
        carLength = cLen;
        carName = name;
    }

    public Car() {    }

    //carCoordinates setter
    public void setXCoordinate(int coordinate) {
        xCoordinate = coordinate;
    }

    //carCoordinates getter
    public int getXCoordinates() {
        return xCoordinate;
    }

    //carCoordinates setter
    public void setYCoordinate(int coordinate) {
        yCoordinate = coordinate;
    }

    //carCoordinates getter
    public int getYCoordinates() {
        return yCoordinate;
    }

    //Name setter
    public void setName(char name) { carName = name; }

    //carName getter
    public char getName() {
        return carName;
    }

    //IsVertical setter
    public void setIsVertical(boolean vert) { isVertical = vert; }

    //isVertical getter
    public boolean getIsVertical() {
        return isVertical;
    }

    //Length setter
    public void setCarLength(int len) { carLength = len; }

    //carLength getter
    public int getLength() {
        return carLength;
    }
}

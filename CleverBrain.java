import student.tetris.*;

/**
 *  This class will implement the Brain interface for tetris.
 *  This will be an "AI" that plays tetris
 *
 *  @author Nicolas Turner (nicturn)
 *  @version (2023.04.03)
 */
public class CleverBrain
    implements Brain
{
    //~ Fields ................................................................



    //~ Constructor ...........................................................
    /**
     * Initializes a newly created NoBrainer object.
     */
    public CleverBrain()
    {
        super();
        /*# Do any work to initialize your class here. */
    }


    //~ Methods ...............................................................
    
    /**
     * - Determine where the piece would land, 
     * if dropped in that column (using the appropriate method on the Board)    
     * - Place the piece in that position 
     * (using the appropriate method on the Board)
     * - Clear any rows that were filled 
     * (using the appropriate method on the Board)
     * - Calculate the score for this possible move, 
     * using your rateBoard() method
     * - Undo the changes
     * 
     * @param board takes board as parameter
     * @param piece takes given piece as a parameter
     * @param heightLimit the limit for the height
     * @return move returns the move
     */
    public Move bestMove(Board board, Piece piece, int heightLimit)
    {
        double score = Double.MAX_VALUE;
        int yCoor = 0;
        
        Move move = new Move(piece);
                
        Piece[] rotations = piece.getRotations();
        
        //for (Piece rotate: piece.getRotations())
        
        for (int rotate = 0; rotate < rotations.length; rotate++)
        {
            for (int x = 0; x < (board.getWidth() - piece.getWidth()); x++)
            {   
                yCoor = board.rowAfterDrop(piece, x);
                Point xyCoor = new Point(x, yCoor);
                
                int place = board.place(rotations[rotate], xyCoor); //changed
                
                
                System.out.println("score" + score); //tests
                System.out.println("rotation" + rotate); //tests
                
                double rating = rateBoard(board);
                System.out.println("INITAIAL  " + rating); //tests
                
                if ((place == Board.PLACE_ROW_FILLED)
                    || (place == Board.PLACE_OK)
                    && (rating < score) 
                    && (yCoor + rotations[rotate].getHeight())
                    < heightLimit) //changed
                {
                    score = rating;
                    
                    move.setLocation(xyCoor);
                    move.setPiece(rotations[rotate]); //changed
                    move.setScore(score);
                } //if
                board.undo();
            } //for
        } //for each
        
        System.out.println("best" + move); //tests
        
        return move;
    }
    /**
     * This method will give a rating based upon how "good" or "bad" 
     * a given board is. The higher the board rating the worse the board is.
     * 
     * @param board takes a board as a parameter to be assessed.
     * @return rating returns the rating
     */
    public double rateBoard(Board board)
    {
        double rating = 0.0;
        int[] columnHeights = board.getColumnHeights();
        
        double sum = 0.0;
        double maximumHeight = Double.MIN_VALUE;
        double minimumHeight = Double.MAX_VALUE;
        
        

        for (int column = 0; column < columnHeights.length; column++)
        {
            int height = columnHeights[column];
            sum = sum + height;
            
            if (height > maximumHeight)
            {
                maximumHeight = height;
            }
            if (height < minimumHeight)
            {
                minimumHeight = height;
            }
        }
    
        return (sum / columnHeights.length) + 
            (maximumHeight - minimumHeight) + maximumHeight;
    } //rateBoard
    /**
     * This will take a board and column as a parameter.
     * This will return the column rating as a double.
     * A higher column rating is less deseriable than a lower.
     * 
     * @param board takes the board
     * @param column takes the specific column 
     * @return rating returns the rating
     */
    public double rateColumn(Board board, int column)
    {
        int[] height = board.getColumnHeights();
        double rating = height[column];
        
        return rating; 
    } //rateColumn
} //class

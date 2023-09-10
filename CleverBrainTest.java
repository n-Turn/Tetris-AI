import student.micro.*;
import static org.assertj.core.api.Assertions.*;
import student.tetris.*;
import java.util.*;

// -------------------------------------------------------------------------
/**
 *  Test clas for Clever brain
 *
 *  @author Nicolas Turner (nicturn)
 *  @version (2023.04.03)
 */
public class CleverBrainTest
    extends TestCase
{
    //~ Fields ................................................................
    private CleverBrain x;

    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new CleverBrainTest test object.
     */
    public CleverBrainTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp()
    {
        /*# Insert your own setup code here */
        x = new CleverBrain();
    }


    // ----------------------------------------------------------
    /*# Insert your own test methods here */
    /**
     * test method for rate bestMove()
     */
    public void testBestMove()
    {
        Board board = new Board(10, 24,
            "          ",
            "          ",
            "          ",
            "######## #",
            "######## #",
            "######## #",
            "######## #"
            );
        Piece piece = Piece.getPiece(Piece.STICK, 0);

        // Now call the brain
        Move move = x.bestMove(board, piece, 20);
        Point p = new Point(8, 0);
        
        // Expect the lower left destination is where the hole is
        assertThat(move.getLocation()).isEqualTo(p); 
        // Expect the piece is not rotated at all
        assertEquals(move.getPiece(), 
            Piece.getPiece(Piece.STICK, 2));
        assertEquals(Board.PLACE_ROW_FILLED, 
            board.place(piece, p));
    }
    /**
     * test method for rate bestMove()
     */
    public void testBestMoveTwo()
    {
        Board board = new Board(10, 24,
            "   ###  # ",
            "  ########",
            "#######   ",
            " #########",
            "##### ### ",
            "######## #",
            "######### "
            );
        System.out.print(board);
        Piece piece = Piece.getPiece(Piece.LEFT_L, 7);

        // Now call the brain
        Move move = x.bestMove(board, piece, 20);
        Point p = new Point(0, 6);
        
        // Expect the lower left destination is where the hole is
        assertThat(move.getLocation()).isEqualTo(p); 
        // Expect the piece is not rotated at all
        assertEquals(move.getPiece(), Piece.getPiece(Piece.LEFT_L, 7));
    }
    /**
     * test method for bestmove to make sure it stops at max height
     */
    public void testBestMoveThree()
    {
        Board board = new Board(10, 24,
            "####### # ",
            "##########",
            "#######   ",
            " #########",
            "##### ### ",
            "######## #",
            "######### "
            );
        System.out.print(board);
        Piece piece = Piece.getPiece(Piece.LEFT_L, 7);

        // Now call the brain
        Move move = x.bestMove(board, piece, 7);
        
        // Expect the lower left destination is where the hole is
        assertThat(move.getLocation()).isEqualTo(null); 
    }
    /**
     * tests the rateBoard method to make sure it gives the right rating
     */
    public void testRateBoard()
    {
        Board start = new Board(10, 4,
            "          ",
            "  #       ",
            "  #  #     ",
            "#### #####"
            ); 
        
        double rating = x.rateBoard(start);
        
        assertEquals(rating, 7.2, .001);
    }
    /**
     * test case where our board has one row filled with blocks except for
     * one single cell, and we want to test rateBoard() on this configuration.
     */
    public void testLonRow0()
    {
        Board board = new Board(10, 24,
            "#### #####"
            );
        Piece piece = Piece.getPiece(Piece.RIGHT_L, 0);

        // Now call the brain
        Move move = x.bestMove(board, piece, 20);

        // Expect the lower left destination is where the hole is
        assertThat(move.getLocation()).isEqualTo(new Point(2, 1));
    }
    /**
     * tests best move again
     */
    public void testRateBoardOnRow0()
    {
        Board board = new Board(10, 24,
            "#### #####"
            );

        // Now call the brain
        double score = x.rateBoard(board);

        // Use your own expected score instead
        assertThat(score).isEqualTo(2.9, within(0.01));
    }
    /**
     * tests rateColumn to make sure it gives the right value
     */
    public void testRateColumn()
    {
        Board start = new Board(10, 4,
            "          ",
            "  #       ",
            "  #       ",
            "#### #####"
            );
        
        double rating = x.rateColumn(start, 2);
        
        assertEquals(rating, 3.0, .001);
    }
}

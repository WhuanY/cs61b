package game2048;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author WUYUHUAN
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        board.setViewingPerspective(side);
        changed = isChanged(changed);//函数返回changed变量的真值
        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    private boolean isChanged(boolean changed) {
        ArrayList<Integer>[] valids = new ArrayList[size()]; // 使用ArrayList数组来动态存储行数
        // 初始化valids数组
        for (int i = 0; i < valids.length; i++) {
            valids[i] = new ArrayList<>();
        }

        //遍历board，如果(col,row)存在方块，则将row存入valids[col][]中
        for(int col=0;col<board.size();col++){
            for(int row= board.size()-1; row>=0;row--){
                if(board.tile(col,row)!=null)valids[col].add(row);
            }
        }

        //遍历每一列
        for(int i = 0;i<size();i++){
            //如果某一列均为空，跳过
            if(valids[i].isEmpty()) continue;
            //注意坐标表示，board左下角为(0,0)，右上角为(3,3)
            if(valids[i].size()==1){
                Tile t = board.tile(i,valids[i].get(0));
                if(t.row()!=3) changed =true;
                board.move(i, 3,t);
            } else if (valids[i].size()==2) {
                Tile t = board.tile(i,valids[i].get(0));
                Tile tile1 = board.tile(i,valids[i].get(1));
                if(t.row()!=3) changed =true;
                board.move(i, 3,t);
                if(tile1.value()==t.value()) {
                    changed =true;
                    board.move(i,3,tile1);
                    score+=board.tile(i,3).value();
                }
                else {
                    if(tile1.row()!=2) changed =true;
                    board.move(i,2,tile1);
                }
            } else if (valids[i].size()==3) {
                Tile t = board.tile(i,valids[i].get(0));
                if(t.row()!=3) changed =true;
                board.move(i, 3,t);
                Tile tile1 = board.tile(i,valids[i].get(1));
                Tile tile2 = board.tile(i,valids[i].get(2));
                if(tile1.value()==t.value()){
                    changed =true;
                    board.move(i,3,tile1);
                    score+=board.tile(i,3).value();
                    board.move(i, 2,tile2);
                }else {
                    if(tile1.row()!=2) changed =true;
                    board.move(i,2,tile1);
                    if(tile2.value()==tile1.value()){
                        changed =true;
                        board.move(i,2, tile2);
                        score+=board.tile(i,2).value();
                    }
                    else {
                        if(tile2.row()!=1) changed =true;
                        board.move(i,1,tile2);
                    }
                }
            } else if (valids[i].size()==4) {
                Tile t = board.tile(i,valids[i].get(0));
                if(t.row()!=3) changed =true;
                board.move(i, 3,t);
                Tile tile1 = board.tile(i,valids[i].get(1));
                Tile tile2 = board.tile(i,valids[i].get(2));
                Tile tile3 = board.tile(i,valids[i].get(3));
                if(tile1.value()==t.value()){
                    changed =true;
                    board.move(i,3,tile1);
                    score+=board.tile(i,3).value();
                    board.move(i, 2,tile2);
                    if(tile2.value()==tile3.value()){
                        board.move(i, 2, tile3);
                        score+=board.tile(i,2).value();
                    }else {
                        board.move(i,1,tile3);
                    }
                }else{
                    if(tile1.row()!=2) changed =true;
                    board.move(i,2,tile1);
                    if(tile1.value()==tile2.value()){
                        changed =true;
                        board.move(i,2,tile2);
                        score+= board.tile(i,2).value();
                        board.move(i,1,tile3);
                    }
                    else{
                        if(tile2.row()!=1) changed =true;
                        board.move(i,1,tile2);
                        if(tile2.value()==tile3.value()){
                            changed =true;
                            board.move(i,1,tile3);
                            score+=board.tile(i,1).value();
                        }
                    }
                }
            }
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    continue;
                }
                if (b.tile(i, j).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        }
        if (havaAjacentNumberAtRow(b) || haveAjacentNumberAtColumn(b)) {
            return true;
        }
        return false;
    }
    private static boolean havaAjacentNumberAtRow(Board b) {
        for (int row = 0; row < b.size(); row++) {
            int temp = b.tile(0, row).value();
            for (int col = 1; col < b.size(); col++) {
                if (b.tile(col,row).value() == temp) {
                    return true;
                }
                else {
                    temp = b.tile(row, col).value();
                }
            }
        }
        return false;
    }
    private static boolean haveAjacentNumberAtColumn(Board b) {
        for (int col = 0; col < b.size(); col++) {
            int temp = b.tile(0, col).value();
            for (int row = 1; row < b.size(); row++) {
                if (b.tile(col, row).value() == temp) {
                    return true;
                }
                else {
                    temp = b.tile(col, row).value();
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}

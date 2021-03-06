public class QueenBoard{
  public static void main(String[]args){
    //QueenBoard q = new QueenBoard(8);
    //q.solve();
    //System.out.println(q);
    //q.clearBoard();
    //System.out.println(q.countSolutions());
    //System.out.println(q);
    //q.solve();
    //System.out.println(q);
    runTest(6);
  }

  private int[][] board;

  public QueenBoard(int size){
    if (size < 0) throw new IllegalArgumentException("size can't be less than 0");
    board = new int[size][size];
  }

  public boolean solve() {
    throwException();
    return solveHelper(0, 0);
  }

  public int countSolutions() {
    throwException();
    int output = countSolutionsHelper(0);
    clearBoard();
    return output;
  }

  //start row is where the for loop will start so that it doesn't get stuck
  //in endless recursion.
  private boolean solveHelper(int currentCol, int startRow){
    if (board.length == 0) return true;
    if (currentCol == board[0].length) return true;
    else if (currentCol < 0) return false;
    else {
      for (int idx = startRow; idx < board.length; idx ++){
        if (addQueen(idx, currentCol)) return solveHelper(currentCol + 1, 0);
      }
      if (currentCol == 0){
        clearBoard();
        return false;
      }
      int start = 0;
      for (int idx = 0; idx < board.length; idx ++){
        if (board[idx][currentCol - 1] == -1){
          removeQueen(idx, currentCol - 1);
          start = idx + 1;
        }
      }
      return solveHelper(currentCol - 1, start);
    }
  }

  public int countSolutionsHelper(int col){
    if (board.length == 0) return 1;
    if (col == board[0].length) return 1;
    else{
      int total = 0;
      for (int idx = 0; idx < board.length; idx ++){
        if (addQueen(idx, col)){
          total += countSolutionsHelper(col + 1);
          removeQueen(idx, col);
        }
      }
      return total;
    }
  }

  public void clearBoard(){
    board = new int[board.length][board.length];
  }

  public void throwException(){
    boolean allZeros = true;
    for (int idx = 0; idx < board[0].length; idx ++){
      if (board[0][idx] != 0) allZeros = false;
    }
    if (!allZeros) throw new IllegalStateException("board must be empty");
  }

  private boolean addQueen(int r, int c){
    if (board[r][c] == 0){
      board[r][c] = -1;
      for (int idx = 1; idx < board.length; idx ++){
        if (r + idx < board.length) board[r + idx][c] ++;
        if (r - idx >= 0) board[r - idx][c] ++;
        if (c + idx < board[0].length) board[r][c + idx] ++;
        if (c - idx >= 0) board[r][c - idx] ++;
        if (r + idx < board.length && c + idx < board[0].length) board[r + idx][c + idx] ++;
        if (r + idx < board.length && c - idx >= 0) board[r + idx][c - idx] ++;
        if (c + idx < board.length && r - idx >= 0) board[r - idx][c + idx] ++;
        if (r - idx >= 0 && c - idx >= 0) board[r - idx][c - idx] ++;
      }
      return true;
    }
    return false;
  }

  private boolean removeQueen(int r, int c){
    if (board[r][c] == -1){
      board[r][c] = 0;
      for (int idx = 1; idx < board.length; idx ++){
        if (r + idx < board.length) board[r + idx][c] --;
        if (r - idx >= 0) board[r - idx][c] --;
        if (c + idx < board[0].length) board[r][c + idx] --;
        if (c - idx >= 0) board[r][c - idx] --;
        if (r + idx < board.length && c + idx < board[0].length) board[r + idx][c + idx] --;
        if (r + idx < board.length && c - idx >= 0) board[r + idx][c - idx] --;
        if (c + idx < board.length && r - idx >= 0) board[r - idx][c + idx] --;
        if (r - idx >= 0 && c - idx >= 0) board[r - idx][c - idx] --;
      }
      return true;
    }
    return false;
  }

  //testcase must be a valid index of your input/output array
public static void runTest(int i){
  QueenBoard b;
  int[]tests =   {1,2,3,4,5,8};
  int[]answers = {1,0,0,2,10,92};
  if(i >= 0 && i < tests.length ){
    int size = tests[i];
    int correct = answers[i];
    b = new QueenBoard(size);
    int ans  = b.countSolutions();

    if(correct==ans){
      System.out.println("PASS board size: "+tests[i]+" "+ans);
    }else{
      System.out.println("FAIL board size: "+tests[i]+" "+ans+" vs "+correct);
    }
  }
}

  public String toString(){
    if (board.length == 0) return "";
    String output = "";
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx2 ++){
        if (board[idx][idx2] == -1) output += "Q ";
        else output += "_ ";
      }
      output += "\n";
    }
    return output.substring(0, output.length() - 1);
  }

  //a debug toString so I can see the values of every square,
  //rather than just whether or not there's a queen there.
  public String TSHelper(){
    if (board.length == 0) return "";
    String output = "";
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx2 ++){
        output += board[idx][idx2] + " ";
      }
      output += "\n";
    }
    return output.substring(0, output.length() - 1);
  }
}

import java.util.Random;
public class Board{
private Tile[][] board;
private int dimensionr;
private int dimensionc;
private MainMarket market;
//constructor give it a nxm size to print those in lanes form
    public Board(int dimensionr,int dimensionc ){
        this.dimensionr= dimensionr;
        this.dimensionc= dimensionc;

        //make a board of dimension by dimension size
        board= new Tile[dimensionr][dimensionc];

        intialize_board();
        market= new MainMarket();
        
    }
    //constructor which will set up board base on how many lane you would like
    public Board(int lane){
        this((lane*2+lane-1),(lane*2+lane-1));

    }
    

public int getrow(){
    return this.dimensionr;
}

public int getcol(){
    return this.dimensionc;
}

//initialize the board
public void intialize_board(){
    set_regularlane(0);
    
}

//add the lane to to the board.

public void set_regularlane(int curr_row){
    int i;
    int k;
    int val;
    Random rand = new Random(); 
    for( i= curr_row; i< curr_row+2; i++){
        for ( k= 0; k< dimensionc;k++){
            if(k==0||k== dimensionc-1){
                board[i][k]= new Tile(i,k,'N');
                
            }    
            else{
                
                 val = rand.nextInt(10);
                
               
                if(val == 0 ){
                    board[i][k]= new Tile(i, k,'B');
                 }
                if(val== 1){
                    board[i][k]= new Tile(i,k,'C');
                
                 }
                 if(val==2 ){
                    board[i][k]= new Tile(i,k,'K');
                
                    }
                 if (val >= 3){
                    board[i][k]= new Tile(i,k,'P');
                 
                }

    
        }
    }
} 
        if(i< dimensionr-1){
        setnolane(i);
     }

    
}

//add the nonelane to board
public void setnolane(int curr_row){
    
    for (int k= 0; k< dimensionc;k++){
       
        board[curr_row][k]= new Tile(curr_row,k,'X');
    }
    if(curr_row+1 < dimensionr-1){
    set_regularlane(curr_row+1);
    }

}

public String movingtoempyspace(int row,int col,char piece){
   
    if(row>=this.dimensionr || col>=this.dimensionc|| row<0 || col<0){
        return "X";
    } 
    
    Tile current= board[row][col];
    if(current.getDisplaytile()=='X'){
        // System.out.println(current.getDisplaytile()=='X');
      
        return "X";
    }
    if(current.getDisplaytile()=='M'){
        
        return "M";
        
    }
   
    setPiece(row, col, piece);
    return "OK";
}
public void setPiece(int r, int c,char piece){
    
    Tile current= board[r][c];
    current.setDisplaytile(piece);
}
//move each hero/monser  to a position r c. 
public void move(int r,int c, String piece){
     System.out.println(r+" "+c);
     Tile current = board[r][c];
    current.set_character_piece(piece);
   
}
public void SetPlayerPiece(char piece){
    //  System.out.println(this.dimensionr/2);
     int r= this.dimensionr/2;
   
    int c= this.dimensionc/2;
    setPiece(r, c,piece);
}
// print the board at current state
public void printBoard(){
    String String1;
    String String2;
    String String3;
    for (int i = 0; i < dimensionr; i++) {
        String1="";
        String2="";
        String3="";
     for (int k = 0; k < dimensionc; k++) {
       
       
        Tile curr = board[k][i];
        if(curr.getDisplaytile()=='N'){
           String1+=" N - N - N ";
           String2+=" |   "+curr.get_characterpiece()+"  | ";
           String3+=" N - N - N ";
        }
        if(curr.getDisplaytile()=='B'){
             String1+=" B - B - B ";
             String2+=" |   "+curr.get_characterpiece()+"  | ";
             String3+=" B - B - B ";

        }
        if(curr.getDisplaytile()=='C'){
            String1+=" C - C - C ";
            String2+=" |   "+curr.get_characterpiece()+"  | ";
            String3+=" C - C - C " ;

        }
        if(curr.getDisplaytile()=='K'){

            String1+=" K - K - K ";
            String2+=" |   "+curr.get_characterpiece()+"  | ";
            String3+=" K - K - K ";
        }
        if(curr.getDisplaytile()=='P'){

            String1+=" P - P - P ";
            String2+=" |   "+curr.get_characterpiece()+"  | ";
            String3+=" P - P - P ";
        }
        if(curr.getDisplaytile()=='X'){
            String1+=" I - I - I ";
            String2+=" | X X X | ";
            String3+=" I - I - I ";


        }
        
       

     } System.out.println(String1);
        System.out.println(String2);
        System.out.println(String3);
        System.out.println();
     System.out.println();
     
    }

    

}

//return the market on the playing field
public MainMarket getMarket(){
    return this.market;
}



    

    public static void main(String[] args){
        Board b1= new Board(3);
        b1.moveHero(0, 7, "H1");
        b1.moveHero(3, 7, "H2");
        b1.moveHero(6, 7, "H3");
        b1.printBoard();
       
    }
}
package cn.edu.nju.TicTacToe;
public class Board {
	/**
	 * 成员变量的初始化代码请修改，请灵活选择初始化方式
	 * 必要时可添加成员变量
	 */
	protected char[][] cells;
	protected GameChessStrategy chessStrategy;
	protected GameWinStrategy winStrategy;

	protected Player player = Player.X;

	/**
	 * 请修改构造方法，并添加合适的构造方法
	 */

	public Board(int boardSize, String gameMode){
		cells = new char[boardSize][boardSize];
		for(int i=0; i<boardSize; i++){
			for(int j=0; j<boardSize; j++){
				cells[i][j] = '_';
			}
		}
		switch (gameMode){
			case "00":{
				this.chessStrategy = new GameChessStrategy();
				this.winStrategy = new GameWinStrategy_HVD();
				break;
			}
			case "01":{
				this.chessStrategy = new GameChessStrategy();
				this.winStrategy = new GameWinStrategy_HV();
				break;
			}
			case "10":{
				this.chessStrategy = new AtMost5();
				this.winStrategy = new GameWinStrategy_HVD();
				break;
			}
			case "11":{
				this.chessStrategy = new AtMost5();
				this.winStrategy = new GameWinStrategy_HV();
				break;
			}
		}
	}

	/**
	 * @param move 下棋的位置
	 * @return 落棋之后的结果
	 */
	public Result nextMove(String move) {
		chessStrategy.putChess(cells, nextPlay(), move);
		return winStrategy.check(cells, cells.length);
	}
	
	/**
	 * @return 下一个落棋的玩家
	 */
	protected Player nextPlay(){
		Player res = player;
		player = player == Player.X ? Player.O : Player.X;
		return res;
	}
	
	/**
	 * 棋盘的输出方法，根据需要进行修改
	 */
	public void print(){
		System.out.print(" ");
		char c = 'A';
		for(int i = 0;i< cells.length;i++){
			System.out.printf(" %c",c++);
		}
		System.out.println();
		for(int i=0 ;i< cells.length; i++){
			System.out.print(i+1);
			for(int j=0; j< cells.length; j++){
				System.out.print(" "+cells[i][j]);
			}
			System.out.println();
		}
	}
}
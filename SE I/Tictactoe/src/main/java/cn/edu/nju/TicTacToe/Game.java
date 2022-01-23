package cn.edu.nju.TicTacToe;
public class Game {
    /**
     * Game的接口方法，我们会通过该方法进行测试
     * @param gameMode 游戏模式,有四种可能，00,01,10,11
     * @param moveStr 落子序列
     * @param size 棋盘大小，size最小为3， 最大为9
     * @return 游戏结果
     * 
     * 根据需要改写方法，参数，返回结果类型不可修改
     */
    public Result playGame(String gameMode, String moveStr, int size){
    	Board board = new Board(size,gameMode);//
    	
    	String[] moves = moveStr.split(",");
    	Result res = Result.GAMING;
    	for(String move: moves){
    		res = board.nextMove(move);
    		board.print();
    		// 游戏结束
    		if( !res.equals(Result.GAMING) )
    			break;
    	}
    	return res;
    }
}
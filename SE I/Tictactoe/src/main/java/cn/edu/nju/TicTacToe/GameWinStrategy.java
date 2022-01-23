package cn.edu.nju.TicTacToe;
/**
 * 横竖斜方式获胜对应的类，应该考虑到可扩展性，当有新的获胜模式出现时更易于添加
 * hint：采用接口的方式，接口与实现相分离
 * @author Xin Feng & Qiu Liu
 *
 */
public class GameWinStrategy{
	char winChar;
	public Result check(char[][] cells, int size){return Result.DRAW; }
	protected void judgeH(char[][] cells, int size){
		for(int i=1;i<size-1;i++){
			for(int j=0;j<size;j++){
				if(cells[i][j]!='_' && cells[i-1][j]==cells[i][j] && cells[i+1][j]==cells[i][j]){
					this.winChar = cells[i][j];
					return;
				}
			}
		}
	}
	protected void judgeV(char[][] cells, int size){
		for(int i=1;i<size-1;i++){
			for(int j=0;j<size;j++){
				if(cells[j][i]!='_' && cells[j][i-1]==cells[j][i] && cells[j][i+1]==cells[j][i]){
					this.winChar = cells[j][i];
					return;
				}
			}
		}
	}
	protected void judgeD(char[][] cells, int size){
		for(int i=1;i<size-1;i++){
			for(int j=1;j<size-1;j++){
				if(cells[i][j]!='_' && cells[i-1][j-1]==cells[i][j] && cells[i+1][j+1]==cells[i][j]){
					this.winChar = cells[i][j];
					return;
				}else if(cells[i][j]!='_' && cells[i+1][j-1]==cells[i][j] && cells[i-1][j+1]==cells[i][j]){
					this.winChar = cells[i][j];
					return;
				}
			}
		}
	}
	protected Result judgeState(char[][] cells, int size){
		switch(winChar){
			case 'X': return Result.X_WIN;
			case 'O': return Result.O_WIN;
			default: break;
		}

		for(int i = 0; i < size; ++i)
		{
			for(int j = 0; j < size; ++j)
			{
				if(cells[i][j] == '_')
					return Result.GAMING;
			}
		}

		return Result.DRAW;
	}
}



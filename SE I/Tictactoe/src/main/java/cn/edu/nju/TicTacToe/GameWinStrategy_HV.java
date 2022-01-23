package cn.edu.nju.TicTacToe;

/**
 * 横竖方式获胜对应的类
 * @author Xin Feng & Qiu Liu
 *
 */
class GameWinStrategy_HV extends GameWinStrategy{
    /**
     * 自行实现检测获胜的方法
     */
    GameWinStrategy_HV(){
        winChar = 0;
    }
    public Result check(char[][] cells, int size)
    {
        super.judgeH(cells,size);
        super.judgeV(cells,size);
        return judgeState(cells,size);
    }
}
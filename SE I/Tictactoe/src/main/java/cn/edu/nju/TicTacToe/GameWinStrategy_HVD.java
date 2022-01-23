package cn.edu.nju.TicTacToe;

class GameWinStrategy_HVD extends GameWinStrategy{
    /**
     * 根据需要修改获胜的方法


     */
    GameWinStrategy_HVD(){
        winChar = 0;
    }
    public Result check(char[][] cells, int size)
    {
        judgeH(cells,size);
        judgeV(cells,size);
        judgeD(cells,size);
        return judgeState(cells,size);
    }


}


/**
 * 矩阵类，实现矩阵的加法，矩阵乘法，点乘以及转置方法
 * 其中加法和点乘方法需要有两种实现方式
 * 1.传入一个MyMatrix对象进行2个矩阵的操作
 * 2.从控制台（console）读入一个矩阵数据，再进行操作
 * 所有的数据均为int型
 * 输入数据均默认为正确数据，不需要对输入数据进行校验
 * @author Ray Liu & Qin Liu
 *
 */
import java.util.Scanner;
public class MyMatrix {
	private int[][] data;
	
	/**
	 * 构造函数，参数为2维int数组
	 * a[i][j]是矩阵中的第i+1行，第j+1列数据
	 * @param a
	 */
	public MyMatrix(int[][] a){
		this.data = a;
	}

	public int[][] getData() {
		return data;
	}

	/**
	 * 实现矩阵加法，返回一个新的矩阵
	 * @param B
	 * @return
	 */
	public MyMatrix plus(MyMatrix B){
		int[][] b = B.getData();
		int x = this.data.length;
//		if(x == 0){
//			int[][] t = int[0][0];
//			return new MyMatrix(t);
//		}
		int y = this.data[0].length;
		int[][] C = new int[x][y];
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				C[i][j] = this.data[i][j] + b[i][j];
			}
		}
		return new MyMatrix(C);
	}

	
	/**
	 * 实现矩阵乘法，返回一个新的矩阵
	 * @param B
	 * @return
	 */
	public MyMatrix times(MyMatrix B){
		int[][] b = B.getData();
		int x1 = this.data.length;
		int x2 = b.length;
//		if(x2 == 0){
//			return new MyMatrix(int[0][0]);
//		}
		int y2 = b[0].length;
		int[][] C = new int [x1][y2];
		for (int i = 0; i < x1; i++)
			for (int j = 0; j < y2; j++)
				//c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
				for (int k = 0; k < x2; k++)
					C[i][j] += this.data[i][k] * b[k][j];
		return new MyMatrix(C);
	}
	
	/**
	 * 实现矩阵的点乘，返回一个新的矩阵
	 * @param b
	 * @return
	 */
	public MyMatrix times(int b){
		int[][] t = new int[data.length][data[0].length];
		for(int x=0; x<data.length; x++) {
			for(int y=0; y<data[x].length; y++) {
				t[x][y] = data[x][y]*b;
			}
		}
		return new MyMatrix(t);
	}
	
	/**
	 * 实现矩阵的转置，返回一个新的矩阵
	 * @return
	 */
	public MyMatrix transpose(){
		int m = data.length;
		int n = data[0].length;
		int[][] newData = new int[n][m];
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				newData[j][i] = data[i][j];
			}
		}
		return new MyMatrix(newData);
	}
	
	/**
	 * 从控制台读入矩阵数据，进行矩阵加法，读入数据格式如下：
	 * m n
	 * m * n 的数据方阵，以空格隔开
	 * example:
	 * 4 3
	 * 1 2 3 
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 返回一个新的矩阵
	 * @return
	 */
	public MyMatrix plusFromConsole(){
		Scanner reader = new Scanner(System.in);
		int[][] A = scanner(reader);
		MyMatrix tmp = new MyMatrix(A);
		return new MyMatrix(this.plus(tmp).getData());
	}
	
	/**
	 * 输入格式同上方法相同
	 * 实现矩阵的乘法
	 * 返回一个新的矩阵
	 * @return
	 */
	public MyMatrix timesFromConsole(){
		Scanner reader = new Scanner(System.in);
		int[][] A = scanner(reader);
		MyMatrix tmp = new MyMatrix(A);
		return new MyMatrix(this.times(tmp).getData());
	}
	
	/**
	 * 打印出该矩阵的数据
	 * 起始一个空行，结束一个空行
	 * 矩阵中每一行数据呈一行，数据间以空格隔开
	 * example：
	 * 
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 
	 */
	public void print(){
		int[][] A = this.data;
		int x = A.length;
		int y = A[0].length;
		System.out.println();
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y - 1; j++)
			{
				System.out.print(A[i][j] + " ");
			}
			System.out.println(A[i][y - 1]);
		}
		System.out.println();
	}

	public int[][] scanner(Scanner reader){
		int x = reader.nextInt();
		int y = reader.nextInt();
		int [][] A = new int [x][y];
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				A[i][j] = reader.nextInt();
			}
		}
		return A;
	}
}

# RE报告

## Card

1. 首先查看String，并没有惊喜……
2. 查看main函数，发现是输入一个长度13的字符串进去，经过shuffle和check两个函数处理。
3. 研究shuffle，发现是随机洗牌，果然和名字shuffle很符合，开始担心，难道写个脚本不断运行输入碰运气？
4. 研究check，哦，那没事了(doge
   字符串s = "3A2564J7KQ98:"，进行了从小到大的排序，a1就一定得是ASCII码从小到大排序，即
   flag为 Trinity{23456789:AJKQ}

## Maze

1. 照例首先查看String，依旧没有惊喜……

2. 输入字符串先经过Check，Check的作用是要求输入只含qwerasd

3. 下面开始走迷宫，虽然大概率wasd常规意思，但我还是很认真的一个一个研究确认了，作用如下表：

   | 输入 | v15  | v14  |
   | ---- | ---- | ---- |
   | q    | 不变 | 不变 |
   | w    | 减一 | 不变 |
   | e    | 不变 | 不变 |
   | r    | 归零 | 归零 |
   | a    | 不变 | 减一 |
   | s    | 加一 | 不变 |
   | d    | 不变 | 加一 |

   结论：qerd全是干扰，wasd常规意思，上下左右

4. 然后研究地图Map，Map居然是全局变量，我到.data 区把Map的数据复制下来，如下txt文件：

   ```
   .data:0000000000004080                 db    5
   .data:0000000000004081                 db    0
   .data:0000000000004082                 db    0
   .data:0000000000004083                 db    0
   .data:0000000000004084                 db    5
   .data:0000000000004085                 db    0
   .data:0000000000004086                 db    0
   .data:0000000000004087                 db    0
   ```

   注意到是__DWORD类型，显然只要取低位，用python简单处理了一下，程序及输出如下：

   ```python
   file_path = r'C:\Users\Morgan\Desktop\maze.txt'
   with open(file_path, encoding='utf-8') as file_obj:
       line = file_obj.readline()
       ans = ''
       cnt1 = 0
       cnt2 = 0
       while line != '':
           cnt1 = (cnt1 + 1) % 4
           if (cnt1 == 1):
               ch = line[len(line) - 2]
               ans += ch
               cnt2 += 1
               if (cnt2 == 10):
                   ans += '\n'
                   cnt2 = 0
           line = file_obj.readline()
   
   print(ans)
   
   # 5555555555
   # 5101000005
   # 5001010105
   # 5101010115
   # 1100011105
   # 5001101015
   # 5011000007
   # 5010010115
   # 5000100105
   # 5555555555
   ```

   PS：IDA用的不熟，全局变量不知道咋办，Map数组分别是什么值可以直接生成吗？直接看.data看的好累……

5. 然后就是欢乐的走迷宫环节，1和5是墙，0是路，7是目标，v15，v14的初值分别为2，1，下面就很简单了。
   最后flag 为 Trinity{dsssasssddwdwddddd}
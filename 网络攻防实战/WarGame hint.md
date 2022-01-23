# level01

PATH=
可以修改环境

自己写echo，先被找到
ln 

```
/bin# PATH=/xxx:$PATH
```

# level02

提前闭合echo，一行执行多个命令，参考SQL注入

# level03

crontab -l -u flag03

# level04

链接，使名字不为token

# level05

ls -la
tar ztvf

# level06

John 暴力破解

# 07

```
netstat -an | grep 8888
```

发现有8888端口，可以访问它，比如
ping那一行做闭合

# 08

43：20

# 09

nes -k
TOCTOU
脚本，不断切换文件
调低优先级nice

# 11

1. 编写加密函数

2. fread编写失误

   编写一个字节的命令，以null结尾

   不断运行

# 12

hash 调用了系统内置的sha1sum，可以闭合命令

# 13

getuid() 

```
file ./license   查看文件类型
hexdump -C ./lic
strings ./lic
strace  显示系统调用
ltrace  跟踪显示库函数，可以运行时使用
gdb ./lic
set 
break *main
run
ni
info registers rip
break *0x000000xxxxx
set $eax=0
```

# 15

```
__attribute((constructor))
__libc_start_main
system("bin/getflag15")
gcc -shared -fPIC -o libc.so.6 constructor.c
```

# 18

checksec

login没有fclose，//即使fclose打开另一个程序，疯狂打开文件，让login无法打开文件

TODO:释放一个距离

# 栈知识补充

栈溢出指程序向栈中某个变量中写入的字节数超过了这个变量本身所申请的字节数

进程地址空间中的某一部分就是该程序对应的栈

程序的栈是从进程地址空间的高地址向低地址增长的

call指令调用函数，编译器使用堆栈传递函数参数、保存返回地址、临时保存寄存器原有值、存储本地局部变量

栈帧的边界由栈帧基地址指针EBP和堆栈指针ESP界定
大多数情况下函数调用时入栈顺序：实参N-1->主调函数返回地址->主调函数帧基指针EBP->被调函数局部变量1-N

本级调用结束后，将EBP指针的值赋给ESP，使ESP指向被调函数栈底以释放局部变量，再将已压栈的主调函数帧基指针弹出到EBP，并弹出返回地址到EIP。ESP继续上移越过参数，最终回到函数调用前的状态，也就是恢复原来主调函数的栈帧

# 20

wargame中的第20-22关解决方法类似，大概思路就是在gets调用的前后设置断点，输入一组字符，如A，只要输入的字符足够多，就会覆盖到变量modified的位置

```
python -c 'print"A"*n'|/home/flag20/flag20
```

通过调试获得地址

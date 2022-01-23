package edu.nju;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {

    // 状态集合
    private final Map<String, State> Q;
    // 输入符号集
    private Set<Character> S;
    // 磁带符号集
    private Set<Character> G;
    // 初始状态
    private String q0;//q——>q0
    // 终止状态集
    private Set<String> F;
    // 空格符号
    private Character B;
    // 磁带数
    private Integer tapeNum;

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.S = S;
        this.G = G;
        this.F = F;
        this.B = B;
        this.q0 = q;
        this.Q = new HashMap<>();
        for (String state : Q) {
            State temp = new State(state);
            temp.setQ(state);
            this.Q.put(state, temp);
        }
        this.tapeNum = tapeNum;
        for (TransitionFunction t : Delta) {
            this.Q.get(t.getSourceState().getQ()).addTransitionFunction(t);
        }
    }

    /**
     * is done in Lab1 ~
     *
     * @param tm
     */
    public TuringMachine(String tm) {
        this.Q = new HashMap<>();
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
        //this.Delta = new HashSet<>();
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
                charProcessor(s, line_num);
                s3.add(s.charAt(1));
            } else if (s.charAt(0) == '#' && s.charAt(1) == 'D') {
                if (this.resolverTransitionFunction(s, line_num) == 1) s3.add('D');
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
        this.checkSets();//检查各集合
    }


    public State getInitState() {
        return Q.get(q0);
    }

    /**
     * isStop
     * 停止的两个条件 1. 到了终止态 2. 无路可走，halts
     *
     * @param q,Z
     * @return
     */
    public boolean isStop(State q, String Z) {//z input
        if (F.contains(q.getQ())) return true;//ends
        else if (q.getKeys().contains(Z)) return false;
        else return true;// halts
    }

    //检查磁带上的字符是否是输入符号组的 ( checkTape )
    public boolean checkTape(Set<Character> tape) {
        for (Character c :
                tape) {
            if (!this.S.contains(c)) return false;
        }
        return true;
    }

    //检查磁带的数量是否正确 ( checkTapeNum )
    public boolean checkTapeNum(int tapeNum) {
        return (tapeNum == this.tapeNum);
    }

    public Character getB() {
        return B;
    }


    /**
     * 7. 迁移函数的新旧状态不属于状态集
     * 8. 迁移函数读取的符号和新写入的符号不属于磁带符号集
     * 9. 图灵机的迁移函数相互冲突，下面解释一下相互冲突的含义
     * 同样的状态，同样的输入，却转移到了不同的状态，得到了不同的输出，磁头有不同的偏移
     *
     * @param s
     * @param lineno
     */
    private int resolverTransitionFunction(String s, int lineno) {
        String[] elements = StringUtils.split(s.substring(3));
        if (elements[1].length() != elements[2].length() || elements.length != 5) {
            System.err.printf("Error: %d" + System.lineSeparator(), lineno);
            return 0;
        } else {
            State sourceState = new State(elements[0]);
            State destinationState = new State(elements[4]);
            TransitionFunction tf = new TransitionFunction(sourceState, destinationState, elements[1], elements[2], elements[3]);

            if (!this.Q.containsKey(tf.getSourceState().getQ())) System.err.print("Error: 7" + System.lineSeparator());
            if (!this.Q.containsKey(tf.getDestinationState().getQ()))
                System.err.print("Error: 7" + System.lineSeparator());
            if (!this.G.containsAll(tf.inputSet())) System.err.print("Error: 8" + System.lineSeparator());
            if (!this.G.containsAll(tf.outputSet())) System.err.print("Error: 8" + System.lineSeparator());
            if (this.Q.get(tf.getSourceState().getQ()).getKeys().contains(tf.getInput()))
                System.err.print("Error: 9" + System.lineSeparator());
            else this.Q.get(tf.getSourceState().getQ()).addTransitionFunction(tf);
            return 1;
        }
    }


    /**
     * is done in lab1 ~
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        sB.append(String.format("#Q = {") + StringUtils.join(this.Q.keySet().toArray(), ',') + '}' + System.lineSeparator());
        sB.append(String.format("#S = {") + StringUtils.join(this.S.toArray(), ',') + '}' + System.lineSeparator());
        sB.append(String.format("#G = {") + StringUtils.join(this.G.toArray(), ',') + '}' + System.lineSeparator());
        sB.append(String.format("#F = {") + StringUtils.join(this.F.toArray(), ',') + '}' + System.lineSeparator());
        sB.append(String.format("#N = %d", this.tapeNum) + System.lineSeparator());
        sB.append(String.format("#B = %c", this.B) + System.lineSeparator());
        for (State state :
                this.Q.values()) {
            for (TransitionFunction tf :
                    state.getDeltas()) {
                sB.append(tf.toString());
            }
        }
        sB.append(String.format("#q0 = %s", this.q0));
        return sB.toString();
    }

    private void setProcessor(String s, int line) {
        if (s.charAt(5) == '{' && s.charAt(s.length() - 1) == '}') {
            String sub = s.substring(6, s.length() - 1);
            String[] statuses = StringUtils.split(sub, ',');
            for (int i = 0; i < statuses.length; i++) {
                statuses[i] = statuses[i].trim();
            }
            switch (s.charAt(1)) {
                case 'Q': {
                    for (String status :
                            statuses) {
                        State temp = new State(status);
                        temp.setQ(status);
                        this.Q.put(status, temp);
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

    private void charProcessor(String s, int line) {
        switch (s.charAt(1)) {
            case 'q':
                if (s.charAt(2) == '0')
                    this.q0 = s.substring(6);
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

    private void checkSets() {
        if (!this.Q.keySet().containsAll(this.F)) System.err.print("Error: 3" + System.lineSeparator());
        if (this.S.contains(this.B)) System.err.print("Error: 4" + System.lineSeparator());
        if (!this.G.contains(this.B)) System.err.print("Error: 5" + System.lineSeparator());
        if (!this.G.containsAll(this.S)) System.err.print("Error: 6" + System.lineSeparator());
    }
    public Map<String, State> getQ(){
        return this.Q;
    }
}

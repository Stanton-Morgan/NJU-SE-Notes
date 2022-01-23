package edu.nju;

import java.util.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;
    State q;
    int steps = 0;
    boolean canRun = true;

    public Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        q = tm.getInitState();
        loadTape(tapes);
    }

    /**
     * 1. 检查能否运行
     * 2. 调用tm.delta
     * 3. 更新磁带
     * 4. 返回下次能否执行
     *
     * @return
     */
    public Boolean execute() {
        if (tm.isStop(this.q, snapShotTape())) {
            canRun = false;
            return canRun;
        }
        TransitionFunction tf = this.q.getDelta(snapShotTape());
        updateTape(tf.getOutput());
        moveHeads(tf.getDirection());
        this.q = tm.getQ().get(tf.getDestinationState().getQ());
        String tmp = snapShotTape();
        if (tm.isStop(this.q, tmp)) {
            canRun = false;
        }
        this.steps++;
        return canRun;
    }

    /**
     * 1. checkTapeNum
     * 2. checkTape
     *
     * @param tapes
     */
    public void loadTape(ArrayList<Tape> tapes) {
        if (!tm.checkTapeNum(tapes.size())) System.err.print("Error: 2" + System.lineSeparator());
        for (Tape t :
                tapes) {
            for (StringBuilder track :
                    t.tracks) {
                Set<Character> tape = new HashSet<>();
                for (Character c :
                        track.toString().toCharArray()) {
                    if (c != tm.getB())
                        tape.add(c);
                }
                if (!tm.checkTape(tape)) {
                    System.err.print("Error: 1" + System.lineSeparator());
                    return;//报一次错就退出
                }
            }
        }
        this.tapes = tapes;
    }

    /**
     * 获取所有磁带的快照
     * 获取所有磁带的快照，也就是把每个磁带上磁头指向的字符全都收集起来
     *
     * @return
     */
    private String snapShotTape() {
        StringBuilder input = new StringBuilder();
        for (Tape tape :
                tapes) {
            input.append(tape.getInput());//拼凑出总input
        }
        return input.toString();
    }

    /**
     * 给出当前图灵机和磁带的快照
     *
     * @return
     */
    public String snapShot() {
        StringBuilder sb = new StringBuilder();
        int maxlen = ("Index" + Integer.valueOf(this.tapes.size() - 1).toString()).length();
        for (Tape t :
                this.tapes) {
            maxlen = maxlen > ("Track" + Integer.valueOf(t.tracks.size() - 1).toString()).length() ? maxlen : ("Track" + Integer.valueOf(t.tracks.size() - 1).toString()).length();
        }
        sb.append("Step" + String.join("", Collections.nCopies(maxlen - "Step".length(), " ")) + ": " + this.steps + System.lineSeparator());
        for (int i = 0; i < this.tapes.size(); i++) {
            sb.append(String.format("Tape%d", i) + String.join("", Collections.nCopies(maxlen - String.format("Tape%d", i).length(), " ")) + ":" + System.lineSeparator());
            sb.append(String.format("Index%d", i) + String.join("", Collections.nCopies(maxlen - String.format("Index%d", i).length(), " ")) + ":");
            tapes.get(i).snapShot(maxlen, sb);
            sb.append(String.format("Head%d", i) + String.join("", Collections.nCopies(maxlen - String.format("Head%d", i).length(), " ")) + ": " + this.tapes.get(i).getHead() + System.lineSeparator());
        }
        sb.append("State" + String.join("", Collections.nCopies(maxlen - "State".length(), " ")) + ": " + this.q.getQ() + System.lineSeparator());
        return sb.toString();
    }


    /**
     * 不断切割newTapes，传递给每个Tape的updateTape方法
     *
     * @param newTapes
     */
    private void updateTape(String newTapes) {
        int start = 0;
        for (Tape tape :
                tapes) {
            tape.updateTape(newTapes.substring(start, start + tape.tracks.size()));
            start += tape.tracks.size();
        }
    }

    /**
     * 将每个direction里的char都分配给Tape的updateHead方法
     *
     * @param direction
     */
    private void moveHeads(String direction) {
        for (int i = 0; i < tapes.size(); i++) {
            tapes.get(i).updateHead(direction.charAt(i));
        }
    }

}

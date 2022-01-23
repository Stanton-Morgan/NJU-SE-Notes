package edu.nju;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 19:37
 */
public class Tape {

    ArrayList<StringBuilder> tracks;
    private final char B;
    private int head;
    private int minlen;//最短的track的长度上限
    private int start;//第一个非空格
    private int end;//最后一个非空格

    public Tape(ArrayList<StringBuilder> tracks, int head, char B) {
        this.tracks = tracks;
        this.head = head;
        this.B = B;
        this.minlen = tracks.get(0).toString().length();
        for (StringBuilder sb :
                this.tracks) {
            minlen = minlen > sb.toString().length() ? sb.toString().length() : minlen;
        }
    }


    public void updateHead(char c) {
        switch (c) {
            case 'l':
                if (head == 0) {
                    for (StringBuilder sb :
                            tracks) {
                        sb.insert(0, this.B);
                    }
                    head++;
                }
                head--;
                break;
            case 'r':
                if (head == minlen - 1) {
                    for (StringBuilder sb :
                            tracks) {
                        sb.append(this.B);
                    }
                    minlen++;
                }
                head++;
                break;
            case '*':
                break;
        }
    }

    public void updateTape(String newTape) {
        for (int i = 0; i < tracks.size(); i++) {
            tracks.get(i).replace(head, head + 1, newTape.substring(i, i + 1));
        }
    }

    public String getInput() {//获取head所指向字符之和
        StringBuilder sb = new StringBuilder();
        for (StringBuilder s :
                this.tracks) {
            sb.append(s.toString().charAt(this.head));
        }
        return sb.toString();
    }

    public void snapShot(int maxlen, StringBuilder sb) {
        getStartEnd(tracks.get(0));
        for (int j = start; j <= end; j++) {
            sb.append(" " + j);
        }
        sb.append(System.lineSeparator());
        for (int i = 0; i < tracks.size(); i++) {
            sb.append(String.format("Track%d", i) + String.join("", Collections.nCopies(maxlen - String.format("Track%d", i).length(), " ")) + ":");
            for (int j = start; j < end; j++) {
                sb.append(" " + tracks.get(i).toString().charAt(j) + String.join("", Collections.nCopies((j + "").length() - 1, " ")));
            }
            sb.append(" " + tracks.get(i).toString().charAt(end) + System.lineSeparator());
        }
    }


    private void getStartEnd(StringBuilder track) {
        boolean flag = false;//是否有非空格
        for (int i = 0; i < track.length(); i++) {
            if (track.toString().charAt(i) != this.B) {
                this.start = i;
                flag = true;
                break;
            }
        }
        for (int i = track.length() - 1; i >= 0; i--) {
            if (track.toString().charAt(i) != this.B) {
                this.end = i;
                flag = true;
                break;
            }
        }
        if (!flag) start = end = head;
        else {
            if (start > head) start = head;
            if (end < head) end = head;
        }
    }

    public int getHead() {
        return this.head;
    }
}

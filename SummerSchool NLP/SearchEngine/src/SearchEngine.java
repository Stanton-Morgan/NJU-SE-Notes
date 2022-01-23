import java.util.*;

/**
 * @Author Morgan Stanton 浮世野指针
 * @CreateTime 2021-07-14-20:52
 */
public class SearchEngine {

    //�������ı�
    private String text;

    //��״̬������������ʽ��صĶ��壬����������ţ�����ͨ������״̬������������ʽ��ȡ��
    public Map<String, String> table;
    public Set<String> actionSet;
    //�����������ݵĳ�ʼ��
    public void initializeTable() {
        this.table = new HashMap<String, String>();
        this.table.put("S-a", "1");
        this.table.put("S-b", "F");
        this.table.put("S-o", "F");
        this.table.put("1-a", "2");
        this.table.put("1-b", "1");
        this.table.put("1-o", "F");
        this.table.put("2-a", "2");
        this.table.put("2-b", "1");
        this.table.put("2-o", "E");
        this.table.put("E-a", "F");
        this.table.put("E-b", "F");
        this.table.put("E-o", "F");
    }

    public void initializeActionSet() {
        this.actionSet = new HashSet<String>();
        this.actionSet.add("a");
        this.actionSet.add("b");
    }


    //�������汾���״̬����һ����Ϊ
    public String state;
    public String action;

    //����б�
    public List<ResultString> matches;

    //���湹�캯���������ı����С���������ֱ�ӳ�ʼ��
    public SearchEngine(String text) {
        this.text = text + "~";
        this.initializeState();
        this.initializeTable();
        this.initializeActionSet();
        this.action = "";

        this.matches = new ArrayList<ResultString>();
    }


    /**�����ĺ��ĺ��������������ı���һ��һ���ַ���״̬���ȶԡ�
     *�����������
     *��1����ǰ�ַ�ת���ɹ�ƥ���״̬E-->��¼ƥ��Ľ�����ӵ�ǰ�ַ�����������ʼ�����棬������һ��ƥ�䡣
     *��2����ǰ�ַ�ת��ƥ��ʧ�ܵ�״̬F-->��ʼ�����棬����һ���ַ���ʼ��������һ��ƥ�䡣
     *��3����ǰ�ַ�ת����һ�����е�״̬-->���¼��ƥ����ַ�������������һ���ַ���
     **/
    public void startSearch() {
        String nextState = "";
        int tmpStartIndex = 0;
        int tmpEndIndex = 0;
        String tmpMatch = "";

        for(int i = 0; i < this.text.length(); i ++) {
            this.action = this.actionSet.contains(this.text.charAt(i) + "") ? this.text.charAt(i) + "" : "o";
            nextState = this.table.get(this.state + "-" + this.action);

            if(nextState == "E") {
                tmpEndIndex = i - 1;
                this.matches.add(new ResultString(tmpStartIndex, tmpEndIndex, tmpMatch));

                i --; //�ӵ�ǰ��ƥ������һ����ʼ����ѭ��
                this.initializeState();
                tmpStartIndex = i + 1;
                tmpMatch = "";
            }else if(nextState == "F") {
//				if(this.state != "S") {
//					i --; //�ӵ�ǰ��ƥ������һ����ʼ����ѭ��
//					tmpStartIndex = i + 1;
//				}else {
//					tmpStartIndex = i;
//				}
                tmpStartIndex = i;
                this.initializeState();
                tmpMatch = "";
            }else {
                tmpMatch = tmpMatch + this.action;
                this.state = nextState;
            }
        }
    }

    private void initializeState() {
        // TODO Auto-generated method stub
        this.state = "S";
    }
}


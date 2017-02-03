package cs.kranthi.edu.uniconnect;

import java.sql.Date;

/**
 * Created by kranthi on 22-11-2016.
 */

public class Answers {

    private int QUESTION_ID;
    private String ANSWER;
    private String ANSWERED_BY;
    private Date TIME_ANSWERED;

    public int getQUESTION_ID() {
        return QUESTION_ID;
    }

    public void setQUESTION_ID(int QUESTION_ID) {
        this.QUESTION_ID = QUESTION_ID;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER = ANSWER;
    }

    public String getANSWERED_BY() {
        return ANSWERED_BY;
    }

    public void setANSWERED_BY(String ANSWERED_BY) {
        this.ANSWERED_BY = ANSWERED_BY;
    }

    public Date getTIME_ANSWERED() {
        return TIME_ANSWERED;
    }

    public void setTIME_ANSWERED(Date TIME_ANSWERED) {
        this.TIME_ANSWERED = TIME_ANSWERED;
    }
}

package cs.kranthi.edu.uniconnect;

import java.sql.Date;

/**
 * Created by kranthi on 21-11-2016.
 */

public class Questions {

    private int QUESTION_ID;
    private String QUESTION;
    private String ASKED_BY;
    private Date TIME_ASKED;
    private String ASKED_UNIVERSITY;
    private String GROUP_NAME;

    public int getQUESTION_ID() {
        return QUESTION_ID;
    }

    public void setQUESTION_ID(int QUESTION_ID) {
        this.QUESTION_ID = QUESTION_ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getASKED_BY() {
        return ASKED_BY;
    }

    public void setASKED_BY(String ASKED_BY) {
        this.ASKED_BY = ASKED_BY;
    }

    public Date getTIME_ASKED() {
        return TIME_ASKED;
    }

    public void setTIME_ASKED(Date TIME_ASKED) {
        this.TIME_ASKED = TIME_ASKED;
    }

    public String getASKED_UNIVERSITY() {
        return ASKED_UNIVERSITY;
    }

    public void setASKED_UNIVERSITY(String ASKED_UNIVERSITY) {
        this.ASKED_UNIVERSITY = ASKED_UNIVERSITY;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }
}

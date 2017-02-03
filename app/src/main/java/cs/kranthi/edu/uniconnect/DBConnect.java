package cs.kranthi.edu.uniconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by kranthi on 16-11-2016.
 */

public class DBConnect {

    public static String user = null;
    public static String Password = null;
    public static String fName = null;
    public static String lName = null;
    public static String uName = null;
    public static String dept = null;
    public static ArrayList<Questions> Qstns;
    public static ArrayList<Answers> Ans;
    static String result;
    static boolean resultb;
    Connection conn;
    Statement stmt;
    ResultSet rs;
    String sql;

    public String getUser(final String name, final String password){


        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT USERNAME, PASSWORD, FIRSTNAME, LASTNAME, UNIVERSITY_NAME, DEPARTMENT FROM ACCOUNTS WHERE USERNAME = '"+name+"' AND PASSWORD = '"+password+"'";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);

                        try{
                            if(rs.next()) {
                                System.out.println("In RS");
                                user = rs.getString("USERNAME");
                                Password = rs.getString("PASSWORD");
                                fName = rs.getString("FIRSTNAME");
                                lName = rs.getString("LASTNAME");
                                uName = rs.getString("UNIVERSITY_NAME");
                                dept = rs.getString("DEPARTMENT");
                            }
                            else{
                                result =  "null";
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            result = "null";
                        }


                        rs.close();
                        stmt.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            if(result == "null")
                return "null";
            else
                return "Success";

        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
        }
        return "null";
    }

    public ArrayList<String> getList(){

        Qstns = new ArrayList<Questions>();
        final ArrayList<String> QstNames = new ArrayList<String>();


        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT * FROM QUESTIONS WHERE ASKED_UNIVERSITY = '"+uName+"' AND (GROUP_NAME = '"+dept+"' OR GROUP_NAME='General') ORDER BY TIME_ASKED DESC";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);
                        int counter=0;
                        try{
                            while(rs.next()) {
                                Questions qstn = new Questions();
                                qstn.setQUESTION_ID(rs.getInt(1));
                                qstn.setQUESTION(rs.getString(2));
                                qstn.setASKED_BY(rs.getString(3));
                                qstn.setTIME_ASKED(rs.getDate(4));
                                qstn.setASKED_UNIVERSITY(rs.getString(5));
                                qstn.setGROUP_NAME(rs.getString(6));
                                System.out.println("Adding "+rs.getString(2));
                                Qstns.add(counter,qstn);
                                QstNames.add(counter++,rs.getString(2));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            result = "null";

                        }
                        rs.close();
                        stmt.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            if(result == "null")
                return null;
            else
                return QstNames;

        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Answers> getAnswers(final String Qstn){

        Ans = new ArrayList<Answers>();
        final ArrayList<String> QstNames = new ArrayList<String>();


        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT * FROM ANSWERS WHERE QUESTION_ID =(SELECT QUESTION_ID FROM QUESTIONS WHERE QUESTION= '"+Qstn+"') ORDER BY TIME_ANSWERED DESC";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);
                        int counter=0;
                        try{
                            while(rs.next()) {
                                Answers Anss = new Answers();
                                Anss.setQUESTION_ID(rs.getInt(1));
                                Anss.setANSWER(rs.getString(2));
                                Anss.setANSWERED_BY(rs.getString(3));
                                Anss.setTIME_ANSWERED(rs.getDate(4));
                                System.out.println("Adding "+rs.getString(2));
                                Ans.add(counter,Anss);
                                //QstNames.add(counter++,rs.getString(2));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            result = "null";

                        }
                        rs.close();
                        stmt.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            if(result == "null")
                return null;
            else
                return Ans;

        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertAnswers(final String Qstn, final String ans){

        Ans = new ArrayList<Answers>();
        final ArrayList<String> QstNames = new ArrayList<String>();


        try {

            Thread thread = new Thread(new Runnable() {

                int QID;
                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT QUESTION_ID FROM QUESTIONS WHERE QUESTION= '"+Qstn+"'";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);
                        int counter=0;
                        try{
                            while(rs.next()) {

                                QID = rs.getInt(1);
                                System.out.println("Adding "+QID);

                                //QstNames.add(counter++,rs.getString(2));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            resultb = false;

                        }
                        sql = "INSERT INTO  ANSWERS VALUES( "+QID+",'"+ans+"','"+ DBConnect.user+"'"+",SYSDATE)";
                        System.out.println(sql);
                        stmt.executeQuery(sql);
                        rs.close();
                        stmt.close();
                        conn.close();
                        resultb = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        resultb = false;
                    }
                }
            });

            thread.start();
            thread.join();
        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
            resultb = false;
        }

        return resultb;
    }

    public boolean insertQuestion(final String Qstn,final String deptL){

        Ans = new ArrayList<Answers>();
        final ArrayList<String> QstNames = new ArrayList<String>();


        try {

            Thread thread = new Thread(new Runnable() {

                int QID;
                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "INSERT INTO QUESTIONs VALUES(QUESTION_NUM.NEXTVAL,'"+Qstn+"','"+ DBConnect.user+"',SYSDATE,'"+ DBConnect.uName+"','"+ deptL+"')";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);

                        rs.close();
                        stmt.close();
                        conn.close();
                        resultb = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        resultb = false;
                    }
                }
            });

            thread.start();
            thread.join();
        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
            resultb = false;
        }

        return resultb;
    }

    public ArrayList<String> getDepartments(){

        final ArrayList<String> depts = new ArrayList<String>();

        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT DEPARTMENT FROM DEPARTMENTS ORDER BY DEPARTMENT ASC";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);
                        int counter=0;
                        depts.add(counter++,"                          ");
                        depts.add(counter++,"General");
                        try{
                            while(rs.next()) {

                                depts.add(counter++,rs.getString(1));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            result = "null";

                        }
                        rs.close();
                        stmt.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            if(result == "null")
                return null;
            else
                return depts;

        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getUniversities(){

        final ArrayList<String> uni = new ArrayList<String>();

        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "SELECT UNAME FROM Universities ORDER BY UNAME ASC";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);
                        int counter=0;
                        uni.add(counter++,"                          ");
                        try{
                            while(rs.next()) {

                                uni.add(counter++,rs.getString(1));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            result = "null";

                        }
                        rs.close();
                        stmt.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            if(result == "null")
                return null;
            else
                return uni;

        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertAccount(final String unsme, final String pwd, final String fname, final String lname, final String dept, final String univ, final String type, final String sem, final String gre, final String tofel){

        Ans = new ArrayList<Answers>();
        final ArrayList<String> QstNames = new ArrayList<String>();


        try {

            Thread thread = new Thread(new Runnable() {

                int QID;
                @Override
                public void run() {
                    try  {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@mcsdb1.sci.csueastbay.edu:1521:mcsdb1", "wg8498", "KHINTUm4");
                        stmt = conn.createStatement();

                        sql = "INSERT INTO ACCOUNTS VALUES('"+unsme+"','"+pwd+"','"+fname+"','"+lname+"','"+univ+"','"+sem+"','"+type+"',"+gre+","+tofel+",'"+dept+"')";
                        System.out.println(sql);
                        rs = stmt.executeQuery(sql);

                        rs.close();
                        stmt.close();
                        conn.close();
                        resultb = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        resultb = false;
                    }
                }
            });

            thread.start();
            thread.join();
        }
        catch (Exception e){
            System.out.println("Catching Oracle Exception "+e);
            e.printStackTrace();
            resultb = false;
        }

        return resultb;
    }
}


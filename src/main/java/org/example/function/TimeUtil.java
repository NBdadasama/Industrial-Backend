package org.example.function;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtil {





    public static Timestamp getNowTime(){

        Date date = new Date();
        return new Timestamp(date.getTime());



    }
}

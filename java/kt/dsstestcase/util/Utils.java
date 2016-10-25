package kt.dsstestcase.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;
import java.math.BigInteger;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;

public class Utils {

        public Utils() {  }

        // =====================================================================
        // 주어진값이 숫자값인지 확인, 아닐경우 value1을 디폴트값으로 리턴
        // =====================================================================
        public static int isNumber(String num, int value1)  {
            int value = 0;
            try {
                value = Integer.valueOf(num).intValue();
            }catch (NumberFormatException e) {  value = value1; }
            catch(Exception e){ value = value1; }

            return value;
        }

        public static int isNumber(int num, int value1)  {
            int value = 0;
            try {
                value = Integer.valueOf(num).intValue();
            }catch (NumberFormatException e) {  value = value1; }
            catch(Exception e){ value = value1; }

            return value;
        }

        public static long isNumber(String num, long value1)  {
            long value = 0;
            try {
                value = Integer.valueOf(num).longValue();
            }catch (NumberFormatException e) {  value = value1; }
            catch(Exception e){ value = value1; }

            return value;
        }

        public static long isNumber(int num, long value1)  {
            long value = 0;
            try {
                value = Integer.valueOf(num).longValue();
            }catch (NumberFormatException e) {  value = value1; }
            catch(Exception e){ value = value1; }

            return value;
        }

        public static long isNumber(long num, long value1)  {
            long value = 0;
            try {
                value = Long.valueOf(num).longValue();
            }catch (NumberFormatException e) {  value = value1; }
            catch(Exception e){ value = value1; }

            return value;
        }

        public static String onlyNumber(String str) {
            if ( str == null ) return "";
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < str.length(); i++){
              if( Character.isDigit( str.charAt(i) ) ) {
                sb.append( str.charAt(i) );
                }
            }
            return sb.toString();
         }

        public static String isDate(String val){
            String ret = isString(val,"");
            if( ret.equals("")){
                ret = "";
            }else{
                if( ret.length() >10 ){
                    ret = ret.substring(0,10);
                }else{
                    ret = "";
                }
            }
            return ret;
        }

        // =====================================================================
        // 주어진값이 문자값인지 확인, 아닐경우 value1 을 디폴트로 리턴
        // =====================================================================
        public static String isString(String value, String value1) {
            try {
                if(value == null){  value = value1; }
            }catch(Exception e){    value = value1; }
            return value;
        }

        // =====================================================================
        // 한글자르기
        // =====================================================================
        public static String hanCut(String value, int size){
            if(value == null) {     return "";      }
            if(value.length() > size){
                value = value.substring(0,size)+"...";
            }
            return value;
        }

        // =====================================================================
        // 년월일만 리턴 : 오라클의 Date 형식의 경우 자동으로 뒤에 HH:MM:SS 값이 붙음으로
        // 이것을 떼줘야 할 필요가 있다
        // =====================================================================
        public static String dateFormat(String data_s, String div) throws Exception {
            if(data_s != null){
                if(data_s.length() > 7 ){
                    //data_s = data_s.substring(0,4)+div+data_s.substring(4,5) +div+ data_s.substring(6,7);
                    data_s = data_s.replace(" 00:00:00","");
                }else{
                    return "";
                }
            }else{
                return "";
            }
            return data_s;
        }

        // =====================================================================
        // 년-월-일의 값으로 리턴
        // =====================================================================
        public static String dateFormat2(String data_s) throws Exception {
            if(data_s != null){
                if(data_s.length() > 13 ){
                    data_s = data_s.substring(0,4) +"-"+ data_s.substring(5,6) +"."+ data_s.substring(6,8)+" "+data_s.substring(8,10)+":"+ data_s.substring(10,12)+":"+data_s.substring(12);
                }else if(data_s.length() == 8){
                    data_s = data_s.substring(0,4) +"-"+ data_s.substring(4,6) +"-"+ data_s.substring(6,8);
                }
            } else{
                data_s = "";
            }
            return data_s.trim();
        }

        // =====================================================================
        // 현재날짜반환
        // =====================================================================
        public static String getCurrentDateTime(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일  HH시 mm분 ss초");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getCurrentYearMonthDay(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getBaseTime(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getSendTime(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getCMSCurrentDate(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getCurrentDashDate(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static String getYesterDate(){
            Calendar cal = Calendar.getInstance(Locale.KOREA);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            return dateFormat.format(cal.getTime());
        }
        public static String getCurrentMonthFirstDate(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-01");
            Date date = new Date(System.currentTimeMillis());
            return dateFormat.format(date);
        }
        public static long getUnixTime(){
            return System.currentTimeMillis()/1000L;
        }
        public static int getTodayWeekInMonth() {       // 이번주가 이번달의 몇번째 주인가?
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));
            String today = new SimpleDateFormat("W").format(calendar.getTime());    //"W"
            return Integer.parseInt(today);
        }
        public static String getWeek(int y, int m, int d, String retTy){        // E, H, S
            String ret = "";
            String ch_week = "";

            java.util.Calendar c= java.util.Calendar.getInstance();
            m = m-1;
            c.clear();
            c.set(y, m, d);
            switch(c.get(c.DAY_OF_WEEK)) {
               case java.util.Calendar.SUNDAY:
                  ch_week = "일요일";
                  ret = "SUN";
                  break;
               case java.util.Calendar.MONDAY:
                  ch_week = "월요일";
                  ret = "MON";
                  break;
               case java.util.Calendar.TUESDAY:
                  ch_week = "화요일";
                  ret = "TUE";
                  break;
               case java.util.Calendar.WEDNESDAY:
                  ch_week = "수요일";
                  ret = "WED";
                  break;
               case java.util.Calendar.THURSDAY:
                  ch_week = "목요일";
                  ret = "THU";
                  break;
               case java.util.Calendar.FRIDAY:
                  ch_week = "금요일";
                  ret = "FRI";
                  break;
               case java.util.Calendar.SATURDAY:
                  ch_week = "토요일";
                  ret = "SAT";
                  break;
            }
            if(retTy.equals("H")){
                return ch_week;
            }else if(retTy.equals("S")){
                return ch_week.replace("요일", "");
            }else{
                return ret;
            }
        }

        public static String secondToStr(int tm){
            int h = tm/3600;
            int m = (tm % 3600 / 60);
            int s = (tm % 3600 % 60);
            String ret = "";
            if(h>0){
                ret += Integer.toString(h)+"시간 ";
            }
            if(m>0){
                ret += Integer.toString(m)+"분";
            }
            return ret;
        }

        public static String secondToStr2(int tm){
            int h = tm/3600;
            int m = (tm % 3600 / 60);
            int s = (tm % 3600 % 60);
            String ret = "";

            if(h>0){
                ret += Integer.toString(h)+":";
            }else{
                ret += "00:";
            }

            if(m>0){
                ret += String.format("%02d", m);
            }else{
                ret += "00";
            }
            return ret;
        }

        private static int lastDay(int year, int month){
                int day = 0;
                switch (month) {
                    case 1 :
                    case 3 :
                    case 5 :
                    case 7 :
                    case 8 :
                    case 10 :
                    case 12 :
                        day = 31;
                        break;
                    case 2 :
                        if ((year % 4) == 0) {
                            if ((year % 100) == 0 && (year % 400) != 0) {
                                day = 28;
                            } else {
                                day = 29;
                            }
                        } else {
                            day = 28;
                        }
                        break;
                    default :
                        day = 30;
                }
            return day;
        }

        /**
         * 요청한 주차의 월요일부터 다음주 일요일까지의 날짜를 배열로 리턴
         * @param year
         * @param month
         * @param week
         */
        public static String[] getFirstAndFinishDayOfWeekFromMonday(int year, int month, int week) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(2);  //주 시작을 월요일로 세팅(월~일)
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.WEEK_OF_MONTH, week);

            calendar.set(Calendar.DAY_OF_WEEK, 2);
            String weekStartDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());    //"yyyyMMDD"
            calendar.set(Calendar.DAY_OF_WEEK, 1);
            String weekEndDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());  //"yyyyMMDD"

            String[] thisWeekDay = {weekStartDay, weekEndDay};
            return thisWeekDay;
        }

        public static String[] getFirstAndFinishDayOfWeekFromMonday2(int year, int month, int week) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);    //주 시작을 일요일로 세팅(월~일)
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.WEEK_OF_MONTH, week);

            calendar.set(Calendar.DAY_OF_WEEK, 1);
            String weekStartDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());    //"yyyyMMDD"

            calendar.set(Calendar.DAY_OF_WEEK, 7);
            String weekEndDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());  //"yyyyMMDD"

            String[] thisWeekDay = {weekStartDay, weekEndDay};
            return thisWeekDay;
        }


        public static String get7DayAgoDate(int year , int month , int day) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month-1, day);
            cal.add(Calendar.DATE, -7);
            java.util.Date weekago = cal.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            return formatter.format(weekago);
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // 파일관련
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        public static String getExtension(String fileStr){
            return fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length());
        }


        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // 리스팅관련
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // =====================================================================
        // 페이징
        // =====================================================================
        public static int totalPage(int max_num,  int list_num ) {
            int page_cnt = (int)(max_num / list_num);
            if (page_cnt == 0) page_cnt = 1;
            else if (page_cnt > 0 && (max_num % list_num) > 0) page_cnt = page_cnt + 1;
            else if (page_cnt > 0 && (max_num % list_num) ==0) page_cnt = page_cnt;

            return page_cnt;
        }

        // =====================================================================
        // 페이지블럭
        // =====================================================================
        public static String pageNavigation(String self_url, String queryString, int max_num, int list_num, int int_pg) {
            StringBuffer buf  = new StringBuffer("\r\n");
            String tmp        = "";
            int page_size     = 10; //(????  1 2 3 4 5 6 7 8 9 10 ????)

            if(queryString == null) queryString ="";
            queryString = queryString.trim();

            int idx = queryString.indexOf("&req_pg");

            if(idx > -1) {
              tmp = queryString.substring(0,idx);
              if((idx = queryString.indexOf("&",idx+1)) > 0) {
                  queryString = tmp + queryString.substring(idx);
              }else {
                  queryString = tmp;
              }//end_else
            }//end if(idx)


            int page_cnt = (int)(max_num / list_num);

            if (page_cnt == 0) page_cnt = 1;
            else if (page_cnt > 0 && (max_num % list_num) > 0) page_cnt = page_cnt + 1;
            else if (page_cnt > 0 && (max_num % list_num) ==0) page_cnt = page_cnt;

            int start_pg = ((int_pg % page_size) > 0) ? ((int)(int_pg/page_size)*page_size + 1) : (((int)(int_pg/page_size)-1)*page_size + 1);
            int last_pg  = page_cnt;

            if (start_pg != 1) {
                buf.append("<a href='" + self_url + "?" + queryString + "&req_pg=" + (start_pg - 1) + "'>????</a> /");
            }else{
                buf.append("???? /");
            }

            for(int i=start_pg; i<= (start_pg + (page_size-1)); i++)  {
                if ( i > page_cnt) break;
                if(i == int_pg) {
                    buf.append("<FONT size=2 color=red> " + i + " </font>/");
                } else {
                    buf.append("<a href='" + self_url + "?"+ queryString + "&req_pg=" + i + "'><FONT size=2> " + i + " </font></a>/");
                }
                last_pg++;
            }//end_for

            if ((start_pg + (page_size-1)) < page_cnt) {
                buf.append("<a href='" + self_url + "?" + queryString + "&req_pg=" + (start_pg + page_size) + "'> ????</a>");
            } else{
                buf.append(" ????");
            }

            return buf.toString();

        }//pageNavigation End Class

        // =====================================================================
        // 페이징처리2
        // =====================================================================
        public static String pageBar(String link_url, int total, int current_page, int page_per_rows, int page_per_block){
            String page_bar = "";
            int total_pages = 0;
            int total_block = 0;
            int block = 0;
            int block_start = 0;
            int block_end = 0;
            int prev_page = 0;
            int next_page = 0;

            if((total % page_per_rows) == 0){  total_pages = (int)(total / page_per_rows);
            }else{  total_pages = (int)(total / page_per_rows) +1 ;
            }

            total_block = (int)(total_pages/page_per_block);
            block = (int)((current_page -1) / page_per_block) + 1;
            block_start = (block - 1) * page_per_block + 1;
            block_end = block_start + page_per_block -1;

            prev_page = block_start - 1;
            page_bar = " <a href='"+link_url+"&page="+Integer.toString(prev_page)+"'>◀&nbsp;</a>"+page_bar;
            if(block == 1){   page_bar = "";   }

            for(int i=block_start-1; i<block_end; i++){  int k = i+1;
                if(current_page == k){
                       page_bar +="<b>"+Integer.toString(k)+"</b> / ";
                }else if(k > total_pages){
                       page_bar += "";
                }else{
                       page_bar +="<a href='"+link_url+"&page="+Integer.toString(k)+"'> <font color=black>"+Integer.toString(k)+"</font> </a> / ";
                } // end if
            } // end for

            next_page = block_start + page_per_block;

            if(block <= total_block){
                 page_bar +="&nbsp;<a href='"+link_url+"&page="+Integer.toString(next_page)+"'>"+
                            "<font color=black>▶</font></a><a href='"+link_url+"&page="+Integer.toString(total_pages)+"'>"+
                            "<font color=black>[끝]</font></a>";
            }else{
                 page_bar += "";
            }

            return page_bar;
        }

        // =====================================================================
        // 개행문자를 <br> 태그로 변환
        // =====================================================================
        public static  String nl2bt(String str) {
            if(str == null) {   return new String("");   }
            StringBuffer buff = new StringBuffer(1024);
            for (int k = 0; k < str.length(); k++) {
                char one = str.charAt(k);
                if ('\n' == one ) {
                    buff.append("<br>");
                }else if(' ' == one) {
                    buff.append("&nbsp;");
                }else{
                    buff.append(one);
                }
            }
            return buff.toString();
        }

        // =====================================================================
        // 한글인코딩
        // =====================================================================
        public static String hanEncode(String src) {
            if(src == null) {   return "";   }
            String result = null;
            try {
                result = URLEncoder.encode(ksc(src));
            }catch(Exception e) {
                result = null;
            }
            return result;
        }

        // =====================================================================
        // 한글디코딩
        // =====================================================================
        public static String hanDecode(String src) {
            if(src == null) {  return "";   }
            String result = null;
            try {
                result = han(URLDecoder.decode(src));
            }catch(Exception e) {
                result = null;
            }
            return result;
        }

        // KSC ================================================
        public static String han(String str)  {
            if(str == null) {  return new String("");    }
            try{
                str = new String(str.getBytes("8859_1"),"KSC5601");
            }catch(Exception e){  }
            return isString(str,"");
        }

        // ISO ================================================
        public static String ksc(String str)  {
            if(str == null) { return new String("");  }
            try{
                str = new String(str.getBytes("KSC5601"),"8859_1");
            }catch(Exception e){    }
            return str;
        }
        // UTF ================================================
        public static String utf(String str)  {
            if(str == null) { return new String("");  }
            try{
                str = new String(str.getBytes("8859_1"),"UTF-8");
            }catch(Exception e){    }
            return str;
        }


        // =====================================================================
        // 년도 콤보박스 만들어주기
        // =====================================================================
        public static String comboYear(String today){
            StringBuffer buff = new StringBuffer(1024);
            today = today.substring(0,4);
            int startday = Integer.parseInt(today)-2;

            for( ; startday < Integer.parseInt(today)+3 ; startday++ ){
                buff.append("<option value='"+ startday +"' ");
                if( today.equals(""+startday) ) {
                    buff.append("   selected ");
                }
                buff.append(" > "+ startday +" 년  </option>\n");
            }
            return buff.toString();
        }

        // To_Day Month =======================================
        public static String comboMonth(String today){
            StringBuffer buff = new StringBuffer(1024);

            for(int i=1; i<13; i++){
                if(i >= 10){
                    buff.append("<option value='"+ i +"' ");
                    if(( today.substring(4,6)).equals(""+i)) {
                        buff.append(" selected ");
                    }
                }else{
                    buff.append("<option value='0"+ i +"' ");
                    if(( today.substring(4,6)).equals("0"+i)) {
                        buff.append(" selected ");
                    }
                }
                buff.append(" > "+ i +" 월  </option>\n");
            }
            return buff.toString();
        }

        // To_Day Month =======================================
        public static String comboMonth2(String today){
            StringBuffer buff = new StringBuffer(1024);

            for(int i=1; i<13; i++){
                if(i >= 10){
                    buff.append("<option value='"+ i +"' ");
                    if(today.equals(""+i)) {
                        buff.append(" selected ");
                    }
                }else{
                    buff.append("<option value='0"+ i +"' ");
                    if(today.equals("0"+i)) {
                        buff.append(" selected ");
                    }
                }
                buff.append(" > "+ i +" 월  </option>\n");
            }
            return buff.toString();
        }


        // To_Day Day =========================================
        public static String comboDay(String today){
            StringBuffer buff = new StringBuffer(1024);

            for(int i=1; i<32; i++){
                if(i <= 9) {
                    buff.append("<option value='0"+ i +"' ");
                    if(( today.substring(6,8)).equals("0"+i)) {
                        buff.append("   selected ");
                    }
                    buff.append(" > "+ i +"??  </option>\n");
                }
                else{
                    buff.append("<option value='"+ i +"' ");
                    if(( today.substring(6,8)).equals(""+i)) {
                        buff.append("   selected ");
                    }
                    buff.append(" > "+ i +" 일  </option>\n");
                }
            }
            return buff.toString();
        }


        // Hour Drop =======================================
        public static String comboHour(String today){
            StringBuffer buff = new StringBuffer();

            for(int i=1; i<=24; i++){
                if(i >= 10){
                    buff.append("<option value='"+ i +"' ");
                    if(today.equals(""+i)) {
                        buff.append(" selected ");
                    }
                    buff.append(" >"+ i +"</option>\n");
                }else{
                    buff.append("<option value='0"+ i +"' ");
                    if(today.equals("0"+i)) {
                        buff.append(" selected ");
                    }
                    buff.append(" >0"+ i +"</option>\n");
                }
            }
            return buff.toString();
        }

        // =====================================================================
        // Date Format (YYYY-MM-DD)
        //  - 오라클에서는 정확한 데이터타입이 맞지 않으면 안됨으로
        //    정확히 체크를 해야된다.
        //  - 1.4에서는 .format()이 지원하지 않음으로 이런 꼼수를 쓴다. -_-;;
        //  - 일이 해당달의 마지막날보다 클경우에는 마지막날을 반환한다.
        // =====================================================================
        public static String setDFM(String year, String month, String day){
            String result = "";
            if(year==null) {      year  = "200"; }
            if(month==null){      month = "1";   }
            if(day==null)  {      day   = "1";   }
            if(year.equals("")) { year  = "200"; }
            if(month.equals("")){ month = "1";   }
            if(day.equals(""))  { day   = "1";   }

            int fm_year  = Integer.parseInt(year);
            int fm_month = Integer.parseInt(month);
            int fm_day   = Integer.parseInt(day);

            String e_year  = "2008";
            String e_month = "01";
            String e_day   = "01";

            if( year.length() != 4 ){
                e_year = Integer.toString(getCurrentYear());
            }else{
                e_year = year;
            }

            if(fm_month<1){
                fm_month = 1;
            }
            if(fm_month>12){  fm_month = 12;    }
            if(fm_month<10){
                e_month = "0"+Integer.toString(fm_month);
            }else{
                e_month = Integer.toString(fm_month);
            }

            if(fm_day<1){     fm_day   = 1;     }
            if(fm_day<10){
                e_day = "0"+Integer.toString(fm_day);
            }else{
                int last_day = Integer.parseInt(getLastDay(fm_year, fm_month));
                if(fm_day>last_day){
                    e_day = Integer.toString(last_day);
                }else{
                    e_day = Integer.toString(fm_day);
                }
            }
            result = e_year+"-"+e_month+"-"+e_day;
            return result;
        }


        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // 쿠키/세션관련
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // =====================================================================
        // URL 파싱
        // =====================================================================
        public static String paramToString(String param) {
            if(param == null) return "";
            param = param.replace('&', '|');
            param = param.replace('=', '*');
            return param;
        }

        public static String stringToParam(String str) {
            if(str == null) return "";
            str = str.replace('|', '&');
            str = str.replace('*', '=');
            return str;
        }

        // =====================================================================
        // 3자리마다 콤마찍기
        // =====================================================================
        public static String numberFormat(int value){
          String result = new DecimalFormat("###,###,###,###,###").format(value);
          if(result.equals("0")){
             result = "0";
          }
          return result;
        }

        public static String numberFormat(String value){
            int new_val = isNumber(Integer.parseInt(value), 0);
            String result = numberFormat(new_val);
            return result;
        }

        public static String numberFormat2(long value){
              String result = new DecimalFormat("###,###,###,###,###").format(value);
              return result;
        }

        public static String numberFormat(long value){
              String result = new DecimalFormat("###,###,###,###,###").format(value);
              if(result.equals("0")){
                 result = "0";
              }
              return result;
        }

        // =====================================================================
        // Email 주소 체크
        // =====================================================================
       public static boolean emailCheck(String email) {
         if (email==null) return false;
         boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
         return b;
       }


        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //  날짜관련 메소드
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // =====================================================================
        // 날짜비교
        // =====================================================================
        public static String compareDate(String s_d,String f_d,String to_day){
            String full_s_day=s_d.substring(0,4) + s_d.substring(4,6) + s_d.substring(6,8);
            String full_e_day=f_d.substring(0,4) + f_d.substring(4,6) + f_d.substring(6,8);
            String full_to_day=to_day.substring(0,4) + to_day.substring(4,6) + to_day.substring(6,8);

            int full_sday=Integer.parseInt(full_s_day);
            int full_eday=Integer.parseInt(full_e_day);
            int full_today=Integer.parseInt(full_to_day);

            String str = "";
            if ((full_today>=full_sday) && (full_today<=full_eday)) {
                return "????";
            }else if(full_today>full_eday)  {
                return "~??";
            }else if(full_today<full_eday)  {
                return "??d";
            }else   {
                return "";
            }
        }

        // ======================================================================
        // 해당월의 마지막날짜 구하기
        // ======================================================================
        public static String getLastDay(int intYear, int intMonth) {
            Calendar cal = new GregorianCalendar();
            cal.setLenient(false);
            cal.set(intYear, intMonth - 1, 1);
            return Integer.toString(cal.getActualMaximum(GregorianCalendar.DATE));
        }

        // ======================================================================
        // 올해구하기
        // ======================================================================
        public static int getCurrentYear(){
            GregorianCalendar today = new GregorianCalendar();
            return today.get(today.YEAR);
        }

        // ======================================================================
        // 이번달 구하기
        // ======================================================================
        public static int getCurrentMonth(){
            GregorianCalendar today = new GregorianCalendar();
            return today.get(today.MONTH)+1;
        }

        // ======================================================================
        // 이번달 구하기 - 02
        // ======================================================================
        public static String getCurrentMonth2(){
            String r = "01";
            GregorianCalendar today = new GregorianCalendar();
            int ret = today.get(today.MONTH)+1;
            if(ret<10){
                r = "0"+Integer.toString(ret);
            }else{
                r = Integer.toString(ret);
            }
            return r;
        }

        // ======================================================================
        // 오늘구하기
        // ======================================================================
        public static int getCurrentDay(){
            GregorianCalendar today = new GregorianCalendar();
            return today.get(today.DATE);
        }

        // ======================================================================
        // 올해의 첫날/마지막날
        // ======================================================================
        public static String getCurrentFirstDay(String isLast){
            if(isLast.equals("first")){
                return Integer.toString(getCurrentYear())+"-01-01";
            }else{
                return Integer.toString(getCurrentYear())+"-12-31";
            }
        }


        // ======================================================================
        // 지금년월로 부터 n 월전/후의 년-월값 구하기
        // JDK 1.4 에서는 String.format가 지원하지 않음으로 0x로 날짜를 맞출려면
        // 쉽게 아래와 같은 방법을 사용한다.
        // ======================================================================
        public static String getTermYearMonth(int prev_month){
            GregorianCalendar today = new GregorianCalendar();
            today.add(Calendar.MONTH,prev_month);
            int year  = today.get(today.YEAR);
            int month = today.get(today.MONTH)+1;

            String end_date = Integer.toString(year)+"-";
            if(month<10){
               end_date += "0"+Integer.toString(month);
            }else{
               end_date += Integer.toString(month);
            }
            return end_date;
        }

        // ----------------------------------------------------------------------
        // 6자리의 년월값을 년도, 월 값으로 변환
        // ----------------------------------------------------------------------
        public static String levyYm2Text(String levyYm){
            String yy = "";
            String mm = "";
            String ret = "기간없음";
            levyYm = isString(levyYm,"");
            if(!levyYm.equals("")){
                if(levyYm.length()==6){
                    yy = levyYm.substring(0,4);
                    mm = levyYm.substring(4,6);
                    levyYm = yy+"년 "+mm+"월";
                }else{
                    levyYm = "기간없음";
                }
            }else{
                yy = "";
                mm = "";
                levyYm = "기간없음";
            }
            return ret;
        }

        // ----------------------------------------------------------------------
        // 10원이하 반올림처리 : round
        // ----------------------------------------------------------------------
        public static long round(double amt){
            long abs = Math.round(amt);
            long ret = abs;
            if(abs>0){
                String tNum    = Long.toString(abs);
                String lastNum = tNum.substring(tNum.length()-1, tNum.length());
                long lastAmt    = isNumber(lastNum, 0);
                long sprAmt     = 10-lastAmt;

                if(lastAmt>=5){
                    ret = abs+sprAmt;
                }else{
                    ret = abs-lastAmt;
                }
            }
            return ret;
        }

        // ----------------------------------------------------------------------
        // 10원이하 올림처리 : CEIL
        // ----------------------------------------------------------------------
        public static long ceil(double amt){
            long abs = (long)Math.ceil(amt);
            long ret = abs;
            if(abs>0){
                String tNum     = Long.toString(abs);
                String lastNum  = tNum.substring(tNum.length()-1, tNum.length());
                long lastAmt    = isNumber(lastNum, 0);
                long sprAmt     = 10-lastAmt;

                if(lastAmt>0){
                    ret = abs+sprAmt;
                }
            }
            return ret;
        }

        // ----------------------------------------------------------------------
        // 10원이하 내림처리 : FLOOR
        // ----------------------------------------------------------------------
        public static long floor(double amt){
            long abs = (long)Math.floor(amt);
            long ret = abs;
            if(abs>0){
                String tNum     = Long.toString(abs);
                String lastNum  = tNum.substring(tNum.length()-1, tNum.length());
                long lastAmt    = isNumber(lastNum, 0);

                ret = abs-lastAmt;
                //System.out.print("--->Floor : "+Long.toString(abs)+" > "+Long.toString(ret)+"\n");
            }
            return ret;
        }


        // **********************************************************************
        // Mime헤더
        // **********************************************************************
        public static String getMime(String m){
            String ret = "text/html";
            if(m.equals("json")){           ret = "application/json;charset=utf-8";
            }else if(m.equals("xml")){      ret = "application/xml;charset=utf-8";
            }else if(m.equals("script")){   ret = "application/x-javascript; charset=utf-8";
            }else if(m.equals("html")){     ret = "text/html; charset=utf-8";
            }
            return ret;
        }


        // **********************************************************************
        // MAP처리
        // **********************************************************************
        // ----------------------------------------------------------------------
        // HttpQueryString 를 Map으로 바꿔서 리턴해준다.
        // ----------------------------------------------------------------------
        public static void QueryToMap(Map<String, String> map, String qs) throws UnsupportedEncodingException {
            if( qs == null ) return;
            String[] nvs = qs.split( "&" );
            for( String nv : nvs ) {
                String[] parts = nv.split( "=" );
                String key = parts[0];
                String val = null;
                if( parts.length > 1 ) val = parts[1];
                if( val != null ) {
                    try {
                        val = URLDecoder.decode( val, "UTF-8" );
                    } catch( UnsupportedEncodingException ex ) {
                        throw new RuntimeException( ex );
                    }
                }
                map.put( key, val );
            }
        }

        // ----------------------------------------------------------------------
        // Map2Select
        // ----------------------------------------------------------------------
        public static String Map2Select(Map<String,String> _map, String selVal){
            String optionHtml = "";
            Iterator<String> iter = _map.keySet().iterator();

            while (iter.hasNext()){
                String nKey  = iter.next();
                String nValue= _map.get(nKey);

                if (nKey.equals(selVal)){
                    optionHtml += "<option value="+nKey+" selected>"+nValue+"</option>";
                }else{
                    optionHtml += "<option value="+nKey+">"+nValue+"</option>";
                }
            }
            return optionHtml;
        }

        // ----------------------------------------------------------------------
        // MapValue
        // ----------------------------------------------------------------------
        public static String MapValue(Map<String,String> _map, String selKeyVal){
            String ret = "";
            Iterator<String> iter = _map.keySet().iterator();

            while (iter.hasNext()){
                String nKey  = iter.next();
                String nValue= _map.get(nKey);

                if (nKey.equals(selKeyVal)){
                    ret = nValue;
                }
            }
            return ret;
        }


        public static String encryptMD5(String input) throws NoSuchAlgorithmException{
            String result = input;
            if(input != null) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while(result.length() < 32) {
                    result = "0" + result;
                }
            }
            return result.replace("=", "");
        }
}

package CodeGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class GenerateInput {
	private static final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM     = "0123456789";
    private static final String SPL_CHARS   = "!@#$%^&*_=+-/():[]~{}.?\\";
	
	//numeric string, need length
	static String randomNum(int length){
		String str = RandomStringUtils.randomNumeric(length);
		return str;
	}
	
	//only alphabet, need length
	static String randomAlp(int length){
		String str = RandomStringUtils.randomAlphabetic(length);
		return str;
	}
	
	//only alphabet using lower case letters, need length 
	static String randomlowerAlp(int length){
		String str = RandomStringUtils.random(length, ALPHA);
		return str;
	}
	
	//only alphabet using capital letters, need length 
	static String randomcapAlp(int length){
		String str = RandomStringUtils.random(length, ALPHA_CAPS);
		return str;
	}
	
	//alphanum string
	static String randomAlpnum(int length){
		String str = RandomStringUtils.randomAlphanumeric(length);
		return str;
	}
	
	//format 1: xxx-xxx-xxxx; format 2: (xxx)-xxx-xxxx; other: xxxxxxxxxx
	//valid: true: generate valid phone number; false: invalid phone number
	static String phoneNum(boolean valid, int format){
		String str;
		int num1, num2, num3; //3 numbers in area code
        int prefix, suffix;
		Random generator = new Random();
        
		if(valid){
			//Area code: 201-989
			num1 = generator.nextInt(8) + 2; //add 2 so there is no 0 to begin  
			num2 = generator.nextInt(9);//should not be 9 
			num3 = generator.nextInt(10);//num3 != num2
			while(num2 == num3){
				num3 = generator.nextInt(10);
			}
		}else{
			num1 = generator.nextInt(2); 
			num2 = generator.nextInt(10);
			num3 = generator.nextInt(10);
		}
        
			//Prefix: 3 numbers, <= 999
			prefix = generator.nextInt(900) + 100;
        
			//Suffix: 4 numbers, <= 9999
			suffix = generator.nextInt(9000) + 1000;
        
        switch(format){
        	case 1://xxx-xxx-xxxx
        		str = "" + num1 + num2+ num3 + "-" + prefix + "-" + suffix;
        		break;
        	case 2://(xxx)-xxx-xxxx
        		str = "(" + num1 + num2+ num3 + ")" + "-" + prefix + "-" + suffix;
        		break;
        	default:
        		str = ""+ num1 + num2+ num3 + prefix + suffix;
        		break;
        }
		return str;
	}
	
	//Microsoft short time: 4 format+ plain
	//1: h:mm tt
	//2: hh:mm tt
	//3: H:mm 
	//4: HH:mm 
	//other: HHmm
	static String time(boolean valid, int format){
		Random generator = new Random();
		final int millisInDay = 24*60*60*1000;
		String str, tt, hh, mm;
		int hour, minute;
		if(valid){
			LocalTime time = new LocalTime(generator.nextInt(millisInDay));
			hour = time.getHourOfDay();
			minute = time.getMinuteOfHour();
		}else{
			hour = 25;
			minute = 70;
			str = "";		
		}
		if(minute < 10 && minute >= 0){
			mm = "0" + minute;
		}else mm = ""+minute;
		
		switch(format){
    	case 1://1: h:mm tt
    		tt = "am";
    		if(hour > 12){
    			hour -= 12;
    			tt = "pm";
    		}
    		str = hour + ":"+ mm + " " + tt;
    		break;
    	case 2://2: hh:mm tt
    		tt = "am";
    		if(hour > 12){
    			hour -= 12;
    			tt = "pm";
    		}
    		if(hour < 10 && hour >= 0){
    			hh = "0" + hour;
    		}else hh = ""+hour;
    		str = hh + ":"+ mm + " " + tt;
    		break;
    	case 3://3: H:mm 
    		str = hour + ":"+ mm;
    		break;
    	case 4://4: HH:mm 
    		if(hour < 10 && hour >= 0){
    			hh = "0" + hour;
    		}else hh = ""+hour;
    		str = hh + ":"+ mm;
    		break;
    	default:
    		if(hour < 10 && hour >= 0){
    			hh = "0" + hour;
    		}else hh = ""+hour;
    		str = hh + mm;
    		break;
		}
		return str;
	}
	
	//
	//Microsoft short date: 7 format + plain
	//1:M/d/yyyy
	//2:M/d/yy
	//3:MM/dd/yy
	//4:MM/dd/yyyy
	//5:yy/MM/dd
	//6:yyyy-MM-dd
	//7:dd-MMM-yy
	//other:MMddyyyy
	@SuppressWarnings("deprecation")
	static String date(boolean valid, int format, String begindate, String enddate) throws ParseException{
		Random generator = new Random();
		String str = "", yy, mm, dd;
		String[] mmm = new String[]{"JAN", "FEB", "MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		int year, month, day;
		Date date;
		Date d1 = new SimpleDateFormat("M/d/yyyy").parse(begindate);
		Date d2 = new SimpleDateFormat("M/d/yyyy").parse(enddate);
		long diff = d2.getTime() - d1.getTime();
		if(diff > 0)date = new Date(d1.getTime() + Math.abs(generator.nextLong()%diff));
		else date = new Date(d2.getTime() + Math.abs(generator.nextLong()%(-diff)));
		if(valid){
			DateTime dateTime = new DateTime(date);
			year = dateTime.getYear();
			month = dateTime.getMonthOfYear();
			day = dateTime.getDayOfMonth();
		}else{
			year = 2016;
			month = 2;
			day = 30;
		}
		
		yy = "" + year;
		yy.substring(2);
		if(month < 10 && month > 0){
			mm = "0" + month;
		}else mm = ""+month;
		if(day < 10 && day > 0){
			dd = "0" + day;
		}else dd = ""+day;
		
		switch(format){
    	case 1://1:M/d/yyyy
    		str = "" + month + "/"+ day + "/" + year;
    		break;
    	case 2://2:M/d/yy
    		str = "" + month + "/"+ day + "/" + yy;
    		break;
    	case 3://3:MM/dd/yy
    		str = mm + "/"+ dd + "/" + yy;
    		break;
    	case 4:	//4:MM/dd/yyyy
    		str = mm + "/"+ dd + "/" + year;
    		break;
    	case 5://5:yy/MM/dd
    		str = yy + "/"+ mm + "/" + dd;
    		break;
    	case 6://6:yyyy-MM-dd
    		str = "" + year + "-"+ mm + "-" + dd;
    		break;
    	case 7://7:dd-MMM-yy
    		str = "" + year + "-"+ mmm[month - 1] + "-" + dd;
    		break;
    	default:
    		str = mm + dd + year;
    		break;
		}
		return str;
	}
	
	//minLen: minimum length of password
	//maxLen: maximum length of password
	//numCapAlpha: number of capital letters
	//numDigits: number of Digits
	//numSplChars: number of special characters
	//Can generate valid or invalid password
	static String password(int minLen, int maxLen, int numCapAlpha, 
            int numDigits, int numSplChars){
		if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if( (numCapAlpha + numDigits + numSplChars) > minLen )
            throw new IllegalArgumentException
            ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random generator = new Random();
        int len = generator.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        boolean[] visit = new boolean[len];
        for(int i = 0; i < len; i++)visit[i]=false;
        int index = 0;
        for (int i = 0; i < numCapAlpha; i++) {
            index = getNextIndex(generator, len, visit);
            pswd[index] = ALPHA_CAPS.charAt(generator.nextInt(ALPHA_CAPS.length()));
            visit[index] = true;
        }
        for (int i = 0; i < numDigits; i++) {
            index = getNextIndex(generator, len, visit);
            pswd[index] = NUM.charAt(generator.nextInt(NUM.length()));
            visit[index] = true;
        }
        for (int i = 0; i < numSplChars; i++) {
            index = getNextIndex(generator, len, visit);
            pswd[index] = SPL_CHARS.charAt(generator.nextInt(SPL_CHARS.length()));
            visit[index] = true;
        }
        for(int i = 0; i < len; i++) {
            if(visit[i] == false) {
                pswd[i] = ALPHA.charAt(generator.nextInt(ALPHA.length()));
                visit[index] = true;
            }
        }
        String str = pswd.toString();
		return str;
	}
	
	private static int getNextIndex(Random generator, int len, boolean[] visit) {
        int index = generator.nextInt(len);
        while(visit[index] != false) index = generator.nextInt(len);
        return index;
    }
	
	
	static String cityName(){
		String str = "Austin";
		DataFactory df = new DataFactory();
		Random generator = new Random();
		int c = generator.nextInt(100);
		for(int i =0; i<=c; i++)str = df.getCity();
		return str;
	}
	
	/*static String stateName(){
		String str = "Texas";
		return str;
	}*/
	
	static String address(){
		String str = "Austin";
		DataFactory df = new DataFactory();
		Random generator = new Random();
		int c = generator.nextInt(100);
		for(int i =0; i<=c; i++)str = df.getAddress();
		return str;
	}
	
	//no restrictions, numeric string, length = 5
	static String zip(){
		String str = randomNum(5);
		return str;
	}
	
	static String email(){
		String str = "";
		Random generator = new Random();
		str = randomAlpnum(generator.nextInt(6)+6) + "@" + randomAlpnum(generator.nextInt(3)+3) + ".com";
		return str;
	}
	
	/*public static void main(String [ ] args) throws ParseException{//for test
		System.out.println(GenerateInput.email());
	}*/
}

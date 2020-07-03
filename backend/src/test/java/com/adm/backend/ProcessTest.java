package com.adm.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessTest {
 
    public static void main(String... args) {

        /**
         * Properties
         */
        //String home = System.getProperty("admin.home");
        //String java_home = System.getProperty("JAVA_HOME");

        // System.out.println(home);	
        // System.out.println(java_home);

        /**
         * CharSequence
         */
        // String str = "c:/workspace/test2";

        // System.out.println((CharSequence)str);
        
        // CharSequence cs = new StringBuffer("Test");

        // System.out.println((String) cs);

        //System.out.println(File.separator);


        /**
         * Pattern , Matcher 
         */
        
        System.out.println(hasPropertyExpress("${T}"));
    }
    
    private static boolean hasPropertyExpress(String strVal) {
        Pattern pattern = Pattern.compile("(\\$\\{).+(\\}).*");
        // 역슬래쉬 \ 뒤에 특수 문자는 특수문자
        // () 소괄호 안에 문자를 하나의 문자로 인식
        // + 앞문자가 하나 이상 
        // . 은 임의 하나의 문자
        // ( $ 와 {  ) . -> ${하나의문자}~~~~  
        
        System.out.println(pattern.toString());
        Matcher matcher = pattern.matcher(strVal);
        System.out.println(matcher.toString());
        return matcher.find();
    }
}
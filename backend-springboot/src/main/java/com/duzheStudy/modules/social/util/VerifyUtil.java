package com.duzheStudy.modules.social.util;

//字符串校验工具
public class VerifyUtil {
    public static String sex = "0,1,2";

    public static String nick_nameVerify(String nick_name){
        if(nick_name==null) return "昵称为空";
        if(nick_name.length()<1) return "昵称最少需要1个字符";
        if(nick_name.length()>15) return "昵称最多不超过15个字符";
        if(nick_name.contains(" ")) return "昵称不能包含空格";
        return null;
    }

    public static String emailVerify(String email){
        if(email==null) return "邮箱为空";
        String tegex="[a-zA-Z0-9_]+@\\w+(\\.com|\\.cn){1}";
        if(email.matches(tegex))
            return null;
        return "邮箱格式不合法";
    }

    public static String passwordVerify(String password){
        if(password==null)  return "密码为空";
        if(password.length()<3)
            return "密码不能少于3位";
        return null;
    }

    public static String sexVerity(String sex){
        if (sex==null||sex.equals(""))   return "未知";
        return String.valueOf(sex.indexOf(sex));
    }
/*    public static char sexVerify(String sex){
        if(sex==null|| sex.equals(""))    return (char) -1;
        return (char) sex.indexOf(sex);
    }*/

}

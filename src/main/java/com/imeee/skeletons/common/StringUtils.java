package com.imeee.skeletons.common;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-23
 * Time: 11:26
 */
public class StringUtils {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateRanomString(int length){
        Random random = new Random();
//		String s = "";
        StringBuffer shortBuffer = new StringBuffer();
        for(int i = 0; i < length; ++i){
            shortBuffer.append(chars[random.nextInt(chars.length)]);
        }

        return shortBuffer.toString();
    }
}

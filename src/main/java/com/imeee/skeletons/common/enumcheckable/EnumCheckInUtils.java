package com.imeee.skeletons.common.enumcheckable;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:46
 */
public class EnumCheckInUtils {

    /** @Description:
     *   判断某个值是否在一个枚举类型中，此方法比较的是枚举类型的"code"字段
     * @param code
     *   检查的值
     * @param enumClz
     *   枚举类型的Class
     * @return
     * @throws Exception
     */
    public static boolean isInEnum(Object code, Class<? extends EnumCheckable> enumClz) throws Exception{

        if(!enumClz.isEnum()){
            throw new Exception("非Enum类型");
        }

        Method valueMeth = enumClz.getMethod("values");
        Method getCodeMeth = enumClz.getDeclaredMethod("_getCode");

        if(valueMeth == null || getCodeMeth == null){
            throw new Exception("EnumUtils.isInEnum(): valueMethod or getCodeMethod is null!");
        }

        if(code == null){
            return false;
        }

        if(!getGenericClass(code.getClass()).equals(
                getGenericClass(getCodeMeth.getReturnType()))){
            throw new Exception("枚举中的字段类型不匹配");
        }

        Enum[] enumss = (Enum[]) valueMeth.invoke(null,null);

        for(Enum e : enumss){
            Object eCode = getCodeMeth.invoke(e,null);
            if(eCode != null && eCode.equals(code)){
                return true;
            }
        }

        return false;
    }

    /**
     * 获取基类的封装类
     * @param clz
     * @return
     */
    public static Class<?> getGenericClass(Class<?> clz){

        Class<?> genericClz = null;
        if(int.class == clz) {
            genericClz = Integer.class;
        } else if(long.class == clz) {
            genericClz = Long.class;
        } else if(double.class == clz) {
            genericClz = Double.class;
        } else if(float.class == clz) {
            genericClz = Float.class;
        } else if(boolean.class == clz) {
            genericClz = Boolean.class;
        } else if(char.class == clz) {
            genericClz = Character.class;
        } else if(byte.class == clz){
            genericClz = Byte.class;
        } else if(short.class == clz) {
            genericClz = Short.class;
        } else {
            genericClz = clz;
        }

        return genericClz;
    }

//    public static void main(String[] args) throws Exception{
//        String category = "appgame";
//        if(isInEnum(category, MmCategoryEnum.class)){
//            System.out.println("in category!");
//        } else {
//            System.out.println("not in category!");
//        }
//    }

}

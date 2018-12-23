package com.imeee.skeletons.facade.requestTracing;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-23
 * Time: 11:20
 */
public class Constants {
    // 这个在logback的打印配置中，可以使用 %X[traceid] 打印跟踪号;
    public static final String SEQ_ATTRIBUTE = "traceid";

    public static final String MILLIS_START = "_RequestStartTime_";

    public static final String MILLIS_FINISHD = "_RequestFinishedTime_";
}

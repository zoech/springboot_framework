# springboot_framework
__spring boot web 应用自用框架__

本文主要介绍原理，在此框架上的开发最好依照预定的规范进行，便于管理，开发规范查看本目录下的[SPECS.MD]("./SPECS.MD")

### 计划进展
1. 添加基础的外存模块(数据库)
2. 添加基础的权限模块

## 源码目录结构

```
.  
|---_biz                         主要的业务层逻辑代码
|   |---_base                    基础的service层，比如用户管理service
|   └---_module1                 业务模块1
|   |   |---_service             service代码
|   |   └---_bo                  service方法的参数和返回实体
|   └---_module2                 业务模块2
|---_boot                        应用启动相关的代码
|   |---_config                  springboot的启动配置类,比如Interceptor的配置、自定义bean放在这里
|   └---_autorun                 随应用启动时自动启动的一些一次性任务
|---_daemon                      守卫线程代码（不需要前端交互的线程的代码），目前专门放定时任务之类
|---_extern                      不需自身实现的功能，使用外部接口的封装代码
|---_facade                      对前端暴露的接口代码,即controller
|   |---_base                    基础模块的api层
|   └---_module1                 业务模块1
|   |   |---_api                 api层，即controller
|   |   |---_enums               api接口参数的一些枚举，比如前端传来一个参数"gender",0为男1为女则定义在此
|   |   └---_model               api接口层的参数实体与返回前端的响应实体
|   └---_module2                 业务模块2
|---_persistence                 数据持久化（一般都是数据库相关的代码）
|   └--_enum                     储存中某些字段的枚举定义
|---_skeletons                   业务无关的一些通用框架性功能代码，比如用户基础service、校验登陆状态的Inteceptor、全局的异常处理
```

## 模型

模型参考单机操作系统，在这个模型中，整个应用主要的是两大功能模块：
* _对外的api接口_
* _可配置的定时任务_

api接口的代码应清晰地放置到facade目录中，按照业务模块进行划分，然后再按照api、enums、model类似的结构放置。  
定时任务的代码则应该放到daemon目录中，目录名取自Unix like操作系统中的 __守卫进程__ 概念。  
__所有其它的模块都是为这两个功能模块所服务__

## 基础架构性功能模块
这些是一些通用性的架构功能，这些实现多数是基于spring boot框架的一些功能以及jvm的反射功能。  
__记住要透彻地将这些功能性的模块和应用实际的业务模块分离__

### 1. 一致的异常状态返回
一般在web应用中经常会遇到处理请求中出现异常，而在代码中用过多的try catch语句来捕获各种异常，以返回对应的处理结果给前端，是非常难看也不优雅的。  
SpringBoot的 __@ControllerAdvice__ 注解 和 __@ExceptionHandler__ 注解结合使用，可以定义系统大部分情况下发生异常时的统一处理过程，或者精确地说，可以定义controller调用中产生异常时的处理过程。

要点：
* skeletons.facade.GlobalExceptionHandler类, 定义了全局的异常处理过程，而过程中会通过Exception类型来返回不同的结果码到前端
```java
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse defaultExceptionHandler(Exception exception){
        log.error("global exception handler: ", exception);

        // 先查找BizeErrorCodeEnum是否有对应的预设错误码，有的话直接返回
        BizErrorCodeEnum[] bizErrorCodeEnums = BizErrorCodeEnum.values();
        for(BizErrorCodeEnum errorEnum : bizErrorCodeEnums){
            if(exception.getClass().equals(errorEnum.getExceptionClz())){
                return BaseResponse.buildDefaultResponse(errorEnum, exception.getMessage());
            }
        }

        // 无法根据Exception 定位错误，则返回系统错误
        return BaseResponse.buildDefaultFailedResponse(exception.getMessage());
    }

}
```
* skeletons.facade.bizexception.* , 定义的一些列业务处理异常
* skeletons.facade.BizErrorCodeEnum类, 定义了各种统一的返回状态码，包含了对应的异常类型
```java
@Getter
public enum BizErrorCodeEnum implements EnumCheckable {

    SUCCESS("0000", "请求成功", null),

    UNKNOWN_EXCEPTION("9999", "系统未知错误", null),
    NOT_LOGIN("0001", "未登录或者登录超时", NotLoginException.class),

    REQUEST_PARAM_ILLEGAL_GET("0002", "请求参数非法",BindException .class),
    REQUEST_PARAM_ILLEGAL_POST("0002", "请求参数非法",MethodArgumentNotValidException .class),

    INCORRECT_PASSWORD("0003", "用户名或密码错误", IncorrcetPasswordException.class),

    DUPLICATE_LOGIN("0004", "重复登陆", DuplicateLoginException.class),


    JSON_PARSE_ERROR("s002", "请求无法解释，请查看请求信息格式是否合法",HttpMessageNotReadableException .class)
    ;



    // 返回码
    private final String code;

    // 返回消息
    private final String msg;

    // 返回这个error code对应的异常
    private final Class<? extends Exception> exceptionClz;


    BizErrorCodeEnum(String code, String msg, Class<? extends Exception> exception) {
        this.code = code;
        this.msg = msg;
        this.exceptionClz = exception;
    }

    @Override
    public String _getCode(){
        return this.code;
    }

}
```

GlobalExceptionHandler类实际就是根据异常类型，在BizErrorCodeEnum里找到具体异常class对应的返回码枚举BizErrorCodeEnum,再用BizErrorCodeEnum构造BaseResponse返回实体。

例子参考 facade.testing.api.TestController.notLogin() api.

### 2. 自定义的枚举类型参数校验
api模块中，前端传送的参数有可能有一些预先约定的类型，比如调用用户注册的接口时，有一个参数"gender"指明用户性别，预定义0为男1为女。
我们可能要校验参数是否再0与1之内，因为参数"gender":3 对于接口来说该参数是无效的。  
最一般的校验方法是在controller里面手工判断"gender"参数是否是0或者1，但是这样也很笨。

考虑自定义@InEnum 注解，在api接口的参数实体里加上 _@Enum(value = Class<? extends EnumCheckable>)_ 来声明式校验。

要点:
* skeletons.common.enumcheckable.EnumCheckable 接口，实现此接口的enum枚举类型都表明是可以使用下面EnumCheckUtils判断某个值是否在这个枚举的取值范围内的
* skeletons.common.enumcheckable.EnumCheckInUtils 类， 提供判断某个值是否在一个enum中，这个enum必须实现EnumCheckable接口
* skeletons.facade.inEnumValidation.InEnum 注解, 使用了 __@Constraint__ 标记此注解可以在api中使用 __@Valid__ 校验
* skeletons.facade.inEnumValidation.InEnumValidator类， @InEnum 注解使用的校验器, 使用了EnumCheckInUtils

例子参考 facade.testing.api.TestController.validation() api.

### 3. 一致的登陆状态校验
大部分api接口需要校验登陆状态，比如典型的 logout() 接口，需要校验是否已经登陆，否则登场操作是没有意义的。  
而同时，另一些接口可能我们不想校验登陆状态，比如login() 总不可能需要已经登陆了才能登陆。

考虑用spring boot 的 interceptor 拦截器实现，在请求到达controller之前拦截spring boot 的调用链，进行统一的登陆校验

要点:
* skeletons.facade.loginInterceptor.RequireLogin 注解， 在controller的api方法上加上这个注解表明这个api需要登陆校验
```java
@RestController
@RequestMapping("api/test")
public class TestController {
    @RequestMapping(value = "/testRequireLogin", method = RequestMethod.POST)
    @RequireLogin
    public BaseResponse testRequireLogin(){
        return BaseResponse.buildDefaultSuccessResponse();
    }
}
```
* skeletons.facade.loginInterceptor.LoginInterceptor 类， 这个是实现了springboot请求拦截机制的实体，过程中会读取api方法中的@RequireLogin注解，如果有这个注解就进行登陆验证
* boot.config.InterceptorConfig 配置类，这个类的配置中将上面的LoginInterceptor加入到了调用链中，LoginInterceptor才能起作用
```java
@SpringBootConfiguration
public class InterceptorConfig {

    @Autowired
    LoginInterceptor loginInterceptor; // 登陆拦截器

    @Bean
    WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                // 配置登陆状态校验
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns("/**");

            }
        };
    }
}
```
* skeletons.baseService.user.BaseUserService 接口， 这个是在LoginInterceptor 中用到的user的service，实际的校验登陆的过程在这个接口中定义，整个应用中需要有一个bean实现这个接口，否则应用无法启动  
LoginInterceptor 中校验的实际过程 如下：
```java
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    BaseUserServices baseUserServices;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 只有标记了 @RequireLogin 且value 为true， 才进行登陆验证
        RequireLogin requireLogin = handlerMethod.getMethod().getAnnotation(RequireLogin.class);
        if(requireLogin == null || !requireLogin.value()){
            return true;
        }
        
        // 实际的校验过程在baseUserServices.validateLogin()
        if(!baseUserServices.validateLogin()){
            // 直接抛出NotLoginException即可,在GlobalExceptionHandler中会到BizErrorCodeEnum里找到对应的返回码返回
            throw new NotLoginException();
        }

        return true;
    }
}
```

_在这个框架内，有一个简单版的基于servlet session 的登陆校验 BaseUserService实现类 biz.base.service.SessionBaseUserServiceExample类,一般来说实际中用户信息存在数据库，所以需要另外实现BaseUserService，具体参考SessionBaseUserServiceExample类来实现就可以_

例子：
facade.testing.api.TestController.testRequireLogin() api.
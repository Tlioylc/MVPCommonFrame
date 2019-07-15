package com.tlioylc.client.Annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/3/7下午4:35
 * desc   : 双击事件注解注册类
 */
@Retention(RetentionPolicy.RUNTIME)//CLASS 编译时注解  RUNTIME运行时注解 SOURCE 源码注解
@Target({ElementType.METHOD})//注解作用范围:FIELD 属性  METHOD方法  TYPE 放在类上
public @interface OnPreventDoubleClick {
    //    @IdRes int value();
    @IdRes int[] value();
}

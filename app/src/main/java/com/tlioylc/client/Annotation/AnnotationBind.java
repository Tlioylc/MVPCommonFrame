package com.tlioylc.client.Annotation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.tlioylc.client.utils.RxView;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/3/7下午4:35
 * desc   : 双击事件处理
 */
public class AnnotationBind {
    public static void bind(@NonNull Object target,@NonNull View context) {
        checkClickListener(target,context);
    }
    public static void bind(Activity activity) {
        View sourceView = activity.getWindow().getDecorView();
        checkClickListener(activity,sourceView);
    }
    public static void bind(View view) {
        checkClickListener(view,view);
    }
    public static void checkClickListener(Object context,View rootView){
        //获取Activity的class
        Class clazz = context.getClass();
        //获取该类中的所有声明的属性
//        Field[] declaredFields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        //遍历所有属性，找到用@ViewById注解了的属性
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            //获取属性上的注解对象
//            @ViewById(R.id.textView) R.id.textView--value
//            TextView textView;//属性
            OnPreventDoubleClick annotation = method.getAnnotation(OnPreventDoubleClick.class);
            if (annotation != null) {
                int[] viewIds = annotation.value();
//                int[] viewIds = annotation.valueArr();
                //单个
                /*if (viewId != 0) {
                    preventDoubleClickById(context, rootView, method, annotation, viewId);
                }else*/
                if (viewIds != null && viewIds.length > 0) {
                    for (int j = 0; j < viewIds.length; j++) {
                        int id = viewIds[j];
                        preventDoubleClickById(context, rootView, method, annotation, id);
                    }
                }
            }
        }

    }

    private static void preventDoubleClickById(Object context, View rootView, Method method, OnPreventDoubleClick annotation, int viewId) {
        View view = rootView.findViewById(viewId);
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS)   //1秒钟之内只取一个点击事件，防抖操作
                .subscribe(o -> {
                    method.setAccessible(true);// 私有的方法
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    //直接调用无参方法
                    if (parameterTypes == null || parameterTypes.length == 0) {
                        try {
                            method.invoke(context);// 调用无参的方法
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {//有参数则调用有参方法
                        try {
                            method.invoke(context, view);// 调用有参的方法 view 代表当前点击的View
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }
}

package com.zmm.diary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/17
 * Time:下午1:35
 */

public class SharedPreferencesUtil {

    private static SharedPreferences sp;

    private final static String SP_NAME = "config";


    private static final Gson GSON = new Gson();
    private static final Gson gson = new GsonBuilder()
            //序列化null，为null的字段也打印出来
            .serializeNulls()
            // 设置日期时间格式，另有2个重载方法
            // 在序列化和反序化时均生效
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            // 禁此序列化内部类
            //.disableInnerClassSerialization()
            //生成不可执行的Json（多了 )]}' 这4个字符）
            //.generateNonExecutableJson()
            //禁止转义html标签
            //.disableHtmlEscaping()
            //格式化输出
            //.setPrettyPrinting()
            //配合@Expose注解使用，用于设置该字段是否需要序列化和反序列化
            //.excludeFieldsWithoutExposeAnnotation()
            .create();

    /**
     * 保存boolean状态
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value){
        if (sp==null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }


    /**
     * 获取boolean状态
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue){
        if (sp == null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String类型信息
     * @param key
     * @param value
     */
    public static void saveString(String key, String value){
        if (sp==null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }


    /**
     * 获取String类型信息
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue){
        if (sp == null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    /**
     * 保存int类型信息
     * @param key
     * @param value
     */
    public static void saveInt(String key, int value){
        if (sp==null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }


    /**
     * 获取int信息
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue){
        if (sp == null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 保存float类型信息
     * @param key
     * @param value
     */
    public static void saveFloat(String key, float value){
        if (sp==null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key, value).commit();
    }


    /**
     * 获取float信息
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String key, float defValue){
        if (sp == null) {
            sp = UIUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, defValue);
    }


    /**
     * 将Json串反序列化为ArrayList集合
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> readValuesAsArrayList(String json, Type t) {
        try {
            return gson.fromJson(json, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象序列化为Json串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化成对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化成对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type){
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}

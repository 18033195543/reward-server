package com.kdl.common.util.jdkUtil;

import com.google.common.collect.Lists;

import java.util.List;

public final class CollectionUtil {

    public  boolean isNullList(List list){
        return  list==null;
    }
    public  boolean isEmptyList(List list){
        if(!isNullList(list)){
            return  list.isEmpty();
        }
        return  false;
    }
    public boolean isAvailableList(List list){
        return  list!=null && !list.isEmpty();

    }
    public static <T>  List<T> getEmptyList(){
        return Lists.newArrayList();
    }

}

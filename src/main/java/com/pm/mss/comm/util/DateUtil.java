package com.pm.mss.comm.util;

import com.pm.mss.comm.dto.ProductInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DateUtil {

    public static void main(String[] args) {
       /* String a="a";
        String b=a;
        a=null;
        System.out.println( b);
        System.out.println( a);

        List<String> list=new ArrayList<String>();
        list.add("1");
        List<String> list1=list;
        list=null;
        System.out.println( list1.get(0));
        Set<ProductInfo> set = new HashSet<ProductInfo>();
        ProductInfo info=new ProductInfo();
        set.add(info);
        info.setId(2);
        set.remove(info);
        System.out.println( info.hashCode());
        info.setId(111111111);
        System.out.println( info.hashCode());
        set.add(info);
        System.out.println( set.size());

        try {
            System.out.println( JsonUtil.getJson(info).getBytes().length);
            info.setProductId("rrrr");
            System.out.println( ObjectAndByte.toByteArray(info).length);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("中国".length());
        System.out.println("中国".getBytes().length);
    }

}

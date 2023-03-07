package com.atguigu.spark.sparkcore;

import scala.Tuple2;

public class Test {
    public static void main(String[] args) {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("a", 1);
        String s = tuple2._1;
        Integer integer = tuple2._2;
        System.out.println(tuple2);
    }
}

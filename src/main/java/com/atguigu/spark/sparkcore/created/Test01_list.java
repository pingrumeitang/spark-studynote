package com.atguigu.spark.sparkcore.created;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Test01_list {
    public static void main(String[] args) throws InterruptedException {
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        JavaSparkContext jsc  = new JavaSparkContext(sparkConf);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> javaRDD = jsc.parallelize(list);
        List<Integer> collect = javaRDD.collect();
        for (Integer integer : collect) {
            System.out.println(integer);
        }
        System.out.println("=========for lambda==========");
        collect.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
        System.out.println("=============简化 1=======");
       collect.forEach((Integer integer)->{
           System.out.println(integer);
       });
        System.out.println("=============简化 2=======");
        collect.forEach((integer)->{
            System.out.println(integer);
        });
        System.out.println("=============简化 3=======");
        collect.forEach(integer->{
            System.out.println(integer);
        });
        System.out.println("=============简化 4=======");
        collect.forEach(integer-> System.out.println(integer));
        System.out.println("=============简化 5=======");
        collect.forEach(System.out::println);
        System.out.println(javaRDD);
         Thread.sleep(99999);


        jsc.stop();
    }
}

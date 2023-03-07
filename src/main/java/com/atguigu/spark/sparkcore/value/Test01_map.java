package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.List;

public class Test01_map {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        JavaRDD<String> javaRDD = jsc.parallelize(list, 2);
        JavaRDD<String> map = javaRDD.map(new Function<String, String>() {
            @Override
            public String call(String s) throws Exception {
                return s + ",1";
            }
        });
        map.collect().forEach(System.out::println);
        System.out.println("=========lambda========");
        javaRDD
                .map(v1 -> v1 + ",1")
                .collect()
                .forEach(System.out::println);
        System.out.println("==========map方法=============");
        //4.关闭资源
        jsc.stop();
    }
}

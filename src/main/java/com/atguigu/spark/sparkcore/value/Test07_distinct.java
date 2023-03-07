package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test07_distinct {
    public static void main(String[] args) throws InterruptedException {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<String> list = Arrays.asList("A", "B", "C", "D", "A");
        JavaRDD<String> javaRDD = jsc.parallelize(list);
        javaRDD.distinct(3).collect().forEach(System.out::println);
          Thread.sleep(99999);
        //4.关闭资源
        jsc.stop();
    }
}

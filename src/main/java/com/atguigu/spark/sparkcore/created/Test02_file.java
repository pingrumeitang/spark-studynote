package com.atguigu.spark.sparkcore.created;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;
import java.util.function.Consumer;

public class Test02_file {
    public static void main(String[] args) {
         //1.创建Spark Conf
         SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
         //2.创建SparkContext
         JavaSparkContext jsc = new JavaSparkContext(sparkConf);
         //3.编写代码
        JavaRDD<String> lineRDD = jsc.textFile("input/1.txt");
        List<String> list = lineRDD.collect();
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        list.forEach(System.out::println);


        //4.关闭资源
         //jsc.stop();
    }
}

package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test04_mapPartiton {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<String> list = Arrays.asList("A", "B", "C", "D", "A");
        JavaRDD<String> javaRDD = jsc.parallelize(list,2);
        javaRDD.mapPartitions(new FlatMapFunction<Iterator<String>, String>() {
            @Override
            public Iterator<String> call(Iterator<String> stringIterator) throws Exception {
                System.out.println("创建连接");
                while (stringIterator.hasNext()){
                    String next = stringIterator.next();
                    System.out.println("写入"+next);
                }

                System.out.println("关闭连接");
                return stringIterator;
            }
        }).collect();
        //4.关闭资源
        jsc.stop();
    }
}

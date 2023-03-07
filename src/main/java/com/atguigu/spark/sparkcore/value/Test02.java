package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.util.*;

public class Test02 {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<String> list = Arrays.asList("A", "B", "C", "D", "A");
        JavaRDD<String> javaRDD = jsc.parallelize(list);
        JavaRDD<Tuple2> map = javaRDD.map(new Function<String, Tuple2>() {
            @Override
            public Tuple2 call(String s) throws Exception {
                return new Tuple2(s, 1);
            }
        });
        map.collect().forEach(System.out::println);
        System.out.println("========labdma========");
        javaRDD
                .map(v1->new Tuple2(v1,1))
                .collect()
                .forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

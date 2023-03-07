package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Test05_filter {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        JavaRDD<String> javaRDD = jsc.textFile("input/1.txt");
        JavaRDD<String> flatMap = javaRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                String[] split = s.split(" ");
                return Arrays.stream(split).iterator();
            }
        });
        JavaRDD<String> filter = flatMap.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {

                return !"".equals(s) && s != null;
            }
        });
        filter.collect().forEach(System.out::println);
        flatMap.collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

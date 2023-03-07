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

public class Test03_flatmap {
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
        JavaRDD<Tuple2<String, Integer>> map = flatMap.map(new Function<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });
        map.collect().forEach(System.out::println);
        System.out.println("=====化简============");
        JavaRDD<Tuple2<String, Integer>> flatMap1 = javaRDD.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Iterator<Tuple2<String, Integer>> call(String s) throws Exception {
                String[] split = s.split(" ");
                ArrayList<Tuple2<String, Integer>> result = new ArrayList<>();
                for (String s1 : split) {
                    result.add(new Tuple2<>(s1, 1));
                }
                return result.iterator();
            }
        });
        flatMap1.collect().forEach(System.out::println);
        System.out.println("============flatmap lambda========");
        javaRDD.flatMap(v1 -> {
            String[] split = v1.split(" ");
            ArrayList<Tuple2<String, Integer>> result = new ArrayList<>();
            for (String s : split) {
                result.add(new Tuple2<>(s,1));
            }
            return result.iterator();
        })
                .collect()
                .forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

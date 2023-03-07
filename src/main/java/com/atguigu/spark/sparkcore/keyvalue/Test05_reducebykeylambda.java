package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.ArrayList;

public class Test05_reducebykeylambda {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        ArrayList<Tuple2<String,Integer>> list = new ArrayList<>();
        list.add(new Tuple2("A",1));
        list.add(new Tuple2("B",1));
        list.add(new Tuple2("C",1));
        list.add(new Tuple2("D",1));
        list.add(new Tuple2("B",1));
        list.add(new Tuple2("D",1));
        list.add(new Tuple2("C",1));
        list.add(new Tuple2("D",1));
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list);
        pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer sum, Integer elem) throws Exception {
                return sum+ elem;
            }
        }).collect().forEach(System.out::println);
        System.out.println("=====lambda============");
        pairRDD.collect().forEach(System.out::println);
        pairRDD.reduceByKey((Integer sum,Integer elem)->{
            return sum+elem;
        }).collect().forEach(System.out::println);
        System.out.println("=====================");
        pairRDD
                .reduceByKey((sum, elem) -> {
                    return sum + elem;
                }).collect().forEach(System.out::println);
        System.out.println("======================");
        pairRDD
                .reduceByKey((sum,elem)->sum+elem)
                .collect()
                .forEach(System.out::println);
        System.out.println("=====================");
        pairRDD
                .reduceByKey(Integer::sum)
                .collect()
                .forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

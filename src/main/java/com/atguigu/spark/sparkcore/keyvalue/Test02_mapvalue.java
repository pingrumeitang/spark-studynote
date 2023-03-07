package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;

public class Test02_mapvalue {
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
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list);
        pairRDD.mapValues(new Function<Integer, String>() {
            @Override
            public String call(Integer integer) throws Exception {
                return "hello";
            }
        }).collect().forEach(System.out::println);
        pairRDD.mapToPair(new PairFunction<Tuple2<String, Integer>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Integer> v1) throws Exception {
                return new Tuple2<>(v1._1,v1._2*2);
            }
        }).collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

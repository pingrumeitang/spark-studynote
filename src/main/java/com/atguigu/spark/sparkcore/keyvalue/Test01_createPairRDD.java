package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test01_createPairRDD {
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
        List<String> list1 = Arrays.asList("A", "B", "C", "D");
        JavaRDD<String> javaRDD = jsc.parallelize(list1);
        javaRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s,1);
            }
        }).collect().forEach(System.out::println);
        pairRDD.collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

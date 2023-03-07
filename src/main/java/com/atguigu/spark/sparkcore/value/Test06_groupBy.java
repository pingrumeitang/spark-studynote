package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;


import java.util.Arrays;
import java.util.List;

public class Test06_groupBy {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<String> list = Arrays.asList("A", "B", "C", "D", "A","B","A");
        JavaRDD<String> javaRDD = jsc.parallelize(list, 2);
        JavaRDD<Tuple2<String, Integer>> map = javaRDD.map(new Function<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });
        JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> groupBy = map.groupBy(new Function<Tuple2<String, Integer>, String>() {
            @Override
            public String call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return stringIntegerTuple2._1;
            }
        });
        groupBy.collect().forEach(System.out::println);
        JavaRDD<Tuple2<String, Integer>> map1 = groupBy.map(new Function<Tuple2<String, Iterable<Tuple2<String, Integer>>>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Iterable<Tuple2<String, Integer>>> v1) throws Exception {
                int sum = 0;
                for (Tuple2<String, Integer> elem : v1._2) {
                    sum += elem._2;
                }
                return new Tuple2<>(v1._1, sum);
            }
        });
        map1.collect().forEach(System.out::println);
        System.out.println("=========lambda=====");
        javaRDD
                .map(word->new Tuple2(word,1))
                .groupBy(v1->v1._1)
                .map(v1->{
                    int sum = 0;
                    for (Tuple2<String,Integer> tuple2 : v1._2) {
                        sum += tuple2._2;
                    }
                    return new Tuple2(v1._1,sum);
                }).collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

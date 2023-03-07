package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;

public class Test07_avg_reduceBykey {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        ArrayList<Tuple2<String, Integer>> list = new ArrayList<>();
        list.add(new Tuple2<>("A", 10));
        list.add(new Tuple2<>("B", 15));
        list.add(new Tuple2<>("C", 20));
        list.add(new Tuple2<>("A", 20));
        list.add(new Tuple2<>("B", 17));
        list.add(new Tuple2<>("A", 14));
        list.add(new Tuple2<>("C", 30));
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list, 2);
        pairRDD
                .mapValues(new Function<Integer, Tuple2<Integer,Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> call(Integer integer) throws Exception {
                        return new Tuple2<>(integer,1);
                    }
                })
                .reduceByKey(new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> sum, Tuple2<Integer, Integer> elem) throws Exception {
                        return new Tuple2<>(sum._1+elem._1,sum._2+elem._2);
                    }
                }).mapValues(new Function<Tuple2<Integer, Integer>, Double>() {
            @Override
            public Double call(Tuple2<Integer, Integer> v1) throws Exception {
                String format = String.format("%f1", Double.valueOf(v1._1) / v1._2);

                return Double.valueOf(format);
            }
        }).collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

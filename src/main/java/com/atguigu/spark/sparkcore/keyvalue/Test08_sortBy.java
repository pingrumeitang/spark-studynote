package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.eclipse.jetty.util.ArrayUtil;
import scala.Tuple2;

import java.util.ArrayList;

public class Test08_sortBy {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        ArrayList<Tuple2<Integer, Integer>> list = new ArrayList<>();
        list.add(new Tuple2<>(2,1));
        list.add(new Tuple2<>(4,2));
        list.add(new Tuple2<>(3,3));
        list.add(new Tuple2<>(7,4));
        list.add(new Tuple2<>(5,5));
        JavaPairRDD<Integer, Integer> pairRDD = jsc.parallelizePairs(list);
        pairRDD.sortByKey().collect().forEach(System.out::println);
        pairRDD.mapToPair(new PairFunction<Tuple2<Integer, Integer>, Integer, Integer>() {
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1) throws Exception {
                return new Tuple2<>(v1._2,v1._1);
            }
        }).sortByKey().collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

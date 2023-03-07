package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.util.ArrayList;

public class Test03_groupBykey {
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
        list.add(new Tuple2("A",1));
        list.add(new Tuple2("D",1));
        list.add(new Tuple2("D",1));
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list);

        pairRDD.groupBy(new Function<Tuple2<String, Integer>, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Integer> v1) throws Exception {
                return new Tuple2<>(v1._1,v1._2);
            }
        });
        pairRDD.groupByKey().map(v1->{
            int sum =0;
            for (Integer elem : v1._2) {
                sum+=elem;
            }
            return new Tuple2(v1._1,sum);
        }).collect().forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

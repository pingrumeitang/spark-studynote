package com.atguigu.spark.sparkcore.keyvalue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.util.ArrayList;

public class Test06_avg_groupBykey {
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
                .groupByKey()
                .map(new Function<Tuple2<String, Iterable<Integer>>, Tuple2>() {
                    @Override
                    public Tuple2 call(Tuple2<String, Iterable<Integer>> v1) throws Exception {
                        Integer sum=0;
                        Integer count = 0;
                        for (Integer integer : v1._2) {
                            sum += integer;
                            count++;
                        }
                        Double avg = Double.valueOf(sum/count);
                        return new Tuple2(v1._1,avg);
                    }
                })
                .collect()
                .forEach(System.out::println);
        System.out.println("============lambda=============");
        pairRDD
                .groupByKey()
                .map(v1->{
                    Integer sum =0;
                    Integer count =0;
                    for (Integer elem : v1._2) {
                        sum += elem;
                        count ++;
                    }
                    Double avg = Double.valueOf(sum/count);
                    return new Tuple2(v1._1,avg);
                })
                .collect()
                .forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}

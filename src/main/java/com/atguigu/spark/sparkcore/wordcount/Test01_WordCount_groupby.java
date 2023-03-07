package com.atguigu.spark.sparkcore.wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class Test01_WordCount_groupby {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        JavaRDD<String> lineRdd = jsc.textFile("input/1.txt", 2);
        JavaRDD<String> filter = lineRdd.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String line) throws Exception {
                return !"".equals(line) && line != null;
            }
        });
        JavaRDD<String> flatMap = filter.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.stream(line.split(" ")).iterator();
            }
        });

        flatMap.take(10).forEach(System.out::println);
        JavaPairRDD<String, Integer> mapToPair = flatMap.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<>(word, 1);
            }
        });
        mapToPair.take(20).forEach(System.out::println);
        JavaPairRDD<String, Iterable<Integer>> groupByKey = mapToPair.groupByKey();
        groupByKey.take(1).forEach(System.out::println);
        JavaPairRDD<String, Integer> mapValues = groupByKey.mapValues(new Function<Iterable<Integer>, Integer>() {
            @Override
            public Integer call(Iterable<Integer> integers) throws Exception {
                Integer sum = 0;
                for (Integer integer : integers) {
                    sum += integer;
                }
                return sum;
            }
        });
        mapValues.collect().forEach(System.out::println);



        //4.关闭资源
        jsc.stop();
    }
}

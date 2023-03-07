package com.atguigu.spark.sparkStreaming;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Test02_Window_wordcount {
    public static void main(String[] args) throws InterruptedException {
        //创建spark流式环境
        JavaStreamingContext streamingContext = new JavaStreamingContext("local[1]", "sparkStreaming", Durations.seconds(3));
        //创建配置参数
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        hashMap.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        hashMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        hashMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        hashMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        //连接kafka获取数据
        JavaInputDStream<ConsumerRecord<String, String>> topic_dbStream= KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(Arrays.asList("topic_db"), hashMap));
        //开窗统计word
        JavaDStream<String> stringJavaDStream = topic_dbStream.flatMap(new FlatMapFunction<ConsumerRecord<String, String>, String>() {
            @Override
            public Iterator<String> call(ConsumerRecord<String, String> v1) throws Exception {
                String[] split = v1.value().split(" ");
                List<String> list = Arrays.asList(split);
                return list.iterator();
            }
        });
        JavaPairDStream<String, Integer> stringIntegerJavaPairDStream = stringJavaDStream.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });
        JavaPairDStream<String, Integer> window = stringIntegerJavaPairDStream.window(Durations.seconds(12), Durations.seconds(6));
        window.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1+v2;
            }
        }).print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}

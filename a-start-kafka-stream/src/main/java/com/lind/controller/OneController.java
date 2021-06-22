package com.lind.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

@RestController
public class OneController {
    static final String version = "16";
    static final String TEST_IN = "test-stream-in" + version;
    static final String TEST_OUT_END = "test-stream-out-end" + version;
    static final String TEST_OUT_WORD = "test-stream-out-word" + version;
    private static final String loremIpsum = "lind zzl lr bobo";
    private static final String[] hashTags = {"latin", "italy", "roman", "caesar", "cicero"};
    private KafkaStreams streams1;
    private Random randomNumber = new Random();
    private String randomMessage;

    /**
     * 根据提供的谓词将KStream分支（或拆分）为一个或多个KStream实例.
     */
    @GetMapping("/branch")
    public void testBranch() {
        Properties props = getStreamProperties();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream(TEST_IN);
        KStream<String, String>[] branches = kstream.branch(
                // 第一个谓词
                (key, value) -> value.endsWith("y"),
                // 第二个谓词
                (key, value) -> value.endsWith("o")
        );

        for (KStream<String, String> stream : branches) {
            stream.foreach(new ForeachAction<String, String>() {
                @Override
                public void apply(String key, String value) {
                    System.out.println(key + " => " + value);
                }
            });
        }

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    /**
     * 过滤并打印以o结尾的消息
     */
    @GetMapping("/filter")
    public void filter() {
        Properties props = getStreamProperties();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream(TEST_IN);
        kstream.filter(new Predicate<String, String>() {
            @Override
            public boolean test(String key, String value) {
                return value.endsWith("o");
            }
        }).foreach(new ForeachAction<String, String>() {
            @Override
            public void apply(String key, String value) {
                System.out.println(key + " => " + value);
            }
        });

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

    }

    /**
     * FlatMap,取得一条记录并由此产生零个，一个或多个记录，同时还可以修改记录键和值，包括类型.
     */
    @GetMapping("/flatMap")
    public void flatMap() {
        Properties props = getStreamProperties();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream(TEST_IN);
        KStream<String, Integer> transformed = kstream.flatMap(
                (key, value) -> {
                    List<KeyValue<String, Integer>> result = new LinkedList<>();
                    result.add(KeyValue.pair(value.toUpperCase(), 1000));
                    result.add(KeyValue.pair(value.toLowerCase(), 9000));
                    return result;
                }
        );

        transformed.foreach((key, value) -> System.out.println(key + " => " + value));
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


    }

    /**
     * groupByKey按键进行分组
     */
    @GetMapping("/group")
    public void group() {
        Properties props = getStreamProperties();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream(TEST_IN);
        KGroupedStream<String, String> groupedStream = kstream.groupByKey(
                Grouped.with(Serdes.String(), Serdes.String())
        );

        groupedStream.count().toStream().foreach((key, value) -> System.out.println(key + " => " + value));
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


    }

    /**
     * 对以#latin结尾的消息，进行发布到其它队列.
     */
    @GetMapping("/startProcessing1")
    public void startProcessing1() {
        Properties props = getStreamProperties();
        final StreamsBuilder builder = new StreamsBuilder();
        //魔术关键行,以#latin结尾的发到另一个队列
        builder.stream(TEST_IN).filter((key, value) -> ((String) value).endsWith("latin")).to(TEST_OUT_END);
        streams1 = new KafkaStreams(builder.build(), props);
        streams1.start();
    }

    /**
     * 对单词进行记数，结果发布到word-total队列.
     */
    @GetMapping("/wordCount")
    public void wordCount() {
        Properties props = getStreamProperties();
        final StreamsBuilder builder = new StreamsBuilder();
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        //魔术关键行,以#latin结尾的发到另一个队列
        KStream<String, String> textLines = builder.stream(TEST_IN, Consumed.with(stringSerde, stringSerde));
        textLines.flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .selectKey((key, word) -> word)
                .groupByKey()
                .count()
                .toStream()
                .to(TEST_OUT_WORD, Produced.with(stringSerde, longSerde));
        textLines.flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .selectKey((key, word) -> word)
                .groupByKey()
                .count()
                .toStream().foreach((key, value) -> System.out.println(key + " => " + value));
        streams1 = new KafkaStreams(builder.build(), props);
        streams1.start();
    }

    private Properties getStreamProperties() {
        Properties props = new Properties();
        props.put(APPLICATION_ID_CONFIG, "streams-word-count");
        props.put(BOOTSTRAP_SERVERS_CONFIG, "192.168.4.26:9092");
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put("metrics.sample.window.ms", 15000);
        props.put("poll.ms", 10);
        return props;
    }

    /**
     * 为了从主题中读取无限制的数据流，我们需要创建一个小型应用程序，以发送无限制的数据流。我们模拟一条Tweet流，
     * 在Tweet末尾恰好有一个标签。每秒都会在该主题上发布一条消息。Tweets始终包含相同的消息（Lorem ipsum…），
     * 主题标签是从5个主题标签的固定列表中随机选择的。
     */
    @GetMapping("/sendMessages")
    public void sendMessages() {
// create instance for properties to access producer configs
        Properties props = new Properties();

        // Assign localhost id, 参考http://kafka.apache.org/documentation/#producerapi
        props.put("bootstrap.servers", "192.168.4.26:9092");
        // Set acknowledgements for producer requests.
        props.put("acks", "1");
        // If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        // Specify buffer size in config
        props.put("batch.size", 16384);
        // Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        // The buffer.memory controls the total amount of memory available to the
        // producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        try {
            while (true) {
                // Every second send a message
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                String label = hashTags[randomNumber.nextInt(hashTags.length)];
                randomMessage = loremIpsum + "#" + label;
                System.out.println("send message:" + randomMessage);
                producer.send(new ProducerRecord<String, String>(TEST_IN, label, randomMessage));
            }
        } finally {
            producer.close();
        }

    }

}

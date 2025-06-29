package com.tweets.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.tweets.config.AwsSecretsManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaProducerConfig {

	@Autowired
	private AwsSecretsManager awsSecretsManager;
	
	@Autowired
	private Environment env;

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		String host = getKafkaHost();
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	private String getKafkaHost() {
		String host = env.getProperty("kafka.host");

		String secretJson = awsSecretsManager.getSecret("aws.kafka.secret.name");

		if (secretJson != null) {
			host = parseJson(secretJson, "host");
		}

		return host;
	}

	private String parseJson(String json, String key) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.has(key) ? jsonObject.getString(key) : null;
		} catch (Exception e) {
			log.error("Error while parsing json {}", json, e);
			return null;
		}
	}
}

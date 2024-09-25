package com.techwhisky.springboot.kafka.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;



@Configuration
public class AppConfig {

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.emailtopic.name}")
	private String emailTopicName;
	
	@Value("${kafka.emailtopic.partitions}")
	private int emailTopicPartitions;
	
	@Value("${kafka.emailtopic.replications}")
	private int emailTopicReplications;
	
	@Value("${kafka.smstopic.name}")
	private String smsTopicName;
	
	@Value("${kafka.smstopic.partitions}")
	private int smsTopicPartitions;
	
	@Value("${kafka.smstopic.replications}")
	private int smsTopicReplications;
	
	@Value("${kafka.whatsapptopic.name}")
	private String whatsappTopicName;
	
	@Value("${kafka.whatsapptopic.partitions}")
	private int whatsappTopicPartitions;
	
	@Value("${kafka.whatsapptopic.replications}")
	private int whatsappTopicReplications;
	
	
	

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(configs);
	}

	@Bean(name = "emailTopic")
	public NewTopic emailTopic() {
		//3 is the partition, 1 is replication factor
		return new NewTopic(emailTopicName, emailTopicPartitions, (short) emailTopicReplications);
	}
	
	@Bean(name = "smsTopic")
	public NewTopic smsTopic() {
		//3 is the partition, 1 is replication factor
		return new NewTopic(smsTopicName, smsTopicPartitions, (short) smsTopicReplications);
	}
	
	@Bean(name = "whatsappTopic")
	public NewTopic whatsappTopic() {
		//3 is the partition, 1 is replication factor
		return new NewTopic(whatsappTopicName, whatsappTopicPartitions, (short) whatsappTopicReplications);
	}

	@Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrapServers);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

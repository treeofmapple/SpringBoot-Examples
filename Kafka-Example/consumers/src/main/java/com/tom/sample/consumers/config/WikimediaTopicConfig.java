package com.tom.sample.consumers.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class WikimediaTopicConfig 
{

	@Bean
	NewTopic wikimediaStreamTopic() 
	{
		return TopicBuilder
				.name("wikimedia-stream")
				.build();
	}
	
}

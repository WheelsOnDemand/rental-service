package com.rental_service.app.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.rental_service.app.kafka.events.RentalCreatedEvent;
import com.rental_service.app.kafka.events.RentalUpdatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalProducer { 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalProducer.class); 

	private KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate1;
	private KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate2;
	public void sendMessage(RentalCreatedEvent rentalCreatedEvent) { 
		LOGGER.info(String.format("Rental created event => %s", rentalCreatedEvent.toString()));

		Message<RentalCreatedEvent> message = MessageBuilder.withPayload(rentalCreatedEvent)
				.setHeader(KafkaHeaders.TOPIC,"rental-created").build();

		kafkaTemplate1.send(message);
	}
	public void sendMessage(RentalUpdatedEvent rentalUpdatedEvent) {
		LOGGER.info(String.format("Rental updated event => %s", rentalUpdatedEvent.toString()));
		
		Message<RentalUpdatedEvent> message = MessageBuilder
				.withPayload(rentalUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, "rental-updated").build();
		
		kafkaTemplate2.send(message);
	}
	
	
	

}
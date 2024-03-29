//package real.world.data.pipelines.service;
//
//import lombok.AllArgsConstructor;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlowAdapter;
//import org.springframework.integration.dsl.IntegrationFlowDefinition;
//import org.springframework.integration.dsl.Transformers;
//import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
//import org.springframework.integration.http.dsl.Http;
//import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.GenericMessage;
//import org.springframework.stereotype.Component;
//import reactor.kafka.spring.integration.samples.channel.adapters.outbound.ReactiveSolrMessageHandler;
//import real.world.data.pipelines.model.ExampleData;
//import software.amazon.awssdk.services.s3.S3AsyncClient;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Component
//@AllArgsConstructor
//public class FlowBuilder extends IntegrationFlowAdapter {
//    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;
//    public S3AsyncClient s3client;
//    public String bucketName = "fajifjaie";
//
//    @Override
//    protected IntegrationFlowDefinition<?> buildFlow() {
//        Function<String, String> mapper = String::toUpperCase;
//
////        TaskExecutor taskExecutor = new TaskExecutorAdapter()
//        return from(reactiveKafkaConsumerTemplate.receiveAutoAck()
//                .map(GenericMessage::new))
//                .<ConsumerRecord<String, String>, String>transform(ConsumerRecord::value)
//                .transform(Transformers.fromJson(ExampleData.class))
//                .enrich(enricher -> enricher
//                        .<Map<String, ?>>requestPayload(message ->
//                                ((List<?>) message.getPayload().get("attributeIds"))
//                                        .stream()
//                                        .map(Object::toString)
//                                        .collect(Collectors.joining(",")))
//                        .requestSubFlow(subFlow ->
//                                subFlow.handle(
//                                        Http.outboundGateway("/attributes?id={ids}", restTemplate)
//                                                .httpMethod(HttpMethod.GET)
//                                                .expectedResponseType(Map.class)
//                                                .uriVariable("ids", "payload")))
//                        .propertyExpression("attributes", "payload.attributes"));
////                .fluxTransform(messageFlux -> messageFlux.map(mapper))
////                .<Message, String>transform(message -> message.getHeaders().getId())
////                .<String, String>transform(String::toUpperCase)
////                .publishSubscribeChannel(s -> s
////                        .subscribe(f -> f
////                                .handle(new ReactiveS3MessageHandler(s3client, new LiteralExpression(bucketName))))
////                        .subscribe(f -> f
////                                .handle(new ReactiveSolrMessageHandler()))
////                );
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "inputChannel")
//    public AbstractReplyProducingMessageHandler fileWritingMessageHandler() {
//        AbstractReplyProducingMessageHandler mh = new AbstractReplyProducingMessageHandler() {
//            @Override
//            protected Object handleRequestMessage(Message<?> message) {
//                String payload = (String) message.getPayload();
//                return "Message Payload : ".concat(payload);
//            }
//        };
//        mh.setOutputChannelName("outputChannel");
//        return mh;
//    }
//
//    private IntegrationFlow bulkWriteToPulsar() {
//        return null;
//    }
//
//    private IntegrationFlow bulkWriteToCockroach() {
//        return null;
//    }
//}

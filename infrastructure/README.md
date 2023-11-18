Сначала запускаем зукипер, а потом кластер кафка.
Потому что кластер кафка проверяет работоспособность зукипера при запуске

1) docker-compose -f common.yml -f zookeeper.yml up
2) docker-compose -f common.yml -f kafka_cluster.yml up
3) docker-compose -f common.yml -f init_kafka.yml up



c:
cd C:\kafka_2.13-3.6.0

bin/windows/zookeeper-server-start.bat config/zookeeper.properties

# Start the Kafka broker service
bin/windows/kafka-server-start.bat config/server.properties

Посмотреть сообщение из топика data-temperature
bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic data-temperature --from-beginning


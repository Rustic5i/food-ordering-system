Сначала запускаем зукипер, а потом кластер кафка.
Потому что кластер кафка проверяет работоспособность зукипера при запуске

1) docker-compose -f common.yml -f zookeeper.yml up
2) docker-compose -f common.yml -f kafka_cluster.yml up
3) docker-compose -f common.yml -f init_kafka.yml up

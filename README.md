Kafka Rest API

[![Test](https://github.com/HubertTatar/kafka-ra/actions/workflows/test.yml/badge.svg)](https://github.com/HubertTatar/kafka-ra/actions/workflows/test.yml)

Prerequisites:
 - docker-compose version 1.29.2

Run:
 - run `docker-compose up` in `env` dir to setup environment 
 - add jvm flag `--add-opens java.base/java.util.concurrent=ALL-UNNAMED` - [Understanding Runtime Access Warnings](https://docs.oracle.com/javase/9/migrate/toc.htm#JSMIG-GUID-7BB28E4D-99B3-4078-BDC4-FC24180CE82B) 


Links:
 - https://www.confluent.io/blog/kafka-listeners-explained/
 - https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/


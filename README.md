# Become a Local Development Jedi with KIND and Strimzi

This repository contains all of the code from my 2025 StrimziCon presentation, including:

* Scripts to create and set up the KIND cluster and Docker Image Registry.
* Yaml files to deploy a Kafka cluster.
* Scripts to access the Kafka cluster from the local terminal.
* Yaml files to deploy a LittleHorse cluster.
* Yaml files to deploy a `KafkaConnect` cluster with Strimzi that includes the LittleHorse Kafka Connector.
* Code needed to create + register a `WfSpec` in the LittleHorse Cluster.
* Yaml files to deploy a `KafkaConnector` that triggers the LittleHorse workflow.

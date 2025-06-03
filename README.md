# Become a Local Development Jedi with KIND and Strimzi

This repository contains all of the code from my 2025 StrimziCon presentation, including:

* Scripts to create and set up the KIND cluster and Docker Image Registry.
* Yaml files to deploy a Kafka cluster.
* Scripts to access the Kafka cluster from the local terminal.
* Yaml files to deploy a LittleHorse cluster.
* Yaml files to deploy a `KafkaConnect` cluster with Strimzi that includes the LittleHorse Kafka Connector.
* Code needed to create + register a `WfSpec` in the LittleHorse Cluster.
* Yaml files to deploy a `KafkaConnector` that triggers the LittleHorse workflow.

## Running the Example

For an in-depth breakdown, go ahead and watch the recording of the session. Or follow along here:

### Cluster Setup

Put the following into your `/etc/hosts` file.

```
# Access Kafka
127.0.0.1 kafka-bootstrap.strimzicon.demo
127.0.0.1 kafka-0.strimzicon.demo
127.0.0.1 kafka-1.strimzicon.demo
127.0.0.1 kafka-2.strimzicon.demo

# Access LH Server
127.0.0.1 lh.strimzicon.demo

# Access LH Dashboard
127.0.0.1 lh-dashboard.strimzicon.demo
```

Next, create the KIND cluster and deploy Strimzi, Kafka, Envoy Gateway, and LittleHorse:

```
./setup.sh
```

You should see the following pods:

```
->kgp -w
NAME                                          READY   STATUS    RESTARTS   AGE
cert-manager-5c66ff4789-8rcr2                 1/1     Running   0          18m
cert-manager-cainjector-7f47fb946-5h6rg       1/1     Running   0          18m
cert-manager-webhook-bbd99d8c6-74lnj          1/1     Running   0          18m
lh-strimzicon-dashboard-865988dc9c-fdmkc      1/1     Running   0          30s
lh-strimzicon-server                          1/1     Running   0          30s
strimzi-cluster-operator-6f4fc4667c-cfnn7     1/1     Running   0          19m
strimzicon-entity-operator-7c59b6cf4f-7dbm2   2/2     Running   0          17m
strimzicon-strimzicon-0                       1/1     Running   0          18m
strimzicon-strimzicon-1                       1/1     Running   0          18m
strimzicon-strimzicon-2                       1/1     Running   0          18m
```

The `strimzicon-strimzicon` pods are the Kafka cluster, managed by Strimzi!

Lastly, you should be able to access the LittleHorse Dashboard at `http://lh-dashboard.strimzicon.demo` in your browser.

### Producing to Kafka

### Deploying the Kafka Connector


## Is There an Easier Way?

You bet! LittleHorse Enterprises LLC provides our customers with a Kubernetes Operator that does a few things:
* Run production-ready LittleHorse clusters
  * Automatically encrypt communication between LH Server instances.
  * Configure persistent storage.
  * Encrypt and authenticate communication between LittleHorse and Kafka.
  * Configure `Ingress` or `TLSRoute` resources to allow north-south traffic to LittleHorse.
  * Manage LittleHorse `Tenant`s and `Principal`s.
  * Handle authentication through OIDC or MTLS.
* Automate the loading of plugins into `KafkaConnect` clusters at Strimzi
  * The `LHKafka` CRD wraps `Kafka`, `KafkaNodePool`, `KafkaRebalance`, and `KafkaConnect`.
  * The `LHKafkaConnector` CRD automatically updates the plugins in the Kafka Connect cluster for the `LHKafka`.
* Run and administer Keycloak securely.

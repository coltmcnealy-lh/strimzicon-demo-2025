#!/bin/bash

set -ex
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)

# Create a KIND cluster, which is just a way of creating a Kubernetes cluster where each
# node is just a docker container running locally. For simplicity, we will create a
# single-node KIND cluster. However, this works the same with multiple nodes too.
kind create cluster --name strimzi-local-dev --config "${SCRIPT_DIR}/kind-config.yaml"
kubectl config use-context kind-strimzi-local-dev

# Basic Cluster Setup
kubectl create namespace strimzicon
kubectl config set-context --current --namespace strimzicon

# Run the Docker Image Registry as a native docker container.
#
# MacOS somehow thinks it's okay to use port 5000 for system-related stuff, so we will
# use port 5001.
docker run --rm -d -p 5001:5000 --name kind-registry registry:2

# Connect the KIND network to the Docker Image Registry.
docker network connect kind kind-registry

# Install Strimzi
helm upgrade --install strimzi oci://quay.io/strimzi-helm/strimzi-kafka-operator \
    --version 0.46.0 \
    --namespace strimzicon

# Install Cert Manager
helm upgrade --install cert-manager jetstack/cert-manager \
    --namespace strimzicon \
    --version v1.15.1 \
    --set installCRDs=true

# Install Envoy Gateway
helm upgrade --install envoygateway oci://docker.io/envoyproxy/gateway-helm \
    --version v1.4.0 \
    --namespace envoy-gateway-system \
    --create-namespace

# Set up some stuff
kubectl apply -f ${SCRIPT_DIR}/envoy-gateway-setup.yaml
kubectl apply -f ${SCRIPT_DIR}/kafka.yaml
kubectl apply -f ${SCRIPT_DIR}/user-and-topic.yaml

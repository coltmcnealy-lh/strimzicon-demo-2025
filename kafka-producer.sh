#!/bin/bash

# Citation: I stole this from a blog post, but I think y'all will recognize the author:
# https://strimzi.io/blog/2024/08/16/accessing-kafka-with-gateway-api/

docker run -it --rm --network host \
    -v /tmp/:/tmp/ quay.io/strimzi/kafka:0.46.0-kafka-4.0.0 \
    bin/kafka-console-consumer.sh \
    --bootstrap-server kafka-bootstrap.strimzicon.demo:9092 \
    --topic customer-arrivals \
    --from-beginning \
    --consumer.config /tmp/kafka-client-config.properties

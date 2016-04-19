#!/bin/bash

dcos spark run --submit-args='--class de.codecentric.dcos_intro.spark.SparkJob https://s3.eu-central-1.amazonaws.com/dcos-intro/dcos-intro-assembly-1.0.jar topic node-0.cassandra.mesos 9042 broker-0.kafka.mesos:9092'
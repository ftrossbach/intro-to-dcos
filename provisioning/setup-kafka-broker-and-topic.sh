#!/bin/bash

dcos kafka broker add 0..2 --port 9092
dcos kafka broker start 0..2
dcos kafka topic add topic --replicas 3 --partitions 10
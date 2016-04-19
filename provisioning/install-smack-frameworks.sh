#!/bin/bash

dcos package install --package-version=0.9.4.0 kafka --yes
dcos package install cassandra  --yes
dcos package install spark  --yes
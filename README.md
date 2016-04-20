# Setting up the SMACK stack and a sample app with DC/OS

## Prerequesites

- install Vagrant and Virtualbox
- install Ansible
- get an an AWS Account. The AWS resources used in this example exceed the free tier, regular AWS fees apply. You have been warned!
- Create an EC2 key pair called dcos-intro
- A Twitter access key

## Environment variables

Export
- DCOS_INTRO_AWS_REGION, e.g. with "eu-central-1"
- DCOS_INTRO_AWS_ACCESS_KEY, your AWS access key
- DCOS_INTRO_AWS_SECRET_KEY, your AWS secret key



## Setup SMACK Frameworks

- SSH into Vagrant box (vagrant ssh)
- call /vagrant/provisioning/install-smack-frameworks.sh

## Wait
- Wait in Marathon (http://<master_load_balancer>/marathon) until all apps are healthy

## Setup Kafka brokers and topics
- call /vagrant/provisioning/setup-kafka-broker-and-topic.sh

## Setup Cassandra keyspace and schema
- SSH into the master node on AWS
- Start cqlsh with ``docker run -ti cassandra:2.2.5 cqlsh node-0.cassandra.mesos``
- Execute the two lines from /vagrant/provisioning/cassandra.cql

## Start ingestion job
- edit /vagrant/provisioning/ingestion.json to add your twitter credentials
- Call /vagrant/provisioning/deploy-ingestion.sh

## Start digestion job
- Call /vagrant/provisioning/deploy-digestion.sh


## Build Fat Jar for example
- cd into "example"
- call ``sbt assembly``
- Fat Jar will contain both Spark job and ingestion job. You would not have this normally, this is a simplification for
  the sake of the example

## Create Docker container
- call ``sbt docker``
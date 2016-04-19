CREATE KEYSPACE IF NOT EXISTS dcos WITH replication = {'class': 'NetworkTopologyStrategy', 'dc1': '3'};
CREATE TABLE IF NOT EXISTS dcos.tweets(date timestamp PRIMARY KEY,text text);
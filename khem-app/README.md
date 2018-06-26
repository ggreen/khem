# Overview

The original is an example PCF/PCC test instance of the application.

[https://khemapp.pcfbeta.io/](https://khemapp.pcfbeta.io/)

## Building

Adding gradle.properties

Example configuration for gemfire-release-repo
This is example configuration which you will need to place into your build.gradle


To access these artifacts, you must add an entry to gradle.properties

    gemfireReleaseRepoUser=user
    gemfireReleaseRepoPassword=Replace with your decrypted password (which we don't have)

## Installation

GemFire

*local only*

	gfsh>start locator --name=locator
	gfsh>start server --name=server1

	gfsh>create region --name=molecules --type=PARTITION_HEAP_LRU

### On PCF

Use the following the push the initial application

	cf push khemApp -p build/libs/khem-app-0.0.1-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git


### PCC setup

	cf create-service p-cloudcache dev-plan pcc

	cf create-service p-cloudcache dev-plan pccHttp -t session-replication

	cf create-service-key pcc khemKey


**Get the key**

	cf service-key pcc khemKey



**Bind application to PCC**

	cf bind-service khemApp pcc


Example output


	{
	 "distributed_system_id": "0",
	 "locators": [
	  "192.168.13.21[55221]"
	 ],
	 "urls": {
	  "gfsh": "https://cloudcache-57689bd0-d810-40cd-9d79-00c1b5184288.run.pcfbeta.io/gemfire/v1",
	  "pulse": "https://cloudcache-57689bd0-d810-40cd-9d79-00c1b5184288.run.pcfbeta.io/pulse"
	 },
	 "users": [
	  {
	   "password": "fUaCtq3h3v570ZNsbbsA",
	   "roles": [
	    "cluster_operator"
	   ],
	   "username": "c_operator_w0wvdtNv33ayqr4zYUKNtw"
	  },
	  {
	   "password": "sSebzykwcoGKzqnJO2aeeA",
	   "roles": [
	    "developer"
	   ],
	   "username": "d_u9PjAK3H0ekNNhdj8ikZew"
	  }
	 ],
	 "wan": {
	  "sender_credentials": {
	   "active": {
	    "password": "OG286PFDgLl4u873O0cw",
	    "username": "g_s_S62kqzpjcdUUZIq0NfTYQ"
	   }
	  }
	 }
	}


## Local GemFire


**Connect to gfsh**

	gfsh>connect --use-http=yes --url=https://cloudcache-57689bd0-d810-40cd-9d79-00c1b5184288.run.pcfbeta.io/gemfire/v1


Configure GemFire connection

	cf set-env khemApp LOCATOR_HOST host
	cf set-env khemApp LOCATOR_PORT port
	cf set-env khemApp SECURITY_USER user
	cf set-env khemApp SECURITY_PASSWORD password



# Useful PCF Commands

Key PCC

	cf delete-service-key pcc khemKey
	cf unbind-service khemApp pcc
	cf delete-service pcc

# Building



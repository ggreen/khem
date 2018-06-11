# PDE GemFire/PCC Demo
## Knowledge Handling Every Molecule

This a demo to uses PCF and Pivotal Cloud Cache.

This example will store some objects in a cache. It allows users
to build information knowledge managed in these object.


This particular application is based on a pharma life-science based use case.
The objects, in this case, are molecules. The molecule object attributes will
be populated using a web-based scientific molecule editor called JSDraw.
A user will be able to store molecule attributes in the cache.

The molecule has attributes such as:

- *SMILES* - a calculated string representation of the molecule structure in a shortened form.
- *Molfile* - a calculated verbose representation of the molecule that holds additional information.
- Weight - a calculated measure of the sum of the atomic heaviness
- Formula - a calculated expression of the molecule relationships or rules in symbols
- Source - a user-entered field to associate the molecule, for example, a manufacturer or scientist.
- Name - user-entered label for the molecule such as the related experiment or project

## GemFire

The cache implementation is Pivotal GemFire. GemFire is an In-Memory Data Grid (IMDG).
GemFire's two main components are a locator and cache server (a.k.a data node).
The client connects to a locator to find its data.  

All data is stored on the cache server. Cache servers/data node register with locators
to be discovered by clients or other data nodes. The number of data node can be
scaled up to handle increased data or clients.

Data is managed in a region. It is similar to a table in a traditional
relational database. Each region can have a different data policy. A replicated region data policy stores a copy of entries on each data node. Data is shared in a partition region
data policy so that each data node only stores pieces of the primary values in entries and or a
configured number of backup copies.

GemFire supports NO SQL operations to get region entry objects very quickly by a key. It supports SQL like
queries through its object query language.  With QOL you can select objects by particular
attribute in a where clause. Its support simple and complex queries (nested queries).

For a "molecules" region, QOL allows you to perform a search such as

    select * from /molecules where smile = 'C'

    select * from /molecules where formula = 'H20' or (weight > 18.0 and weight < 20)



GemFire supports other features such as events listeners (similar to database triggers), joins, transactions, functions (similar to stored procedures), full-text searches and more. The GemFire use case tends to be based on FAST data access patterns. See (https://pivotal.io/pivotal-gemfire)[https://pivotal.io/pivotal-gemfire] for more details on GemFire.

## Pivotal Cloud Foundry (PCF)

The demo implementation of this application is deployed on Pivotal implement of Cloud
Foundry, named Pivotal Cloud Foundry. Cloud Foundry is an opinionated cloud-native
platform that forces implement to maintain the 12-factor based principals.
See (https://12factor.net/)[https://12factor.net/] for details.

## Pivotal Cloud Cache (PCC)

The PCF applications are stateless. Any information that is cached or stateful is
managed in a backing service. GemFire implementation of the PCF backing service
is called Pivotal Cloud Cache (PCC).

PCC allows the user to quickly provision multiple environment instances of GemFire.
An instance can be based on a select plan that dictates the size of the cache.
Complex environments can be quickly created with simple commands
(Ex: cf create-service p-cloudcache <plan> <name>).

You access also bind PCC instance to an application so that the connections and
credentials details are securely provided to consuming applications.


Sub Project

The idea of this demo application is to allow scientist to determine similar molecule based
on SMILES, formulas, weights and etc.

- [khem-app](https://github.com/ggreen/khem/tree/master/khem-app)

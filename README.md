# PDE GemFire/PCC Demo
## Knowledge Handling Every Molecule

This document provides an introduction to [Pivotal GemFire](https://pivotal.io/pivotal-gemfire)'s [Pivotal Cloud Cache](https://docs.pivotal.io/p-cloud-cache/1-0/index.html)(PCC)
implementation within Pivotal Cloud Foundry (PCF).

This document outlines how application can store some objects in a cache. It can allow users
to build information knowledge managed in these object.

The [KHEM](https://khemapp.pcfbeta.io/) demo application is based on a pharma life-science based use case.
The objects, in this case, are molecules. The molecule object attributes will
be populated using a web-based scientific molecule editor called JSDraw.

![app](https://github.com/ggreen/khem/blob/master/docs/app.png )


A user can store molecule attributes in the cache. The molecule object has attributes such as:

![molecule](https://github.com/ggreen/khem/blob/master/docs/molecule.png)

- *SMILES* - a calculated string representation of the molecule structure in a shortened form.
- *Molfile* - a calculated verbose representation of the molecule that holds additional information.
- *Weight* - a calculated measure of the sum of the atomic heaviness
- *Formula* - a calculated expression of the molecule relationships or rules in symbols
- *Source* - a user-entered field to associate the molecule, for example, a manufacturer or scientist.
- *Name* - user-entered label for the molecule such as the related experiment or project

## GemFire

The cache implementation is [Pivotal GemFire](https://pivotal.io/pivotal-gemfire). GemFire is an In-Memory Data Grid (IMDG).
GemFire's two main components are a locator and cache server (a.k.a data node).
The client connects to a locator to find its data.  

All data is stored on the cache server. Cache servers/data nodes register with locators
to be discovered by clients or other data nodes. The number of data nodes can be
scaled up to handle increased data or clients.

### GemFire Regions

Data is managed in a [region](http://gemfire.docs.pivotal.io/93/geode/basic_config/data_regions/chapter_overview.html). It is similar to a table in a traditional
relational database. Each region can have a different data policy. A replicated region data policy stores a copy of entries on each data node. Data is shared in a partition region
data policy so that each data node only stores pieces of the primary values in entries and or a
configured number of backup copies.

### Data Access

GemFire supports NO SQL operations to get region entry objects very quickly by a key.
Regions are based on key/value pairs.

    Region<String, Molecule> molecules

Users can store entry in a region using a "put" operation.

    molecules.put(key,molecule);


Users can retrieve an entry object directly from a region "get" operation by providing the key identifier.
See the coding example below.



    Molecule findMolecule(String key)
    {
        return molecules.get(key)
    }

It supports SQL like
queries through its Object Query Language (OQL).  With [QOL](http://gemfire.docs.pivotal.io/93/geode/developing/querying_basics/chapter_overview.html) you can select objects by a particular
attribute in a where clause.

For a "molecules" region, QOL would allow you to perform a search such as

    select * from /molecules where smile = 'C'

    select * from /molecules where formula = 'H20' or (weight > 18.0 and weight < 20)


It supports simple and complex queries (nested queries).


### Other Features

GemFire supports other features such as events listeners (similar to database triggers), joins, transactions, functions (similar to stored procedures), full-text searches and more. The GemFire use case tends to be based on FAST data access patterns. See [https://pivotal.io/pivotal-gemfire](https://pivotal.io/pivotal-gemfire) for more details on GemFire.

## Pivotal Cloud Foundry (PCF)

The demo implementation of this application is deployed on Pivotal implement of Cloud
Foundry, named Pivotal Cloud Foundry. Cloud Foundry is an opinionated cloud-native
platform that forces implement to maintain the 12-factor based principals.
See [https://12factor.net/](https://12factor.net/) for details.


## Pivotal Cloud Cache (PCC)

PCF allows users to manually or auto scale application instances to meet increase
capacity demands. Cloud native applications are stateless. Any information that is stateful
must be available to all application instances.  Cached state information can be
managed in a backing service. GemFire implementation of a caching backing service
is called [Pivotal Cloud Cache](https://docs.pivotal.io/p-cloud-cache/1-0/index.html) (PCC).

PCC allows the user to quickly provision multiple environment instances of GemFire.
An instance can be based on a desired plan that dictates the number of locators/data nodes and the size of the cache.
Complex environments can be quickly created with simple commands

Example command to create a PCC instance

    cf create-service p-cloudcache <plan> <name>


You can bind PCC service instances to  application instances so that the connections and
credentials details are securely provided to consuming applications.

    cf bind-service <app> <name>

# Sub Project

The idea of this demo application is to allow scientist to determine similar molecule based
on SMILES, formulas, weights and etc.

- [khem-app](https://github.com/ggreen/khem/tree/master/khem-app)

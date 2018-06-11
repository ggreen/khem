# PDE GemFire/PCC DEmo
## Knowledge Handling Every Molecule (KHEM)

This a demo to using PCF and Pivotal Cloud Cache.

This example will store some objects into a cache. It allow user
to build information managed in these object.


This particular application is based on a pharma life science based use case.
The objects in this case are molecules. The molecule object attributes will
be populated using a web based scientific molecule editor called JSDraw.
User will be able to store molecule attributes in the cache.

Molecule has attributes such as

- *SMILES* - calculated string representation of the molecule structure in a shortened form.
- *Molfile* - calculated verbose representation of the molecule also holds additional (not structure) information.
- Weight - calculated measure of the sum of the atomic heaviness
- Formula - calculated expression of the molecule relationships or rules in symbols
- Source - user entered associate the molecule for example a manufacture or scientist.
- Name - user entered label for the molecule such as the related experiment or project

## GemFire

The cache implementation is Pivotal GemFire. GemFire is an In Memory Data Grid (IMDG).
It supports NO SQL operations to get object very quickly by a key. It support SQL like
queries through it's object query language. With OQL you can select object by particular
attribute in a where cause. It support simple and complex queries (nested queries).


OQL allows you to perform search such as

    select * from /molecules where smile = 'C'

    select * from /molecules where formula = 'H20' or (weight > 18.0 and weight < 20)


GemFire supports other features such as events listeners (similar to database triggers), joins, transactions, functions (similar to stored procedures) , full text searches and more. The GemFire use cause
tend to be based on FAST data access patterns. See (https://pivotal.io/pivotal-gemfire)[https://pivotal.io/pivotal-gemfire] for more details on GemFire.

## Pivotal Cloud Foundry (PCF)

The demo implementation of this application is deployed on Pivotal implement of Cloud
Foundry, named Pivotal Cloud Foundry. Cloud Foundry is an opinionated cloud native
application that forces implement to maintain the 12-factor based principals.
See (https://12factor.net/)[https://12factor.net/] for details.

## Pivotal Cloud Cache (PCC)

The PCF applications are state-less. Any information that is cached or stateful is
managed in a backing service.









The idea is to allow scientist to determine similar molecule based
on their canonical SMILES values.


Sub Project

- [khem-app](https://github.com/ggreen/khem/tree/master/khem-app)

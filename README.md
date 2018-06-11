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

- *SMILES* a string representation of the molecule structure in a shortened form.
- *Molfile* a verbose representation of the molecule also holds additional (not structure) information.
- Formula - an expression of the molecule relationships or rules in symbols
- Weight - a measure of the sum of the atomic heaviness




The idea is to allow scientist to determine similar molecule based
on their canonical SMILES values.


Sub Project

- [khem-app](https://github.com/ggreen/khem/tree/master/khem-app)

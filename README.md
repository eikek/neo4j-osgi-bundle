## neo4j osgi superbundle

### neo4j version 1.8

Creates a "super bundle" containing the neo4j community edition. It additionally
comes with a bundle activator which registeres the `GraphDatabaseFactory` as a
service with the osgi runtime. The blueprint xml files are removed, since I got
errors with aries.

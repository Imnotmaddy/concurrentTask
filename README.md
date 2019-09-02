# concurrentTask

1. To run this application specify datasources in application-{test/dev}.properties. Name of datasource must start with datasource<i><b>{index}</b></i>, e.g.
datasource0.url, datasource1.username. Indexes start from 0

2. Endpoint /datasource returns all active datasources
3. Endpoint /benchmark requires <b>*query*</b> and array of <b>*dataSources*</b> as requestBody. Only sql queries without any parametres are allowed.
4. Schema.sql is a script executed in every DB. By default it creates users table with 2 inserted rows.


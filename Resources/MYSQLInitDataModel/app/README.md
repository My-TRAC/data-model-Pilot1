

# MYSQLInitDataModel

This tool initializes a MYSQL database given the data model of My-TRAC platform.
For each given topic, the tool creates the corresponding table with the correct
data types. 

## Compilation

```
mvn assembly:assembly
```

## Usage

```
Usage: MYSQLInitDataModel [options]
  Options:
  * -db, --database
      MySQL Database name
  * -ms, --mysql
      MySQL server URL
  * -pw, --password
      MySQL password
  * -sr, --schema-registry
      Schema registry URL to get the schemas of the topics from
  * -tn, --topic-names
      List of topic names to initialize the MySQL datbase with
  * -u, --username
      MySQL username
  * -h, --help
```

Example:

```
java -cp target/MYSQLInitDataModel-1.0-SNAPSHOT-jar-with-dependencies.jar MYSQLInitDataModel --username <username> --password <password> --database <database> --topic-names agency,activity,user,user_choices_route --schema-registry http://localhost:8081 --mysql "127.0.0.1:3306"
```

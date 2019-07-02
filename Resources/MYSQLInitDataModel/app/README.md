

# MYSQLInitDataModel

This tool initializes a MYSQL database given the data model of My-TRAC platform.
For each given topic, the tool creates the corresponding table with the correct
data types. 

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

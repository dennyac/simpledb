Simple Database
=================

An in-memory database similar to REDIS.

##Getting Started

1. Clone this repository

```
git clone https://github.com/dennyac/simpledb.git
```

2. To start the simple database command line, execute the following commands from the project base directory

  ```shell
  cd target
  java -cp simpledb-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.dennyac.simpledb.Driver
  ```


##System Design

###Data Structures

####Database

The Database contains a Map from all the variables to values. Additionally a Map is used to store the frequency for each value.

####Transactions

All operations that take place within a transaction are saved as part of the transaction. Since each transaction updates a small number of variables, the transaction state consists of a map with the updated variables (as key) and the current value of that variable within that transaction. The transaction state also contains a Map of all the deleted variables and the frequency of the values. Within a transaction, when a variable is updated for the first time, the value is fetched from the database, and all subsequent modifications happen within the transaction state.

For nested transactions, the state is passed to the child transaction. So when a commit is issued, it is sufficient to apply the state of the current transaction to the database. Similarly for rollbacks, the current transaction just has to be removed and the state of the parent transaction will be restored from the stack. Also for lookups checking the current transaction is sufficient, and if the variable is not modified within the transaction, a request can be made to the database.


###Complexity

The time complexity of all operations is O(1) due to the data structures used above. Please note that there is a space overhead in copying transaction state with respect to nested transactions. But this helps in avoiding checking parent transactions when performing lookups.
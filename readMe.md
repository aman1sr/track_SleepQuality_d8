

#What the App does: 
app uses Room Database to store the sleep records


## Architecture
This app uses a simplified architecture, 
- UI controller
- View model and LiveData
- A Room database

## The test class (ExampleInstrumentedTest.kt)  to TEST the DB
if all the tests passed, you now know several things:

- The database gets created correctly.
- You can insert a SleepNight into the database.
- You can get back the SleepNight.
- The SleepNight has the correct value for the quality.


## Why use 
<details>
  <summary>1.DAO</summary>

You must define each entity as an annotated data class, and the interactions with that entity as an annotated interface, called a data access object (DAO).
Room uses these annotated classes to create tables in the database, and to create queries that act on the database.

</details>

# avaloq-assessment

## Design decisions at the bottom!!!
-------------------------

# Clone this project
>git clone https://github.com/lsarot/avaloq-assessment.git
<br>or
<br>git clone git@github.com:lsarot/avaloq-assessment.git

>cd avaloq-assessment

-------------------------

# Run tests
>mvn clean install

-------------------------

# Run solution

## Without Docker
>mvn spring-boot:run
<br>or
<br>java -jar target/avaloq-assessment-0.0.1-SNAPSHOT.jar

## With Docker
## Tear up
>docker compose up --build --force-recreate
## Tear down
>docker compose down
<br>or
<br>ctrl+c

-------------------------
-------------------------

# Try endpoints with Swagger
>http://localhost:8080/swagger-ui/index.html

**Use port 8081 if deployed using Docker**

## Try endpoints manually using browser or curl

**Use port 8081 if deployed using Docker**

>Check all
<br>http://localhost:8080/api/dice/v1/simulations/all
<br>http://localhost:8080/api/dice/v1/rolls/all

>Run custom simulation
<br>http://localhost:8080/api/dice/v1/simulations/sim-custom?dn=2&ds=4&tr=5
<br>**dn: dice number | ds: dice sides | tr: total rolls**

>Check statistics
<br>http://localhost:8080/api/dice/v1/stats/grouped
<br>http://localhost:8080/api/dice/v1/stats/distribution?dn=2&ds=4
<br>**dn: dice number | ds: dice sides**

# Try H2 console
>http://localhost:8080/h2-console

**(not exposed when using Docker)**
* Once inside, use this connection url:<br>
>**jdbc:h2:mem:avaloqsim;DB_CLOSE_DELAY=-1;**

## Try with these queries once inside the H2 console
>SELECT * FROM SIMULATION;
<br>SELECT * FROM ROLL;

>--GROUPED
<br>select  count(time_stamp) total_sim, sum(rolls) rolls, sides, dice
<br>from simulation
<br>group by sides,dice
<br>order by sides,dice;

>--DISTRIBUTION
<br>select t.roll_sum, t.count, round((100*t.count/t.tot), 2) as perc
<br>from (
<br>select r.roll_sum, sum(r.qty) count, (select sum(r.qty) from roll r, simulation s where s.time_stamp=r.time_stamp and s.sides=4 and s.dice=2) tot
<br>from roll r 
<br>where time_stamp in (select time_stamp from simulation where sides=4 and dice=2) 
<br>group by r.roll_sum
<br>order by r.roll_sum
<br>) t
<br>group by t.roll_sum
<br>order by t.roll_sum;

-------------------------
-------------------------

# DESIGN DECISIONS

## Package structure

>src/main/java/com/leonardo/demos/avaloqassessment
- configuration
	- datasource
	- swagger
- controller
	- rest
- model
	- dto
		- stats
	- persistence
		- dao
		- entity
		- repository
- service
- MainClass

## Persistence layer

- **In-memory database using H2**
To avoid drawbacks when testing on your part!

## Table SIMULATION

| Column Name | time_stamp <br>(PK) | rolls | sides | dice |
|----------|----------|----------|----------|----------|
| Column Type | numeric | numeric | numeric | numeric |

## Table ROLL

| Column Name | time_stamp <br>(PK)(FK) | roll_sum (PK) | qty |
|----------|----------|----------|----------|
| Column Type | numeric | numeric | numeric |

## Others

- 3 rest controllers: **simulation, roll, stats**.

- All endpoints returning **ResponseEntity** as wrapper to integrate response code, body and headers.

- Validation using **JSR-380 standard** with Hibernate validator as provider.

- Using **DAO layer** for easing CRUD operations over DDBB. 
	- Provided with **spring-boot-starter-data-jpa** dependency, it allows us to use **@Repository** marked interface extending from **CrudRepository<ReturnType, PrimaryKeyType>** to access CRUD operations over an Entity; also provides **@Query** annotation over interface methods to specify a particular sql statement.

- Using **Repository layer**, which contains DAOs, to execute more complex queries, this because the DAO layer in last point is not allowed to return any other kind of objects than what we declared as parameter type (**ReturnType** above mentioned).

- The DAO layer also is not able to map the result into another type as mentioned, so our **Repository layer** is able to combine several DAOs, multiple tables and basically retrieving any tuple and mapping it to a DTO.

- Our **Repository layer** uses pure JDBC to accomplish this. We could have also used another solution like **MyBatis**.

- 3 services: **JdbcSvc, ValidatorSvc, SimulatorSvc**.

- **JdbcSvc** uses java generics to allows us to reuse just one method to retrieve from any ResultSet.

- Junit and Mockito libraries to test few methods with mocking.

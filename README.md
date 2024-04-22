# BEerConnect
<div align="center">
  <img src="logo.jpeg" alt="Image" width="700"/>
</div>
## Application Description.

The application aims to propose an administration system for a network of beer lovers. The application is a JPA-based Java backend that implements CRUD (Create, Read, Update, Delete) operations for all entities in the system.

The main goal is to provide beer lovers with an application, capable of effectively managing all activities related to their passion. By using JPA in our Java backend, we ensure high efficiency in network administration, allowing users to easily create, read, update, and delete relevant information.

### Diagramma ER

<div align="center">
  <img src="ER.png" alt="Image" width="700"/>
</div>

# Project structure

### Entities.

The application is structured around several entities, each designed to manage specific aspects of the network dedicated to beer lovers. The implemented entities are listed below:

- Client: represents a user of the application, a beer lover who actively participates in the community. The fields that characterize this entity are a name, an email address (which must be unique), a date of birth (the user must be of legal age), an address, and preferences; 

- Pub: represents a brewery location within the network. The fields that characterize this entity are a name, the country of location, and a year of establishment;

- Beer: fundamental entity representing a beer in the system. Characterized by beer name (unique to each), type (Lager, Ale, Stout, etc.), aroma, alcohol content, color, ingredients, price, quantity of beer produced, and pub from which it is brewed;

- Limited Edition: entity derived from Beer, it represents a limited edition of another beer, in addition to the fields of the parent entity it has in addition the name of the original beer and the year of production.

In addition, each entity has an identifier field (ID) that allows the various instances of each entity to be uniquely recognized.

In each of these classes, entity attributes and annotations have been defined that allow data persistence to be managed on the db using JPA.

### Inheritance management.

Managing Limited Edition entities requires a special mapping approach, since the concepts of hierarchy found in object-oriented programming do not translate directly to databases. The approach chosen to address this challenge is the `Single Table' model, which involves using a single table to manage the Beer and Limited Edition entities, distinguishing them through the use of specific columns.\
The single table, called `beer` is designed to incorporate all the common attributes of the Beer and Limited Edition entities. The table structure includes:

- Columns for all Beer attributes, which are also used by Limited Edition;

- Additional columns specific to Limited Edition, which contain NULL values in rows corresponding to instances of Beer;

- A `beer_type` column of type string, indicating the type of object represented (Beer or Limited Edition). Within this column, for each instance is contained the _normal_/_limited_ value indicating whether the instance belongs to the Beer or Limited Edition class.

This "Single Table" approach was chosen because it allows efficient management of the relationships between Beer and Limited Edition entities, while preserving data consistency and completeness in the context of a single table.







### Relationships.

The application presents several relationships among the entities previously described, delineating significant connections within the brewing community. The main relationships implemented are outlined below:

- _Production_ between Pub and Beer (one-to-many): a Pub can produce many beers, but one beer is produced by only one Pub. The connection is managed through the `id_pub` field present in each entity of type Beer (and Limited Edition). This field allows each beer to be associated with the specific pub from which it was brewed, thus ensuring a clear and unique production relationship.

- _Know_ (self-loop relationship on Client): this relationship represents the connection between clients who know each other within the beer community. This relationship is structured according to the follower/followed pattern of Instagram, a Client can follow _N_ other users and be followed by _N_ users. If Client A follows Client B, Client B is not obliged to follow Client A as well.\
The relationship is managed through the `client_to_client` table in the database, which contains the `client_id` and `client_followed` columns. This relationship provides flexibility so that a Client can follow multiple users without necessarily receiving a follow in response.\
The purpose of this relationship is to allow the various Clients to go and read the taste preferences or written reviews of the Clients they follow or the Clients they are followed by.
- _Review_ between Client and Beer (many-to-many): each Client can write _N_ reviews on _N_ beers and each beer can receive _N_ reviews from _N_ different Clients.\
This relationship is managed by a table called `client_review`, which in addition to containing the foreign keys of the reviewed beer and the reviewing Client, contains two additional fields, `rating` and `review`. Where, the first field is a value between 1 and 5 representing a rating given to the beer and the second contains the verbal description of the review.\
Advanced management of this relationship requires the creation of an intermediate ClientReview entity and the use of JPA @OneToMany annotations on both sides, thus ensuring efficient and complete management of beer reviews. Despite this, the relationship remains many-to-many.

### Implementation.

For persistence management, interfaces were introduced within the package repository, one for each entity, which extend JpaRepository to take advantage of JPA's standard CRUD methods. In order to meet the assignment requirements, specific search queries involving at least two entities were developed, which make selections based on nonkey attributes.

The function required in the design specification, called `findClientByReview` and implemented in the `ClientReview` class, allows retrieving customer information based on reviews associated with beers. Specifically, the function provides the ability to filter results based on the nationality of the beer and the rating assigned. 

For business logic management, service classes have been implemented within the service package, each dedicated to an entity in the system. These classes allow the implementation of extended methods from repository interfaces and the use of methods for CRUD operations. This structure provides an additional layer of control between the application and the database.

Exceptions are handled through the appropriate classes in the exception package.





## How to use the application.

The application relies on a PostgreSQL database, so it is necessary to create such a database with the desired name. Next, define the `config.properties` file (which must be located in "2023_assignment3_beerconnect\src\mainresources"), and fill in the following fields:

- spring.datasource.username="username_postgres"

- spring.datasource.password="password_postgres"

- spring.datasource.url=jdbc:postgresql://localhost:5432/"chosen_db_name"

Be sure to replace "username_postgres," "password_postgres," and "name_db_chosen" with the appropriate credentials and the name chosen for the database.

# Strategies.

We chose to enable automatic generation of the data definition language (DDL) by allowing Spring JPA to handle the creation of the database schema itself. Hibernate is configured to create the DDL each time the application starts, ensuring that the database schema is always aligned with the application. Viewing Hibernate-generated SQL queries is enabled in the log console, facilitating development and debugging. Datasource initialization is delayed until Hibernate configurations are completed, ensuring proper order of operations. The database initialization SQL script runs automatically each time the application is started to maintain a consistent state of the database. 




## Testing

Within the BeerConnect project, the implementation of testing emerges as an essential practice to ensure the robustness and correctness of the operations performed on the various entities in the system. A special focus has been devoted to test classes for key entities, highlighting operations for managing beers, users, limited edition beers, and pubs.

The `@BeforeEach` and `@AfterEach` annotations are used to prepare and clean up the test environment before and after each individual test. The `setUp` function handles initialization using an SQL script, while `tearDown` handles cleanup operations. This approach aims to isolate the tests, ensuring that each one starts from a known state and consistency, thus contributing to repeatability and reliability.

The `BeerTests` class is designed to validate CRUD operations related to beers. The tests include searching and retrieving beers, updating information, and deleting beers. The correctness of these operations is critical to ensure that beers in the system are easily accessible and that changes occur consistently.





In `ClientTests`, attention was paid to user management. The tests embrace operations such as adding new users, editing information, and managing user relationships such as following and stopping following other users. These tests are crucial to ensure that BeerConnect's social features are reliable and that users' information is properly managed.

Regarding limited edition beers, the `LimitedEditionTests` class is responsible for testing the proper handling of these special beers. The tests cover finding, updating, and deleting limited edition beers, ensuring that these beers are also handled accurately and in accordance with system expectations.

In the `PubTests` they test pub-related operations within BeerConnect. From tests of retrieving and displaying pubs, to adding and editing information, to deleting specific pubs, every aspect is carefully evaluated to ensure the integrity of pub-related data.


Finally, the `ClientReviewTests` class is dedicated to testing the fundamental operations related to customer reviews in BeerConnect. The tests cover key aspects such as adding, editing, and managing reviews, ensuring the reliability of the social functionality of the application. Through diverse scenarios, from basic tests such as obtaining reviews by ID to handling more complex situations such as duplicate reviews and updates, the proper management of customer information within the system is ensured.

## map-advanon

A simple kotlin REST API project to consume external map service. Developed 
using TDD approach - examine ``git log`` to see the history of commits.

Coverage over 90%

Plese, do read kDocs - each class, function and non-private element is 
carefully commented and documented. Also, tests is a great source of 
documentation - every use case is covered.

### Running tests

Run ``./gradlew test`` or use your favorite IDE.

### Building & running

Project is fully dockerized. Simply invoke the following command to run it:

``./gradlew clean bootJar buildDocker && docker-compose up``

You need to have ``docker`` & ``docker-compose`` installed to start the 
project this way. Alternatively, you can install ``MongoDB`` manually and 
start it to listen on ``mongodb://map-advanon-mongo/map-advanon``. You can 
also change ``spring.data.mongodb.uri`` property to adjust it to your 
configuration (when docker is not available).

REST API is configured to listen on ``localhost:8080``.

### API endpoints

Following endpoints are active:

#### /api/maps

``POST`` will create a local representation of Apiary map model.

``GET`` with ``Accept: text/plain`` will return the ascii-art representation 
of a map. If map doesn't exist yet (because no ``POST`` was previously 
invoked), an empty representation will be returned.

#### /api/islands

``GET`` returns all islands found on the map. If map is not available yet (no
 ``POST`` on ``/api/maps`` was called), then an empty collection is returned.

#### /api/islands/{id}

``GET`` returns a concrete island. If map is not available (no ``POST`` on 
``/api/maps`` was called or island with such id doesn't exist), an empty result
 is returned instead.

### Implementation details

Islands discovery is implemented by converting the Apiary map model to a 
graph representation and running the algorithm to find connected components 
using ``JGraphT`` [library](https://github.com/jgrapht/jgrapht).
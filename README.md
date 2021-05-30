# ticket-booking-app

This is a simple system for ticket booking. Program uses Java 11 and Maven, which takes care of downloading additional dependencies, building, testing and running app.

The default port for application is 8080, you can change it in /resources/application.properties file. App uses persisten H2 database, that should be downloaded by Maven and created by mentioned later scripts.

App has three endpoints:

| URL           | Method           | 
| ------------- |:-------------:| 
| /book/list-screenings?*query*         | GET |
| /book/available-seats/*{screeningId}* | GET  |
| /book/make-reservation                | POST | 

Returned values are in JSON format.

### GET /book/list-screenings?*query* 

This endpoint enables fetching sorted by title movies list with sorted by time screenings lists. 
Returned are also movie description and screenings id so that making reservation was possible. Query is optional - when not specified, 
screenings are returned from now to one day ahead. If there is at least one query param, the second one is required.

| query param           | value           | 
| ------------- |:-------------:| 
| from        | Date in *yyyy-MM-dd'T'HH:mm:ss* format |
| to | Date in *yyyy-MM-dd'T'HH:mm:ss* format |

Possible url:

`http://localhost:8080/book/list-screenings?from=2021-06-30T08:00:00&to=2021-06-30T23:00:00`


Corresponding possible returned value:
``` json
[
   {
      "movie":{
         "title":"La Vita E Bella",
         "description":"A nice movie."
      },
      "movieScreenings":[
         {
            "id":3,
            "date":"2021-06-30T09:15:00"
         },
         {
            "id":1,
            "date":"2021-06-30T12:00:00"
         }
      ]
   },
   {
      "movie":{
         "title":"Ranczo Wilkowyje",
         "description":"A funny movie."
      },
      "movieScreenings":[
         {
            "id":4,
            "date":"2021-06-30T13:15:00"
         },
         {
            "id":6,
            "date":"2021-06-30T18:15:00"
         }
      ]
   }
]
```

### GET /book/available-seats/*{screeningId}*

Fetches room plan for given screening.

Possible url:

`http://localhost:8080/book/available-seats/6`

Possible output:

``` json
{
   "1":[
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true
   ],
   "2":[
      true,
      false,
      false,
      true,
      true,
      true,
      true,
      true
   ],
   "3":[
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true
   ]
}
```

where key in key-value pair is row identifier (seat row number) and values are list of consecutive seats - index of
element in array is also a seat number (starting from one). True means, that the seat is free, and false - that has been already booked by someone else. 

### POST /book/make-reservation

This endpoint accepts POST requests with JSON body as in following example:

``` json
{
   "firstName":"Jan",
   "lastName":"Kowalski",
   "screeningId":6,
   "seats":[
      {
         "rowNr":2,
         "seatNr":2
      },
      {
         "rowNr":2,
         "seatNr":3
      }
   ],
   "tickets":[
      "child",
      "adult"
   ]
}

```

If body of request is well-prepared and seats may be booked, reservation will be done and confirmation will be sent. Confirmation may look like:

``` json
{
   "firstName":"Jan",
   "lastName":"Kowalski",
   "done":"2021-05-30T14:55:25",
   "screening":{
      "id":6,
      "roomId":3,
      "date":"2021-06-30T18:15:00",
      "movie":{
         "title":"Ranczo Wilkowyje",
         "description":"A funny movie."
      }
   },
   "bookedSeats":[
      {
         "rowNr":2,
         "seatNr":2
      },
      {
         "rowNr":2,
         "seatNr":3
      }
   ],
   "reservationTickets":[
      {
         "ticketType":{
            "typeName":"child",
            "price":12.50
         }
      },
      {
         "ticketType":{
            "typeName":"adult",
            "price":25.00
         }
      }
   ],
   "amountToPay":37.50,
   "shouldBePaidUntil":"2021-06-30T18:10:00"
}
```

## Data validation

Attempts to make data valid in mentioned areas has been made:

* First name starts with capital letter and is at least three characters long
* Last name starts with capital letter 
* Last name is at least three characters long or consists of two dash-separated parts
* Tickets cannot be booked later than 15 minutes before screening
* Time interval for listing screenings should be valid, i.e. "from" param must be before "to"
* There cannot be a single place left in a row between already reserved seats
* System handles Polish characters

This data is for now validated in server app code.

## Installation

If you don't have Java and Maven on your machine, you can run `sudo apt-get install maven` in your terminal - it should download both required Maven and Java Development Kit.

**Important** - in next step I assumed, that your Maven repository is located in default location which is `~/.m2/repository` for Linux - if it is different on your machine, change corresponding path in `/database/database_setup.sh` script (on Mac it should be `/Users/<user_name>/.m2/repository`)

Next step is to run included in project script:

`$ sh build.sh`

from project main directory.
Now Maven will download required dependencies, build app and perform some unit testing.

## Run

In order to run app, type command:

`$ sh run.sh`

from project main directory.
## Test

Simple script performing request has been added to a project. Please run it using command:

`$ sh use_case.sh`

from project main directory.

What does the script do is:

1. Fetching available screenings in given time period.
2. Fetching available seats for chosen screening.
3. Booking two seats with two different ticket types.
4. Attempting to book seat that would leave one single seat - in this case app should not allow for that.

Because actions are performed against persistent database, if you want to run whole test case and get the same result, please stop the app and run:

`$ sh build.sh`

`$ sh run.sh`

which will rebuild the database structure (making previously reserved seats free again).

Of course, instead of rebuilding database, you can change some seat numbers in the script and see the results.


## Comments

* Some integration and unit test have been written. Due to end of my university semester and lack of time, there are some important classes and operations that have not been tested well (mainly seat reserving process).
Well, next step to improve the app should be to test it better.

* Data mentioned earlier (as first/last name) is validated in server code - some of validate cases may or should be validated also in the database - for example dbms should be taking care if there are not two different ticket types with the same name valid (column "valid" in table ticket_types) at the same time. Some more database constraints should be written.

* Endpoint returning screening list returns all the screenings, that the app is able to find - this could be paginated. Such approach can be important for app and client 
  performance if there is a lot of search results. I could do it in next step.   

* I think that some service helper/data storage classes could be written better to make better use of Spring's dependency injection - maybe the code would be easier to test.

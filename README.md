In this project, we will build Spring Boot application, 'NxtStayz', we aim to provide a platform that connects rooms with their respective hotels. Through 'NxtStayz', users can explore and understand which rooms belong to which hotel, ensuring a reservation process. The platform emphasizes the variety of rooms each hotel has to offer, giving users a clearer insight into their accommodation choices.

The key entities for this application are `Hotel` and `Room`. The `Room` entity shares a Many-to-One relationship with the `Hotel`, indicating that each hotel can offer a variety of rooms.

<details>
<summary>**Implementation Files**</summary>

Use these files to complete the implementation:

- `RoomController.java`
- `RoomRepository.java`
- `RoomJpaService.java`
- `RoomJpaRepository.java`
- `Room.java`
- `HotelController.java`
- `HotelRepository.java`
- `HotelJpaService.java`
- `HotelJpaRepository.java`
- `Hotel.java`

</details>

Create a database that contains two tables `hotel` and `room` using the given database schema.

You can refer to this [session](https://learning.ccbp.in/course?c_id=e345dfa4-f5ce-406e-b19a-4ed720c54136&s_id=6a60610e-79c2-4e15-b675-45ddbd9bbe82&t_id=f880166e-2f51-4403-81a0-d2430694dae8), for creating a database.

_Create the SQL files and compose accurate queries to run the application. Inaccurate SQL files will result in test case failures._

<details>
<summary>**Database Schema**</summary>

#### Hotel Table

| Columns  |                 Type                  |
| :------: | :-----------------------------------: |
|    id    | INTEGER (Primary Key, Auto Increment) |
|   name   |                 TEXT                  |
| location |                 TEXT                  |
|  rating  |                INTEGER                |

#### Room Table

|  Columns   |                 Type                 |
| :--------: | :----------------------------------: |
|     id     | INTEGER(Primary Key, Auto Increment) |
| roomNumber |                 TEXT                 |
|    type    |                 TEXT                 |
|   price    |                DOUBLE                |
|  hotelId   |        INTEGER (Foreign Key)         |

You can use the given sample data to populate the tables.

<details>
<summary>**Sample Data**</summary>

#### Hotel Data

|  id   |          name           |  location   | rating |
| :---: | :---------------------: | :---------: | :----: |
|   1   |     The Plaza Hotel     |  New York   |   4    |
|   2   | The Beverly Hills Hotel | Los Angeles |   5    |
|   3   |       The Langham       |   Chicago   |   3    |

#### Room Data

|  id   | roomNumber |        type         |  price  | hotelId |
| :---: | :--------: | :-----------------: | :-----: | :-----: |
|   1   |   A-101    |     Deluxe Room     | 375.00  |    1    |
|   2   |   A-205    |        Suite        | 950.00  |    1    |
|   3   |   B-106    |   Penthouse Suite   | 2500.00 |    1    |
|   4   |   C-401    | Superior Guest Room | 465.00  |    2    |
|   5   |   D-202    |      Bungalow       | 1250.00 |    2    |
|   6   |   A-107    |   Penthouse Suite   | 3300.00 |    2    |
|   7   |   A-301    |     Grand Room      | 410.00  |    3    |
|   8   |   C-313    |   Executive Suite   | 700.00  |    3    |
|   9   |   D-404    |    Premier Suite    | 880.00  |    3    |

</details>

</details>

<MultiLineNote>

Use only `room` and `hotel` as the table names in your code.

</MultiLineNote>

### Completion Instructions

- `Room.java`: The `Room` class should contain the following attributes.

    | Attribute |  Type  |
    | :-------: | :----: |
    |  roomId   |  int   |
    | roomName  | String |
    |   type    | String |
    |   price   | double |
    |   hotel   | Hotel  |

- `RoomRepository.java`: Create an `interface` containing the required methods.
- `RoomJpaService.java`: Update the service class with logic for managing room data.
- `RoomController.java`: Create the controller class to handle HTTP requests.
- `RoomJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.
  
- `Hotel.java`: The `Hotel` class should contain the following attributes.

    | Attribute |  Type  |
    | :-------: | :----: |
    |  hotelId  |  int   |
    | hotelName | String |
    | location  | String |
    |  rating   |  int   |

- `HotelRepository.java`: Create an `interface` containing the required methods.
- `HotelJpaService.java`: Update the service class with logic for managing hotel data.
- `HotelController.java`: Create the controller class to handle HTTP requests.
- `HotelJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

Implement the following APIs.

<details>
<summary>**API 1: GET /hotels**</summary>

#### Path: `/hotels`

#### Method: `GET`

#### Description:

Returns a list of all hotels in the `hotel` table.

#### Response

```json
[
    {
        "hotelId": 1,
        "hotelName": "The Plaza Hotel",
        "location": "New York",
        "rating": 4
    },
    ...
]
```

</details>

<details>
<summary>**API 2: POST /hotels**</summary>

#### Path: `/hotels`

#### Method: `POST`

#### Description:

Creates a new hotel in the `hotel` table. The `hotelId` is auto-incremented.

#### Request

```json
{
    "hotelName": "Fontain Miami Beach",
    "location": "Miami",
    "rating": 4
}
```

#### Response

```json
{
    "hotelId": 5,
    "hotelName": "Fontain Miami Beach",
    "location": "Miami",
    "rating": 4
}
```

</details>

<details>
<summary>**API 3: GET /hotels/{hotelId}**</summary>

#### Path: `/hotels/{hotelId}`

#### Method: `GET`

#### Description:

Returns a hotel based on the `hotelId`. If the given `hotelId` is not found in the `hotel` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "hotelId": 1,
    "hotelName": "The Plaza Hotel",
    "location": "New York",
    "rating": 4
}
```

</details>

<details>
<summary>**API 4: PUT /hotels/{hotelId}**</summary>

#### Path: `/hotels/{hotelId}`

#### Method: `PUT`

#### Description:

Updates the details of a hotel based on the `hotelId` and returns the updated hotel details. If the given `hotelId` is not found in the `hotel` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "hotelName": "Fontainebleau Miami Beach",
    "rating": 5
}
```

#### Success Response

```json
{
    "hotelId": 4,
    "hotelName": "Fontainebleau Miami Beach",
    "location": "Miami",
    "rating": 5
}
```

</details>

<details>
<summary>**API 5: DELETE /hotels/{hotelId}**</summary>

#### Path: `/hotels/{hotelId}`

#### Method: `DELETE`

#### Description:

Deletes a hotel from the `hotel` table based on the `hotelId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). Also, remove the associtation with the room by keeping a _null_ value in the `room` table.

If the given `hotelId` is not found in the `hotel` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. 

#### Sample Room object when its corresponding hotel is deleted

```json
{
    "roomId": 1,
    "roomNumber": "A-101",
    "roomType": "Deluxe Room",
    "price": 375.0,
    "hotel": null
}
```

</details>

<details>
<summary>**API 6: GET /hotels/{hotelId}/rooms**</summary>

#### Path: `/hotels/{hotelId}/rooms`

#### Method: `GET`

#### Description:

Returns a list of all rooms of the hotel based on the `hotelId`. If the given `hotelId` is not found in the `hotel` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "roomId": 1,
        "roomNumber": "A-101",
        "roomType": "Deluxe Room",
        "price": 375.0,
        "hotel": {
            "hotelId": 1,
            "hotelName": "The Plaza Hotel",
            "location": "New York",
            "rating": 4
        }
    },
    ...
]
```



</details>

<details>
<summary>**API 7: GET /hotels/rooms**</summary>

#### Path: `/hotels/rooms`

#### Method: `GET`

#### Description:

Returns a list of all rooms in the `room` table.

#### Response

```json
[
    {
        "roomId": 1,
        "roomNumber": "A-101",
        "roomType": "Deluxe Room",
        "price": 375.0,
        "hotel": {
            "hotelId": 1,
            "hotelName": "The Plaza Hotel",
            "location": "New York",
            "rating": 4
        }
    },
    ...
]
```

</details>

<details>
<summary>**API 8: POST /hotels/rooms**</summary>

#### Path: `/hotels/rooms`

#### Method: `POST`

#### Description:

Creates a new room in the `room` table and create an association between the room and the hotel based on the `hotelId` of the `hotel` field. The `roomId` is auto-incremented.

#### Request

```json
{
    "roomNumber": "D-201",
    "roomType": "Ocean Room",
    "price": 300.00,
    "hotel": {
        "hotelId": 2
    }
}
```

#### Response

```json
{
    "roomId": 10,
    "roomNumber": "D-201",
    "roomType": "Ocean Room",
    "price": 300.0,
    "hotel": {
        "hotelId": 2,
        "hotelName": "The Beverly Hills Hotel",
        "location": "Los Angeles",
        "rating": 5
    }
}
```

</details>

<details>
<summary>**API 9: GET /hotels/rooms/{roomId}**</summary>

#### Path: `/hotels/rooms/{roomId}`

#### Method: `GET`

#### Description:

Returns a room based on the `roomId`. If the given `roomId` is not found in the `room` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "roomId": 1,
    "roomNumber": "A-101",
    "roomType": "Deluxe Room",
    "price": 375.0,
    "hotel": {
        "hotelId": 1,
        "hotelName": "The Plaza Hotel",
        "location": "New York",
        "rating": 4
    }
}
```

</details>

<details>
<summary>**API 10: PUT /hotels/rooms/{roomId}**</summary>

#### Path: `/hotels/rooms/{roomId}`

#### Method: `PUT`

#### Description:

Updates the details of a room based on the `roomId` and returns the updated room details. If the `hotelId` in the `hotel` field is provided, update the association between the room and the hotel based on the `hotelId`. If the given `roomId` is not found in the `room` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "roomNumber": "D-401",
    "roomType": "Oceanfront Room",
    "price": 350.00,
    "hotel": {
        "hotelId": 4
    }
}
```

#### Success Response

```json
{
    "roomId": 11,
    "roomNumber": "D-401",
    "roomType": "Oceanfront Room",
    "price": 350.0,
    "hotel": {
        "hotelId": 4,
        "hotelName": "Fontainebleau Miami Beach",
        "location": "Miami",
        "rating": 5
    }
}
```

</details>

<details>
<summary>**API 11: DELETE /hotels/rooms/{roomId}**</summary>

#### Path: `/hotels/rooms/{roomId}`

#### Method: `DELETE`

#### Description:

Deletes a room from the `room` table based on the `roomId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `roomId` is not found in the `room` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 12: GET /rooms/{roomId}/hotel**</summary>

#### Path: `/rooms/{roomId}/hotel`

#### Method: `GET`

#### Description:

Returns a hotel of the room based on the `roomId`. If the given `roomId` is not found in the `room` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "hotelId": 1,
    "hotelName": "The Plaza Hotel",
    "location": "New York",
    "rating": 4
}
```

</details>

**Do not modify the code in `NxtStayzApplication.java`**
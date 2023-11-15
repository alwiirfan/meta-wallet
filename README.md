# META Wallet
## META Wallet is an application that has `top up` and `transfer` features as the main feature.

## Download the Repository
- Download the repository to your computer using the `git clone` command. Url of the repository can be seen in the desired repository.
```
git clone <repository url> <destination folder>
```

#### Example :
```
git clone https://git.enigmacamp.com/enigma-camp/enigmacamp-2.0/batch-11-java/team/team-2/final-project-metawallet.git
```

## How to run Test:
1. Make sure you have installed the Java Development Kit (JDK) at least `Version 11` and Maven on your computer.
2. Open a terminal or command prompt and navigate to the project directory Example: `final-project-metawallet`.
3. Run the following command to run all tests:
```
mvn clean test
```

## How to Run Application:
```
mvn spring-boot:run
```

## Noted:
- Make sure you have done the proper configuration in the `application.properties` file.

# API Spec
## Authentication

### Register User
#### Request:
- Method: POST
- Endpoint: `/v1/auth/register`
- Header:
  - Content-Type : application/json
  - Accept : application/json
- Body:
```json
{
  "name" : "String",
  "username" : "String",
  "password" : "String",
  "email" : "String",
  "address" : "String",
  "city" : "String",
  "country" : "String",
  "mobilePhone" : "String",
  "dateOfBirth" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "username" : "String",
    "email" : "String"
  },
  "paging" : null
}
```

### Register Admin
#### Request:
- Method: POST
- Endpoint: `/v1/auth/register/admin`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "username" : "String",
  "password" : "String",
  "email" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "username" : "String",
    "email" : "String"
  },
  "paging" : null
}
```

### Login
#### Request:
- Method: POST
- Endpoint: `/v1/auth/login`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "password" : "String",
  "email" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "email" : "String",
    "roles" : [
      "String"
    ],
    "token" : "String"
  },
  "paging" : null
}
```

## User

### Get All User `[ADMIN]`
#### Request:
- Method: GET
- Endpoint: `/v1/users`
- Header:
    - Accept : application/json
#### Response:

```json
{
  "statusCode": "Integer",
  "message": "String",
  "data": [
    {
      "id": "String",
      "name": "String",
      "email": "String",
      "username": "String",
      "address": "String",
      "mobilePhone": "String",
      "country": "String",
      "city": "String",
      "dateOfBirth": "String"
    },
    {
      "id": "String",
      "name": "String",
      "email": "String",
      "username": "String",
      "address": "String",
      "mobilePhone": "String",
      "country": "String",
      "city": "String",
      "dateOfBirth": "String"
    }
  ],
  "paging": null
}
```

### Get User By Id
#### Request:
- Method: GET
- Endpoint: `/v1/users/{userId}`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "id": "String",
    "name": "String",
    "email": "String",
    "username": "String",
    "address": "String",
    "mobilePhone": "String",
    "country": "String",
    "city": "String",
    "dateOfBirth": "String"
  },
  "paging" : null
}
```

### Get Wallet By User Id
#### Request:
- Method: GET
- Endpoint: `/v1/users/{userId}/wallet`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "userId": "String",
    "walletId": "String",
    "balance": "Long"
  },
  "paging" : null
}
```

### User Top up Wallet
#### Request:
- Method: PATCH
- Endpoint: `/v1/users/wallet`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "userId" : "String",
  "balance" : "Long"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "userId": "String",
    "walletId": "String",
    "balance": "Long"
  },
  "paging" : null
}
```

### Update User
#### Request:
- Method: PUT
- Endpoint: `/v1/users`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "id" : "String",
  "name" : "String",
  "address" : "String",
  "mobilePhone" : "String",
  "city" : "String",
  "country" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "id": "String",
    "name": "String",
    "email": "String",
    "username": "String",
    "address": "String",
    "mobilePhone": "String",
    "country": "String",
    "city": "String",
    "dateOfBirth": "String"
  },
  "paging" : null
}
```

### Change Password User
#### Request:
- Method: PATCH
- Endpoint: `/v1/users/{userId}`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "currentPassword" : "String",
  "newPassword" : "String",
  "confirmationPassword" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : null,
  "paging" : null
}
```

### Delete User By User Id `[ADMIN]`
#### Request:
- Method: DELETE
- Endpoint: `/v1/users/{userId}`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : null,
  "paging" : null
}
```

## Admin
### Get All Admin `[ADMIN]`
#### Request:
- Method: GET
- Endpoint: `/v1/admin`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : [
    {
      "id" : "String",
      "name" : "String",
      "username" : "String",
      "email" : "String"
    }
  ],
  "paging" : null
}
```

## Transfer
### Transfer User to User
#### Request:
- Method: POST
- Endpoint: `/v1/transfer`
- Header:
    - Content-Type : application/json
    - Accept : application/json
- Body:
```json
{
  "fromUserId" : "String",
  "toUserId" : "String",
  "nominalTransfer" : "String"
}
```

#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : {
    "id" : "String",
    "fromUserId" : "String",
    "toUserId" : "String",
    "nominalTransfer" : "Long",
    "tax" : "Long",
    "total" : "Long",
    "transDate" : "LocalDateTime"
  },
  "paging" : null
}
```

### Get All In History
#### Request:
- Method: GET
- Endpoint: `/v1/transfer/inhistory/{userId}`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : [
    {
      "id" : "String",
      "transferIn" : "Long",
      "transferOut" : "Long",
      "fromUserId" : "String",
      "toUserId" : "String",
      "transDate" : "LocalDateTime",
      "tax" : "Long"
    }
  ],
  "paging" : null
}
```

### Get All Out History
#### Request:
- Method: GET
- Endpoint: `/v1/transfer/outhistory/{userId}`
- Header:
    - Accept : application/json
#### Response:
```json
{
  "statusCode" : "Integer",
  "message" : "String",
  "data" : [
    {
      "id" : "String",
      "transferIn" : "Long",
      "transferOut" : "Long",
      "fromUserId" : "String",
      "toUserId" : "String",
      "transDate" : "LocalDateTime",
      "tax" : "Long"
    }
  ],
  "paging" : null
}
```
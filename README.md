# Account Helper Application

This is a bare-bones application with a REST interface around a account-helper application.

The application provides list operations on a Account object and the related AccountTransaction object.

## Build

    ./gradlew build
    or
    ./gradlew clean build

## Run the app

    ./gradlew bootRun

# REST API

The REST API to the example app is described below.

## Get list of Account objects

### Request

`GET /accounts/`

    curl -i -H 'Accept: application/json' http://localhost:8090/account-helper/accounts

### Response

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Sat, 18 May 2019 07:39:43 GMT

    {
        "content": [
            {
                "id": 1,
                "accountNumber": "fsgfsgfsgfsdg",
                "accountName": "hgfdhgdfhgdfd",
                "accountType": "SAVINGS",
                "balanceDate": "2019-06-22T14:00:00",
                "currencyType": "AUD",
                "openingBalance": "1200.00"
            },
            {
                "id": 2,
                "accountNumber": "hfgh",
                "accountName": "fhfhfh",
                "accountType": "CURRENT",
                "balanceDate": "2019-06-22T14:00:00",
                "currencyType": "SGD",
                "openingBalance": "1200.00"
            },
            {
                "id": 3,
                "accountNumber": "fsdgfdgsfdgfdg",
                "accountName": "fhfhfh",
                "accountType": "CURRENT",
                "balanceDate": "2019-06-22T14:00:00",
                "currencyType": "AUD",
                "openingBalance": "1200.00"
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "offset": 0,
            "pageSize": 20,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 3,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "numberOfElements": 3,
        "empty": false
    }

## Get list of Account objects with Account Transactions associated (if any) as well
### Request

`GET /accounts/`

    curl -i -H 'Accept: application/json' http://localhost:8090/account-helper/accounts/2/transactions

### Response

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Sat, 18 May 2019 07:39:43 GMT

    {
        "id": 2,
        "accountNumber": "hfgh",
        "accountName": "fhfhfh",
        "accountType": "CURRENT",
        "balanceDate": "2019-06-22T14:00:00",
        "currencyType": "SGD",
        "openingBalance": "1200.00",
        "accountTransactions": [
            {
                "id": 3,
                "creditAmount": "0.00",
                "debitAmount": "343.22",
                "balanceType": "DEBIT"
            },
            {
                "id": 1,
                "creditAmount": "343.22",
                "debitAmount": "0.00",
                "balanceType": "CREDIT"
            },
            {
                "id": 2,
                "creditAmount": "343.22",
                "debitAmount": "0.00",
                "balanceType": "CREDIT"
            },
            {
                "id": 4,
                "creditAmount": "0.00",
                "debitAmount": "343.22",
                "balanceType": "DEBIT"
            }
        ]
    }
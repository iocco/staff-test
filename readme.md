# Staff Test project

## To run tests
```
./mvnw test
```

## To run server
```
docker compose up
```

Should run on `localhost:8080`

## To Auth

The only endpoint that doesn't need authentication is `/user`
you can create a username and get a token used to authenticate
over the other endpoints

### Sample Request
```
{
    "username": "user",
    "password": "pass"
}
```

### Sample Response

```
{
    "username": "user",
    "password": "pass",
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzdGFmZlRlc3QiLCJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY1NzIwNTI0OCwiZXhwIjoxNjU3MjA1ODQ4fQ.mAxLIe7YFFe2oLg9fg6k98-5M40ukSGoGjUDw6frUYwlPYuVlZJWYivKlWhN0-618Ou3c9tTvqIoUrlmd3WJ3A"
}
```

### Endpoints

All of this endpoints need the `Authorization` header with the token that you can get on `/user/
otherwise you should receive a 403 error.

### POST /employees
  - **Creates a new employee** *It does not accept to employees with the sane name, returning 409 on that case*
  #### Sample Request 
  ``` 
  {
    "name": "pablo",
    "salary": 100000.0,
    "currency": "USD",
    "department": "Engineering",
    "sub_department": "Web",
    "on_contract": true
  }
  ```
  #### Sample Response
  ```
  {
    "name": "pablo",
    "salary": 100000.0,
    "currency": "USD",
    "department": "Engineering",
    "sub_department": "Web",
    "on_contract": true
  ```

### DELETE /employees/{name}


    
  - **Deletes the employee with the param name**, gets and empty response if successful 404, if not found.
### GET /contractSalaries

  - **Gets summary statistics** for all the employees with `on_contract = true`
Sample Response
```
{
    "mean": 100000.0,
    "max": 110000.0,
    "min": 90000.0
}
```

### GET /contractSalariesDepartment
- **Gets summary statistics detailed by department** for all the employees with `on_contract = true`
#### Sample Response
```
{
    "Engineering": {
        "mean": 110000.0,
        "max": 110000.0,
        "min": 110000.0
    },
    "Banking": {
        "mean": 90000.0,
        "max": 90000.0,
        "min": 90000.0
    }
}
```

### GET /contractSalariesSubDepartment
- **Gets summary statistics detailed by sub department** for all the employees with `on_contract = true`
#### Sample Response
```
{
    "Engineering": {
        "Platform": {
            "mean": 110000.0,
            "max": 110000.0,
            "min": 110000.0
        }
    },
    "Banking": {
        "Loan": {
            "mean": 90000.0,
            "max": 90000.0,
            "min": 90000.0
        }
    }
}
```

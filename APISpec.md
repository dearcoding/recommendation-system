# Movie Recommendation API Documentation

## Overview
This API provides comprehensive movie recommendation and user interaction features, allowing users to discover, search, and receive user-based movie recommendations.

## Base URL
`/v1`

## Endpoints

### Movies

#### 1. List Movies
- **URL:** `/movies`
- **Method:** `GET`
- **Description:** Retrieve a filtered list of movies

#### Parameters
| Name | Type | Location | Required | Description |
|------|------|----------|----------|-------------|
| `genre` | string | Query | Optional | Filter movies by genre |
| `minRating` | number | Query | Optional | Minimum movie rating (0-5) |
| `maxRating` | number | Query | Optional | Maximum movie rating (0-5) |

#### Response
- **Status:** 200 OK
- **Content:** Array of `MovieDTO`

#### Example
```http
GET /v1/movies?genre=Action&minRating=3&maxRating=5
```

#### 2. Search Movies
- **URL:** `/movies/search`
- **Method:** `GET`
- **Description:** Advanced movie search with multiple filters

#### Parameters
| Name | Type | Location | Required | Description |
|------|------|----------|----------|-------------|
| `title` | string | Query | Optional | Partial movie title match |
| `genres` | string | Query | Optional | Comma-separated genre list |
| `keywords` | string | Query | Optional | Comma-separated keywords |

#### Response
- **Status:** 200 OK
- **Content:** Array of `MovieDTO`

#### Example
```http
GET /v1/movies/search?title=Inception&genres=Sci-Fi,Action
```

#### 3. Get User History
- **URL:** `/users/{userId}/history`
- **Method:** `GET`
- **Description:** Retrieve user's viewing and rating history

#### Parameters
| Name | Type | Location | Required | Description |
|------|------|----------|----------|-------------|
| `userId` | integer | Path | Required | User's unique identifier |
| `returnType` | enum | Query | Optional | History type (ALL, VIEWS, RATINGS) |

#### Response
- **Status:** 200 OK
- **Content:** `HistoryDTO`

#### Example
```http
GET /v1/users/123/history?returnType=VIEWS
```

#### 4. Add User Event
- **URL:** `/users/{userId}/addEvent`
- **Method:** `POST`
- **Description:** Record user interaction with a movie

#### Request Body
```json
{
    "movieId": 3,
    "rating": 5,
    "viewPercentage": 70
}
```

#### Response
- **Status:** 200 OK
- **Content:** `UserDTO`

#### 5. Get User Recommendations
- **URL:** `/users/{userId}/recommendations`
- **Method:** `GET`
- **Description:** Get movie recommendations by user id

#### Response
- **Status:** 200 OK
- **Content:** Array of `MovieDTO`

## Data Models

### MovieDTO
```json
{
    "id": 1,
    "title": "Toy Story",
    "genres": [
        "Adventure", 
        "Animation", 
        "Children", 
        "Comedy", 
        "Fantasy"
    ]
}
```

### HistoryDTO
```json
{
    "ratings": [
        {"movieId": 1, "rating": 4},
        {"movieId": 2, "rating": 5}
    ],
    "views": [
        {"movieId": 1, "viewPercentage": 85},
        {"movieId": 2, "viewPercentage": 70}
    ]
}
```
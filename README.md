# BreweryDB API Client

This repository contains the Java Client for the BreweryDB API. It is currently a work in progress.

## Usage

### Creating a client

```java
BreweryDBClient client = new BreweryDBClient("api-key");
```

### Operating on a `Result`

All client methods return a `Result`. Each result has metadata about the request, such as the page number, total results, total number of pages, a message, and a status.

To retrieve the data from a result, call the `getData()` method.

### Retrieving a list of beers

```java
GetBeersRequest request = BeerRequest.getBeers()
            .withName("Newcastle*")
            .withBreweries()
            .withOrder(BeerOrder.NAME)
            .withSort(SortDirection.DESCENDING)
            .build();

GetBeersResult result = client.getBeers(request);
List<Beer> beers = result.getData();
```

### Retrieve a beer by ID

```java
GetBeerResult result = client.getBeer("beerId");
Beer beer = result.getData();

// Customize the request
GetBeerRequest request = BeerRequest.getBeer()
    .withBreweries()
    .withIngredients()
    .build();
GetBeerResult result = client.getBeer("7ET5OY", request);
Beer beer = result.getData();
```

### Retrieve a random beer

```
GetRandomBeerResult result = client.getRandomBeer();
Beer beer = result.getData();
```

### Retrieve a list of breweries

```java
GetBreweriesRequest request = BreweryRequest.getBreweries()
    .withName("*")
    .withEstablished("2016")
    .withOrder(BreweryOrder.NAME)
    .withSort(SortDirection.ASCENDING)
    .withLocations()
    .build();

GetBreweriesResult result = client.getBreweries(request);
List<Brewery> breweries = result.getData();
```

### Retrieve a single brewery

```java
GetBreweryResult result = client.getBrewery("breweryId");
Brewery brewery = result.getData();
```

## Asynchronous usage

All methods also have an asynchronous implementation, which can be used by calling the `xxxAsync` version of the method. For example:

```java
Future<GetRandomBeerResult> future = client.getRandomBeerAsync();

// do some other work

GetRandomBeerResult result = future.get();
Beer beer = result.getData();
```
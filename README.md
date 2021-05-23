# The Movie DDB android app

## Build the project

    - create in project root new file with name private.properties
    - put there your TMDB API in format API_KEY="[your key here]"

## What is not done

    - caching loaded data inside repository
    - pagination on main list
    - not handled errors such as no connection, not found ...
    - DI for remote module
    - add proguard configuration
    - detail - can be improved by return detail response data immediatelly and and request for collectiion
    - but in current implementation all necessary data are in MovieOverview
    - in terms of UX would be nice to have transition on image
    - RemoteService module should contains Config for initialise OkHttpClient with cache from app module as well as pass base url from app/config.xml

## Screenshots && video

### Application
![Application](https://github.com/rslama/themoviedb/blob/main/screenshots/application.gif)
### Now playing movies list
![Now playing movie](https://github.com/rslama/themoviedb/blob/main/screenshots/list.png)
                           
### Movie detail without collection
![Detail without collection](https://github.com/rslama/themoviedb/blob/main/screenshots/detail_without_collection.png)

### Movie detail with collection
![Detail with collection](https://github.com/rslama/themoviedb/blob/main/screenshots/detail_with_collection.png)


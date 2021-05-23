# The Movie DDB android app

## Build the project

    - create in project root new file with name private.properties
    - put there your TMDB API in format API_KEY="[your key here]"


#what is not done

    - caching loaded data inside repository
    - pagination on main list
    - not handled errors such as no connection, not found ...
    - DI for remote module
    - add proguard configuration
    - detail - can be improved by return detail response data immediatelly and and request for collectiion
    - but in current implementation all necessary data are in MovieOverview
    - in terms of UX would be nice to have transition on image
    - RemoteService module should contains Config for initialise OkHttpClient with cache from app module as well as pass base url from app/config.xml
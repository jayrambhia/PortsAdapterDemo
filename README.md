# Hexagonal / Ports and Adapters architecture

This is a demo project which uses the ports and adapters architecture. The aim of this project is to use this architecture in an android app with the best practices. The benefit of the hexagonal architecture is to swap out components and make testing easier. With that in mind, this project tries to create the components in such a way that they can be swapped out and the project will demonstrate that.

## Architecture

There are 4 key aspects of this architecture.

1. Hexagon => Contains business logic
2. Driver => Connects to the hexagon to perform tasks
3. Driven => Connected to the hexagon to perform non-business related tasks (side effects, api, persistence, etc)
4. Actor => UI / User who wants to perform tasks

### Hexagon

This component / module contains all the business logic. It exposes **ports** for the driver to connect => `Driver Port`. If it requires additional functionalities that are not part of the business logic, it exposes **ports** for other (driven) modules to connect to it => `Driven Port`.

Essentially, hexagon can be considered as a component with one input/output socket and multiple other sockets which are inconsequential to the driver/user resulting in isolation. Since to connect to a hexagon we just need ports, theortically, it can also be replaced completely with another hexagon component.

### Driver

This is the component which is directly connected to the UI/User. All the actions are dispatched to the driver. The driver connects to the hexagon to perform these _tasks_ via the **ports** exposed by the hexagon. This component would define an adapter which will be used to connect to the hexagon.

### Driven

This is the component which the hexagon uses to perform certain tasks that are not in the realm of core business logic. eg. API calls, Db calls, etc. The driven component would connect to the hexagon via the **ports** exposed by the hexagon. This component would define an adapter which the hexagon will use.

----

With this structure, the driver component and the driven component are in total isolation of each other. Each of these components connect via ports, making each one of it completely replaceable. The **driven** component depends on the hexagon for the port definitions. The **driver** component depends on the hexagon to perform tasks. The hexagon does not depend on anything (theoretically). It would still need to know which driven adapter to call. This can potentially be avoided if some other module injects the driven adapter to the hexagon.

---------------

# Application

While theoretically the architecture sounds really promising, implementing for an **Android** app can be challenging and this is what this project tries to do.

## Structure

This is a multi-module application with each of the component having its own module. While it is possible to have separate sets of modules for different features, I have not done that yet.

## Modules

### hexagon

This module contains the necessary business logic. Each feature has its own hexagon module which exposes driver and driven ports. The component **defines** and implements the driver ports so that a driver can connect to it. It also **defines** the driven port so that the driven component can implement an adapter which the hexagon will use.

Each hexagon component is free to define how it wants to interact with the driver port (ie. updates). It can have callbacks, events, streams, etc.

```
interface ImageSearchDriverPort {
  fun search(query: String)
}

interface ImageSearchDrivenPort {
  fun search(query: String): List<Image>?
}

class ImageSearchModule(val port: ImageSearchDrivenPort) : ImageSearchDriverPort {
  override fun search(query: String) {
    // dispatch loading state
    val images = port.search(query)
    // dispatch new state with images
  }
}
```

Although, a component in *hexagon* implements `driver port`, it is **not** an adapter. The adapter is the component which will call the hexagon component.

### driver

This module contains the components which would connect the UI/user to the hexagon. This is the driver adapter. Since this is an android application, I have decided to use `ViewModel` and the ViewModel will be used as a driver adapter. Activity / Fragment are considered as UI.
UI will dispatch an action to the adapter and the adapter will connect to the hexagon via ports.

```
class ImageSearchViewModel(val port: ImageSearchDriverPort) : ViewModel() {
  init {
    port.addCallback { state ->
      // Dispatch updates to UI
    }
  }

  fun search(query: String) {
    port.search(query)
  }
}
```

The ViewModel here merely acts as an adapter to connect to the hexagon and does not contain any business logic.

### driven

This module contains the components which are used by the hexagon to perform certain tasks that are not in the realm of the business logic. The component will implement the driven port and it becomes a driven adapter.

```
class FlickrSearch : ImageSearchDrivenPort {
  override fun search(query): List<Image>? {
    // Gather more params eg. API key, auth headers, etc.
    // Make an API call and return results.
  }
}
```

`FlickrSearch` is a driven adapter which will make an API call and return list of images. With ports and adapters, we can replace this adapter with `GiphySearch` and the driver or the hexagon would not know the difference.

The only responsibility of this adapter is to call the Flickr API. Even this adapter can have use a port which will make the API call (delegating responsibilities). We can use `Okhttp` or `retrofit` or any other piece of code to make an API call and it would be isolated from the driver and the hexagon.

### network

This module exposes **ports** and **adapters** for making API calls.

```
interface NetworkPort {
  fun <T> get(url: String, params: Map<String, String>): NetworkResult<T>
}

class OkhttpRequests : NetworkPort {
  override fun <T> get(url: String, params: Map<String, String>): NetworkResult<T> {
    // Make actual API call.
  }
}
```

`FlickrSearch` will use `NetworkPort` to make a GET request. This module is not dependent on **hexagon**. Maybe, it makes sense to let the hexagon expose the network port and this module would just implement an adapter. (TODO)

### app

This is the application module for Android and it defines the UI. This is the main module of this project and it depends on all the modules as it needs to know which driver adapter to connect to the hexagon. It also provides the driven adapter to the hexagon via constructor injection.

```
viewModel = ViewModelProviders.of(
  activity,
  ImageSearchViewModelFactory(
    ImageSearchModule( // hexagon
      FlickrSearch( // driven
        OkhttpRequests() // network adapter
      )
  )
).get(ImageSearchViewModel::class.java) // driver

viewModel.search("nyan cat");
```
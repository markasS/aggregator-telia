#  aggregator-telia

`aggregator-telia` application is responsible for aggregating fake-json API (https://jsonplaceholder.typicode.com/) 
into GraphQL API. <br> The application was implemented according to Telia homework task requirements. 

### Setup

To run the application the following tools should be installed:
* Maven (https://maven.apache.org/download.cgi)
* JRE 11 (or later)

Steps to run the application:

1. To verify if automated tests are passed, execute in application root folder the following command `mvn test`
2. To build application artifacts run `mvn clean install`
3. Navigate to application `/target` folder
4. To run the application execute the following command: `java -jar aggregator-telia.jar `
5. Navigate to GraphQL playground by entering the following address in the browser: `http://localhost:8080/playground`

## Documentation

The detailed documentation of GraphQL queries and mutations is described in GraphQL playground (http://localhost:8080/playground) docs tab.
In this Readme we will describe usage examples for queries and mutations


### Queries

The following GraphQL queries can be performed:
`searchPosts`
`searchAlbums`
`searchTodos`
`searchUsers`

As an input queries accept the list of entity ids, if the list is empty, then all entities are returned.    

Example of `searchPosts` query:
```graphql
query {
  searchPosts(params :{ids: [1,2]}) {
    id,
    title,
    comments {
      body
      name
    }
    user {
      username
      email
      company {
        name
      }
      address {
        city
        geo {
          lat
          lng
        }
      }
    }
  }
}
```

### Mutations

Fake JSON API provides CRUD methods for the following entities: `Post`, `Todo`, `Album`, `Photo`, `Comment`, `User`
**Create**, **update**, **patch** and **delete** mutations are supported for every entity.

`Post` **create** mutation:

```graphql
mutation {  
  createPost(post : {body :"test", id: 1, userId: 100, title: "title"}) {
    title
    body
  }
}
 ```

`Post` **update** mutation:

```graphql
mutation {  
  updatePost(post : {body :"test", id: 1, userId: 100, title: "title"}) {
    title
    body
  }
}
```

`Post` **patch** mutation (differently from update operation not all fields are required):

```graphql
mutation {  
  patchPost(patchPostInput : {
    body : "Test"
  }, id: 1) {
    body
    id
    title
  }
}
```

`Post` **delete** mutation
```graphql
mutation {  
  deletePost(id: 1)
}
```




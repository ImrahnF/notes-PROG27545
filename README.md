# Springbok Application and Notes
This repository serves as a learning resource for me to enhance my comprehension of the course `PROG27545 Web Application Design & Implementation`, which covers approximately weeks 1-7 (inclusive).

The repository contains a Springboot application that attempts to implement the concepts learned in class. It provides explanations along the way and includes examples that refer to the project for a better understanding. 

## Annotations:
- `@Controller`
  - This annotation tells Spring that the class will handle **HTTP Requests**. It is responsible for returning views, such as html files (index.html). It is marked as a **controller** in the ***Model-View Controller (MVC)*** Architecture.
  - Controllers are responsible for taking user inputs and processing it using the model and then returning a view (HTML pages).

- `@GetMapping("/")`
  - This annotation is used to handle **HTTP GET** requests. In this case, it will run the method when it is at the default/base url `localhost:8080/`. 
  - Often used for fetching data or rendering HTML views/pages.
  - At the end of the method under the `GetMapping("/")`, it runs `return "index"` which tells the view to load the `index.html` page,

- `PostMapping("/add")`
  - This annotation is used to handle **HTTP POST** requests, often done through submitting data to the server (such as a form submission). In this case, it can be imagined as filling out a task form and submitting it to the server to add it.
  - The `/add` URL is specified in an HTML form's `action` attribute and specifies that it is a `POST` request. *Below is an HTML form*:
  ```html
  <form action="/add" method="POST">
  ``` 
  - The method under the mapping also returns the `"redirect:/"` which simply redirects the user back to the main page to display the updated list.

These annotations are implemented in the 
`TaskController.java` file [here](https://github.com/ImrahnF/todo-list-notes-PROG27545/blob/main/src/TodoListApplication/src/main/java/sheridan/omrahn/todolistapplication/controller/TaskController.java).

## Return Statements in Annotation Methods
Under `@GetMapping` and `@PostMapping`, there are methods that eventually return something:
### `return "index"`
```java
@GetMapping("/")
public String showTasks(...)
  // ... 
  return "index"; 
}
```
This is used to specify which view/template (in this case, an HTML file) to render when the method under `GetMapping("/")` is called. Since this line runs when the user is at the base/default URL, it returns `"index"` which corresponds to the `"index.html"` file in the `templates/` folder located [here](https://github.com/ImrahnF/todo-list-notes-PROG27545/tree/main/src/TodoListApplication/src/main/resources/templates).

What this basically means in terms of **this project** is that when the user is at the URL `localhost:8080/`, it runs the `showTasks()` method that eventually returns `"index"` to load/render the `index.html` page.


### `return "redirect:/"`
```java
@PostMapping("/add")
public String addTask(...)
  // ... 
  return "redirect:/"; 
}
```
Although similar to the previous return statement, this tells Spring to **redirect** instead of rendering a page. In this case, it redirects to the default URL. After the redirect, it will then be handled by the previous method under `@GetMapping("/")`.

These return statements are implemented in the 
`TaskController.java` file [here](https://github.com/ImrahnF/todo-list-notes-PROG27545/blob/main/src/TodoListApplication/src/main/java/sheridan/omrahn/todolistapplication/controller/TaskController.java).

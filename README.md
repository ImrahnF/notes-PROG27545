# Task.java
This class is just there to hold each individual "Task" element. It has 
`description` and `completed` of the datatypes `String` and `boolean`. 

# TaskController.java
This file is crucial for handling HTTP requests and managing tasks. Here's a breakdown of the concepts:

## Annotations:
- `@Controller`
  - This annotation tells Spring that the class will handle **HTTP Requests**. It is responsible for returning views, such as html files (index.html). It is marked as a **controller** in the ***Model-View Controller (MVC)*** Architecture.
  - Controllers are responsible for taking user inputs and processing it using the model and then returning a view (HTML pages).

- `@GetMapping("/")`
  - This annotation is used to handle **HTTP GET** requests. In this case, it will run the moethod when it is at the default/base url `localhost:8080/`. 
  - Often used for fetching data or rendering HTML views/pages.
  - At the end, it runs `return "index"` which tells the view to load the `index.html` page,

- `PostMapping("/add")`
  - This annotation is used to handle **HTTP POST** requests, often done through submitting data to the server (such as a form submission). In this case, it can be imagined as filling out a task form and submitting it to the server to add it.
  - The `/add` URL is specified in an HTML form's `action` attribute and its method would be a `POST`. *Below is an HTML form*:
  ```html
  <form action="/add" method="POST">
  ``` 
  - This also returns the `"redirect:/"` which simply redirects the user back to the main page to display the updated list.

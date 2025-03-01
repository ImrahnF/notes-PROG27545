# Task.java
This class is just there to hold each individual "Task" element. It has 
`description` and `completed` of the datatypes `String` and `boolean`. 

# TaskController.java
This file is crucial for handling HTTP requests and managing tasks. Here's a breakdown of the concepts:

## Annotations:
- `@Controller`
  - This annotation tells Spring that the class will handle **HTTP Requests**. It is responsible for returning views, such as html files (index.html). It is marked as a **controller** in the ***Model-View Controller (MVC)*** Architecture.
  - Controllers are responsible for taking user inputs and processing it using the model and then returning a view (HTML pages).

# Springbok Application and Notes
This repository serves as a learning resource for me to enhance my comprehension of the course `PROG27545 Web Application Design & Implementation`, which tries to cover weeks 1-7 (inclusive).

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

## Annotations in Method Arguments
### (GET) Requests With `@RequestParam` and `Model`
Using `@RequestParam` + `Model` in a GET request reads data from the URL directly and stores it in a **model**. This is done only once and data does **not persist** across requests.

**Example:** User types in URL or clicks link directed to `localhost:8080/main?data=hello`
```java
@GetMapping("/main")
public String mainPage(@RequestParam("data") String data, Model model) {
    model.addAttribute("message", data);
    return "main";
}
```
The controller reads the `data=hello` and adds it as an attribute to `message`, letting `message` be equal to `hello` and `main.html` is loaded. This means the `message` value (`hello`) is available to be used in the `main.html` view.

- It only works if "data" exists in the **URL query**.
- It **does not persist** across requests; it's just sent to the view.

### (GET) Requests With `@RequestParam` and `HttpSession`
Same as before, but using a `@RequestParam` + `HttpSession` now stores the value in a **session**, making it persistent.

**Example:** Request URL `localhost:8080/main?data=hello`
```java
@GetMapping("/main")
public String mainPage(@RequestParam("data") String data, HttpSession session) {
    session.setAttribute("savedData", data);
    return "main";
}
```
The controller reads the URL again, saves the `data` into the **session** instead of a **model**. Now, whenever another page is loaded, the `savedData` value (`hello`) will still persist until the session ends.
- It still requires data in the URL initially, but then it gets stored in the session.
- The **data persists** until the session **expires** or is **cleared**.

### (POST) Requests With `@RequestParam` and `Model`
Using `@RequestParam` + `Model` in a **POST** request **does not** read data from the URL. Instead, it only looks at the **request body** (such as form data).
```java
@PostMapping("/main")
public String handlePost(@RequestParam("data") String data, Model model) {
    model.addAttribute("message", data);
    return "main";
}
```
Trying to open the URL `localhost:8080/main?data=hello`, the method will **NOT** run. Instead, the form must send data using **POST**. Below is an example HTML form:
```html
<form action="/main" method="post">
    <input type="text" name="data">
    <button type="submit">Submit</button>
</form>
```

This form submits the contents of the **textbox input** with the name `data` in the **request body**. This is then handled by the `@PostMapping` controller above.
- **GET** and **POST** are completely separate: You can have both on the same URL, but they won’t interfere.
- **POST** ignores URL parameters and only reads from the request body. In this case, it reads `data`.

### (POST) Requests With `@RequestParam` and `HttpSession`
Using `@RequestParam` + `HttpSession` in a **POST** is the same as the previous example, but it just stores it in a session, making it persistent.
```java
@PostMapping("/main")
public String handlePost(@RequestParam("data") String data, HttpSession session) {
    session.setAttribute("savedData", data);
    return "redirect:/main";
}
```

### How Does `@SessionAttribute` Work?
This annotation does **NOT** read from the **URL** or **Request Body**. It only reads from **existing session data**.
**Example:**
```java
@GetMapping("/dashboard")
public String dashboard(@SessionAttribute(name = "savedData", required = false) String savedData, Model model) {
    model.addAttribute("message", savedData);
    return "dashboard";
}
```
`@SessionAttribute` is a shortcut for retrieving session attributes without explicitly calling `session.getAttribute`.


This example assumes that there is existing **session data** (`savedData`), set by a previously manipulated `HttpSession`. If the `savedData` does not exist, it returns `null` (or causes an error if `required = true`).

### Summary:
- `@RequestParam` in `@GetMapping`:
  - Reads data from URL (eg. `/data=hello`)
  - Does **NOT** persist across multiple requests and is a one-time read.
- `@RequestParam` + `Model` in `@GetMapping`:
  - Reads data from URL (eg. `/data=hello`)
  - Data is only passed to a view
- `@RequestParam` + `HttpSession` in `@GetMapping`:
  - Reads data from URL (eg. `/data=hello`) and is **stored in a session**.
  - Data persists
- `RequestParam` in `@PostMapping`
  - Reads data from a form submission with a **POST** request (not from the URL).
  - Does not persist - just reads **POST** request.
- `RequestParam` + `HttpSession` in `@PostMapping`
  - Reads form data and is stored in the **session**, allowing persistence across requests.
- `SessionAttribute`
  - Reads **existing session data**, continuing data persistence. 
  - Just a shortcut for accessing session data.


## Return Statements in Annotations
Under `@GetMapping` and `@PostMapping`, there are methods that eventually return something. 
### `return "index"`
```java
@GetMapping("/")
public String showTasks(...)
  // ... 
  return "index"; 
}
```
This is used to specify which view/template (in this case, an HTML file) to render when the method under `GetMapping("/")` is called. Since this line runs when the user is at the base/default URL, it returns `"index"` which corresponds to the `"index.html"` file in the `templates/` folder.

What this basically means in terms of **this project** is that when the user is at the URL `localhost:8080/`, it runs the `showTasks()` method that eventually returns `"index"` to load/render the `index.html` page.


### `return "redirect:/"`
```java
@PostMapping("/add")
public String addTask(...)
  // ... 
  return "redirect:/"; 
}
```
Although similar to the previous return statement, this tells Spring to **redirect** instead of rendering a page. In this case, it redirects to the default URL. After the redirect, it will then be handled by the previous method under `@GetMapping("/")`. We could also edit this to `redirect:/main`.

These return statements are implemented in the 
`TaskController.java` file [here](https://github.com/ImrahnF/todo-list-notes-PROG27545/blob/main/src/TodoListApplication/src/main/java/sheridan/omrahn/todolistapplication/controller/TaskController.java).

## HTTP Sessions & Models
**HTTP Sessions** is a way to store data across several HTTP requests coming from the same user. This is temporary "storage" that last for the user's session on the website. It is useful for tracking things such as authentication statuses, shopping carts, etc. However, in this case, it is used to track our **To-do list**.

### How is Data Sent Through Sessions?
The data we are working with is sent through sessions using the `HttpSession` interface, allowing us to *store* and *retrieve* data tied to a session. 

**Adding** attributes to a session is done through `session.addAttribute("key", value)` and retrieving is done through `session.getAttribute("key")` where:
- `"key"` refers to the attribute **name**
- `value` refers to the **value** of the attribute.

An **example:**
```java
// Save list of student names (Strings) to session
session.setAttribute("Students", studentList);
// Retrieve students from session
List<String> updatedStudentList = (List<String>) session.getAttribute("Students");
```
This example shows us setting a list of student strings (`studentList`) to the `"Students"` attribute in the session which. It then creates a new `updatedStudentsList` list and populates it with what we just populated the `Students` attribute with.

### How is this different from models?
The difference between a **Session** and **Model** is the fact that a **Model** is used for temporary data that is passed from the controller to a view (i.e. a web page) that will *only* be used to display data.

A **Session**, as mentioned, is used to store data that needs to persist across several HTTP requests during the user's session on the website. 

**Conclusion:** While models are usually for temporary data (one-time requests), sessions are used to store data across the entire user's interaction with the app. The session data can be updated and accessed at any point during the user’s session.

## HTTP Request Types
- **GET Request**:
  - A **GET** request is triggered when a user visits a URL by typing it out or clicking a link that directs them to it. This doesn't change any server data.
- **POST Request:**
  - A **POST** request is triggered by sending data directly to the server, specifying that it is a **POST** request (not through URLs). This is done through ways such as submitting an HTML form and specifying what method:
  ```html
  <form action="/add" method="POST">
  ``` 

### What if there is a GET and POST on Same URL?
If there are GET and POST handlers for the same URL, it is differentiated based on the specified HTTP request type.

**Example:**

This HTML form does a **POST** request to the URL `localhost:8080/test`.
```html
<form action="/test" method="post">
    <input type="text" name="data">
    <button type="submit">Submit</button>
</form>
```
The input field has a `name="data"` attribute. When the form is submitted, the text in the input field will be sent with the key `data`. 

When the form is submitted, it should run the `processForm()` method under the `PostMapping("/test")` annotation as we are doing a **POST** request. The method under `@GetMapping` would run if we were to click a link that brought us to `localhost:8080/test` or if we were to directly type out the URL.

The **POST** request would look something like this: `data=enteredValue`.
```java
@Controller
public class TestController {

    // This will handle the GET request to localhost:8080/test
    @GetMapping("/test")
    public String showForm() {
        return "formPage";  // loads formPage.html
    }

    // This will handle the POST request to localhost:8080/test
    @PostMapping("/test")
    public String processForm(@RequestParam String data) {
        // Process the form data here
        return "resultPage";  // load resultPage.html
    }
}
```
The `@RequestParam` annotation looks for a request parameter named `data` which matches the form's `name="data"`. This is passed as the `data` argument in the `processForm()` method which then can be used as needed.

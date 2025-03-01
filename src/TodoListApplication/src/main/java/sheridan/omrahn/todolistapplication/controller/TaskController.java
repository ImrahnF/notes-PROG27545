package sheridan.omrahn.todolistapplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import sheridan.omrahn.todolistapplication.domain.Task;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    // Display current list of tasks in root URL (localhost:8080/)
    @GetMapping("/")
    public String showTasks(Model model, @SessionAttribute(name = "tasks", required = false) List<Task> tasks) {
        // Initialize task list if empty
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        // Passes data to the view using model.addAttribute(). Adds `List<Task> tasks` to the "tasks" attribute.
        // 'add' attribute does not persist across sessions. Usually just a one way thing
        model.addAttribute("tasks", tasks);
        return "index"; // Thymelef template to return index.html
    }

    // When there is a 'POST' request for the localhost:8080/add URL, it comes with a "description" parameter.
    @PostMapping("/add")
    public String addTask(@RequestParam String description, HttpSession session) {
        // Get the session's 'tasks' attribute/
        List<Task> tasks = (List<Task>)session.getAttribute("tasks");

        // Initialize if null
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        // Add new task based off of the description parameter.
        tasks.add(new Task(description));
        // update or create "tasks" attribute for the session. can persist.
        session.setAttribute("tasks", tasks);
        return "redirect:/"; // Redirect to root directory
    }

    // 'POST' request for marking a task as complete
    @PostMapping("/complete")
    public String completeTask(@RequestParam int index, HttpSession session) {
        List<Task> tasks = (List<Task>)session.getAttribute("tasks");
        // Ensure it is nonempty and not out of index
        if (tasks != null && index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markAsCompleted();
            session.setAttribute("tasks", tasks); // Update tasks
        }
        return "redirect:/"; // Redirect back to main page
    }

    // Same thing, just for incomplete tasks
    @PostMapping("/incomplete")
    public String incompleteTask(@RequestParam int index, HttpSession session) {
        List<Task> tasks = (List<Task>)session.getAttribute("tasks");
        // Ensure it is nonempty and not out of index
        if (tasks != null && index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.unmarkAsCompleted();
            session.setAttribute("tasks", tasks); // Update tasks
        }
        return "redirect:/"; // Redirect back to main page
    }

}

# Bruh User Guide

<img width="1065" height="946" alt="image" src="https://github.com/user-attachments/assets/eb765a71-1fc8-47ed-a3c9-967084018720" />

Say hello to Bruh - your new laid-back but totally reliable task manager. Bruh’s here to make your life easier by keeping track of your to-dos, deadlines, and events, all while keeping the vibe casual and stress-free. Whether you’re organizing your day, marking off tasks, or even finding specific notes, Bruh’s got you covered. And don’t worry, Bruh’s got an answer for every command you throw at it, with no judgment.


## Adding todo tasks

Add a basic task with no due date. Useful for tasks that don’t need a deadline but still need doing.

**Example usage:**

```

todo <task-name>

```

Example:
```

todo Read book

```

**Expected output:**
```

Got it. I've added this task:
[T][ ] Read book
Now you have 3 tasks in the list.

```

---

## Adding deadlines

Add a task with a deadline (due *by* a specific date and time).

**Example usage:**

```

deadline <task-name> /by yyyy-MM-dd HH:mm

```

Example:
```

deadline Submit lab report /by 2025-09-30 23:59

```

**Expected output:**
```

Got it. I've added this task:
[D][ ] Submit lab report (by: Sep 30 2025 11:59 PM)
Now you have 4 tasks in the list.

```

---

## Adding events

Add a task that occurs during a time window (start and end).

**Example usage:**

```

event <task-name> /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm

```

Example:
```

event CS lecture /from 2025-09-20 10:00 /to 2025-09-20 12:00

```

**Expected output:**
```

Got it. I've added this task:
[E][ ] CS lecture (from: Sep 20 2025 10:00 AM to: Sep 20 2025 12:00 PM)
Now you have 5 tasks in the list.

```

---

## Marking tasks

Mark a task as done.

**Example usage:**

```

mark <task-number>

```

Example:
```

mark 2

```

**Expected output:**
```

Nice! I've marked this task as done:
[T][X] Read manga

```

---

## Unmarking tasks

Mark a task as not done (undo a `mark`).

**Example usage:**

```

unmark <task-number>

```

Example:
```

unmark 2

```

**Expected output:**
```

Sike! Task actually not done yet:
[T][ ] Read manga

```

---

## Deleting tasks

Delete a task from your list.

**Example usage:**

```

delete <task-number>

```

Example:
```

delete 3

```

**Expected output:**
```

Noted. I've removed this task:
[D][ ] Submit lab report
Now you have 4 tasks in the list.

```

---

## Listing tasks

List all your tasks. You can also view tasks by a specific date.

**Example usage:**

```

list
list yyyy-MM-dd

```

Examples:
```

list
list 2025-09-20

```

**Expected output:**
```

1. [T][X] Read book
2. [E][ ] CS lecture (from: Sep 20 2025 10:00 AM to: Sep 20 2025 12:00 PM)

```

---

## Finding tasks

Search for tasks using a keyword.

**Example usage:**

```

find <keyword>

```

Example:
```

find lecture

```

**Expected output:**
```

Here are the matching tasks in your list:

1. [E][ ] CS lecture (from: Sep 20 2025 10:00 AM to: Sep 20 2025 12:00 PM)

```

---

## Sorting tasks

Sort tasks either by date or alphabetically.

**Example usage:**

```

sort date
sort alphabet

```

Examples:
```

sort date
sort alphabet

```

**Expected output:**
```

Tasks have been sorted by date.

```
or
```

Tasks have been sorted alphabetically.

```

---

## Exiting the app

Exit the app. Say goodbye to Bruh.

**Example usage:**

```

bye

```

**Expected outcome:**
Closes the app.



---

## Common Errors

| Error Message | Explanation |
|---------------|-------------|
| `Idk what u tryna say...` | Unknown command. Try a valid one like `todo`, `deadline`, etc. |
| `task number cannot be empty` | You forgot the task number. This happens with `mark`, `unmark`, `delete`. |
| `Invalid date format` | Your date or time format is wrong. Use `yyyy-MM-dd` or `yyyy-MM-dd HH:mm`. |
| `Invalid format for deadline/event` | You’re missing `/by`, `/from`, or `/to` - or used the wrong format. |

---
```

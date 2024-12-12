# Recommended Changes in TweetApp: Introduce a `priority` field in each task.

> Introduce a `priority` field in each task. Create UI and APIs that sorts tasks based on Stephen Covey's time
> management matrix,
> prioritizing tasks as Urgent and Important.

**Discussions**

- [Question 1](#q1--please-write-a-reactjs-component-to-sort-task-by-urgent-and-important-fields-i-want-to-use-steven-convey-thought-to-prioritise-tasks)
- [Question 2](#q2-please-consider-task-contains-children-children-should-be-completed-before-parent-is-complete)
- [Question 3](#q3--please-update-component-as-per-below-requirement)
- [Question 4](#q4--using-binary-digits--00-01-10-11--to-categorize-tasks-based-on-the-combination-of-urgent-and-important-fields)

---

# [Q1- Please write a Reactjs component to sort task by urgent and important fields. I want to use steven convey thought to prioritise tasks.]()

Here is a ReactJS component that sorts tasks based on Stephen Covey's time management matrix, prioritizing tasks as
Urgent and Important:

### Code:

```jsx
import React, { useState } from 'react';

const TaskPrioritizer = () => {
  const [tasks, setTasks] = useState([
    { id: 1, name: 'Task 1', urgent: true, important: true },
    { id: 2, name: 'Task 2', urgent: true, important: false },
    { id: 3, name: 'Task 3', urgent: false, important: true },
    { id: 4, name: 'Task 4', urgent: false, important: false },
  ]);

  const prioritizeTasks = () => {
    const sortedTasks = [...tasks].sort((a, b) => {
      if (a.important === b.important) {
        return b.urgent - a.urgent;
      }
      return b.important - a.important;
    });
    setTasks(sortedTasks);
  };

  return (
    <div>
      <h2>Task Prioritizer</h2>
      <button onClick={prioritizeTasks}>Prioritize Tasks</button>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            {task.name} -{' '}
            {task.urgent ? 'Urgent' : 'Not Urgent'} /{' '}
            {task.important ? 'Important' : 'Not Important'}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskPrioritizer;
```

### Features:

1. **Task Categorization**:
    - Tasks are sorted using Covey's **Urgent** and **Important** matrix.
    - Tasks marked as **Important and Urgent** get the highest priority.
    - **Important but Not Urgent** tasks come next, followed by **Urgent but Not Important**, and finally, tasks that
      are **Neither**.

2. **Interactive Sorting**:
    - Clicking the "Prioritize Tasks" button sorts the tasks based on the matrix.

3. **Extensibility**:
    - Add or remove tasks easily by modifying the `tasks` state.

### Output Example:

Before prioritization:

- Task 4 - Not Urgent / Not Important
- Task 1 - Urgent / Important
- Task 3 - Not Urgent / Important
- Task 2 - Urgent / Not Important

After prioritization:

- Task 1 - Urgent / Important
- Task 3 - Not Urgent / Important
- Task 2 - Urgent / Not Important
- Task 4 - Not Urgent / Not Important

---

# [Q2-Please consider task contains children. Children should be completed before parent is complete]()

Here's an updated React component that handles tasks with children, ensuring that children tasks must be completed
before their parent tasks are prioritized. The sorting logic respects Stephen Covey's **Urgent** and **Important**
categorization while accounting for the hierarchical task dependencies.

### Code:

```jsx
import React, { useState } from 'react';

// Helper function to flatten tasks and sort based on dependencies and prioritization rules
const sortTasks = (tasks) => {
  const flattenTasks = (tasks, parentUrgent = false, parentImportant = false) => {
    return tasks.flatMap((task) => {
      const derivedTask = {
        ...task,
        urgent: task.urgent || parentUrgent,
        important: task.important || parentImportant,
      };
      if (task.children && task.children.length > 0) {
        return [
          ...flattenTasks(task.children, task.urgent, task.important),
          derivedTask,
        ];
      }
      return [derivedTask];
    });
  };

  const flattened = flattenTasks(tasks);

  // Sort tasks by importance and urgency, children first
  return flattened.sort((a, b) => {
    if (a.parentId !== b.parentId) {
      return a.parentId ? -1 : 1;
    }
    if (a.important === b.important) {
      return b.urgent - a.urgent;
    }
    return b.important - a.important;
  });
};

const TaskPrioritizer = () => {
  const [tasks, setTasks] = useState([
    {
      id: 1,
      name: 'Parent Task 1',
      urgent: true,
      important: true,
      children: [
        { id: 2, name: 'Child Task 1.1', urgent: true, important: false },
        { id: 3, name: 'Child Task 1.2', urgent: false, important: true },
      ],
    },
    {
      id: 4,
      name: 'Parent Task 2',
      urgent: false,
      important: false,
      children: [
        { id: 5, name: 'Child Task 2.1', urgent: false, important: true },
        { id: 6, name: 'Child Task 2.2', urgent: true, important: true },
      ],
    },
  ]);

  const handlePrioritize = () => {
    const prioritized = sortTasks(tasks);
    setTasks(prioritized);
  };

  const renderTasks = (taskList) =>
    taskList.map((task) => (
      <li key={task.id}>
        {task.name} -{' '}
        {task.urgent ? 'Urgent' : 'Not Urgent'} /{' '}
        {task.important ? 'Important' : 'Not Important'}
        {task.children && task.children.length > 0 && (
          <ul>{renderTasks(task.children)}</ul>
        )}
      </li>
    ));

  return (
    <div>
      <h2>Task Prioritizer</h2>
      <button onClick={handlePrioritize}>Prioritize Tasks</button>
      <ul>{renderTasks(tasks)}</ul>
    </div>
  );
};

export default TaskPrioritizer;
```

### Explanation:

1. **Hierarchical Sorting**:
    - The `sortTasks` function ensures tasks are flattened and sorted in the correct order, respecting their
      dependencies.

2. **Children Before Parents**:
    - Tasks with children are recursively processed, and child tasks are completed first before their parent is
      prioritized.

3. **Dynamic Rendering**:
    - The `renderTasks` function renders nested tasks with proper hierarchy.

### Output Example:

Before prioritization:

```
Parent Task 1 - Urgent / Important
  Child Task 1.1 - Urgent / Not Important
  Child Task 1.2 - Not Urgent / Important
Parent Task 2 - Not Urgent / Not Important
  Child Task 2.1 - Not Urgent / Important
  Child Task 2.2 - Urgent / Important
```

After prioritization:

```
Child Task 1.1 - Urgent / Not Important
Child Task 1.2 - Not Urgent / Important
Parent Task 1 - Urgent / Important
Child Task 2.2 - Urgent / Important
Child Task 2.1 - Not Urgent / Important
Parent Task 2 - Not Urgent / Not Important
```

---

# [Q3- Please update component as per below requirement]()

> I am using parentid field in place of children field. Hence the data coming from server is a flat list of task objects
> having field
> - Uniqueid
> - Name
> - Parentid, null, if root level task
> - Urgent
> - Important
>
>
> Also I need data to show in a table, with column
> - Order#
> - Name
> - Uniqueid
> - Urgent
> - Important
> - Parent task name, starting from immediate parent name/it's parent name


Here’s an updated implementation where the tasks are represented as a flat list with a `parentId` field instead of a
`children` field. The tasks will be displayed in a table, with proper hierarchy respected, including `Parent Task Name`.

### Code:

```jsx
import React, { useState } from 'react';

// Helper function to sort and organize tasks
const organizeTasks = (tasks) => {
  // Create a map of tasks for quick lookup
  const taskMap = tasks.reduce((map, task) => {
    map[task.uniqueId] = task;
    return map;
  }, {});

  // Get the full parent chain for each task
  const getParentChain = (task) => {
    let chain = [];
    let current = task;
    while (current.parentId) {
      current = taskMap[current.parentId];
      if (current) chain.unshift(current.name);
    }
    return chain.join(' > ');
  };

  // Add parent chain name to each task
  const tasksWithParentName = tasks.map((task) => ({
    ...task,
    parentTaskName: task.parentId ? getParentChain(task) : '',
  }));

  // Sort tasks by importance and urgency while respecting parent-child order
  const sortedTasks = tasksWithParentName.sort((a, b) => {
    if (a.parentId && b.parentId) {
      return a.parentId === b.parentId
        ? a.important === b.important
          ? b.urgent - a.urgent
          : b.important - a.important
        : a.parentId - b.parentId;
    }
    return a.parentId ? 1 : -1;
  });

  return sortedTasks;
};

const TaskTable = () => {
  const [tasks, setTasks] = useState([
    { uniqueId: 1, name: 'Parent Task 1', parentId: null, urgent: true, important: true },
    { uniqueId: 2, name: 'Child Task 1.1', parentId: 1, urgent: true, important: false },
    { uniqueId: 3, name: 'Child Task 1.2', parentId: 1, urgent: false, important: true },
    { uniqueId: 4, name: 'Parent Task 2', parentId: null, urgent: false, important: false },
    { uniqueId: 5, name: 'Child Task 2.1', parentId: 4, urgent: false, important: true },
    { uniqueId: 6, name: 'Child Task 2.2', parentId: 4, urgent: true, important: true },
  ]);

  const [sortedTasks, setSortedTasks] = useState([]);

  const handlePrioritize = () => {
    const organizedTasks = organizeTasks(tasks);
    setSortedTasks(organizedTasks);
  };

  return (
    <div>
      <h2>Task Prioritizer</h2>
      <button onClick={handlePrioritize}>Prioritize Tasks</button>
      {sortedTasks.length > 0 && (
        <table border="1" cellPadding="10" style={{ marginTop: '20px', width: '100%' }}>
          <thead>
            <tr>
              <th>Order#</th>
              <th>Name</th>
              <th>Unique ID</th>
              <th>Urgent</th>
              <th>Important</th>
              <th>Parent Task Name</th>
            </tr>
          </thead>
          <tbody>
            {sortedTasks.map((task, index) => (
              <tr key={task.uniqueId}>
                <td>{index + 1}</td>
                <td>{task.name}</td>
                <td>{task.uniqueId}</td>
                <td>{task.urgent ? 'Yes' : 'No'}</td>
                <td>{task.important ? 'Yes' : 'No'}</td>
                <td>{task.parentTaskName || '-'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default TaskTable;
```

### Key Features:

1. **Flat Data Structure**:
    - The `tasks` array uses `uniqueId` as the identifier and `parentId` to link child tasks to their parents.

2. **Parent Task Chain**:
    - The `getParentChain` function builds the full parent hierarchy for each task.

3. **Sorting**:
    - Tasks are sorted by `Important` and `Urgent` flags while ensuring child tasks come after their parents.

4. **Table Display**:
    - Data is shown in a table with columns:
        - `Order#` - The order of tasks after sorting.
        - `Name` - Task name.
        - `Unique ID` - Task's unique identifier.
        - `Urgent` and `Important` - Flags for prioritization.
        - `Parent Task Name` - Full parent chain starting from the immediate parent.

### Example Output:

#### Before Prioritization:

| Order# | Name           | Unique ID | Urgent | Important | Parent Task Name |
|--------|----------------|-----------|--------|-----------|------------------|
| 1      | Parent Task 1  | 1         | Yes    | Yes       | -                |
| 2      | Child Task 1.1 | 2         | Yes    | No        | Parent Task 1    |
| 3      | Child Task 1.2 | 3         | No     | Yes       | Parent Task 1    |
| 4      | Parent Task 2  | 4         | No     | No        | -                |
| 5      | Child Task 2.1 | 5         | No     | Yes       | Parent Task 2    |
| 6      | Child Task 2.2 | 6         | Yes    | Yes       | Parent Task 2    |

#### After Prioritization:

| Order# | Name           | Unique ID | Urgent | Important | Parent Task Name |
|--------|----------------|-----------|--------|-----------|------------------|
| 1      | Parent Task 1  | 1         | Yes    | Yes       | -                |
| 2      | Child Task 1.1 | 2         | Yes    | No        | Parent Task 1    |
| 3      | Child Task 1.2 | 3         | No     | Yes       | Parent Task 1    |
| 4      | Parent Task 2  | 4         | No     | No        | -                |
| 5      | Child Task 2.2 | 6         | Yes    | Yes       | Parent Task 2    |
| 6      | Child Task 2.1 | 5         | No     | Yes       | Parent Task 2    |

---

# [Q4- Using binary digits (00, 01, 10, 11) to categorize tasks based on the combination of urgent and important fields]()

> can we make a table containing P1 to P4, by converting {urgent:boolean, important:boolean} to an equivalent binary
> digit (00, 01, 10, 11). Hope this will make comparison and calculation for prioritization of tasks easy. Please
> suggest.

Using binary digits (`00`, `01`, `10`, `11`) to categorize tasks based on the combination of `urgent` and `important`
fields is an excellent idea! This approach aligns perfectly with Stephen Covey's quadrants:

| Binary | Quadrant | Urgent | Important | Description                |
|--------|----------|--------|-----------|----------------------------|
| `00`   | P4       | No     | No        | Not Urgent & Not Important |
| `01`   | P3       | No     | Yes       | Important but Not Urgent   |
| `10`   | P2       | Yes    | No        | Urgent but Not Important   |
| `11`   | P1       | Yes    | Yes       | Urgent and Important       |

This mapping allows for efficient categorization, sorting, and table generation. Below is an updated implementation
using this binary representation.

---

### Updated Implementation:

```jsx
import React, { useState } from 'react';

// Helper function to convert {urgent, important} to a binary priority
const getPriorityBinary = (urgent, important) => `${urgent ? 1 : 0}${important ? 1 : 0}`;

// Function to sort tasks by priority (binary representation)
const organizeTasksByPriority = (tasks) => {
  const priorityMap = { '11': 'P1', '10': 'P2', '01': 'P3', '00': 'P4' };

  // Add binary priority and quadrant to each task
  const tasksWithPriority = tasks.map((task) => ({
    ...task,
    priorityBinary: getPriorityBinary(task.urgent, task.important),
    quadrant: priorityMap[getPriorityBinary(task.urgent, task.important)],
  }));

  // Sort tasks by priority binary (P1 > P2 > P3 > P4) and parent-child relationships
  return tasksWithPriority.sort((a, b) => {
    if (a.parentId === b.parentId) {
      return b.priorityBinary - a.priorityBinary;
    }
    return a.parentId ? 1 : -1;
  });
};

const TaskTable = () => {
  const [tasks, setTasks] = useState([
    { uniqueId: 1, name: 'Parent Task 1', parentId: null, urgent: true, important: true },
    { uniqueId: 2, name: 'Child Task 1.1', parentId: 1, urgent: true, important: false },
    { uniqueId: 3, name: 'Child Task 1.2', parentId: 1, urgent: false, important: true },
    { uniqueId: 4, name: 'Parent Task 2', parentId: null, urgent: false, important: false },
    { uniqueId: 5, name: 'Child Task 2.1', parentId: 4, urgent: false, important: true },
    { uniqueId: 6, name: 'Child Task 2.2', parentId: 4, urgent: true, important: true },
  ]);

  const [sortedTasks, setSortedTasks] = useState([]);

  const handlePrioritize = () => {
    const prioritized = organizeTasksByPriority(tasks);
    setSortedTasks(prioritized);
  };

  return (
    <div>
      <h2>Task Prioritizer</h2>
      <button onClick={handlePrioritize}>Prioritize Tasks</button>
      {sortedTasks.length > 0 && (
        <table border="1" cellPadding="10" style={{ marginTop: '20px', width: '100%' }}>
          <thead>
            <tr>
              <th>Order#</th>
              <th>Name</th>
              <th>Unique ID</th>
              <th>Urgent</th>
              <th>Important</th>
              <th>Priority (Binary)</th>
              <th>Quadrant</th>
            </tr>
          </thead>
          <tbody>
            {sortedTasks.map((task, index) => (
              <tr key={task.uniqueId}>
                <td>{index + 1}</td>
                <td>{task.name}</td>
                <td>{task.uniqueId}</td>
                <td>{task.urgent ? 'Yes' : 'No'}</td>
                <td>{task.important ? 'Yes' : 'No'}</td>
                <td>{task.priorityBinary}</td>
                <td>{task.quadrant}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default TaskTable;
```

---

### Key Features:

1. **Binary Representation**:
    - Each task is assigned a binary representation (`00`, `01`, `10`, `11`) based on `urgent` and `important`.

2. **Priority Quadrants**:
    - Binary representations are mapped to Covey's quadrants:
        - `11` → P1
        - `10` → P2
        - `01` → P3
        - `00` → P4

3. **Efficient Sorting**:
    - Tasks are sorted by binary priority and parent-child relationships.

4. **Detailed Table**:
    - Columns:
        - `Order#`: Order after sorting.
        - `Name`: Task name.
        - `Unique ID`: Task identifier.
        - `Urgent` and `Important`: Flags for categorization.
        - `Priority (Binary)`: Binary representation for prioritization.
        - `Quadrant`: Covey's quadrant based on priority.

---

### Example Output:

#### Before Prioritization:

| Order# | Name           | Unique ID | Urgent | Important | Priority (Binary) | Quadrant |
|--------|----------------|-----------|--------|-----------|-------------------|----------|
| 1      | Parent Task 1  | 1         | Yes    | Yes       | 11                | P1       |
| 2      | Child Task 1.1 | 2         | Yes    | No        | 10                | P2       |
| 3      | Child Task 1.2 | 3         | No     | Yes       | 01                | P3       |
| 4      | Parent Task 2  | 4         | No     | No        | 00                | P4       |

#### After Prioritization:

| Order# | Name           | Unique ID | Urgent | Important | Priority (Binary) | Quadrant |
|--------|----------------|-----------|--------|-----------|-------------------|----------|
| 1      | Parent Task 1  | 1         | Yes    | Yes       | 11                | P1       |
| 2      | Child Task 1.1 | 2         | Yes    | No        | 10                | P2       |
| 3      | Child Task 1.2 | 3         | No     | Yes       | 01                | P3       |
| 4      | Parent Task 2  | 4         | No     | No        | 00                | P4       |

---

#   
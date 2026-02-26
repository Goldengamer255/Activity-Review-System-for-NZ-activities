# Operator Management System 🗺️

A terminal-based Operator Management System built in Java to manage tourism operators across New Zealand, handling activities and a full review pipeline using object-oriented programming principles.

---

## 🚀 Features

- **100+ tourism operator management** across New Zealand via CLI
- **16 CLI commands** for creating operators, managing activities, and processing reviews
- **3 review types** — Public, Private, and Expert — with unique fields and behaviour
- **Review endorsement, resolution, and image upload** support
- **Top activity rankings** per location based on aggregated public and expert reviews
- **Scalable architecture** projected to support 10,000+ reviews annually

---

## 🛠️ Tech Stack

- Java
- Object-Oriented Programming (OOP)
- Command Line Interface (CLI)

---

## 📂 Project Structure

```
├── src/
│   ├── Main.java                        # Entry point, CLI handler, command parser
│   ├── OperatorManagementSystem.java    # Core system logic
│   ├── MessageCli.java                  # CLI message definitions
│   └── ...                              # Operator, Activity, Review classes
└── README.md
```

---

## 🏃 How to Run

1. Clone the repository:
```bash
git clone https://github.com/YOURUSERNAME/operator-management-system.git
```

2. Compile the project:
```bash
javac src/*.java
```

3. Run the system:
```bash
java Main
```

4. Type `HELP` to see all available commands.

---

## 💻 Available Commands

| Command | Args | Description |
|---|---|---|
| `SEARCH_OPERATORS` | 1 | Search operators by keyword in name or location |
| `CREATE_OPERATOR` | 2 | Create a new operator with name and location |
| `VIEW_ACTIVITIES` | 1 | View all activities for an operator |
| `CREATE_ACTIVITY` | 3 | Create an activity with name, type, and operator ID |
| `SEARCH_ACTIVITIES` | 1 | Search activities by keyword |
| `ADD_PUBLIC_REVIEW` | 1 | Add a public review for an activity |
| `ADD_PRIVATE_REVIEW` | 1 | Add a private review for an activity |
| `ADD_EXPERT_REVIEW` | 1 | Add an expert review for an activity |
| `DISPLAY_REVIEWS` | 1 | Display all reviews for an activity |
| `ENDORSE_REVIEW` | 1 | Endorse a public review |
| `RESOLVE_REVIEW` | 2 | Resolve a private review with a response |
| `UPLOAD_REVIEW_IMAGE` | 2 | Upload an image for an expert review |
| `DISPLAY_TOP_ACTIVITIES` | 0 | Show top activity per location |
| `HELP` | 0 | Print all available commands |
| `EXIT` | 0 | Exit the application |

---

## 📊 Highlights

| Feature | Detail |
|---|---|
| Operators Supported | 100+ |
| CLI Commands | 16 |
| Review Types | Public, Private, Expert |
| Projected Annual Reviews | 10,000+ |
| Architecture | OOP with scalable design |

---

## 🎓 What I Learned

- Designing scalable OOP systems with clean separation of concerns
- Parsing and validating CLI input with quote-aware argument splitting
- Implementing polymorphic review types with unique fields and behaviour
- Building robust command-driven Java applications with enum-based command handling

---

## 📬 Contact

**Tanush Panuganti**
- GitHub: [Goldengamer255](https://github.com/Goldengamer255)
- LinkedIn: [tanush-panuganti](https://linkedin.com/in/tanush-panuganti)
- Email: tanush.panu@gmail.com

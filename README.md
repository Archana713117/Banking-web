# 💳 ATM Banking System (Java Web Application)

🚀 A full-stack **ATM Simulation Web Application** built using **Java Servlets, JDBC, and MySQL**, providing secure and interactive banking operations.

---

## ✨ Features

🔐 **Secure Login**

* Card number & PIN authentication
* PIN encrypted using SHA-256

💰 **Transactions**

* Deposit Money
* Withdraw Money (with balance validation)
* Prevents negative balance

📊 **Account Services**

* Check Balance
* Mini Statement (transaction history)

🔁 **PIN Management**

* Change PIN
* Forgot PIN (Reset functionality)

🎯 **User Experience**

* Clean UI with HTML & CSS
* Popup dialogs (no page reload)
* Session management for security

---

## 🛠 Tech Stack

| Technology    | Usage                     |
| ------------- | ------------------------- |
| Java Servlets | Backend logic             |
| JDBC          | Database connectivity     |
| MySQL         | Database                  |
| HTML/CSS      | Frontend UI               |
| JavaScript    | Dynamic UI (popups, AJAX) |
| Apache Tomcat | Server                    |

---

## 🧠 Key Concepts Implemented

* Session Management (`HttpSession`)
* Secure Password Handling (SHA-256 hashing)
* Transaction-based Balance Calculation
* MVC-like Architecture
* Form handling with Servlets
* AJAX-based user interaction

---

## 🔒 Security Features

* PIN is **hashed (SHA-256)** before storing
* No plain-text credentials stored
* Session-based authentication
* Prevents unauthorized access

---

## 📂 Project Structure

```
ATM-Simulator/
│
├── .java (Servlets)
├── .html (Frontend pages)
├── .css / .js
├── WEB-INF/
│   ├── web.xml
│   └── classes/
```

---

## ⚙️ How to Run

1. Install **Apache Tomcat**
2. Setup **MySQL database**
3. Update DB credentials in code
4. Compile servlets
5. Deploy in Tomcat (`webapps/ROOT`)
6. Run:

```
http://localhost:8080/login.html
```

---

## 🚀 Future Improvements

* OTP-based authentication
* Email/SMS alerts
* REST API integration
* Better UI (React / Bootstrap)
* Use bcrypt for stronger encryption

---

## 🙋‍♀️ Author

**Archana Achu**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub — it helps a lot!

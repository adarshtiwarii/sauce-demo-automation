
# 🚀 SauceDemo E2E Test Automation Framework

Complete End-to-End Testing Framework with Selenium, BDD (Cucumber), TestNG, JMeter, and Jenkins.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Prerequisites](#prerequisites)
4. [Project Structure](#project-structure)
5. [Setup Instructions](#setup-instructions)
6. [Execution Commands](#execution-commands)
7. [Test Reports](#test-reports)
8. [JMeter Performance Testing](#jmeter-performance-testing)
9. [Jenkins CI/CD](#jenkins-cicd)
10. [Troubleshooting](#troubleshooting)

---

## 📊 Project Overview

**Application Under Test:** https://www.saucedemo.com/

**Test Coverage:**
- ✅ Login Functionality (Valid/Invalid scenarios)
- ✅ Product Management (Add/Remove from cart, Sorting)
- ✅ Shopping Cart Operations
- ✅ Complete Checkout Process
- ✅ User Session Management (Logout)

**Total Test Cases:** 50+
- Cucumber BDD Scenarios: 20+
- TestNG Test Cases: 30+
- JMeter Performance Tests: 2

---

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 11+ |
| Build Tool | Maven | 3.9+ |
| Test Framework | TestNG | 7.9.0 |
| BDD Framework | Cucumber | 7.15.0 |
| Web Automation | Selenium WebDriver | 4.16.1 |
| Performance Testing | JMeter | 5.6+ |
| CI/CD | Jenkins | Latest |
| Reporting | Extent Reports, Allure | 5.1.1, 2.25.0 |
| Logging | Log4j2 | 2.22.1 |

---

## ✅ Prerequisites

### Required Software:

1. **Java JDK 11 or higher**
```bash
   java -version
   # Should show: java version "11" or higher
```

2. **Maven 3.9+**
```bash
   mvn -version
   # Should show: Apache Maven 3.9.x
```

3. **Git**
```bash
   git --version
```

4. **JMeter 5.6+** (Optional - for performance testing)
```bash
   jmeter --version
```

5. **Jenkins** (Optional - for CI/CD)

---

## 📁 Project Structure
```
sauce-demo-automation/
│
├── src/
│   ├── main/java/
│   │   ├── pages/              # Page Object Model
│   │   ├── utils/              # Utilities
│   │   └── constants/          # Constants
│   │
│   └── test/
│       ├── java/
│       │   ├── stepDefinitions/  # BDD Steps
│       │   ├── runners/          # Test Runners
│       │   ├── hooks/            # Cucumber Hooks
│       │   └── testng/           # TestNG Tests
│       │
│       └── resources/
│           ├── features/         # Gherkin Features
│           ├── config/           # Configuration
│           └── testdata/         # Test Data
│
├── jmeter/                     # JMeter Files
├── jenkins/                    # Jenkins Pipeline
├── test-output/                # Test Reports
└── pom.xml                     # Maven Config
```

---

## 🚀 Setup Instructions

### Step 1: Clone Repository
```bash
git clone <repository-url>
cd sauce-demo-automation
```

### Step 2: Install Dependencies
```bash
mvn clean install -DskipTests
```

This will download all required dependencies (~5-10 minutes on first run).

### Step 3: Verify Setup
```bash
mvn clean compile
```

If successful, you should see:
```
[INFO] BUILD SUCCESS
```

---

## ▶️ Execution Commands

### 🎯 Run All Tests (Cucumber BDD + TestNG)
```bash
mvn clean test
```

### 🎯 Run Only Cucumber BDD Tests
```bash
mvn test -Dtest=CucumberTestRunner
```

### 🎯 Run Only TestNG Tests
```bash
mvn test -Dtest=LoginTests,ProductTests,CheckoutTests
```

### 🎯 Run Tests with Specific Tags

**Smoke Tests:**
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

**Regression Tests:**
```bash
mvn test -Dcucumber.filter.tags="@regression"
```

**E2E Tests:**
```bash
mvn test -Dcucumber.filter.tags="@e2e"
```

**Multiple Tags:**
```bash
mvn test -Dcucumber.filter.tags="@smoke or @regression"
```

### 🎯 Run with Different Browser

**Chrome:**
```bash
mvn test -Dbrowser=chrome
```

**Firefox:**
```bash
mvn test -Dbrowser=firefox
```

**Edge:**
```bash
mvn test -Dbrowser=edge
```

**Headless Mode:**
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

### 🎯 Run Specific Test Class
```bash
mvn test -Dtest=LoginTests
mvn test -Dtest=ProductTests
mvn test -Dtest=CheckoutTests
```

### 🎯 Run Specific Test Method
```bash
mvn test -Dtest=LoginTests#testValidLogin
```

### 🎯 Parallel Execution
```bash
mvn test -DthreadCount=3
```

---

## 📊 Test Reports

### 1️⃣ Cucumber HTML Report

**Location:** `target/cucumber-html-reports/overview-features.html`

**Open:**
```bash
# Windows
start target/cucumber-html-reports/overview-features.html

# Mac
open target/cucumber-html-reports/overview-features.html

# Linux
xdg-open target/cucumber-html-reports/overview-features.html
```

**Features:**
- ✅ Feature-wise execution summary
- ✅ Step-by-step scenario details
- ✅ Pass/Fail statistics
- ✅ Screenshots for failed steps
- ✅ Execution time

### 2️⃣ Extent Report

**Location:** `test-output/ExtentReport.html`

**Open:**
```bash
start test-output/ExtentReport.html
```

**Features:**
- ✅ Dashboard with charts
- ✅ Test hierarchy
- ✅ Logs and screenshots
- ✅ System information
- ✅ Exception stack traces

### 3️⃣ Allure Report (Best!)

**Generate and Open:**
```bash
mvn allure:serve
```

This will:
1. Generate the report
2. Start a local server
3. Open report in browser automatically

**Manual Generation:**
```bash
mvn allure:report
```

**Features:**
- ✅ Beautiful interactive UI
- ✅ Test trends over time
- ✅ Categorized failures
- ✅ Test history
- ✅ Graphs and charts
- ✅ Attachments (screenshots, logs)

### 4️⃣ TestNG Report

**Location:** `test-output/index.html`

**Features:**
- ✅ Suite-level summary
- ✅ Test method details
- ✅ Groups execution
- ✅ Passed/Failed/Skipped counts

---

## 🔥 JMeter Performance Testing

### GUI Mode (For Creating/Editing Tests)
```bash
jmeter
```

Then: File → Open → `jmeter/testplans/SauceDemo_LoadTest.jmx`

### Non-GUI Mode (For Execution)

**Load Test:**
```bash
jmeter -n -t jmeter/testplans/SauceDemo_LoadTest.jmx \
       -l jmeter/results/load_test_results.jtl \
       -e -o jmeter/reports/load_test/
```

**Stress Test:**
```bash
jmeter -n -t jmeter/testplans/SauceDemo_StressTest.jmx \
       -l jmeter/results/stress_test_results.jtl \
       -e -o jmeter/reports/stress_test/
```

### View JMeter Report

**Open:**
```bash
start jmeter/reports/load_test/index.html
```

**Report Includes:**
- ✅ APDEX (Application Performance Index)
- ✅ Statistics table
- ✅ Response time graphs
- ✅ Throughput charts
- ✅ Error analysis
- ✅ Percentile graphs

### JMeter Test Configuration

**Load Test:**
- Virtual Users: 50
- Ramp-up Time: 30 seconds
- Loop Count: 5
- Duration: ~2.5 minutes

**Scenarios:**
1. Open Login Page
2. Perform Login
3. View Products
4. Navigate Cart

---

## 🔧 Jenkins CI/CD Setup

### Step 1: Install Required Plugins

In Jenkins, install:
- ✅ Maven Integration
- ✅ Allure Plugin
- ✅ HTML Publisher
- ✅ Email Extension
- ✅ Git Plugin

### Step 2: Create New Pipeline Job

1. New Item → Pipeline
2. Name: `SauceDemo-Automation`
3. Pipeline → Definition: Pipeline script from SCM
4. SCM: Git
5. Repository URL: `<your-git-repo>`
6. Script Path: `jenkins/Jenkinsfile`

### Step 3: Configure Parameters

The Jenkinsfile includes:
- `BROWSER`: chrome/firefox/edge
- `TEST_SUITE`: smoke/regression/all
- `RUN_JMETER`: true/false

### Step 4: Build Job

Click "Build with Parameters" and select options.

### Jenkins Pipeline Stages:

1. **Checkout** - Clone repository
2. **Build** - Compile project
3. **Run Selenium Tests** - Execute automation
4. **Run JMeter Tests** - Performance testing (optional)
5. **Generate Allure Report** - Create Allure report
6. **Publish Reports** - Publish all reports

### Email Notifications

Pipeline sends emails on:
- ✅ Success
- ❌ Failure
- ⚠️ Unstable

---

## 🔍 Troubleshooting

### Issue 1: WebDriver Not Found

**Error:**
```
WebDriverException: Driver executable not found
```

**Solution:**
WebDriverManager auto-downloads drivers. If it fails:
1. Check internet connection
2. Clear Maven cache: `mvn clean`
3. Update pom.xml dependencies

### Issue 2: Tests Failing Randomly

**Possible Causes:**
- Network latency
- Element not loaded

**Solutions:**
- Increase timeout in `config.properties`:
```properties
  explicit.wait=20
```
- Use headless mode for stability:
```bash
  mvn test -Dheadless=true
```

### Issue 3: Port Already in Use (Jenkins)

**Error:**
```
Address already in use: bind
```

**Solution:**
```bash
# Find process on port 8080
netstat -ano | findstr :8080

# Kill process
taskkill /F /PID <process_id>
```

### Issue 4: Maven Build Fails

**Error:**
```
Failed to execute goal
```

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests to verify build
mvn clean install -DskipTests
```

### Issue 5: Allure Command Not Found

**Solution:**

**Windows:**
```bash
# Install via Scoop
scoop install allure

# Or download from: https://github.com/allure-framework/allure2/releases
```

**Mac:**
```bash
brew install allure
```

**Linux:**
```bash
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure
```

---

## 📝 Best Practices Followed

1. ✅ **Page Object Model** - Clean separation of page elements and tests
2. ✅ **BDD Approach** - Business-readable test scenarios
3. ✅ **Data-Driven Testing** - Externalized test data
4. ✅ **Comprehensive Reporting** - Multiple report formats
5. ✅ **CI/CD Integration** - Automated execution pipeline
6. ✅ **Performance Testing** - Load and stress testing included
7. ✅ **Logging** - Detailed execution logs
8. ✅ **Screenshots** - Automatic capture on failures
9. ✅ **Cross-Browser Support** - Chrome, Firefox, Edge
10. ✅ **Maintainable Code** - Modular and reusable components

---

## 🎯 Quick Start Guide

**For Beginners - 5 Steps to Run Tests:**
```bash
# 1. Clone project
git clone <repo-url>
cd sauce-demo-automation

# 2. Install dependencies
mvn clean install -DskipTests

# 3. Run smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# 4. View Cucumber report
start target/cucumber-html-reports/overview-features.html

# 5. View Allure report
mvn allure:serve
```

---

## 📧 Support

For issues or questions:
- Create an issue in the repository
- Contact: adarsht072gmail.com

---

## 📄 License

This project is for learing purposes.

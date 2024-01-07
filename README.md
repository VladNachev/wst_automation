# Simple automation framework for testing APIs and Web Services

## Preconditions
- [x] JDK 11+
- [x] Maven 3.5+
- [x] Terminal for command execution

## Clone repository

```bash
git clone https://github.com/VladNachev/wst_automation
```

## Executing all rest tests with default configuration
```shell
mvn clean test
```

## Reporting
For test reporting, Allure Report is used. 

## To generate the reports:
```shell
allure generate
```

## To view the reports:
```shell
allure serve
```
![Report General](/images/allure_report_general.png)

![Report General](/images/allure_suites_details.png)
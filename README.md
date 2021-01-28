# ServiceManager

## Introduction

ServiceApp contains most of the essential parts for backend implementation 
for supporting service management actions which are requested from UI.

However due to the limit of time, UI is not included in the current version.

## User cases include:
- When user login to the dashboard, user can view a list of services based on user
- User is able to create a service by filling a form with Name & URL
- User is able to find a service by Name of the service
- User can select one service and update it
- User can select one service and delete it

Bulk functions is not supported in current version, can be easily added.

## Project Structure (Package description)
```
- servicemanager
  |__ ServiceApp
    |__dao
    |__entities  
    |__handler  // Business service layer
    |__rest		// Handle all requests from clint 
      |__model
    |__startup	// For start up all services when application starts
    |__util		// Utility package for validation, conversion etc.
```

## Test
Test should follow the same package structure.
However, only few test cases are involved due to limited time again, 
unit tests should be included at least.
	
## NOTE
It's running out of time!
If the output is considered as qualified, we can discuss more later.......	
	
	
	
	
	
	
	
	
	
	
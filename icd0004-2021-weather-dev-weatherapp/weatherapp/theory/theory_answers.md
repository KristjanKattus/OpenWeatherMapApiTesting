**1. Which of the following activities cannot be automated**


- [ ] Test execution
- [ ] Exploratory testing
- [x] **Discussing testability issues * <- Cannot be automated**
- [ ] Test data generation



**2. How do we describe a good unit test?**


- [ ] Flawless, Ready, Self-healing, True, Irresistible
- [ ] Red, Green, Refactor
- [x] **Fast, Repeatable, Self-validating, Timely, Isolated * <- F.I.R.S.T. aka good unit test**
- [ ] Tests should be dependent on other tests



**3. When is it a good idea to use XPath selectors**


- [ ] When CSS or other selectors are not an option or would be brittle and hard to maintain
- [ ] When we need to find an element based on parent/child/sibling relationship
- [ ] When an element is located deep within the HTML (or DOM) structure
- [x] **All the above * <- That's the correct one**



**4. Describe the TDD process**

> Red - Green - Refactor

- Write a test
- Check if test fails
- Write just enough implementation for test to pass
- Refactor implementation
- Write more tests



**5. Write 2 test cases or scenarios for a String Calculator application, which has a method calculate() that takes 
   a string of two numbers separated by a comma as input, and returns the sum.**

   
   Example: Given the input "1,5" When the method calculate() is called Then I should see "6" as a result.
   
   1) Given the input "K,2" When the method calculate() is called Then I should see invalid argument exception as a result.
   2) Given the input "null-2" When the method calculate() is called Then I should see invalid format exception as a result.

Hint: "C.O.R.R.E.C.T"

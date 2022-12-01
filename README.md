# TDD Ports and adapters kata
Based on [This blog post](http://matteo.vaccari.name/blog/archives/154.html)

## Using
 - Kotest and mockk for testing
 - Arrow-kt Either and Validated for Railway Oriented Programming (Thrown in at the end so this wasn't tested)
 - Served with Micronaut

### Ports
 - `BirthdayGreetingTrigger` (primary adapter) - Provides an entry point to run `BirthdayGreeter`. This isn't technically needed, as any calling class could call into the `BirthdayGreeter` and the interface technically provides little decoupling or polymorphic value.
   - This port could support adhoc or scheduled triggers, for example 
 - `BirthdayGreetingNotifier` (secondary adapter) - Sends birthday greetings to specific employees
   - This port could support emails, push notifications, or other service calls
 - `EmployeeRepository` (secondary adapter) - Retrieves list of all employees along with emails and birthdays
   - This port could support any storage method, (in-memory, database, service call)

### Adapters
- `OneOffBirthdayGreetingTrigger: BirthdayGreetingTrigger` for adhoc greetings
- `FileSystemEmployeeRepository: EmployeeRepository` for storing employee data in the file system. This is the expected implementation from the exercise
- `ConsoleBirthdayGreetingNotifier: BirthdayGreetingNotifier` sends all birthday greetings to the console. This is a simple replacement for the eventual email solution that is laid out in the exercise description.

### Use Case
This refers to the outer layer of the domain "hexagon" (from the hexagonal architecture mental model). These classes wire together injected adapters and perform side effects, leaving domain code pure.
 - `BirthdayGreeter` - Checks for birthdays on a given day using the provided `EmployeeRepository` and notifies employees with birthdays using the provided `BirthdayGreetingNotifier`

### Notes
The `BirthdayGreeter.sendGreetings` method is tested by creating a stub version of a `EmployeeRepository` adapter (this could also be tested with a real in-memory repository implementation) and a mock of the `BirthdayGreetingNotifier` adapter to verify the correct calls are made.

The domain methods on the `BirthdayChecker` object were originally private methods on the use case so are not unit tested. The full complexity of these functions should be covered by use case tests.

Validation is not tested because it was a separate, later layer added on to this project and was beyond the Tdd scope of this exercise.

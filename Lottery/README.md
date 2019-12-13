# Powerball!

The Powerball lottery is very excited about version 1 of their shiny new console app. 
Unfortunately, their entire development team quit to "follow their bliss". 
Version 1 is broken and incomplete.

You've been hired to finish the app. Please find the requirements below 
and be sure to ask clarifying questions when issues arise. 
More about the lottery: https://en.wikipedia.org/wiki/Powerball.

## Things to update

- The "Enter Pick" use case isn't validating correctly. It's too permissive. Users are able to pick the same number twice and sometimes they can pick invalid numbers. Please fix this.
- The previous developers claim the lottery drawing is working. It's not. When a user runs the drawing, the application crashes and results are never displayed. Please fix the drawing and display the winners.
- There are various little bugs that people noticed but forgot to document. Please fix them when you encounter them.

## Things to add

- Implement Spring dependency injection.
- The team completely forgot to track where customers live. This causes all sorts of issues when delivering prizes. Add a zip code to the customer and be sure it is both saved and retrieved from the "database". Zip codes are required and must be valid: 5 digits or 5 digits + 4 digits.
- Add a new main menu option "Find Picks by Zip Code". This should returns all picks (Tickets) for a single zip code.
- Add tests to cover all of your fixes and new features!

## To Start

- Download the Lottery project source from <your class repository>.
- Move it to your personal repository to work on it.
- Commit and push your changes early and often.

## Powerball Rules

The exhaustive rules are here: https://en.wikipedia.org/wiki/Powerball#Playing_the_game (Links to an external site.).
Summarized

- A player picks six numbers -- five "normal" numbers and a Powerball.
- The first five numbers must be between 1 and 69.
- The first five numbers cannot have repeated picks. Each must be unique.
- The Powerball must be between 1 and 26.
- The Powerball can be the same value as one of the first five numbers.


akka-fast-reflect
=======

Dependency Injection for AKKA

A small library for bridging the gap between Dependency Injection and AKKA

Our goal is quite simple, we have to mock the businessService within our BusinessActor. The difficulty comes from the fact that in a test case we are going to have an ActorRef reference variable to our actor and not a plain Actor reference. This means that our actor is actually nested within an ActorRef reference. As you might already guessed this brings further complications for mocking our businessService reference within the actor instance itself. Or in other words, our businessService is deeply nested and the exact path is actorRef.actorCell.actor.businessService. Luckily for us, since version 1.4 of FEST-Reflect we can play with (deeply) nested fields with a single line of code.

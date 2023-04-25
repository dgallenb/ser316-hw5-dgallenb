Gradle should just be runnable from the root folder. The build.gradle file is in the app folder for reasons I can't explain, but it still seems to work just fine.

Patterns used:
-State: The game cycles between BaseState, ExploreState, MenuState, BattleState, ShopState, and RestState, depending on the inputs given. States are stored in the GamePlay.java file, if you want to see the implementation. States handle moving from exploration to battle to shopping.
-Factory: CodemonFactory.java and MoveFactory.java are both factories that produce new objects on demand. These generate moves of various types for codemons and give them whatever stats I need.
-Singleton: Both factories are singletons, because I don't need more than one instance of those ever.
-Decorator: The MonTypeMulti and EvolvedCodemon classes are both decorators on MonType and Codemon (respectively). MonTypeMulti in particular handles dual typed codemons that can partially resist various types.
-Template: This was designed later and is a bit looser an implementation, but DeadPhase, ReturnPhase, and CapturedPhase are all templated off CleanupPhase, and each handle the actual cleanup postbattle differently, depending on the needs of the game.

Changes I have made that deviate from the requirements:
-Codemons learn up to 6 moves.

If you want to know which types are better against which, figure it out yourself (or check SUPERSECRETNOTES.txt)

Known defects: applying training orders (focused, inspired, brutal, agile) gives a bonus for the rest of the battle to the mon, not just a 1-round bonus.
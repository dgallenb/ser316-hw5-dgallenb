Gradle should just be runnable from the root folder. The build.gradle file is in the app folder for reasons I can't explain, but it still seems to work just fine.

Gameplan is to use a factory to generate codemons, make the factory a singleton, make codemons following the decorator design pattern (to handle evolutions), and have the game cycle between various states as the game switches between exploration, battle, battle menu (healing through this), etc. 

If you want to know which types are better against which, figure it out yourself (or check SUPERSECRETNOTES.txt)
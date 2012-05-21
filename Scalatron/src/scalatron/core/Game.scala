/** This material is intended as a community resource and is licensed under the
  * Creative Commons Attribution 3.0 Unported License. Feel free to use, modify and share it.
  */

package scalatron.core

import akka.dispatch.ExecutionContext
import akka.actor.ActorSystem
import scalatron.botwar.Config
import scalatron.botwar.BotWarSimulation.SimState

/** Base trait for game implementations that run within a Scalatron server (e.g. BotWar). */
trait Game
{
    /** @return the name of this game; TODO: like player names, should be derived from plug-in name, e.g. "BotWar" */
    def name: String

    /** @return the package path to be used for constructing fully qualified class names for bot plug-ins,
      *         e.g. "scalatron.botwar.botPlugin" */
    def gameSpecificPackagePath: String

    /** Dump the game-specific command line configuration options via println. */
    def printArgList()

    /** Run a loop that simulates tournament rounds, either until user exists (e.g. by closing
      * the main window) or until the given number of rounds has been played.
      * @param pluginPath the plug-in base directory path below which to scan for bot plug-ins
      * @param argMap the command line argument map to use to configure the tournament run
      * @param rounds the number of rounds to play in the tournament loop before returning
      * @param tournamentState the tournament state object to update whenever a round ends
      * @param secureMode if true, certain bot processing restrictions apply
      * @param verbose if true, log to the console verbosely
      * @param actorSystem execution context for trusted code (e.g. from Akka ActorSystem)
      * @param executionContextForUntrustedCode execution context for untrusted code (e.g. for bot control functions)
      */
    def runVisually(
        pluginPath: String,
        argMap: Map[String, String],
        rounds: Int,
        tournamentState: TournamentState,
        secureMode: Boolean,
        verbose: Boolean
    )(
        actorSystem: ActorSystem,
        executionContextForUntrustedCode: ExecutionContext
    )


    /** Run a loop that simulates tournament rounds until the given number of rounds has been
      * played. Does not open a display window.
      * @param pluginPath the plug-in base directory path below which to scan for bot plug-ins
      * @param argMap the command line argument map to use to configure the tournament run
      * @param rounds the number of rounds to play in the tournament loop before returning
      * @param tournamentState the tournament state object to update whenever a round ends
      * @param secureMode if true, certain bot processing restrictions apply
      * @param verbose if true, log to the console verbosely
      * @param actorSystem execution context for trusted code (e.g. from Akka ActorSystem)
      * @param executionContextForUntrustedCode execution context for untrusted code (e.g. for bot control functions)
      */
    def runHeadless(
        pluginPath: String,
        argMap: Map[String, String],
        rounds: Int,
        tournamentState: TournamentState,
        secureMode: Boolean,
        verbose: Boolean
    )(
        actorSystem: ActorSystem,
        executionContextForUntrustedCode: ExecutionContext
    )


    /** Starts a headless, private game instances and returns the associated initial simulation state.
      * @param plugins the plug-ins to load into the game
      * @param roundConfig the configuration for this game round (includes the permanent configuration by reference)
      * @param executionContextForUntrustedCode execution context for untrusted code (e.g. for bot control functions)
      * @return an initial simulation state.
      */
    def startHeadless(plugins: Iterable[Plugin.FromJarFile], roundConfig: RoundConfig)(executionContextForUntrustedCode: ExecutionContext): SimState
}
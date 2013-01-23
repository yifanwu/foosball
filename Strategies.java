public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = false;
    public static enum GAME_TYPE {
        EVEN,OFFENSIVE,DEFENSIVE
    }

    /**
     * the rows are indexed per below
     * -4   -3  -2  -1  0   1   2   3   4
     * made two dementional for easier access
     */
    static final int[][] INIT_TYPES = {{3,1,2,2,3,3,2,3,3}, {2,1,1,1,4,3,3,3,4}, {4,3,3,1,4,3,1,1,2}};
    public static int single_guy = 0;

    static final int[] silly_init_2 = {0, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3,
            -4, -4, -4, -4, -4, -4, -3, -3, -3, -3, -3,
            100, 100, 100, 100};

    public static int chooseInitType(int[] GAME_TYPE_RESULT) {
        double randNum = Math.random();
        if (v) {
            System.out.println("the random number is: "+randNum);
        }
        double total = 0.0;

        for (int x: GAME_TYPE_RESULT) {
            total += x;
        }

        int resultSum = 0;

        for (int j = 0; j < GAME_TYPE_RESULT.length; j++) {
            resultSum += GAME_TYPE_RESULT[j];
            if (randNum < resultSum/total) {
                return j;
            }
            // buggy
            return GAME_TYPE_RESULT.length-1;
        }
        return -1;
    }

    /**
     * This takes in the original layout and then swaps the tired player off
     * @param init
     * @return
     */
    public static int[] updateInitRosterForTired(int[] init, int[] gameState) {
        int mostFatigued = helper.getMostFatiguedPlayer(helper.getTeamFatigue(gameState));
        int positionOfFatigued = init[mostFatigued];
        init[mostFatigued] = 100;
        // find the first that's 100 and replace
        for(int i = 0; i<init.length; i++) {
            if (init[i] == 100) {
                init[i] = positionOfFatigued;
            }
        }

        return init;
    }

    /**
     * This assumes that the last row is the strongest
     * @param init
     * @return
     */
    public static int[] updateInitRosterForLastRow(int[] init) {
        int[] updated = new int[26];
        return updated;
    }

    // BETTER ONE EVEN THOUGH DOESN'T MAKE SENSE
    // moves player from more likely row to ball row
    public static int[] movePlayerTowardBall(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] oppRoster = helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (playersOnBallRow.length < oppsOnBallRow.length && ballRow - 1 > -5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    // does not work as well as not trying to keep at least one player on each row
    public static int[] keepAtLeastOnePlayerOnEachRow(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] oppRoster = helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        int[] defenseSidePlayers = helper.getPlayersOnRow(roster, ballRow - 1);
        int defenseSideLeastFatigued = helper.getLeastFatiguedPlayer(defenseSidePlayers, myTeamFatigues);
        int[] offenseSidePlayers = helper.getPlayersOnRow(roster, ballRow + 1);
        int offenseSideLeastFatigued = helper.getLeastFatiguedPlayer(offenseSidePlayers, myTeamFatigues);

        if (playersOnBallRow.length < oppsOnBallRow.length && ballRow - 1 > -5) {
            playerToMove = (defenseSidePlayers.length > 1) ? defenseSideLeastFatigued : -1;
        }
        else if (ballRow + 1 < 5) {
            playerToMove = (offenseSidePlayers.length > 1) ? offenseSideLeastFatigued : -1;
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    // moves player from less likely row (based on fatigue) to ball row
    // performs pretty equal to movePlayerTowardBall2
    public static int[] moveBasedOnFatigue(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] oppRoster = helper.getOppRoster(gameState);
        int[] myTeamFatigues = helper.getTeamFatigue(gameState);
        int[] oppTeamFatigues = helper.getTeamFatigue(gameState);

        int ballRow = gameState[3];

        int[] playersOnBallRow = helper.getPlayersOnRow(roster, ballRow);
        int playersFatigue = helper.getFatigueOfRow(playersOnBallRow, myTeamFatigues);

        int[] oppsOnBallRow = helper.getPlayersOnRow(oppRoster, ballRow);
        int oppFatigue = helper.getFatigueOfRow(oppsOnBallRow, oppTeamFatigues);

        int playerToMove = -1;

        if (oppFatigue > playersFatigue && ballRow - 1 > -5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, oppTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, oppTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }


    public static int chooseLonePlayer(int[] roster, int[] fatigues) {
        int[] defense = helper.getPlayersOnRow(roster, -4);
        int[] offense = helper.getPlayersOnRow(roster, 4);

        if (defense.length > offense.length) {
            return helper.getLeastFatiguedPlayer(defense, fatigues);
        }
        else {
            return helper.getLeastFatiguedPlayer(offense, fatigues);
        }
    }

    public static int[] oneGuyMoving(int[] gameState) {
        System.out.println("+++++++++++++++++++++++");

        int[] roster = helper.getTeamRoster(gameState);
        int[] fatigue = helper.getTeamFatigue(gameState);
        int ballRow = gameState[3];

        System.out.println("Ball row " + ballRow);
        if (ballRow == -3) {
            int[] playersOnRow2 = helper.getPlayersOnRow(roster, -2);
            if (playersOnRow2.length == 0) {
                int[] players = helper.getPlayersOnRow(roster, -4);
                int leastFatigued = helper.getLeastFatiguedPlayer(players, fatigue);
                roster[leastFatigued] = ballRow;
            }
            else {
                roster[playersOnRow2[0]] = ballRow;
            }
        }
        else if (ballRow == 3) {
            int[] playersOnRow2 = helper.getPlayersOnRow(roster, 2);
            if (playersOnRow2.length == 0) {
                int[] players = helper.getPlayersOnRow(roster, 4);
                int leastFatigued = helper.getLeastFatiguedPlayer(players, fatigue);
                roster[leastFatigued] = ballRow;
            }
            else {
                roster[playersOnRow2[0]] = ballRow;
            }
        }
        else {
            int[] players = helper.getPlayersOnRow(roster, ballRow + 1);
            if (players.length == 0) {
                players = helper.getPlayersOnRow(roster, ballRow - 1);
            }

            if (players.length > 0) {
                roster[players[0]] = ballRow;
            }
            else {
                // move lone player towards ball
                int lonePlayer = getLonePlayer(roster);

                if (lonePlayer != -1) {
                    int direction = ballRow - roster[lonePlayer];
                    if (direction < 0)
                        roster[lonePlayer] -= 1;
                    else
                        roster[lonePlayer] += 1;
                }
                else {
                    // else choose a lone player from either offense or defense
                    int playerToMove = chooseLonePlayer(roster, fatigue);
                    if (roster[playerToMove] == 4) {
                        roster[playerToMove] = 3;
                    }
                    else if (roster[playerToMove] == -4) {
                        roster[playerToMove] = -3;
                    }

                }
            }
        }

        System.out.println("----------------------");
        return roster;
    }

    private static int getLonePlayer(int[] roster) {
        for (int i = 0; i < roster.length; i++) {
            if (roster[i] != 100 && roster[i] != 4 && roster[i] != -4) {
                return i;
            }
        }
        return -1;
    }



    public static int[] twoGuysMoving(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] fatigue = helper.getTeamFatigue(gameState);
        int ballRow = gameState[3];

        System.out.println("Ball row " + ballRow);
        if (ballRow == -3) {
            int[] playersOnRow2 = helper.getPlayersOnRow(roster, -2);
            if (playersOnRow2.length == 0) {
                int[] players = helper.getPlayersOnRow(roster, -4);
                int leastFatigued = helper.getLeastFatiguedPlayer(players, fatigue);
                roster[leastFatigued] = ballRow;
            }
            else {
                int leastFatigued = helper.getLeastFatiguedPlayer(playersOnRow2, fatigue);
                roster[leastFatigued] = ballRow;
            }
        }
        else if (ballRow == 3) {
            int[] playersOnRow2 = helper.getPlayersOnRow(roster, 2);
            if (playersOnRow2.length == 0) {
                int[] players = helper.getPlayersOnRow(roster, 4);
                int leastFatigued = helper.getLeastFatiguedPlayer(players, fatigue);
                roster[leastFatigued] = ballRow;
            }
            else {
                int leastFatigued = helper.getLeastFatiguedPlayer(playersOnRow2, fatigue);
                roster[leastFatigued] = ballRow;
            }
        }
        else {
            int[] playerOnRow = helper.getPlayersOnRow(roster, ballRow);
            if (playerOnRow.length > 0) {
                return roster;
            }

            int[] players1 = helper.getPlayersOnRow(roster, ballRow + 1);
            int[] players2 = helper.getPlayersOnRow(roster, ballRow - 1);

            int leastFatigue1 = helper.getLeastFatiguedPlayer(players1, fatigue);
            int leastFatigue2 = helper.getLeastFatiguedPlayer(players2, fatigue);

            if (leastFatigue1 == -1) {
                roster[leastFatigue2] = ballRow;
            }
            else if (leastFatigue2 == -1) {
                roster[leastFatigue1] = ballRow;
            }
            else {
                if (fatigue[leastFatigue1] > fatigue[leastFatigue2]) {
                    roster[leastFatigue2] = ballRow;
                }
                else {
                    roster[leastFatigue1] = ballRow;
                }
            }
        }

        System.out.println("----------------------");
        return roster;
    }



}


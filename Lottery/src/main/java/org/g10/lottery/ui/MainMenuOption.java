package org.g10.lottery.ui;

/*
   Enum for main menu and the options therin
added a field for generating tickets and accessing the secret menu
though it always gets displayed, so it's not that secret
 */
public enum MainMenuOption {

    EXIT(0, "Exit"),
    ENTER_PICK(1, "Enter Pick"),
    QUICK_PICK(2, "Quick Pick"),
    FIND_PICKS_BY_LAST_NAME(3, "Find Picks By Last Name"),
    RUN_LOTTERY(4, "Run Lottery!"),
    GENERATE_TICKETS(5, "Only Cool People can see this option");

    private final int value;
    private final String name;

    private MainMenuOption(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption mmo : MainMenuOption.values()) {
            if (mmo.getValue() == value) {
                return mmo;
            }
        }
        return EXIT;
    }
}

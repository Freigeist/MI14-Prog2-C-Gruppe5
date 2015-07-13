package net.white_it.ships.helper;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class SettingsHelper {
    private int playerCount, fieldSize, uboote, korvetten, fregatten, zerstoerer;
    private String[] names;
    private boolean[] isBot;

    public SettingsHelper(int playerCount, int fieldSize, int uboote, int korvetten, int fregatten, int zerstoerer, String[] names, boolean[] isBot) {
        this.playerCount = playerCount;
        this.fieldSize = fieldSize;
        this.uboote = uboote;
        this.korvetten = korvetten;
        this.fregatten = fregatten;
        this.zerstoerer = zerstoerer;
        this.names = names;
        this.isBot = isBot;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getUboote() {
        return uboote;
    }

    public int getKorvetten() {
        return korvetten;
    }

    public int getFregatten() {
        return fregatten;
    }

    public int getZerstoerer() {
        return zerstoerer;
    }

    public String[] getNames() {
        return names;
    }

    public boolean[] getIsBot() {
        return isBot;
    }
}

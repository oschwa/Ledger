package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManualCommandTest {

    private String[] testManual;
    private ManualCommand manCommand;
    private Command mockCommand;
    private Player mockPlayer;

    @BeforeEach
    public void setUp() {
        testManual = new String[] {
                ChatColor.YELLOW + "/ledger:man -> manual page",
                ChatColor.YELLOW + "/ledger:new -> create a new Ledger",
                ChatColor.YELLOW + "/ledger:scrap -> delete your existing Ledger",
                ChatColor.YELLOW + "/ledger:add [player name] -> add a player in the server to your Ledger",
                ChatColor.YELLOW + "/ledger:leave -> leave another player's Ledger",
                ChatColor.YELLOW + "/ledger:members -> view names of player in your Ledger"
        };

        manCommand = new ManualCommand();

        mockPlayer = mock(Player.class);

        mockCommand = mock(Command.class);
        when(mockCommand.getName()).thenReturn("man");
    }
    @Test
    public void manCommandSendsMessageToPlayerTest() {
        manCommand.onCommand(mockPlayer, mockCommand, "man", new String[]{});
        verify(mockPlayer).sendMessage(testManual);
    }
}
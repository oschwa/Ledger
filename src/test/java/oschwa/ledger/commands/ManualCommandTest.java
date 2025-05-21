package oschwa.ledger.commands;

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
                "/ledger man -> manual page",
                "/ledger new -> create a new Ledger",
                "/ledger scrap -> delete your existing Ledger",
                "/ledger add [player name] -> add a player to your Ledger",
                "/ledger leave -> leave another player's Ledger",
                "/ledger members -> view names of player in your Ledger"
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
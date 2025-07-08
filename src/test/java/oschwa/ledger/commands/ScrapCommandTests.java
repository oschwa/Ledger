package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScrapCommandTests {

    private LedgerGroupRegistry ledgerGroupRegistry;
    private ScrapCommand scrapCommand;

    private Player mockPlayer;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();
        scrapCommand = new ScrapCommand(ledgerGroupRegistry);

        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);
    }

    @Test
    public void scrapRemovesLedgerGroupTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        scrapCommand.onCommand(mockPlayer, mockCommand, "scrap", new String[]{});
        assertFalse(ledgerGroupRegistry.containsGroup(mockPlayer));
    }

    @Test
    public void scrapSendsPlayerMessageTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        scrapCommand.onCommand(mockPlayer, mockCommand, "scrap", new String[]{});
        verify(mockPlayer).sendMessage("Ledger scrapped!");
    }

    @Test
    public void scrapSendsMessageForGroupDoesNotExistExceptionTest() {
        when(mockPlayer.getName()).thenReturn("test");
        scrapCommand.onCommand(mockPlayer, mockCommand, "scrap", new String[]{});
        verify(mockPlayer).sendMessage("test does not have a registered Ledger");
    }

}
package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewCommandTests {
    private NewCommand newCommand;
    private LedgerGroupRegistry ledgerGroupRegistry;
    private Player mockPlayer;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();
        newCommand = new NewCommand(ledgerGroupRegistry);
        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);
        when(mockCommand.getName()).thenReturn("new");
    }

    @Test
    public void newCommandAddsLedgerGroupToRegistryTest() {
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        assertTrue(ledgerGroupRegistry.containsGroup(mockPlayer));
    }

    @Test
    public void newCommandSendsMessageToPlayerTest() {
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        verify(mockPlayer).sendMessage("New Ledger created!");
    }

    @Test
    public void newCommandFailsToAddExistingGroupTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        assertEquals(1, ledgerGroupRegistry.getSize());
    }

    @Test
    public void newCommandSendsMessageUponExceptionTest() {
        when(mockPlayer.getName()).thenReturn("test");
        ledgerGroupRegistry.addGroup(mockPlayer);
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        verify(mockPlayer).sendMessage("test already has assigned Ledger");
    }
}
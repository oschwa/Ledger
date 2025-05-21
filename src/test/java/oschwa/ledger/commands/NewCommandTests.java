package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void newCommandFailsToAddExistingGroupTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        assertThrows(GroupExistsException.class, () ->
                newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{}));
        assertEquals(1, ledgerGroupRegistry.getSize());
    }
}
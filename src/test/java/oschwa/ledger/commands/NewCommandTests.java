package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        verify(mockPlayer).sendMessage(ChatColor.YELLOW + "[Ledger] New Ledger created.");
    }

    @Test
    public void newCommandFailsToAddExistingGroupTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        assertEquals(1, ledgerGroupRegistry.getSize());
    }

    @Test
    public void newCommandSendsMessageUponExceptionTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        newCommand.onCommand(mockPlayer, mockCommand, "new", new String[]{});
        verify(mockPlayer).sendMessage(ChatColor.RED + "[Ledger] You already have a Ledger.");
    }
}
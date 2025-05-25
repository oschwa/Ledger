package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AddCommandTests {

    private AddCommand addCommand;
    private LedgerGroupRegistry ledgerGroupRegistry;

    private Server mockServer;
    private Player mockPlayer;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();

        mockServer = mock(Server.class);
        addCommand = new AddCommand(mockServer, ledgerGroupRegistry);

        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);
    }

    @Test
    public void addCommandAddsMemberToLedger() {
        ledgerGroupRegistry.addGroup(mockPlayer);

        Player mockPlayer2 = mock(Player.class);
        UUID uuid = UUID.randomUUID();
        when(mockPlayer2.getName()).thenReturn("playerTwo");
        when(mockServer.getPlayer("playerTwo")).thenReturn(mockPlayer2);
        when(mockPlayer2.getUniqueId()).thenReturn(uuid);

        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        assertTrue(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(uuid));
    }

    @Test
    public void addCommandFailsToAddNonExistentPlayerTest() {
        UUID uuid = UUID.randomUUID();
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        assertFalse(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(uuid));
    }

    @Test
    public void addCommandSendsMessageTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);

        Player mockPlayer2 = mock(Player.class);
        UUID uuid = UUID.randomUUID();
        when(mockPlayer2.getName()).thenReturn("playerTwo");
        when(mockServer.getPlayer("playerTwo")).thenReturn(mockPlayer2);
        when(mockPlayer2.getUniqueId()).thenReturn(uuid);

        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage("playerTwo has been added to your Ledger.");
    }
}
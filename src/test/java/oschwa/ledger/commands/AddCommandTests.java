package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddCommandTests {

    private AddCommand addCommand;
    private LedgerGroupRegistry ledgerGroupRegistry;

    private Server mockServer;
    private Player mockPlayer;
    private Player mockPlayer2;
    private UUID mockPlayer2UUID;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();

        mockServer = mock(Server.class);
        addCommand = new AddCommand(mockServer, ledgerGroupRegistry);

        mockPlayer = mock(Player.class);
        mockPlayer2 = mock(Player.class);
        mockCommand = mock(Command.class);

        mockPlayer2UUID = UUID.randomUUID();

        when(mockPlayer2.getName()).thenReturn("playerTwo");
        when(mockServer.getPlayer("playerTwo")).thenReturn(mockPlayer2);
        when(mockPlayer2.getUniqueId()).thenReturn(mockPlayer2UUID);

    }

    @Test
    public void addCommandFailsWhenNoPlayerIsGivenTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{});
        assertFalse(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(mockPlayer2UUID));
        verify(mockPlayer).sendMessage(ChatColor.RED + "[Ledger] You must provide a player name.");
    }

    @Test
    public void addCommandFailsWhenNoLedgerTest() {
        assertFalse(addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{}));
    }

    @Test
    public void addCommandAddsMemberToLedger() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        assertTrue(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(mockPlayer2UUID));
    }

    @Test
    public void addCommandFailsToAddNonExistentPlayerTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerThree"});
        assertFalse(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(mockPlayer2UUID));
    }

    @Test
    public void addCommandSendsMessageForNonExistentPlayerTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerThree"});
        verify(mockPlayer).sendMessage(ChatColor.RED + "[Ledger] playerThree does not exist on this server.");
    }

    @Test
    public void addCommandSendsMessageTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage(ChatColor.YELLOW + "[Ledger] playerTwo has been added to your Ledger.");
    }

    @Test
    public void addCommandSendsMessageForMemberExistsExceptionTest() {
        when(mockPlayer2.getName()).thenReturn("playerTwo");

        ledgerGroupRegistry.addGroup(mockPlayer);
        ledgerGroupRegistry.getGroup(mockPlayer).addMember(mockPlayer2);

        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage(ChatColor.RED + "[Ledger] playerTwo is already a member of your Ledger.");
    }

    @Test
    public void addCommandFailsIfLedgerDoesNotExistTest() {
        assertFalse(addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"}));
    }

    @Test
    public void addCommandSendsMessageForGroupDoesNotExistTest() {
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage(ChatColor.RED + "[Ledger] You do not have an active Ledger.");
    }
}
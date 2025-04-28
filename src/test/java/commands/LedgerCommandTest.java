package commands;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.commands.LedgerCommand;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerCommandTest {

    private LedgerCommand command;
    private String[] testManual;

    @BeforeEach
    public void setUp() {
        command = new LedgerCommand();
        testManual = new String[] {
                "/ledger -> manual page"
        };
    }

    @Test
    public void manCommandOutputsMessageForPlayerTest() {
        Player player = Mockito.mock(Player.class);

        Command mockCommand = Mockito.mock(Command.class);
        when(mockCommand.getName()).thenReturn("ledger");

        command.onCommand(player, mockCommand, "ledger", new String[]{});

        verify(player).sendMessage(testManual);
    }

}
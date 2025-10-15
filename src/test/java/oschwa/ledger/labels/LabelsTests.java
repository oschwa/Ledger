package oschwa.ledger.labels;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class LabelsTests {
    private Label label;
    private String labelName;
    @Mock
    private Player player;

    @BeforeEach
    public void setUp() {
        player = mock(Player.class);
        labelName = "test";
    }
}

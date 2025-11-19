package oschwa.ledger.labels;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import oschwa.ledger.LedgerPlugin;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Label {
    private final String name;

    private final ItemStack labelItem;

    private final PersistentDataContainer pdc;

    private final NamespacedKey labelKey;

    private final NamespacedKey configKey;

    public Label(String name) {

        this.name = name;

        this.labelKey = new NamespacedKey(LedgerPlugin.getPlugin(), "ledger_label");

        this.configKey = new NamespacedKey(LedgerPlugin.getPlugin(), "label_config");

        ItemStack labelItem = new ItemStack(Material.NAME_TAG);

        ItemMeta labelMeta = labelItem.getItemMeta();

        labelMeta.customName(Component.text(this.name));

        //  assign label item meta.

        PersistentDataContainer pdc = labelMeta.getPersistentDataContainer();

        this.pdc = pdc;

        pdc.set(labelKey, PersistentDataType.STRING, name);

        //  assign map to as persistent data using Json.

        Type type = new TypeToken<Map<String, String>>(){}.getType();

        Map<String, String> config = new HashMap<>();

        Gson gson = new Gson();

        String json = gson.toJson(config, type);

        pdc.set(configKey, PersistentDataType.STRING, json);

        labelItem.setItemMeta(labelMeta);

        this.labelItem = labelItem;
    }

    public boolean containsItem(ItemStack item) {

        Map<String, String> config = getMap();

        return config.containsKey(item.getType().name());
    }

    public void assignItem(ItemStack item, Integer slot) {

        //  get persistent map.

        Map<String, String> config = getMap();

        //  add item.

        Material material = Material.getMaterial(item.getType().name());

        String materialName = material.name();

        config.put(materialName, String.valueOf(slot));

        //  assign map as persistent data using Json.

        saveMap(config);
    }

    public void unassignItem(ItemStack item) {

        //  get persistent map.

        Map<String, String> config = getMap();

        //  remove item.

        config.remove(item.getType().name());

        saveMap(config);
    }

    private void saveMap(Map<String, String> config) {

        //  save map

        Type type = new TypeToken<Map<String, String>>(){}.getType();

        Gson gson = new Gson();

        String newMapJson = gson.toJson(config, type);

        pdc.set(configKey, PersistentDataType.STRING, newMapJson);

    }

    private Map<String, String> getMap() {

        //  get persistent map.

        String currMapJson = pdc.get(configKey, PersistentDataType.STRING);

        Type type = new TypeToken<Map<String, String>>(){}.getType();

        return new Gson().fromJson(currMapJson, type);

    }

    public Map<ItemStack, Integer> getLabelContents() {

        //  get map from data container.

        Map<String, String> config = getMap();

        //  adapt strings arguments to chest slots and Bukkit materials.

        Map<ItemStack, Integer> contents = new HashMap<>();

        for (Map.Entry<String, String> entry : config.entrySet()) {

            int slot = Integer.parseInt(entry.getValue());

            Material material = Material.getMaterial(entry.getKey());

            ItemStack item = new ItemStack(material);

            contents.put(item, slot);
        }

        return contents;
    }

    public ItemStack getLabelItem() {

        return labelItem;

    }

    public String getName() {

        return name;

    }

    public static Label builder(String name) {

        return new Label(name);

    }
}

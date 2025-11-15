package oschwa.ledger.registries;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Registry<K, V> {
    protected final Map<K, V> registry;

    public Registry() {
        registry = new HashMap<>();
    }

    public boolean contains(K k) {
        return registry.containsKey(k);
    }

    public void add(K k, V v)  {
        registry.put(k, v);
    }

    public void remove(K k) {
        registry.remove(k);
    }

    public Optional<V> get(K k) {
        return Optional.ofNullable(registry.get(k));
    }

    public int getSize() {
        return registry.size();
    }
}

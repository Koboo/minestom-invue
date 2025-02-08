package eu.koboo.minestom.examples.invue.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.execute.ExecuteDefault;
import eu.koboo.minestom.examples.invue.views.AllowInteractionExampleProvider;
import eu.koboo.minestom.examples.invue.views.AnnotatedTabExampleProvider;
import eu.koboo.minestom.examples.invue.views.AnvilInputExampleProvider;
import eu.koboo.minestom.examples.invue.views.pagination.PageableExampleProvider;
import eu.koboo.minestom.examples.invue.views.pagination.ScrollableHorizontalExampleProvider;
import eu.koboo.minestom.examples.invue.views.pagination.ScrollableVerticalExampleProvider;
import eu.koboo.minestom.examples.invue.views.search.SearchExampleProvider;
import eu.koboo.minestom.examples.invue.views.switching.MultiLayersExampleProvider;
import eu.koboo.minestom.examples.invue.views.switching.SwitchOneExampleProvider;
import eu.koboo.minestom.invue.api.ViewRegistry;
import eu.koboo.minestom.invue.api.component.RootViewComponent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Command(name = "view")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class CommandView {

    private static final Map<String, Function<ViewRegistry, RootViewComponent>> VIEWS = new HashMap<>() {{
        put("annotated", AnnotatedTabExampleProvider::new);
        put("page", PageableExampleProvider::new);
        put("scrollhorizontal", ScrollableHorizontalExampleProvider::new);
        put("scrollvertical", ScrollableVerticalExampleProvider::new);
        put("switch", SwitchOneExampleProvider::new);
        put("allow", AllowInteractionExampleProvider::new);
        put("anvil", AnvilInputExampleProvider::new);
        put("search", SearchExampleProvider::new);
        put("depth", registry -> new MultiLayersExampleProvider(registry, 1));
    }};

    ViewRegistry registry;

    @ExecuteDefault
    public void onExecuteDefault(@Context Player player) {
        printUsage(player);
    }

    @Execute
    public void onExecute(@Context Player player, @Arg("ViewName") String viewName) {
        Function<ViewRegistry, RootViewComponent> viewSupplier = VIEWS.get(viewName);
        if (viewSupplier == null) {
            printUsage(player);
            player.sendMessage("View with name " + viewName + " not found.");
            return;
        }

        RootViewComponent viewProvider = viewSupplier.apply(registry);
        viewProvider.open(player);
    }

    private void printUsage(Player player) {
        player.sendMessage("Usage: /view <name>");
        player.sendMessage("Available views:");
        player.sendMessage(String.join(", ", VIEWS.keySet()));
    }

}
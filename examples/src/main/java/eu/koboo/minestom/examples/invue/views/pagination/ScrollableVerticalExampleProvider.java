package eu.koboo.minestom.examples.invue.views.pagination;

import eu.koboo.minestom.examples.invue.views.pagination.components.PaginationBorder;
import eu.koboo.minestom.examples.invue.views.pagination.loader.ExampleItemLoader;
import eu.koboo.minestom.invue.api.ViewBuilder;
import eu.koboo.minestom.invue.api.ViewRegistry;
import eu.koboo.minestom.invue.api.ViewType;
import eu.koboo.minestom.invue.api.component.ViewProvider;
import eu.koboo.minestom.invue.api.pagination.ViewPagination;
import eu.koboo.minestom.invue.api.slots.ViewPattern;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScrollableVerticalExampleProvider extends ViewProvider {

    // Render order of components:
    // - this
    //   - PaginationBorder
    //     - PaginationActionButtons
    //       - ViewPagination
    public ScrollableVerticalExampleProvider(ViewRegistry registry) {
        super(registry, ViewType.SIZE_6_X_9);
        ViewPattern pattern = registry.pattern(
            "K###>###Z",
            "#SSSSSSS#",
            "#CCCCCCC#",
            "#RRRRRRR#",
            "#OOOOOOO#",
            "####<####"
        );
        ViewPagination pagination = registry.scrollable(
            new ExampleItemLoader(),
            pattern.getListOfSlots('S', 'C', 'R', 'O')
        );
        addChild(new PaginationBorder(pagination, pattern));
    }

    @Override
    public void modifyBuilder(@NotNull ViewBuilder viewBuilder, @NotNull Player player) {
        viewBuilder.title("<red>Vertical scrollable example");
    }
}

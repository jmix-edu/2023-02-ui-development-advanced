package com.company.jmixpm.datatype;

import io.jmix.core.JmixOrder;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ColorPicker;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.ComponentGenerationContext;
import io.jmix.ui.component.ComponentGenerationStrategy;
import org.springframework.core.Ordered;

import javax.annotation.Nullable;

@org.springframework.stereotype.Component("jmixpm_ColorDatatypeComponentGenerationStrategy")
public class ColorDatatypeComponentGenerationStrategy implements ComponentGenerationStrategy, Ordered {
    private final MetadataTools metadataTools;
    private final UiComponents uiComponents;

    public ColorDatatypeComponentGenerationStrategy(MetadataTools metadataTools, UiComponents uiComponents) {
        this.metadataTools = metadataTools;
        this.uiComponents = uiComponents;
    }

    @Nullable
    @Override
    public Component createComponent(ComponentGenerationContext context) {
        MetaClass metaClass = context.getMetaClass();
        MetaPropertyPath mmp = metadataTools.resolveMetaPropertyPathOrNull(metaClass, context.getProperty());
        if (mmp != null
                && mmp.getRange().isDatatype()
                && ((Datatype<?>) mmp.getRange().asDatatype()) instanceof ColorDatatype) {
            ColorPicker colorPicker = uiComponents.create(ColorPicker.NAME);
            colorPicker.setDefaultCaptionEnabled(true);

            if (context.getValueSource() != null) {
                colorPicker.setValueSource(context.getValueSource());
            }
            return colorPicker;
        }
        return null;
    }

    @Override
    public int getOrder() {
        return JmixOrder.HIGHEST_PRECEDENCE + 10;
    }
}

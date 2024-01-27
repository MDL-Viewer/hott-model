module de.treichels.hott.model {
    requires kotlin.stdlib;
    requires de.treichels.hott.util;
    requires java.desktop;
    requires java.xml;
    requires java.logging;
    requires jakarta.xml.bind;
    exports de.treichels.hott.model;
    exports de.treichels.hott.model.enums;
    exports de.treichels.hott.model.firmware;
    exports de.treichels.hott.messages;
}
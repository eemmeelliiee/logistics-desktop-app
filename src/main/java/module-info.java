module isp.retake {

    /*
    * Export the 'se.lu.ics' package, allowing other modules
    * to access the public classes and interfaces within this package.
    */
    exports se.lu.ics;
    exports se.lu.ics.models;
    /*
    * Declare that this module requires the 'javafx.fxml' module to function.
    * This means that the current module depends on
    * the JavaFX FXML library for its functionality.
    */
    requires javafx.fxml;
    /*
    * Declare that this module requires the 'javafx.controls' module.
    * This means it uses the JavaFX Controls library, which includes
    * UI components like buttons, text fields, etc.
    */
    requires javafx.controls;
    /*
    * Declare a transitive dependency on the 'javafx.graphics' module.
    * This means that not only does this module
    * require 'javafx.graphics', but any module that requires
    * this module ('javafx.gui.lab') will also implicitly require 'javafx.graphics'.
    */
    requires transitive javafx.graphics;
    /*
    * Open the 'se.lu.ics.controllers' package, allowing other modules
    * to access the public classes and interfaces within this package.
    * This is necessary because the JavaFX runtime will need to
    * create instances of the classes in this package.
    */
    opens se.lu.ics.controllers to javafx.fxml;
    }
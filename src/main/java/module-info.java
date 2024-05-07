module s2105064.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens s2105064.server to javafx.fxml;
    exports s2105064.server;
    exports s2105064.server.controller;
    opens s2105064.server.controller to javafx.fxml;
    exports s2105064.CommonClass;
    opens s2105064.CommonClass to javafx.fxml;
    exports s2105064.client.controller;
    opens s2105064.client.controller to javafx.fxml;

    exports s2105064.client;

}
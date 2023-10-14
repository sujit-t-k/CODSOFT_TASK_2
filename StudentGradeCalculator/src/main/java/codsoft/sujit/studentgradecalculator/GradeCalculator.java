package codsoft.sujit.studentgradecalculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GradeCalculator extends Application {
    private Pane pane_content;
    private Stage stage_current;
    private TextField[] txtMark;

    @Override
    public void start(Stage stage) {
        this.pane_content = new Pane();
        Scene scene = new Scene(pane_content, Constants.DBL_SCREEN_WIDTH, Constants.DBL_SCREEN_HEIGHT);
        stage.setScene(scene);
        this.buildCustomizedWindow();
        this.buildGUI();
        stage.initStyle(StageStyle.UNDECORATED);
        this.stage_current = stage;
        stage.centerOnScreen();
        stage.show();
    }

    private Line getLine(final double startX, final double startY, final double endX, final double endY) {
        Line l = new Line(startX, startY, endX, endY);
        l.setStrokeWidth(Constants.DBL_LINE_STROKE_WIDTH);
        l.setStroke(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        l.setOnMousePressed(event -> {
            Values.DBL_SCREEN_X_LOC = event.getX();
            Values.DBL_SCREEN_Y_LOC = event.getY();
        });
        l.setOnMouseDragged(event -> {
            if(event.getScreenX() > 20.0D && event.getScreenX() < java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 20.0D) {
                this.stage_current.setX(event.getScreenX() - Values.DBL_SCREEN_X_LOC);
            }
            if(event.getScreenY() > 20.0D && event.getScreenY() < java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 20.0D) {
                this.stage_current.setY(event.getScreenY() - Values.DBL_SCREEN_Y_LOC);
            }
        });
        return l;
    }

    private void buildCustomizedWindow() {
        Polygon pg_title = new Polygon(0.0D,0.0D,240.0D,0.0D,210.0D,40.0D,0.0D,40.0D,0.0D,0.0D);
        pg_title.setFill(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        pg_title.setLayoutX(Constants.DBL_LINE_STROKE_WIDTH/2.0D);
        pg_title.setLayoutY(Constants.DBL_LINE_STROKE_WIDTH/2.0D);
        Text txtTitle = new Text("GRADE CALCULATOR");
        txtTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
        txtTitle.setLayoutX(10.0D);
        txtTitle.setLayoutY(30.0D);
        txtTitle.setMouseTransparent(true);
        pg_title.setOnMousePressed(event -> {
            Values.DBL_SCREEN_X_LOC = event.getX();
            Values.DBL_SCREEN_Y_LOC = event.getY();
        });
        pg_title.setOnMouseDragged(event -> {
            if(event.getScreenX() > 20.0D && event.getScreenX() < java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 20.0D) {
                this.stage_current.setX(event.getScreenX() - Values.DBL_SCREEN_X_LOC);
            }
            if(event.getScreenY() > 20.0D && event.getScreenY() < java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 20.0D) {
                this.stage_current.setY(event.getScreenY() - Values.DBL_SCREEN_Y_LOC);
            }
        });
        this.pane_content.getChildren().addAll(pg_title,txtTitle,getLine(Constants.DBL_LINE_STROKE_WIDTH/2.0D,0.0D,Constants.DBL_LINE_STROKE_WIDTH/2.0D,Constants.DBL_SCREEN_HEIGHT),
                getLine((Constants.DBL_SCREEN_WIDTH-Constants.DBL_LINE_STROKE_WIDTH/2.0D),0.0D,(Constants.DBL_SCREEN_WIDTH-Constants.DBL_LINE_STROKE_WIDTH/2.0D),Constants.DBL_SCREEN_HEIGHT),
                getLine(0.0D,Constants.DBL_LINE_STROKE_WIDTH/2.0D,Constants.DBL_SCREEN_WIDTH,Constants.DBL_LINE_STROKE_WIDTH/2.0D),
                getLine(0.0D,Constants.DBL_SCREEN_HEIGHT-Constants.DBL_LINE_STROKE_WIDTH/2.0D,Constants.DBL_SCREEN_WIDTH,Constants.DBL_SCREEN_HEIGHT-Constants.DBL_LINE_STROKE_WIDTH/2.0D));
        this.pane_content.setBackground(new Background(new BackgroundFill(Color.web(Constants.STR_CLR_PANE_CONTENT_BG), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private Text getTextNode(final String TXT, final double X, final double Y, final String FONT_FAMILY, final double FONT_SIZE, final boolean BOLD_FONT) {
        Text txtNode = new Text(TXT);
        txtNode.setLayoutX(X);
        txtNode.setLayoutY(Y);
        txtNode.setFont(Font.font(FONT_FAMILY, (BOLD_FONT) ? FontWeight.BOLD : FontWeight.NORMAL, FontPosture.REGULAR, FONT_SIZE));
        return txtNode;
    }

    private TextField getTextFieldNode(final double X, final double Y, final String PROMPT_TEXT) {
        TextField txtFieldNode = new TextField(){
            @Override
            public void paste() {}
        };
        txtFieldNode.setLayoutX(X);
        txtFieldNode.setLayoutY(Y);
        txtFieldNode.textProperty().addListener((ol,ov,nv) -> {
            String N = nv.replaceAll("[^0-9]","");
            if(N.length() != nv.length()) txtFieldNode.textProperty().setValue(N);
            nv = N;
            if (!N.isEmpty()) {
                if(Integer.parseInt(N) == 0 && !N.equals("0")) {
                    Platform.runLater(() -> {
                        txtFieldNode.textProperty().set("0");
                    });
                } else if(Integer.parseInt(N) > 100) {
                    if(!ov.isEmpty() && ov.replaceAll("[0-9]","").isEmpty()) {
                        txtFieldNode.textProperty().set(ov);
                    } else {
                        txtFieldNode.textProperty().set("");
                    }
                } else {
                    txtFieldNode.setText(nv);
                }
            }
        });
        txtFieldNode.setPrefWidth(45.0D);
        txtFieldNode.setPrefHeight(30.0D);
        return txtFieldNode;
    }

    private void buildGUI() {
        this.txtMark = new TextField[Constants.INT_SUBJECT_COUNT];
        Text txtDetails = getTextNode("Enter your marks",20.0D,85.0D,"Georgia",18.0D,true);
        txtDetails.setFill(Color.web("#DBBD44"));
        Text txtMinimize = getTextNode("-",335.0D,32.0D,"Times New Roman",28.0D,true);
        txtMinimize.setFill(Color.web("#300101"));
        txtMinimize.setMouseTransparent(true);
        Text txtClose = getTextNode("X",365.4D,30.0D,"Times New Roman",12.0D,true);
        txtClose.setFill(Color.web("#300101"));
        txtClose.setMouseTransparent(true);
        Circle cMinimize = new Circle();
        cMinimize.setRadius(10.0D);
        cMinimize.setFill(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        cMinimize.setCenterX(340.0D);
        cMinimize.setCenterY(26.0D);
        cMinimize.setOnMouseClicked(e->{
            this.stage_current.setIconified(true);
        });
        cMinimize.setOnMouseEntered(e->{
            txtMinimize.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 32));
            txtMinimize.setLayoutY(txtMinimize.getLayoutY()+1.6D);
            cMinimize.setRadius(12.4D);
            cMinimize.setFill(Color.web("#070AFF"));
        });
        cMinimize.setOnMouseExited(e->{
            txtMinimize.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 28));
            txtMinimize.setLayoutY(txtMinimize.getLayoutY()-1.6D);
            cMinimize.setRadius(10.0D);
            cMinimize.setFill(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        });
        Circle cClose = new Circle();
        cClose.setRadius(10.0D);
        cClose.setFill(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        cClose.setCenterX(370.0D);
        cClose.setCenterY(26.0D);
        cClose.setOnMouseClicked(e->{
            Platform.exit();
        });
        cClose.setOnMouseEntered(e->{
            txtClose.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 14));
            txtClose.setLayoutY(txtClose.getLayoutY()+2.0D);
            cClose.setRadius(12.4D);
            cClose.setFill(Color.web("#FF0408"));
        });
        cClose.setOnMouseExited(e->{
            txtClose.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 12));
            txtClose.setLayoutY(txtClose.getLayoutY()-2.0D);
            cClose.setRadius(10.0D);
            cClose.setFill(Color.web(Constants.STR_CLR_WINDOW_BORDER));
        });
        double labelX = 50.0D, textX = 230.0D, Y = 120.0D;
        Text[] txtEnter = new Text[Constants.INT_SUBJECT_COUNT];
        Button btnCalculate = new Button("CALCULATE");
        Text txtEnterDetail = this.getTextNode("Enter marks of all subjects within valid range : 0 to 100 (inclusive)", 10.0D, 360.0D, "Calibri",14.0D,false);
        txtEnterDetail.setFill(Color.RED);
        txtEnterDetail.setWrappingWidth(380.0D);
        txtEnterDetail.setTextAlignment(TextAlignment.CENTER);
        for(int i = 0; i < Constants.INT_SUBJECT_COUNT; i++) {
            txtEnter[i] = getTextNode("Enter your " + Constants.STR_SUBJECTS[i] + " mark : ",labelX,Y,"Times New Roman",16.0D,false);
            txtEnter[i].setTextAlignment(TextAlignment.RIGHT);
            txtEnter[i].setWrappingWidth(175.0D);
            txtEnter[i].setFill(Color.WHITE);
            this.txtMark[i] = getTextFieldNode(textX, Y - 20.0D, Constants.STR_SUBJECTS[i].toUpperCase() + " MARK");
            this.txtMark[i].addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
            this.txtMark[i].setBackground(new Background(new BackgroundFill(Color.web("#29A4FF"), CornerRadii.EMPTY, Insets.EMPTY)));
            this.txtMark[i].setFont(Font.font("Agency FB", FontWeight.BOLD, FontPosture.REGULAR, 18));
            this.txtMark[i].textProperty().addListener(e->{
                boolean isWithinRange = true;
                int pending = 0;
                for(int j = 0; j < this.txtMark.length; j++) {
                    if(this.txtMark[j].getText().replaceAll("[^0-9]","").isEmpty()) {
                        pending++;
                        txtEnterDetail.setText("Please enter mark of " + Constants.STR_SUBJECTS[j]);
                    }
                    isWithinRange = isWithinRange && (!this.txtMark[j].getText().replaceAll("[^0-9]","").isEmpty() && Integer.parseInt(this.txtMark[j].getText().replaceAll("[^0-9]","")) < 101);
                    //isWithinRange = this.txtMark[j].getText().replaceAll("[^0-9]","").isEmpty() || (!this.txtMark[j].getText().isEmpty()) && Integer.parseInt(this.txtMark[j].getText().replaceAll("[^0-9]","")) < 101;
                }
                if(pending == 1) {
                    txtEnterDetail.setText(txtEnterDetail.getText().concat(" subject."));
                } else if (pending > 1) {
                    txtEnterDetail.setText(txtEnterDetail.getText().concat(" and "  + (pending-1) + " other subject" + ((pending > 2) ? "s." : ".")).replace("mark","marks"));
                }
                if(isWithinRange) {
                    txtEnterDetail.setVisible(false);
                    btnCalculate.setVisible(true);
                } else {
                    txtEnterDetail.setVisible(true);
                    btnCalculate.setVisible(false);
                }
            });
            Y += 40.0D;
            this.pane_content.getChildren().addAll(txtEnter[i],this.txtMark[i]);
        }
        btnCalculate.setLayoutX(140.0D);
        btnCalculate.setLayoutY(350.0D);btnCalculate.setVisible(false);
        btnCalculate.setBackground(new Background(new BackgroundFill(Color.web("#ECF22D"), CornerRadii.EMPTY, Insets.EMPTY)));
        btnCalculate.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        btnCalculate.setOnMouseEntered(e->{
            btnCalculate.setBackground(new Background(new BackgroundFill(Color.web("#047EE0"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        btnCalculate.setOnMouseExited(e->{
            btnCalculate.setBackground(new Background(new BackgroundFill(Color.web("#ECF22D"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        btnCalculate.setOnAction(e->{
            boolean dataAvailable = true;
            for(int i = 0; i < Constants.INT_SUBJECT_COUNT && dataAvailable; i++) {
                if(this.txtMark[i].getText().isEmpty()) dataAvailable = false;
            }
            if(dataAvailable) {
                for (int i = 0; i < Constants.INT_SUBJECT_COUNT; i++) this.pane_content.getChildren().removeAll(txtEnter[i], this.txtMark[i]);
                this.pane_content.getChildren().removeAll(txtDetails, btnCalculate, txtEnterDetail);
                displayResult();
            }
        });
        this.pane_content.getChildren().addAll(txtDetails,cMinimize,cClose,txtMinimize,txtClose,btnCalculate, txtEnterDetail);
    }

    private void displayResult() {
         Line l_left = new Line(70.0D,66.0D,70.0D, 335.0D), l_right = new Line(320.0D,66.0D,320.0D, 335.0D),
         l_center = new Line(170.0D,66.0D,170.0D, 335.0D), l_top = new Line(70.0D,66.0D,320.0D, 66.0D), l_bottom = new Line(70.0D,94.0D,320.0D, 94.0D);
         l_left.setStroke(Color.WHITE);
         l_right.setStroke(Color.WHITE);
         l_center.setStroke(Color.WHITE);
         l_top.setStroke(Color.WHITE);
         l_bottom.setStroke(Color.WHITE);
         double Y = 112.0D;
         Rectangle r = new Rectangle();
         r.setWidth(210.0D+42.0D-2.0D);
         r.setHeight(60.0D);
         r.setLayoutX(71.0D);
         r.setLayoutY(275.0D);
         r.setFill(Color.web("#141D1F"));
         this.pane_content.getChildren().addAll(r, l_top, l_bottom, l_center);
         Text txtTableTitle1 = this.getTextNode("SUBJECT", 80.0D, 83.0D, "Times New Roman", 16.0D, true),
              txtTableTitle2 = this.getTextNode("MARKS SCORED", 180.0D, 83.0D, "Times New Roman", 16.0D, true);
         txtTableTitle1.setFill(Color.WHEAT);
         txtTableTitle2.setFill(Color.WHEAT);
         int total = 0;
         Text[] txtSubject = new Text[Constants.INT_SUBJECT_COUNT], txtMarkScored = new Text[Constants.INT_SUBJECT_COUNT];
         Line[] lBorder = new Line[Constants.INT_SUBJECT_COUNT];
         for(int i = 0; i < Constants.INT_SUBJECT_COUNT; i++) {
             final int MARK = Integer.parseInt(this.txtMark[i].getText());
             txtSubject[i] = this.getTextNode(Constants.STR_SUBJECTS[i], 94.0D, Y, "Times New Roman", 16.0D, false);
             txtSubject[i].setTextAlignment(TextAlignment.CENTER);
             txtSubject[i].setFill((MARK >= 50) ? Color.LIMEGREEN : Color.RED);
             txtMarkScored[i] = this.getTextNode("  " + this.txtMark[i].getText() + " / 100", 210.0D, Y, "Times New Roman", 16.0D, false);
             txtMarkScored[i].setTextAlignment(TextAlignment.CENTER);
             txtMarkScored[i].setFill((MARK >= 50) ? Color.LIMEGREEN : Color.RED);
             lBorder[i] = new Line(70.0D,Y+13.0D,320.0D, Y+13.0D);
             lBorder[i].setStroke(Color.WHITE);
             this.pane_content.getChildren().addAll(lBorder[i],txtSubject[i],txtMarkScored[i]);
             Y += 30.0D;
             total += MARK;
         }
        Text txtTotal = this.getTextNode("TOTAL", 80.0D, Y, "Times New Roman", 16.0D, false);
        txtTotal.setTextAlignment(TextAlignment.CENTER);
        txtTotal.setFill(Color.web("#DBBD44"));
        Text txtTotalValue = this.getTextNode(" " + total + " / " + (100*Constants.INT_SUBJECT_COUNT), 210.0D, Y, "Times New Roman", 16.0D, false);
        txtTotalValue.setTextAlignment(TextAlignment.CENTER);
        txtTotalValue.setFill(Color.web("#DBBD44"));
        Y += 30.0D;
        double average = (double) total / (double) Constants.INT_SUBJECT_COUNT;
        Text txtAverage = this.getTextNode("AVERAGE", 80.0D, Y, "Times New Roman", 16.0D, false);
        txtAverage.setTextAlignment(TextAlignment.CENTER);
        txtAverage.setFill(Color.web("#DBBD44"));
        Text txtAverageValue = this.getTextNode(String.format("   %.2f",average) + "%", 210.0D, Y, "Times New Roman", 16.0D, false);
        txtAverageValue.setTextAlignment(TextAlignment.CENTER);
        txtAverageValue.setFill(Color.web("#DBBD44"));
        final String strGrade = (average >= 90.0D) ? "O" : ((average >= 80.0D) ? "A+" : ((average >= 70.0D) ? "A" : ((average >= 60.0D) ? "B+" : ((average >= 50.0D) ? "B" : "F"))));
        Rectangle rectBG = new Rectangle();
        rectBG.setWidth(220.0D);
        rectBG.setHeight(40.0D);
        rectBG.setX(20.0D);
        rectBG.setY(342.0D);
        rectBG.setFill(Color.web((average >= 50.0D) ? "#29E31A" : "#EB0937"));
        Text txtResult = this.getTextNode("RESULT GRADE : " + strGrade, 30.0D, 367.0D, "Times New Roman", 20.0D, true);
        Button btnBack = new Button("BACK");
        btnBack.setLayoutX(302.0D);
        btnBack.setLayoutY(342.0D);
        btnBack.setBackground(new Background(new BackgroundFill(Color.web("#4D4EB8"), CornerRadii.EMPTY, Insets.EMPTY)));
        btnBack.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 22));
        btnBack.setTextFill(Color.WHITE);
        btnBack.setOnMouseEntered(e->{
            btnBack.setBackground(new Background(new BackgroundFill(Color.web("#A709ED"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        btnBack.setOnMouseExited(e->{
            btnBack.setBackground(new Background(new BackgroundFill(Color.web("#4D4EB8"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        Y -= 30.0D;
        Line ld1 = new Line(70.0D,Y+13.0D,320.0D, Y+13.0D), ld2 = new Line(70.0D,Y+43.0D,320.0D, Y+43.0D);
        ld1.setStroke(Color.WHEAT);
        ld2.setStroke(Color.WHEAT);
        this.pane_content.getChildren().addAll(txtTotal,txtTotalValue,txtAverage,txtAverageValue, ld1,
                ld2,l_left, l_right, txtTableTitle1, txtTableTitle2, rectBG, txtResult, btnBack);

        btnBack.setOnAction(e->{
            this.pane_content.getChildren().removeAll(txtTotal,txtTotalValue,txtAverage,txtAverageValue, ld1,
                    ld2,l_left, l_right, txtTableTitle1, txtTableTitle2, rectBG, txtResult, btnBack,r, l_top, l_bottom, l_center);
            for(int i = 0; i < Constants.INT_SUBJECT_COUNT; i++) {
                this.pane_content.getChildren().removeAll(lBorder[i],txtSubject[i],txtMarkScored[i]);
            }
            this.buildGUI();
        });
    }

    private static final class Constants {
        private final static double DBL_SCREEN_WIDTH = 400.0D, DBL_SCREEN_HEIGHT = 400.0D, DBL_LINE_STROKE_WIDTH = 6.0D;
        private final static String STR_CLR_WINDOW_BORDER = "#DBBD44", STR_CLR_PANE_CONTENT_BG = "#1D0929";
        private final static String[] STR_SUBJECTS = new String[]{"English","Tamil","Maths","Science","Social","CS"};
        private final static int INT_SUBJECT_COUNT = STR_SUBJECTS.length;
    }

    private final static class Values {
        public static double DBL_SCREEN_X_LOC = 0.0D, DBL_SCREEN_Y_LOC = 0.0D;
    }

    public static void main(String[] args) {
        launch();
    }
}
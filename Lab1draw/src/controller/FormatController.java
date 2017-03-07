package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.pojo.FormattingObject;

public class FormatController implements Initializable{
	@FXML private ColorPicker colourChooser;
	@FXML private Label colourLabel;
	@FXML private Label fillLabel;
	@FXML private ChoiceBox<String> fillbox;
	@FXML private Slider widthChooser;
	@FXML private Label labelWidth;
	@FXML private HBox FormatMenu;
	@FXML private Button undoButton;
	@FXML private Button redoButton;
	private FormattingObject formatObject;
	private ResourceBundle bundle;
	ObservableList<String> list;;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		 bundle = ResourceBundle.getBundle("bundles.loadingBundle", new Locale("fo", "FO"));
		// Fill box setting
		list = FXCollections.observableArrayList(bundle.getString("fill1"),bundle.getString("fill2"));
		fillbox.setItems(list);
		fillbox.setTooltip(new Tooltip("Select Fill"));
		fillbox.setValue(list.get(0));
		fillbox.valueProperty().addListener(e->{
			formatObject.setFill(fillbox.getValue());
		});
		//ColourBox
		colourChooser.setValue(Color.web(bundle.getString("color")));
		colourChooser.valueProperty().addListener(E ->{
			formatObject.setColour(colourChooser.getValue());
		
		});
		// Slider and Label
		String val =bundle.getString("width");
		widthChooser.setValue(Double.valueOf(val));
		labelWidth.setText(val);
		widthChooser.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String value = Integer.toString((int) widthChooser.getValue());
				formatObject.setWidth((int) widthChooser.getValue());
				labelWidth.setText(value);
			}
		});
		
		
		formatObject = this.getFormattingObject();
	}

	public void SetValue(int value) {
		System.out.println(value);
		widthChooser.setValue((double) value);
		String val = Integer.toString((int) widthChooser.getValue());
		labelWidth.setText(val);
	}

	public FormattingObject getFormattingClass() {
		return formatObject;
	}

	private FormattingObject getFormattingObject() {
		return new FormattingObject(colourChooser.getValue(), (int) widthChooser.getValue(), fillbox.getValue());
	}
	
	public void eventUndo(){
		MediatorController.getInstance().undo();
	}
	public void eventRedo(){
		MediatorController.getInstance().undo();
	}

	public void loader(FormattingObject object){
		if(object != null){
			this.formatObject.resetObject(object);
			fillbox.setValue(object.getFill());
			colourChooser.setValue(object.getColour());
			formatObject.setWidth(object.getWidth());
			labelWidth.setText(Integer.toString(object.getWidth()));	
			System.out.println(object.toStringAll());
		}
	}
}

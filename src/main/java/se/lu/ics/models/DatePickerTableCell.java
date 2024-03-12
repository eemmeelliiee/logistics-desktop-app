package se.lu.ics.models;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerTableCell<S, T> extends TableCell<S, T> {

    private final DatePicker datePicker;
    private final StringConverter<T> converter;

    public DatePickerTableCell(StringConverter<T> converter) {
        this.converter = converter;
        this.datePicker = new DatePicker();
        this.datePicker.setConverter((StringConverter<LocalDate>) converter);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            datePicker.setValue((LocalDate) item);
            setGraphic(datePicker);
        }
    }
}
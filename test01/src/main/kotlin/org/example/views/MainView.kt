package org.example.views

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MainView : View() {
    override val root = vbox(2) {
        listview(values) {
            cellFragment(TestCellFragment::class)
        }
    }
}

class TestCellFragment : ListCellFragment<TestText>() {
    private val txt = TestTextModel().bindTo(this)
    private var txtContent = text(txt.text)

    override val root = vbox {
        widthProperty().addListener { _, _, newValue ->
            newValue?.let {
                txtContent.wrappingWidth = it.toDouble() - 25.0
            }
        }
        cellProperty.onChange { cell ->
            if (cell != null) {
                txtContent.wrappingWidth = cell.listView.width - 25.0
            }
        }

        this += txtContent

    }
}

data class TestText(val text: String)

class TestTextModel : ItemViewModel<TestText>() {
    val text = bind { SimpleStringProperty(item?.text ?: "") }
}

val values = listOf(
        TestText("Short text"),
        TestText("A bit longer, medium text, another one which should be nicely wrapped and unwrapped as necessary"),
        TestText("Another short"),
        TestText("Quite long text whichfghfgyiugyidugkgikghkdjghkhgkjdhgkjhgkjdhgkughiugiuygiuoghiugkjeghoiegioegigegg should be wrapped and automatically adjusted."),
        TestText("Third short")).observable()
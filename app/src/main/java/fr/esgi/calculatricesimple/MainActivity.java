package fr.esgi.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private int firstNumber = 0;
    private int secondNumber = 0;
    private Ops operator = null;
    private boolean isTypingFirstNumber = true;


    private enum Ops {
        PLUS("+"), MOINS("-"), FOIS("*"), DIVISE("/");

        private final String symbol;

        Ops(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.result);
    }

    public void addNumber(View v) {
        Button b = (Button) v;
        try {
            int value = Integer.parseInt(b.getText().toString());
            String currentText = resultTextView.getText().toString();

            if (isTypingFirstNumber && currentText.length() < 9) {
                firstNumber = firstNumber * 10 + value;
                updateDisplay(value);
            } else if (!isTypingFirstNumber && secondNumber < 1000000000) {
                secondNumber = secondNumber * 10 + value;
                resultTextView.setText(currentText + value);
            } else {
                Toast.makeText(this, "Nombre trop grand", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Mauvaise Valeur", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOperator(View v) {
        Button b = (Button) v;
        switch (b.getText().toString()) {
            case "+":
                operator = Ops.PLUS;
                break;
            case "-":
                operator = Ops.MOINS;
                break;
            case "*":
                operator = Ops.FOIS;
                break;
            case "/":
                operator = Ops.DIVISE;
                break;
            default:
                throw new IllegalArgumentException("Opérateur non reconnu");
        }
        resultTextView.setText(resultTextView.getText().toString() + " " + operator.getSymbol() + " ");
        isTypingFirstNumber = false;
    }

    public void EqualPress(View v) {
        if (operator != null) {
            if (secondNumber == 0 && operator == Ops.DIVISE) {
                Toast.makeText(this, "Erreur division par zéro impossible", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (operator) {
                case PLUS:
                    firstNumber = firstNumber + secondNumber;
                    break;
                case MOINS:
                    firstNumber = firstNumber - secondNumber;
                    break;
                case FOIS:
                    firstNumber = firstNumber * secondNumber;
                    break;
                case DIVISE:
                    firstNumber = firstNumber / secondNumber;
                    break;
            }
            resultTextView.setText(String.valueOf(firstNumber));
            secondNumber = 0;
            operator = null;
            isTypingFirstNumber = true;
        }
    }

    public void clear(View v) {
        firstNumber = 0;
        secondNumber = 0;
        operator = null;
        isTypingFirstNumber = true;
        resultTextView.setText("");
    }

    public void buttonDelete(View v) {
        if (isTypingFirstNumber) {
            firstNumber = firstNumber / 10;
            updateDisplay(firstNumber);
        } else {
            secondNumber = secondNumber / 10;
            resultTextView.setText(resultTextView.getText().toString().substring(0, resultTextView.getText().toString().length() - 1));
        }
    }

    private void updateDisplay(int value) {
        resultTextView.setText(String.valueOf(firstNumber));
    }
}

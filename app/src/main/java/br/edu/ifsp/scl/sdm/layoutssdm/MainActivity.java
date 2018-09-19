package br.edu.ifsp.scl.sdm.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";


    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;


    private LinearLayout telefoneLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private ArrayList<String> stringsTelefoneArrayList;
    private ArrayList<Integer> intTipoTelefoneArrayList;
    private static String KEY_TELEFONES_ARMAZENADOS = "KEY_TELEFONES_ARMAZENADOS";
    private static String KEY_TIPOS_TELEFONES_ARMAZENADOS = "KEY_TIPOS_TELEFONES_ARMAZENADOS";

    //Email adicionado dinâmicamente
    private LinearLayout emailLinearLayout;
    private ArrayList<View> emailArrayList;
    private ArrayList<String> stringsEmailArrayList;
    private static String KEY_EMAILS_ARMAZENADOS = "KEY_EMAILS_ARMAZENADOS";

    private LayoutInflater layoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);


        notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditText = findViewById(R.id.nomeEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);

        //Telefone
        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        telefoneArrayList = new ArrayList<>();
        stringsTelefoneArrayList = new ArrayList<>();
        intTipoTelefoneArrayList = new ArrayList<>();

        //Email
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        emailArrayList = new ArrayList<>();
        stringsEmailArrayList = new ArrayList<>();

        layoutInflater = getLayoutInflater();

        // Exibindo/ocultando RadioGroup por evento de Click Listener
        /*
        notificacoesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });*/

        // Exibindo/ocultando RadioGroup por evento de On Checked Change Listener
        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });

        Log.e("AQUI", "onCreate() Executado");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificacoesCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        //Salva os e-mail dinâmicos
        stringsEmailArrayList = new ArrayList<>(); //Conserta um bug de duplicação dos e-mails ao girar a tela mais que uma vez
        if(emailArrayList.size() > 0) {
            for (View emailView : emailArrayList) { //  recupera as views adicionadas dinâmicamente para guardar os internos de telefone
                EditText edtTxtEmail = emailView.findViewById(R.id.emailEditTextDinamico);  // recupera o edtTxtEmail
                stringsEmailArrayList.add(edtTxtEmail.getText().toString());
            }
        }
        //Grava os dados no bundle - para e-mails
        outState.putStringArrayList(KEY_EMAILS_ARMAZENADOS, stringsEmailArrayList);



        //Salva os telefones dinâmicos
        stringsTelefoneArrayList = new ArrayList<>(); //Conserta um bug de duplicação de telefone ao girar a tela mais que uma vez
        if(telefoneArrayList.size() > 0) {
            for (View telefoneView : telefoneArrayList) { //  recupera as views adicionadas dinâmicamente para guardar os internos de telefone
                EditText edtTxtTelefone = telefoneView.findViewById(R.id.telefoneEditText);  // recupera o edtTxtTelefone
                stringsTelefoneArrayList.add(edtTxtTelefone.getText().toString());

                Spinner spinnerTipoTelefone = telefoneView.findViewById(R.id.tipoTelefoneSpinner);  // recupera o spinner

                if(spinnerTipoTelefone.getSelectedItem().equals("Fixo")){ // verifica o tipo do fone e guarda com seu valor na lista
                    intTipoTelefoneArrayList.add(0);   // 0 para fixo  1 para celular.
                }
                else{
                    intTipoTelefoneArrayList.add(1);
                }
            }
        }
        //Grava os dados no bundle - para telefones
        outState.putStringArrayList(KEY_TELEFONES_ARMAZENADOS, stringsTelefoneArrayList);
        outState.putIntegerArrayList(KEY_TIPOS_TELEFONES_ARMAZENADOS, intTipoTelefoneArrayList);

        Log.e("AQUI", "onSaveInstanceState() Executado");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("AQUI", "onRestoreInstanceState() Executado");

        notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));
        // Exibe/Esconde radio group caso o evento implementado seja no OnClick
        /*if (notificacoesCheckBox.isChecked())
        {
            notificacoesRadioGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            notificacoesRadioGroup.setVisibility(View.GONE);
        }*/
        int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, -1);
        if (idRadioButtonSelecionado != -1) {
            notificacoesRadioGroup.check(idRadioButtonSelecionado);
        }


        //Recupera a lista de emails
        stringsEmailArrayList = savedInstanceState.getStringArrayList(KEY_EMAILS_ARMAZENADOS);

        if(stringsEmailArrayList.size() > 0) {
            for(String emailString : stringsEmailArrayList) {
                View email = adicionarEmailDinamicoView(); //Infla a view e adiciona na tela dinâmicamente
                EditText emailEditTextDinamico = email.findViewById(R.id.emailEditTextDinamico);
                emailEditTextDinamico.setText(emailString);
                Log.e("AQUI", "Adicionado e-mail: " + emailString);
            }
        }


        //Recupera a lista de telefones
        stringsTelefoneArrayList = savedInstanceState.getStringArrayList(KEY_TELEFONES_ARMAZENADOS);
        intTipoTelefoneArrayList = savedInstanceState.getIntegerArrayList(KEY_TIPOS_TELEFONES_ARMAZENADOS);

        if(stringsTelefoneArrayList.size() > 0) {
            int i = 0; // variável de controle para recuperar valores de tipo de telefone
            for(String telefoneString : stringsTelefoneArrayList) {
                View telefone = adicionarTelefoneDinamicoView(); //Infla a view e adiciona na tela dinâmicamente
                EditText telefoneEditTextDinamico = telefone.findViewById(R.id.telefoneEditText);
                telefoneEditTextDinamico.setText(telefoneString);

                Spinner spinnerTipoTelefone = telefone.findViewById(R.id.tipoTelefoneSpinner);  // recupera o spinner
                spinnerTipoTelefone.setSelection(intTipoTelefoneArrayList.get(i)); // seta o tipo do spinner.

                i++;
                Log.e("AQUI", "Adicionado telefone: " + telefoneString);
            }
        }

        Log.e("AQUI", "onRestoreInstanceState() Executado");
    }


    public void limparFormulario(View botao){
        notificacoesCheckBox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditText.setText("");
        telefoneEditText.setText("");
        emailEditText.setText("");
        nomeEditText.requestFocus();
    }

    public void adicionarTelefone(View botao){
        if (botao.getId() == R.id.adicionarTelefoneButton){

            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }


    public void adicionarEmail(View botao){
        if (botao.getId() == R.id.adicionarEmailButton){

            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout,null);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }

    //Infla a view e adiciona na tela dinâmicamente
    public  View adicionarEmailDinamicoView(){
        View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout,null);
        emailArrayList.add(novoEmailLayout);
        emailLinearLayout.addView(novoEmailLayout);

        return novoEmailLayout;
    }


    //Infla a view e adiciona na tela dinâmicamente
    public  View adicionarTelefoneDinamicoView(){
        View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout,null);
        telefoneArrayList.add(novoTelefoneLayout);
        telefoneLinearLayout.addView(novoTelefoneLayout);

        return novoTelefoneLayout;
    }



}

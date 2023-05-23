package com.amadei.joao.messagegetter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amadei.joao.messagegetter.data.Result;
import com.amadei.joao.messagegetter.data.datasource.AdviceDataSource;
import com.amadei.joao.messagegetter.data.datasource.model.AdviceModel;
import com.amadei.joao.messagegetter.data.repository.AdviceRepository;

public class MainActivity extends AppCompatActivity {
    private AdviceModel currentAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_get_advice = this.findViewById(R.id.btn_get_advice);

        btn_get_advice.setOnClickListener(v -> {
            getAdvice();
        });
    }

    public void getAdvice(){
        AdviceRepository adviceRepository = AdviceRepository.getInstance(new AdviceDataSource());
        Result<AdviceModel> resultOrFail = adviceRepository.getAdvice();

        if(resultOrFail instanceof Result.Success){
            setCurrentAdvice(((Result.Success<AdviceModel>) resultOrFail).getData());
        }else{
            Log.i("Error getting one advice => ", resultOrFail.toString());
        }

        TextView txvAdviceText = this.findViewById(R.id.txv_advice_text);
        TextView txvAdviceCode = this.findViewById(R.id.txv_advice_code);

        //setar os valores
        txvAdviceCode.setText(this.currentAdvice.getId()+"");
        txvAdviceText.setText(this.currentAdvice.getBody());
    }

    public void setCurrentAdvice(AdviceModel currentAdvice) {
        this.currentAdvice = currentAdvice;
    }
}
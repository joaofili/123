package com.amadei.joao.messagegetter.data.repository;

import com.amadei.joao.messagegetter.data.Result;
import com.amadei.joao.messagegetter.data.datasource.AdviceDataSource;
import com.amadei.joao.messagegetter.data.datasource.model.AdviceModel;

public class AdviceRepository {
    private static volatile AdviceRepository instance;

    private final AdviceDataSource dataSource;

    private AdviceModel currentAdvice;

    public AdviceRepository(AdviceDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static AdviceRepository getInstance(AdviceDataSource dataSource) {
        if(instance == null){
            instance = new AdviceRepository(dataSource);
        }

        return instance;
    }

    public void setAdvice(AdviceModel advice){ this.currentAdvice = advice;}

    public Result<AdviceModel> getAdvice() {
        Result<AdviceModel> result = dataSource.getAdvice();
        if(result instanceof  Result.Success){
            setAdvice(((Result.Success<AdviceModel>) result).getData());
        }
        return result;
    }
}

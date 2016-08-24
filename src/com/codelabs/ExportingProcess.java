package com.codelabs;

/**
 * Created by adammb on 8/16/16.
 */
public class ExportingProcess {
    private Long processNo;
    private Long noObjek;

    public ExportingProcess(Long processNo){
        this.processNo=processNo;
        System.out.println("Objek dengan proses nomor "+processNo+" berhasil dibuat.");
    }

    public Long getProcessNo() {
        return processNo;
    }
}

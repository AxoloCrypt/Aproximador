package com.aproximador.data;

import java.util.ArrayList;
import java.util.List;

public class History
{
    private List<Aproximation> aproximations;

    public History() {aproximations = new ArrayList<>(); }

    public History(List<Aproximation> aproximations){
        this.aproximations = aproximations;
    }

    /*
    @param aproximation
    @return
    Deletes passed aproximation from history.
     */
    public void deleteAproximation(Aproximation aproximation){
        aproximations.remove(aproximation);
    }

    //TODO
    // Implementation needed
    public void exportPDF(){

    }

    public List<Aproximation> getAproximations() {
        return aproximations;
    }

    public void setAproximations(List<Aproximation> aproximations) {
        this.aproximations = aproximations;
    }
}

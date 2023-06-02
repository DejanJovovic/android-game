package com.example.androidquiz;

public class KorakPoKorakHints {

    private String hint1, hint2, hint3, hint4, hint5, hint6, hint7;

    private String konacno1;

    private String userSelectedField;



    public KorakPoKorakHints(String hint1, String hint2, String hint3, String hint4,
                             String hint5, String hint6, String hint7, String konacno1) {
        this.hint1 = hint1;
        this.hint2 = hint2;
        this.hint3 = hint3;
        this.hint4 = hint4;
        this.hint5 = hint5;
        this.hint6 = hint6;
        this.hint7 = hint7;
        this.konacno1 = konacno1;
    }

    public KorakPoKorakHints(String hint1, String hint2, String hint3, String hint4,
                             String hint5, String hint6, String hint7, String konacno1, String userSelectedField) {
        this.hint1 = hint1;
        this.hint2 = hint2;
        this.hint3 = hint3;
        this.hint4 = hint4;
        this.hint5 = hint5;
        this.hint6 = hint6;
        this.hint7 = hint7;
        this.konacno1 = konacno1;
        this.userSelectedField = userSelectedField;
    }


    public String getHint1() {
        return hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public String getHint4() {
        return hint4;
    }

    public String getHint5() {
        return hint5;
    }

    public String getHint6() {
        return hint6;
    }

    public String getHint7() {
        return hint7;
    }

    public String getKonacno1() {
        return konacno1;
    }

    public String getUserSelectedField() {
        return userSelectedField;
    }

    public void setUserSelectedField(String userSelectedField) {
        this.userSelectedField = userSelectedField;
    }
}
